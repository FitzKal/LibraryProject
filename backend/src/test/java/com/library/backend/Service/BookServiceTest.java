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

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookDTOConverter bookDTOConverter;

    @Mock
    private JWTService jwtService;

    @Mock
    private HttpServletRequest req;

    @InjectMocks
    private BookService underTest;

    @Test
    public void testUpdateBookShouldCheckOwnershipAndSave() {
        // Given
        Long id = 11L;

        given(jwtService.extractTokenFromRequest(req)).willReturn("t");
        given(jwtService.getUsernameFromToken("t")).willReturn("owner");

        var owner = new User();
        owner.setUsername("owner");
        owner.setRole(Role.USER);
        given(userRepository.findByUsername("owner")).willReturn(owner);

        var existing = new Book();
        existing.setId(id);
        existing.setUser(owner);
        given(bookRepository.findById(id)).willReturn(Optional.of(existing));

        var updateReq = new BookRequestDTO();
        updateReq.setTitle("New Title");

        var updatedResp = new BookResponseDTO();
        updatedResp.setTitle("New Title");

        // In many services, you map incoming changes onto the existing entity
        given(bookRepository.save(existing)).willReturn(existing);
        given(bookDTOConverter.BookToResponse(existing)).willReturn(updatedResp);

        // When
        var result = underTest.updateBook(req, id, updateReq);

        // Then
        assertThat(result.getTitle()).isEqualTo("New Title");
        verify(bookRepository).findById(id);
        verify(bookRepository).save(existing);
    }

    @Test
    public void testUpdateBookShouldThrowWhenNotFound() {
        // Given
        Long id = 999L;

        given(bookRepository.findById(id)).willReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class,
                () -> underTest.updateBook(req, id, new BookRequestDTO()));
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
    }

    @Test
    public void testDeleteBookShouldCheckOwnershipAndDelete() {
        // Given
        given(jwtService.extractTokenFromRequest(req)).willReturn("t");
        given(jwtService.getUsernameFromToken("t")).willReturn("owner");
        var owner = User.builder()
                .userId(1L)
                .username("owner")
                .books(new ArrayList<>())
                .build();
        var book = Book.builder().id(1L).user(owner).build();
        owner.addBook(book);
        given(userRepository.findByUsername("owner")).willReturn(owner);
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));

        // When
        underTest.deleteBook(req, 1L);

        // Then
        verify(bookRepository).deleteById(1L);
    }

    @Test
    public void testDeleteBookShouldAllowAdmin() {
        // Given
        given(jwtService.extractTokenFromRequest(req)).willReturn("t");
        given(jwtService.getUsernameFromToken("t")).willReturn("owner");
        var owner = User.builder()
                .userId(1L)
                .username("owner")
                .role(Role.ADMIN)
                .books(new ArrayList<>())
                .build();
        var anotherUser = User.builder().username("mock").build();
        var book = Book.builder().id(1L).user(anotherUser).build();
        given(userRepository.findByUsername("owner")).willReturn(owner);
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));

        // When
        underTest.deleteBook(req, 1L);

        // Then
        verify(bookRepository).deleteById(1L);
    }

    @Test
    public void testUpdateBookShouldThrowWhenNotOwnerOrAdmin() {
        // Given
        Long id = 2L;

        given(jwtService.extractTokenFromRequest(req)).willReturn("t");
        given(jwtService.getUsernameFromToken("t")).willReturn("intruder");

        var intruder = new User();
        intruder.setUsername("intruder");
        intruder.setRole(Role.USER);
        given(userRepository.findByUsername("intruder")).willReturn(intruder);

        var owner = new User();
        owner.setUsername("owner");
        var book = new Book();
        book.setId(id);
        book.setUser(owner);
        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        // When / Then
        assertThrows(RuntimeException.class,
                () -> underTest.updateBook(req, id, new BookRequestDTO()));
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
    }
}
