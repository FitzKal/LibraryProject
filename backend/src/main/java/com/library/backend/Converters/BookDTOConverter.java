package com.library.backend.Converters;

import com.library.backend.DTOs.BookRequestDTO;
import com.library.backend.DTOs.BookResponseDTO;
import com.library.backend.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookDTOConverter {
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "id",ignore = true)
    BookResponseDTO bookResponseFromRequest(BookRequestDTO bookRequestDTO);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id",ignore = true)
    Book bookFromRequest(BookRequestDTO bookRequestDTO);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "id",ignore = true)
    BookResponseDTO BookToResponse(Book book);

}
