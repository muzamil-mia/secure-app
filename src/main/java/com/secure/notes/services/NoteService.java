package com.secure.notes.services;

import com.secure.notes.model.Note;

import java.util.List;

public interface NoteService {

    Note createNoteForUser(String userName, String content);

    Note updateNote(Long noteId, String content, String userName);

    List<Note> getNotesForUser(String userName);

    void deleteNote(Long noteId, String userName);
}
