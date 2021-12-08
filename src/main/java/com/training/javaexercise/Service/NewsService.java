package com.training.javaexercise.Service;

import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Television;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface NewsService {

    List<News> getAllNews(Pageable pageable);
    News createNews(News news);
    News getNewsById(Long id);
    News getNewsByTitle(String newsTitle);
    Television getTvInfo(Long id);


}
