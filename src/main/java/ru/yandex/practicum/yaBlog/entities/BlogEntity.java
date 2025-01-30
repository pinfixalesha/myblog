package ru.yandex.practicum.yaBlog.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class BlogEntity {
    private Long id;
    private Timestamp datetime;
    private String title;
    private String text;
    private String picture;
    private String tags;
    private Integer likeCount;
}
