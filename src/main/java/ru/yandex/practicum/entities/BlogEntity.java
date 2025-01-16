package ru.yandex.practicum.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BlogEntity {
    private Long id;
    private Timestamp datetime;
    private String title;
    private String text;
    private String picture;
    private String tags;
    private Integer likeCount;
}
