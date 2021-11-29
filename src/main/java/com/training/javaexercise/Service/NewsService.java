package com.training.javaexercise.Service;

import com.training.javaexercise.Model.News;
import lombok.Builder;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface NewsService {

    List<News> getAllNews(Pageable pageable);
    News createNews(News news);
    News getNewsById(Long id);
    News getNewsByTitle(String newsTitle);

}
