package ru.yandex.practicum.yaBlog.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentEntity {
    private Long id;
    private Timestamp datetime;
    private Long blog;
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Сравнение ссылок
        if (o == null || getClass() != o.getClass()) return false; // Проверка на null и тип

        CommentEntity that = (CommentEntity) o;

        // Если id не null, сравниваем по id (уникальному полю)
        if (id != null && that.id != null) {
            return id.equals(that.id);
        }

        // Если id null, сравниваем остальные поля
        return (Objects.equals(datetime, that.datetime))  &&
                (Objects.equals(comment, that.comment))  &&
                (Objects.equals(blog, that.blog));
    }

}
