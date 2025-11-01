package com.library.backend.DTOs;

import com.library.backend.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String pictureSRC;
    private Genre genre;
    private String username;
}
