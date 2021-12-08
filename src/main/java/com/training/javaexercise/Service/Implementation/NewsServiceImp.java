package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Television;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.NewsService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Builder
public class NewsServiceImp implements NewsService {

    private final NewsRepository newsRepository;
    private final RestTemplate restTemplate;

    private Pageable tenNewsPerPage = PageRequest.of(0,10); //NAME COULD BE WRONG

    @Override
    public List<News> getAllNews(Pageable pageable) {
        Page<News> pageNews = newsRepository.findAll(tenNewsPerPage);
        List<News> newsToReturn = pageNews.getContent();
        return newsToReturn;
    }

    @Override
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News getNewsById(Long id) {
        return null;
    }

    @Override
    public News getNewsByTitle(String newsTitle) {
        return newsRepository.findByNewsTitle(newsTitle);
    }

    // THIS METHOD ALREADY DOING SO MUCH BY FIRST FETCHING THE DATA AND THEN COMBINE THEM
    // THIS IS VIOLATING THE SINGLE ROLE METHOD RULES
    // NEED TO BE CHANGED
    @Override
    public Television getTvInfo(Long id) {
        Broadcast broadcast = restTemplate.getForObject("http://FirstClient/api/broadcast/get/"+id,Broadcast.class);
        Channel channel = restTemplate.getForObject("http://SecondClient/api/channel/hello", Channel.class);
        News news = newsRepository.findNewsByNewsId(id);
        Television tv = new Television(news.getNewsTitle(),broadcast.getBroadcastCode(),channel.getName());
        return tv;
    }
}
