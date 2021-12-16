package com.training.javaexercise;

import com.training.javaexercise.Model.Author;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.AuthorRepository;
import com.training.javaexercise.Repository.NewsRepository;
import com.training.javaexercise.Service.AuthorService;
import com.training.javaexercise.Service.Implementation.AuthorServiceImpl;
import com.training.javaexercise.Service.Implementation.NewsServiceImp;
import com.training.javaexercise.Service.NewsService;
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
public class Service_Test {

    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthorService authorService;

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
}
