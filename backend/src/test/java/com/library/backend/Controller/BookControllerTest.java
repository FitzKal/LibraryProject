package com.library.backend.Controller;

import com.library.backend.DTOs.BookRequestDTO;
import com.library.backend.DTOs.BookResponseDTO;
import com.library.backend.Service.BookService;
import com.library.backend.models.Genre;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private HttpServletRequest httpRequest;

    @InjectMocks
    private BookController controller;

    @Test
    void testCreateBookShouldReturnCreatedAndResponseBody() {
        // Given
        var req = BookRequestDTO.builder()
                .title("Clean Code")
                .description("A Handbook of Agile Software Craftsmanship")
                .author("Robert C. Martin")
                .pictureSRC("/img/cleancode.jpg")
                .genre(Genre.THRILLER)
                .build();

        var resp = BookResponseDTO.builder()
                .id(1L)
                .title("Clean Code")
                .description("A Handbook of Agile Software Craftsmanship")
                .author("Robert C. Martin")
                .pictureSRC("/img/cleancode.jpg")
                .genre(Genre.THRILLER)
                .username("alice")
                .build();

        given(bookService.createBook(httpRequest, req)).willReturn(resp);

        // When
        var entity = controller.createBook(httpRequest, req);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isEqualTo(resp);
        verify(bookService).createBook(httpRequest, req);
    }

    @Test
    void testListAllShouldReturnOkAndResponseList() {
        // Given
        var resp1 = BookResponseDTO.builder().id(1L).title("Book 1").genre(Genre.FANTASY).username("u1").build();
        var resp2 = BookResponseDTO.builder().id(2L).title("Book 2").genre(Genre.SCIFI).username("u2").build();
        var list = List.of(resp1, resp2);
        given(bookService.getAllBooks()).willReturn(list);

        // When
        var entity = controller.listAll();

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(list);
        verify(bookService).getAllBooks();
    }

    @Test
    void testGetBookByIdShouldReturnOkAndResponseBody() {
        // Given
        long id = 42L;
        var resp = BookResponseDTO.builder().id(id).title("The Answer").genre(Genre.THRILLER).username("arthur").build();
        given(bookService.getBookById(id)).willReturn(resp);

        // When
        var entity = controller.getBookById(id);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(resp);
        verify(bookService).getBookById(id);
    }

    @Test
    void testUpdateBookShouldReturnOkAndResponseBody() {
        // Given
        long id = 5L;
        var req = BookRequestDTO.builder()
                .title("Refactoring")
                .description("Improving the Design of Existing Code")
                .author("Martin Fowler")
                .pictureSRC("/img/refactoring.jpg")
                .genre(Genre.THRILLER)
                .build();

        var resp = BookResponseDTO.builder().id(id).title("Refactoring").genre(Genre.THRILLER).username("bob").build();
        given(bookService.updateBook(httpRequest, id, req)).willReturn(resp);

        // When
        var entity = controller.updateBook(id, req, httpRequest);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(resp);
        verify(bookService).updateBook(httpRequest, id, req);
    }

    @Test
    void testDeleteBookShouldReturnOkAndMessage() {
        // Given
        long id = 10L;
        var message = "The book with the id: " + id + " has been deleted";
        given(bookService.deleteBook(httpRequest, id)).willReturn(message);

        // When
        var entity = controller.deleteBook(id, httpRequest);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(message);
        verify(bookService).deleteBook(httpRequest, id);
    }

    @Test
    void testUserBooksShouldReturnOkAndResponseList() {
        // Given
        var resp1 = BookResponseDTO.builder().id(7L).title("Mine 1").genre(Genre.FANTASY).username("me").build();
        var resp2 = BookResponseDTO.builder().id(8L).title("Mine 2").genre(Genre.SCIFI).username("me").build();
        var list = List.of(resp1, resp2);
        given(bookService.getUserBooks(httpRequest)).willReturn(list);

        // When
        var entity = controller.userBooks(httpRequest);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(list);
        verify(bookService).getUserBooks(httpRequest);
    }
}