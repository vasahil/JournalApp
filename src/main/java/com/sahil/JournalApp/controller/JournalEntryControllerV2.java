package com.sahil.JournalApp.controller;

//it is a component that contains all classes/methods for http request...

import com.sahil.JournalApp.entity.JournalEntry;
import com.sahil.JournalApp.entity.User;
import com.sahil.JournalApp.service.JournalEntryService;
import com.sahil.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

//    @GetMapping
//    public List<JournalEntry> getAll() {
//        return journalEntryService.getAll();
//    }

    @Autowired
    private UserService userservice;

//    @GetMapping("/{username}")
//    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
//        User byUserName = userservice.findByUserName(username);
//        List<JournalEntry> journalEntry = byUserName.getJournalEntry();
//        if(journalEntry != null && !journalEntry.isEmpty()){
//            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User byUserName = userservice.findByUserName(username);
        List<JournalEntry> journalEntry = byUserName.getJournalEntry();
        if(journalEntry != null && !journalEntry.isEmpty()){
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userservice.findByUserName(username);
        List<JournalEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry =  journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.deleteById(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userservice.findByUserName(username);
        List<JournalEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent(): old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }

        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


// ? -> generic type