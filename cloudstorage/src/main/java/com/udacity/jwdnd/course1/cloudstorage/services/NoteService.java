package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(Integer userId) {
        return this.noteMapper.getNotes(userId);
    }

    public Integer deleteNote(Integer noteId) {
        return this.noteMapper.deleteNote(noteId);
    }

    public Note updateNote(Note note) {
        return this.noteMapper.update(note);
    }

    public Integer createNote(Note note) {
        return this.noteMapper.insertNote(note);
    }
}
