package ru.yandex.practicum.yaBlog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogEditModel {
        private Long id;
        private String title;
        private String text;
        private MultipartFile picture;
        private String tags;
}
