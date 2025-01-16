package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogModel {
    private Long id;
    private Timestamp datetime;
    private String title;
    private String clob;
    private String picture;
    private String tags;
}
