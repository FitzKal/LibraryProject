package com.library.backend.Controller;

import com.library.backend.DTOs.BookRequestDTO;
import com.library.backend.DTOs.BookResponseDTO;
import com.library.backend.Service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(HttpServletRequest request,
                                                      @RequestBody @NonNull BookRequestDTO bookRequestDTO){
        var response = bookService.createBook(request,bookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> listAll(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@NonNull @PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @NonNull @PathVariable Long id,
            @NonNull @RequestBody BookRequestDTO bookRequestDTO,
            HttpServletRequest request){
        var response = bookService.updateBook(request,id,bookRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id,HttpServletRequest request){
        var response = bookService.deleteBook(request,id);
        return ResponseEntity.ok(response);
    }

}
