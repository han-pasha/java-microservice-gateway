package com.training.javaexercise;

import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.Implementation.NewsServiceImp;
import com.training.javaexercise.Service.NewsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class Service_Test {

    @Autowired
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;

    @Test
    public void testCorrectImplementation() {
        Assertions.assertInstanceOf(NewsServiceImp.class, newsService);
    }

    @Test
    public void testLoadUserByUsername() {
        Mockito.when(newsRepository.findByNewsTitle("Clean Code"))
                .thenReturn(newsService.createNews(new News(null,
                        "Clean Code",
                        null,
                        null)));

        News news = newsService.getNewsByTitle("Clean Code");
        Assertions.assertNotNull(news);
        Assertions.assertEquals(news.getNewsTitle(), "Clean Code");
    }
}
