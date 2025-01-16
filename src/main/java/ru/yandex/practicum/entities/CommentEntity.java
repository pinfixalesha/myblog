package ru.yandex.practicum.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentEntity {
    private Long id;
    private LocalDateTime datetime;
    private Long blog;
    private String comment;
}
