package ru.yandex.practicum.yaBlog.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class BlogsEntity {
    private Long id;
    private Timestamp datetime;
    private String title;
    private String text;
    private String picture;
    private String tags;
    private Integer likeCount;
    private Integer commentCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Сравнение ссылок
        if (o == null || getClass() != o.getClass()) return false; // Проверка на null и тип

        BlogsEntity that = (BlogsEntity) o;

        // Если id не null, сравниваем по id
        if (id != null && that.id != null) {
            return id.equals(that.id);
        }

        // Если id null, сравниваем остальные поля
        return (Objects.equals(datetime, that.datetime)) &&
                (Objects.equals(title, that.title)) &&
                (Objects.equals(text, that.text)) &&
                (Objects.equals(picture, that.picture)) &&
                (Objects.equals(tags, that.tags)) &&
                (Objects.equals(likeCount, that.likeCount)) &&
                (Objects.equals(commentCount, that.commentCount));
    }
}
