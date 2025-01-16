package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogsModel {
    private Long id;
    private String title;
    private String shotText;
    private String picture;
    private String tags;
    private Integer commentsCount;
    private Integer likeCount;
}
