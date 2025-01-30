package ru.yandex.practicum.yaBlog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogModel {
    private Long id;
    private String title;
    private String text;
    private List<String> textByParagraph;
    private String picture;
    private String tags;
    private Integer likeCount;
}
