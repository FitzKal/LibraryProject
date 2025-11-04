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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final BookDTOConverter bookDTOConverter;

    public BookResponseDTO createBook(HttpServletRequest request, BookRequestDTO bookRequestDTO){
        var user = getUserFromRequest(request);
        var convertedBook = bookDTOConverter.bookFromRequest(bookRequestDTO);
        user.addBook(convertedBook);
        bookRepository.save(convertedBook);
        var convertedToResponse = bookDTOConverter.bookResponseFromRequest(bookRequestDTO);
        convertedToResponse.setUsername(user.getUsername());
        return convertedToResponse;
    }

    public BookResponseDTO getBookById(Long id){
        var book = bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
        var response = bookDTOConverter.BookToResponse(book);
        response.setUsername(book.getUser().getUsername());
        return response;
    }

    public List<BookResponseDTO> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(book -> {
                    var response = bookDTOConverter.BookToResponse(book);
                    response.setUsername(book.getUser().getUsername());
                    return response;
                }).toList();
    }

    public BookResponseDTO updateBook(HttpServletRequest request, Long id, BookRequestDTO bookRequestDTO){
        var bookToUpdate = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        var user = getUserFromRequest(request);
        if (checkOwnerShip(user.getUsername(),bookToUpdate)){
            bookToUpdate.setTitle(bookRequestDTO.getTitle());
            bookToUpdate.setDescription(bookRequestDTO.getDescription());
            bookToUpdate.setPictureSRC(bookRequestDTO.getPictureSRC());
            bookToUpdate.setGenre(bookRequestDTO.getGenre());
            bookRepository.save(bookToUpdate);
            return bookDTOConverter.BookToResponse(bookToUpdate);
        }else {
            throw new RuntimeException("You are not the owner of the book, or don't have permission to edit the book");
        }

    }

    public String deleteBook(HttpServletRequest request, Long id){
        var bookToDelete = bookRepository.findById(id)
                .orElseThrow(()->new RuntimeException("The book cannot be found"));
        var user = getUserFromRequest(request);
        if (checkOwnerShip(user.getUsername(),bookToDelete)){
            user.removeBook(bookToDelete);
            bookRepository.deleteById(id);
            return "The book with the id: "+ id +" has been deleted";
        }else {
            throw new RuntimeException("You are not the owner of the book, or don't have permission to delete the book");
        }

    }

    private User getUserFromRequest(HttpServletRequest request){
        var tokenFromRequest = jwtService.extractTokenFromRequest(request);
        var username = jwtService.getUsernameFromToken(tokenFromRequest);
        return userRepository.findByUsername(username);
    }

    private boolean checkOwnerShip(String username, Book book){
        var user = userRepository.findByUsername(username);
        return (book.getUser().getUsername().equals(username) || user.getRole().equals(Role.ADMIN));
    }
}
