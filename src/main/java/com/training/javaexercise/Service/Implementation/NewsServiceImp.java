package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.NewsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImp implements NewsService {

    @Autowired
    private final NewsRepository newsRepository;

    private Pageable tenNewsPerPage = PageRequest.of(0,10); //NAME COULD BE WRONG

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getAllNewsByTitle(String title) {
//        return newsRepository.findAllNewsByTitle(title,tenNewsPerPage);
        return new ArrayList<>();
    }

    @Override
    public News inserNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findNews(Long id) {
        return newsRepository.findNewsByNewsId(id);
    }


}
