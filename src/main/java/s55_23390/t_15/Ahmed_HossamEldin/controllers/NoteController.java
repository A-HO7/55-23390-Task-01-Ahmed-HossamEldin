package s55_23390.t_15.Ahmed_HossamEldin.controllers;

import org.springframework.web.bind.annotation.*;
import s55_23390.t_15.Ahmed_HossamEldin.models.Note;
import s55_23390.t_15.Ahmed_HossamEldin.services.NoteService;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @GetMapping("/search")
    public Note searchNotesByTitle(@RequestParam String title) {
        return noteService.searchNotesByTitle(title);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }
}
