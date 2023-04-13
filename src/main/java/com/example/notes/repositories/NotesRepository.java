package com.example.notes.repositories;

import com.example.notes.entities.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotesRepository extends CrudRepository<Note, Long> {
    List<Note> findByTitle(String title);
}
