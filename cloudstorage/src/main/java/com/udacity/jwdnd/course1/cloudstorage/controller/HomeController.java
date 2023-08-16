package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public final FileService fileService;

    public boolean isTabNote = true;
    public boolean isTabCredential = false;
    public boolean isTabFile = false;
    public boolean isShowAlert = false;
    public String alertText = "";

    public HomeController(
            NoteService noteService,
            UserService userService,
            CredentialService credentialService,
            EncryptionService encryptionService,
            FileService fileService
    ) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String homeView(
            Note note,
            Model model,
            Credential credential,
            EncryptionService encryptionService
    ) {

        return "redirect:home/files";
    }

    @GetMapping("/files")
    public String homeFilesView(
            Authentication authentication,
            Note note,
            Model model,
            Credential credential,
            File file
    ) {
        model.addAttribute("isTabCredential", false);
        model.addAttribute("isTabNote", false);
        model.addAttribute("isTabFile", true);

        User user = userService.getUser(authentication.getName());
        List<File> files = fileService.getFiles(user.getUserId());

        model.addAttribute("files", files);

        model.addAttribute("isShowAlert", isShowAlert);
        model.addAttribute("textAlert", alertText);

        return "home";
    }

    @GetMapping("/notes")
    public String homeNotesView(
            Authentication authentication,
            Note note,
            Model model,
            Credential credential
    ) {
        this.setupNotesData(authentication, model);
        model.addAttribute("isTabCredential", false);
        model.addAttribute("isTabNote", true);
        model.addAttribute("isTabFile", false);
        OffAlert(model);
        return "home";
    }

    @GetMapping("/credentials")
    public String homeCredentialsView(
            Authentication authentication,
            Credential credential,
            Note note,
            Model model,
            EncryptionService encryptionService
    ) {
        this.setupCredentialsData(authentication, model);
        model.addAttribute("isTabCredential", true);
        model.addAttribute("isTabNote", false);
        model.addAttribute("isTabFile", false);
        OffAlert(model);
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

    private void setupCredentialsData(
            Authentication authentication,
            Model model
    ) {
        User user = userService.getUser(authentication.getName());
        this.credentials = credentialService.getCredentials(user.getUsername());
        model.addAttribute("credentials", credentials);
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

        return "redirect:/home/notes";
    }

    @PostMapping("/addOrUpdateCredential")
    public String addOrUpdateCredential(
            @ModelAttribute("credential") Credential credential,
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUser(authentication.getName());
        if (credential.getCredentialid() == null) {
            credentialService.insertCredential(credential, user.getUsername());
        } else {
            credentialService.updateCredential(credential);
        }

        return "redirect:/home/credentials";
    }

    @DeleteMapping("/deletenote/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteId) {
        noteService.deleteNote(noteId);
        notes = notes.stream()
                .filter(note1 -> !Objects.equals(note1.getNoteid(), noteId))
                .collect(Collectors.toList());
        return "redirect:/home/notes";
    }

    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("filename") String filename
    ) {
       return this.fileService.downloadFile(filename);
    }

    @DeleteMapping("/deletecredential/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer credentialid) {
        credentialService.deleteCredential(credentialid);
        return "redirect:/home/credentials";
    }

    @DeleteMapping("/deletefile/{fileid}")
    public String deletefile(@PathVariable("fileid") Integer fileid) {
        fileService.deleteFile(fileid);
        return "redirect:/home/files";
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile file,
            Authentication authentication,
            Model model
    ) throws IOException {
        User user = userService.getUser(authentication.getName());

        if (file.isEmpty()) {
            return "redirect:/home";
        }

        Long size = file.getSize();
        String contenttype = file.getContentType();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileService.isFileExists(fileName)) {
            alertText = "Duplicate file name";
            isShowAlert = true;
            return "redirect:/home";
        } else {
            OffAlert(model);
        }

        File newFile = new File(null, fileName, contenttype, size.toString(), user.getUserId(), file.getBytes());

        fileService.insertFile(newFile);

        return "redirect:/home";
    }

    private void OnAlert(Model model) {
        model.addAttribute("isShowAlert", isShowAlert);
        model.addAttribute("textAlert", alertText);
    }

    private void OffAlert(Model model) {
        model.addAttribute("isShowAlert", false);
        model.addAttribute("textAlert", "");
    }
}
