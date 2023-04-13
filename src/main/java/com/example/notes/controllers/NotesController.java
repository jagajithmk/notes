package com.example.notes.controllers;

import com.example.notes.entities.Note;
import com.example.notes.exceptions.NoteIdMismatchException;
import com.example.notes.exceptions.NoteNotFoundException;
import com.example.notes.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    NotesRepository notesRepository;

    @GetMapping
    public Iterable findAll() {
        return notesRepository.findAll();
    }

    @GetMapping("/title/{noteTitle}")
    public List findByTitle(@PathVariable String noteTitle) {
        return notesRepository.findByTitle(noteTitle);
    }

    @GetMapping("/{id}")
    public Note findOne(@PathVariable Long id) {
        return notesRepository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@RequestBody Note note) {
        return notesRepository.save(note);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notesRepository.findById(id).orElseThrow(NoteNotFoundException::new);
        notesRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Note updateNote(@RequestBody Note note, @PathVariable Long id) {
        if (note.getId() != id) {
            throw new NoteIdMismatchException();
        }
        notesRepository.findById(id).orElseThrow(NoteNotFoundException::new);
        return notesRepository.save(note);
    }
}
