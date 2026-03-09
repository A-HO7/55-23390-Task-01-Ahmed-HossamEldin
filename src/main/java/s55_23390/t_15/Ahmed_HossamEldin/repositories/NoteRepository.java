package s55_23390.t_15.Ahmed_HossamEldin.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import s55_23390.t_15.Ahmed_HossamEldin.models.Note;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class NoteRepository {

    private List<Note> notes;
    private java.io.File jsonFile;

    public NoteRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/notes.json");
        if (inputStream == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to read notes.json");
        }
        try {
            this.jsonFile = new java.io.File(getClass().getResource("/notes.json").toURI());
        } catch (Exception e) {
            this.jsonFile = new java.io.File("/data/notes.json");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.notes = objectMapper.readValue(inputStream, new TypeReference<List<Note>>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error parsing notes.json", e);
        }
    }

    public List<Note> findAll() {
        return notes;
    }

    public Optional<Note> findById(String id) {
        return notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();
    }

    public List<Note> findByUserId(String userId) {
        return notes.stream()
                .filter(note -> note.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Optional<Note> findByTitle(String title) {
        return notes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst();
    }

    public Note save(Note note) {
        note.setId(java.util.UUID.randomUUID().toString());
        notes.add(note);
        try {
            new ObjectMapper().writeValue(jsonFile, notes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write to notes.json", e);
        }
        return note;
    }

    public Optional<Note> update(String id, Note updated) {
        Optional<Note> existingNoteOpt = findById(id);
        if (existingNoteOpt.isPresent()) {
            Note existingNote = existingNoteOpt.get();
            existingNote.setTitle(updated.getTitle());
            existingNote.setContent(updated.getContent());
            existingNote.setUserId(updated.getUserId());
            try {
                new ObjectMapper().writeValue(jsonFile, notes);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write to notes.json", e);
            }
            return Optional.of(existingNote);
        }
        return Optional.empty();
    }

    public boolean deleteById(String id) {
        boolean removed = notes.removeIf(note -> note.getId().equals(id));
        if (removed) {
            try {
                new ObjectMapper().writeValue(jsonFile, notes);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write to notes.json", e);
            }
        }
        return removed;
    }

}
