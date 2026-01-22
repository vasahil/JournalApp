package com.sahil.JournalApp.service;

import com.sahil.JournalApp.entity.JournalEntry;
import com.sahil.JournalApp.entity.User;
import com.sahil.JournalApp.repository.JournalEntryRepository;
import com.sahil.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;



    @Transactional                                   //Transactional to happen there must be a replication of mongodb.
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntry().add(saved);

            userService.saveEntry(user);
        } catch (Exception e) {

            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry.");
        }

    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String username) {
        User byUserName = userService.findByUserName(username);
        boolean removed = byUserName.getJournalEntry().removeIf(x -> x.getId().equals(id));
        if(removed){
            userService.saveEntry(byUserName);
            journalEntryRepository.deleteById(id);
        }

    }
}



// controller ---> service ---> repository