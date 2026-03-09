package s55_23390.t_15.Ahmed_HossamEldin.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import s55_23390.t_15.Ahmed_HossamEldin.models.Note;
import s55_23390.t_15.Ahmed_HossamEldin.repositories.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public List<Note> getNotesByUserId(String userId) {
        List<Note> notes = noteRepository.findByUserId(userId);
        if (notes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No notes found for this user");
        }
        return notes;
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(String id, Note note) {
        return noteRepository.update(id, note)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public Note searchNotesByTitle(String title) {
        return noteRepository.findAll().stream()
                .filter(note -> note.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public void deleteNote(String id) {
        boolean deleted = noteRepository.deleteById(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }
    }
}
