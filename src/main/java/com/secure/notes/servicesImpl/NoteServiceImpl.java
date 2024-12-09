package com.secure.notes.servicesImpl;

import com.secure.notes.model.Note;
import com.secure.notes.repository.NoteRepository;
import com.secure.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createNoteForUser(String userName, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUserName(userName);
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    @Override
    public Note updateNote(Long noteId, String content, String userName) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setContent(content);
        Note updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @Override
    public List<Note> getNotesForUser(String userName) {
        List<Note> personalNotes = noteRepository.findByOwnerUserName(userName);
        return personalNotes;
    }

    @Override
    public void deleteNote(Long noteId, String userName) {
        noteRepository.deleteById(noteId);
    }
}
