package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
    List<Credential> credentials;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;

    public final EncryptionService encryptionService;

    public boolean isTabNotes = true;
    public boolean isTabCredential = false;
    public boolean isTabFile = false;

    public HomeController (
            NoteService noteService,
            UserService userService,
            CredentialService credentialService,
            EncryptionService encryptionService
    ) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String homeView (
            Authentication authentication,
            Note note,
            Model model,
            Credential credential,
            EncryptionService encryptionService
    ) {
        this.setupNotesData( authentication, model );
//        this.setupCredentialsData(authentication, model);
        model.addAttribute( "isTabFile", this.isTabNotes );
        return "home";
    }

    @GetMapping("/credentials")
    public String homeCredentialsView (
            Authentication authentication,
            Credential credential,
            Note note,
            Model model,
            EncryptionService encryptionService
    ) {
        this.setupCredentialsData( authentication, model );

        return "home";
    }

    private void setupNotesData (Authentication authentication, Model model) {
        User user = userService.getUser( authentication.getName() );
        if ( user != null ) {
            this.notes = noteService.getNotes( user.getUserId() );
        } else {
            this.notes = new ArrayList<>();
        }
        model.addAttribute( "notes", notes );
    }

    private void setupCredentialsData (
            Authentication authentication,
            Model model
    ) {
        User user = userService.getUser( authentication.getName() );
        this.credentials = credentialService.getCredentials( user.getUsername() );
        model.addAttribute( "credentials", credentials );
    }

    @PostMapping("/addAndUpdateNote")
    public String addNote (
            @ModelAttribute("note") Note note,
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUser( authentication.getName() );
        if ( note.getNoteid() == null ) {
            Note newNote = new Note( null, note.getNotetile(), note.getNotedescription(), user.getUserId() );
            Integer noteId = noteService.createNote( newNote );
            newNote.setNoteid( noteId );
            notes.add( newNote );

        } else {
            this.noteService.updateNote( note );
        }

        model.addAttribute( "notes", noteService.getNotes( user.getUserId() ) );

        return "redirect:/home";
    }

    @PostMapping("/addOrUpdateCredential")
    public String addOrUpdateCredential (
            @ModelAttribute("credential") Credential credential,
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUser( authentication.getName() );
        credentialService.insertCredential( credential, user.getUsername() );

        return "redirect:/home";
    }

    @GetMapping("/deletenote/{noteid}")
    public String deleteNote (@PathVariable("noteid") Integer noteId) {
        noteService.deleteNote( noteId );
        notes = notes.stream()
                .filter( note1 -> !Objects.equals( note1.getNoteid(), noteId ) )
                .collect( Collectors.toList() );
        return "redirect:/home";
    }

    @DeleteMapping("/deletecredential/{credentialid}")
    public String deleteCredential (@PathVariable("credentialid") Integer credentialid) {
        credentialService.deleteCredential( credentialid );
        return "redirect:/home";
    }
}
