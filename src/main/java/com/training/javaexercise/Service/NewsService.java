package com.training.javaexercise.Service;

import com.training.javaexercise.Model.News;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface NewsService {

    List<News> getAllNews();
    List<News> getAllNewsByTitle(String title);
    News inserNews(News news);
    News findNews(Long id);

}
