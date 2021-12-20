package com.training.javaexercise.Service.Implementation;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Television;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "television")
//@Builder
public class NewsServiceImp implements NewsService {

    @Autowired
    private final NewsRepository newsRepository;
    @Autowired
    private BroadcastInfoImpl broadcastInfo;
    @Autowired
    private ChannelInfoImpl channelInfo;

    // CACHE
    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
    Map<String, News> televisions = hazelcastInstance.getMap("televisions");

    // CONSTANT
    private final Pageable tenNewsPerPage = PageRequest.of(0,10); //NAME COULD BE WRONG

    @Override
//    @Cacheable
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
    @Cacheable()
    public News getNewsById(Long id) {
        if(televisions.containsKey(id.toString())) {
            return televisions.get(id.toString());
        } else {
            News news = newsRepository.findNewsByNewsId(id);
            televisions.put(id.toString(), news);
            return news;
        }
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
}
