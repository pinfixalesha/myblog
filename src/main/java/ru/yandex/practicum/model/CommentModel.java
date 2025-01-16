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
public class CommentModel {
    private Long id;
    private LocalDateTime datetime;
    private Long blog;
    private String comment;
}
