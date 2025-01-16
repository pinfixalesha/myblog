package ru.yandex.practicum.repository;
import ru.yandex.practicum.model.BlogModel;
import java.util.List;

public interface BlogRepository {
    List<BlogModel> findPage(Integer number, Integer size);

}
