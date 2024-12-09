package com.secure.notes.controller;

import com.secure.notes.model.Note;
import com.secure.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    private NoteService noteService;

    @PostMapping
    public Note createNote(@RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        System.out.println("USER DETAILS: " + userName);
        return noteService.createNoteForUser(userName, content);
    }

    @GetMapping
    public List<Note> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        System.out.println("USER DETAILS: " + userName);
        return noteService.getNotesForUser(userName);
    }

    @PutMapping("/{noteId}")
    public Note updateNote(@PathVariable Long noteId, @RequestBody String content, @AuthenticationPrincipal UserDetails userDetails){
        String userName = userDetails.getUsername();
        //noteService.updateNote(noteId, content, userName);
        return noteService.updateNote(noteId, content, userName);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId, @AuthenticationPrincipal UserDetails userDetails){
        String userName = userDetails.getUsername();
        noteService.deleteNote(noteId, userName);
    }
}
