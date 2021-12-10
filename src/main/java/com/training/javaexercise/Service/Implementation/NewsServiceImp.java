package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Television;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Builder
public class NewsServiceImp implements NewsService {

    private final NewsRepository newsRepository;
    @Autowired
    private BroadcastInfoImpl broadcastInfo;
    @Autowired
    private ChannelInfoImpl channelInfo;

    private final Pageable tenNewsPerPage = PageRequest.of(0,10); //NAME COULD BE WRONG

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
        return newsRepository.findNewsByNewsId(id);
    }

    @Override
    public News getNewsByTitle(String newsTitle) {
        return newsRepository.findByNewsTitle(newsTitle);
    }

    // THIS METHOD ALREADY DOING SO MUCH BY FIRST FETCHING THE DATA AND THEN COMBINE THEM
    // THIS IS VIOLATING THE SINGLE ROLE METHOD RULES
    // NEED TO BE CHANGED
    // ALREADY CHANGED ==================================================================!>
    @Override
    public Television getTvInfo(Long id) {
        Broadcast broadcast = broadcastInfo.getBroadcast(id);
        Channel channel = channelInfo.getChannel();
        News news = getNewsById(id);
        return new Television(news.getNewsTitle(),broadcast.getBroadcastCode(),channel.getName());
    }

    // ALL FALLBACK METHOD WOULD BE UNDER THIS COMMENT
//    public News getFallbackNews() {
//        return new News(null, "Unknown News", null, null);
//    }
//
//    public Television getFallbackTelevision(Long id) {
//        return new Television("No News", "No Broadcast", "No Channel");
//    }

}
