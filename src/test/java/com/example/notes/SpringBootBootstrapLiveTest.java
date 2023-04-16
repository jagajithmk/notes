package com.example.notes;

import com.example.notes.entities.Note;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT
            = "http://localhost:8080/api/notes";

    private Note createRandomNote() {
        Note note = new Note();
        note.setTitle(randomAlphabetic(10));
        note.setDetails(randomAlphabetic(15));
        return note;
    }

    private String createNoteAsUri(Note note) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(note)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllNotes_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetNotesByTitle_thenOK() {
        Note note = createRandomNote();
        createNoteAsUri(note);
        Response response = RestAssured.get(
                API_ROOT + "/title/" + note.getTitle());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }
    @Test
    public void whenGetCreatedNoteById_thenOK() {
        Note Note = createRandomNote();
        String location = createNoteAsUri(Note);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(Note.getTitle(), response.jsonPath()
                .get("title"));
    }

    @Test
    public void whenGetNotExistNoteById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewNote_thenCreated() {
        Note Note = createRandomNote();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Note)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidNote_thenError() {
        Note Note = createRandomNote();
        Note.setDetails(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Note)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedNote_thenUpdated() {
        Note Note = createRandomNote();
        String location = createNoteAsUri(Note);
        Note.setId(Long.parseLong(location.split("api/notes/")[1]));
        Note.setDetails("new detail");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Note)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("new detail", response.jsonPath()
                .get("details"));
    }

    @Test
    public void whenDeleteCreatedNote_thenOk() {
        Note Note = createRandomNote();
        String location = createNoteAsUri(Note);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
    
}

