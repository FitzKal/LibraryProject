package com.library.backend.Converters;

import com.library.backend.DTOs.BookRequestDTO;
import com.library.backend.DTOs.BookResponseDTO;
import com.library.backend.models.Book;
import com.library.backend.models.Genre;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class BookDTOConverterTest {

    private final BookDTOConverter underTest = Mappers.getMapper(BookDTOConverter.class);

    @Test
    void bookResponseFromRequest_shouldMapBasicFields_andIgnoreUsername() {
        BookRequestDTO req = BookRequestDTO.builder()
                .title("T")
                .description("D")
                .author("A")
                .pictureSRC("/p.png")
                .genre(Genre.FANTASY)
                .build();

        BookResponseDTO resp = underTest.bookResponseFromRequest(req);

        assertThat(resp.getTitle()).isEqualTo("T");
        assertThat(resp.getDescription()).isEqualTo("D");
        assertThat(resp.getAuthor()).isEqualTo("A");
        assertThat(resp.getPictureSRC()).isEqualTo("/p.png");
        assertThat(resp.getGenre()).isEqualTo(Genre.FANTASY);
        assertThat(resp.getUsername()).isNull(); // ignored by mapping
    }

    @Test
    void bookFromRequest_shouldMapFields_andIgnoreUser() {
        BookRequestDTO req = BookRequestDTO.builder()
                .title("Title")
                .description("Desc")
                .author("Author")
                .pictureSRC("/img.jpg")
                .genre(Genre.SCIFI)
                .build();

        Book entity = underTest.bookFromRequest(req);

        assertThat(entity.getTitle()).isEqualTo("Title");
        assertThat(entity.getDescription()).isEqualTo("Desc");
        assertThat(entity.getAuthor()).isEqualTo("Author");
        assertThat(entity.getPictureSRC()).isEqualTo("/img.jpg");
        assertThat(entity.getGenre()).isEqualTo(Genre.SCIFI);
        assertThat(entity.getUser()).isNull();
    }

    @Test
    void bookToResponse_shouldMapEntityFields_andIgnoreUsername() {
        Book book = Book.builder()
                .id(5L)
                .title("T5")
                .description("D5")
                .author("AU")
                .pictureSRC("/5.png")
                .genre(Genre.ROMANCE)
                .build();

        BookResponseDTO resp = underTest.BookToResponse(book);

        assertThat(resp.getId()).isEqualTo(5L);
        assertThat(resp.getTitle()).isEqualTo("T5");
        assertThat(resp.getDescription()).isEqualTo("D5");
        assertThat(resp.getAuthor()).isEqualTo("AU");
        assertThat(resp.getPictureSRC()).isEqualTo("/5.png");
        assertThat(resp.getGenre()).isEqualTo(Genre.ROMANCE);
        assertThat(resp.getUsername()).isNull();
    }
}
