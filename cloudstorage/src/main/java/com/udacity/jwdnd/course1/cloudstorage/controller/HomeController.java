package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    List<File> Files;
    List<Note> notes;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Note note, Model model) {
        this.setupNotesData(authentication, model);


        return "home";
    }

    private void setupNotesData(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        if (user != null) {
            this.notes = noteService.getNotes(user.getUserId());
        } else {
            this.notes = new ArrayList<>();
        }
        model.addAttribute("notes", notes);
    }

    @PostMapping("/addAndUpdateNote")
    public String addNote(
            @ModelAttribute("note") Note note,
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUser(authentication.getName());
        if (note.getNoteid() == null) {
            Note newNote = new Note(null, note.getNotetile(), note.getNotedescription(), user.getUserId());
            Integer noteId = noteService.createNote(newNote);
            newNote.setNoteid(noteId);
            notes.add(newNote);

        } else {
            this.noteService.updateNote(note);
        }

        model.addAttribute("notes", noteService.getNotes(user.getUserId()));

        return "redirect:/home";
    }

    @GetMapping("/deletenote/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteId) {
        noteService.deleteNote(noteId);
        notes = notes.stream().filter(note1 -> !Objects.equals(note1.getNoteid(), noteId)).collect(Collectors.toList());
        return "redirect:/home";
    }
}
