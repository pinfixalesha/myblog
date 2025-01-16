package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogModel {
    private Long id;
    private LocalDateTime datetime;
    private String title;
    private String clob;
    private String picture;
    private String tags;
}
