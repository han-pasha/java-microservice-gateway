package com.training.javaexercise;

import com.training.javaexercise.Model.Author;
import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.AuthorRepository;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.AuthorService;
import com.training.javaexercise.Service.Implementation.AuthorServiceImpl;
import com.training.javaexercise.Service.Implementation.BroadcastInfoImpl;
import com.training.javaexercise.Service.Implementation.ChannelInfoImpl;
import com.training.javaexercise.Service.Implementation.NewsServiceImp;
import com.training.javaexercise.Service.NewsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
@AllArgsConstructor
public class Service_Test {

    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ChannelInfoImpl channelService;
    @Autowired
    private BroadcastInfoImpl broadcastService;

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void testCorrectImplementation() {
        Assertions.assertSame(NewsServiceImp.class, newsService);
    }

    @Test
    public void testLoadNews() {
        Mockito.when(newsRepository.findByNewsTitle("Clean Code"))
                .thenReturn(newsService.createNews(new News(null,
                        "Clean Code",
                        null,
                        null)));

        News news = newsService.getNewsByTitle("Clean Code");
        Assertions.assertNotNull(news);
        Assertions.assertEquals(news.getNewsTitle(), "Clean Code");
    }

    @Test
    public void testLoadAuthor() {
        Mockito.when(authorRepository.findByAuthorName("Han"))
                .thenReturn(authorService.createAuthor(new Author(null,
                        "Han",
                        new ArrayList<>(),
                        new ArrayList<>())));

        Author author = authorService.getAuthorByName("Han");
        Assertions.assertNotNull(author);
        Assertions.assertEquals(author.getAuthorName(),"Han");
    }

    @Test
    public void testGettingChannel_FromChannelClient() {
        /*
         * The problem right now is, even though the link is right,
         * the service would always fail and used the fallback instead.
         */
        Mockito.when(channelService.getChannel())
                .thenReturn(channelService.getFallbackChannel());
        Channel channel = channelService.getChannel();
        Assertions.assertNotNull(channel);
        Assertions.assertNotEquals(channel.getChannelNumber(), 404);
    }

    @Test
    public void testGettingBroadcast_FromBroadcastClient() {
        /*
         * The problem right now is, even though the link is right,
         * the service would always fail and used the fallback instead.
         */
        Mockito.when(broadcastService.getBroadcast(1L))
                .thenReturn(broadcastService.getFallbackBroadcast(1L));
        Broadcast broadcast = broadcastService.getBroadcast(1L);
        Assertions.assertNotNull(broadcast);
        Assertions.assertNotEquals(broadcast.getBroadcastCode(), "404");
    }

}
