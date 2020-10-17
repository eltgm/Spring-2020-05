package ru.otus.newsbot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class NewsDto {
    private String text;
    private MultipartFile[] photos;
}
