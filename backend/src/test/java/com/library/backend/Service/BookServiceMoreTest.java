package com.library.backend.Service;

import com.library.backend.Converters.BookDTOConverter;
import com.library.backend.DTOs.BookRequestDTO;
import com.library.backend.DTOs.BookResponseDTO;
import com.library.backend.models.Book;
import com.library.backend.models.Role;
import com.library.backend.models.User;
import com.library.backend.repositories.BookRepository;
import com.library.backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMoreTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private JWTService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookDTOConverter bookDTOConverter;

    @Mock
    private HttpServletRequest req;

    @InjectMocks
    private BookService underTest;

    @Test
    void createBook_shouldSaveAndReturnResponseWithUsername() {
        // Given
        given(jwtService.extractTokenFromRequest(req)).willReturn("tok");
        given(jwtService.getUsernameFromToken("tok")).willReturn("alice");
        var alice = User.builder().userId(1L).username("alice").role(Role.USER).books(new java.util.ArrayList<>()).build();
        given(userRepository.findByUsername("alice")).willReturn(alice);

        var requestDTO = BookRequestDTO.builder().title("T").author("A").build();
        var bookEntity = Book.builder().id(100L).build();
        given(bookDTOConverter.bookFromRequest(requestDTO)).willReturn(bookEntity);
        given(bookRepository.save(bookEntity)).willReturn(bookEntity);

        var responseFromRequest = BookResponseDTO.builder().title("T").author("A").build();
        given(bookDTOConverter.bookResponseFromRequest(requestDTO)).willReturn(responseFromRequest);

        // When
        var result = underTest.createBook(req, requestDTO);

        // Then
        assertThat(result.getUsername()).isEqualTo("alice");
        assertThat(result.getTitle()).isEqualTo("T");
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void getBookById_shouldReturnMappedResponseWithUsername() {
        // Given
        var u = User.builder().username("bob").build();
        var book = Book.builder().id(5L).user(u).build();
        given(bookRepository.findById(5L)).willReturn(Optional.of(book));

        var mapped = new BookResponseDTO();
        given(bookDTOConverter.BookToResponse(book)).willReturn(mapped);

        // When
        var result = underTest.getBookById(5L);

        // Then
        assertThat(result.getUsername()).isEqualTo("bob");
        verify(bookRepository).findById(5L);
        verify(bookDTOConverter).BookToResponse(book);
    }

    @Test
    void getBookById_shouldThrowWhenNotFound() {
        given(bookRepository.findById(404L)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> underTest.getBookById(404L));
    }

    @Test
    void getUserBooks_shouldFilterByCurrentUser() {
        // Given current user: alice
        given(jwtService.extractTokenFromRequest(req)).willReturn("t");
        given(jwtService.getUsernameFromToken("t")).willReturn("alice");
        var alice = User.builder().username("alice").build();
        given(userRepository.findByUsername("alice")).willReturn(alice);

        var bob = User.builder().username("bob").build();
        var b1 = Book.builder().id(1L).user(alice).build();
        var b2 = Book.builder().id(2L).user(bob).build();
        given(bookRepository.findAll()).willReturn(List.of(b1, b2));

        given(bookDTOConverter.BookToResponse(b1)).willReturn(new BookResponseDTO());
        given(bookDTOConverter.BookToResponse(b2)).willReturn(new BookResponseDTO());

        // When
        var list = underTest.getUserBooks(req);

        // Then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getUsername()).isEqualTo("alice");
    }

}
