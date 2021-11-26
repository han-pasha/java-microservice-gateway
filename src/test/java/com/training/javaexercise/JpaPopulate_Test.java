package com.training.javaexercise;

import com.training.javaexercise.Model.Author;
import com.training.javaexercise.Model.Awards;
import com.training.javaexercise.Model.Content;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.AuthorRepository;
import com.training.javaexercise.Repository.AwardsRepository;
import com.training.javaexercise.Repository.ContentRepository;
import com.training.javaexercise.Repository.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
 * To make this run, actually doesn't need these 2.
 * @DataJpaTest
 * @RunWith(SpringRunner.class)
 */
public class JpaPopulate_Test {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AwardsRepository awardsRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ContentRepository contentRepository;

    @BeforeAll
    public void populate() {
        // POPULATE AUTHOR
        this.authorRepository.save(new Author(
                null,
                "AuthorName1",
                new ArrayList<>(),  // THIS IS EMPETY NEWS
                new ArrayList<>()   // THIS IS EMPETY AWARDS
        ));
        this.authorRepository.save(new Author(
                null,
                "AuthorName2",
                new ArrayList<>(),  // THIS IS EMPETY NEWS
                new ArrayList<>()   // THIS IS EMPETY AWARDS
        ));
        this.authorRepository.save(new Author(
                null,
                "AuthorName3",
                new ArrayList<>(),  // THIS IS EMPETY NEWS
                new ArrayList<>()   // THIS IS EMPETY AWARDS
        ));
        // POPULATE AWARDS
        this.awardsRepository.save(new Awards(
                null,
                "Best Authors",
                9,
                new ArrayList<>()
        ));
        this.awardsRepository.save(new Awards(
                null,
                "Junior Authors",
                2,
                new ArrayList<>()
        ));
        // POPULATE CONTENT
        this.contentRepository.save(new Content(
                null,
                "Content 1",
                "Ini Contoh isi article etc...etc...",
                null,
                new Date()

        ));
        this.contentRepository.save(new Content(
                null,
                "Content 2",
                "Ini Contoh isi article kedua bingung mau buat apa etc...etc...",
                null,
                new Date()

        ));

        // THIS IS TEDIOUS
        // POPULATE NEWS
        Content content1 = contentRepository.findByContentName("Content 1");
        Content content2 = contentRepository.findByContentName("Content 2");
        Author author1 = authorRepository.findByAuthorName("AuthorName1");
        Author author2 = authorRepository.findByAuthorName("AuthorName2");

        this.newsRepository.save(new News(
                null,
                "Clean all Code",
                content1,
                author1));

        this.newsRepository.save(new News(
                null,
                "Clean Code",
                content2,
                author2));

        System.out.println("IF THIS GETS EXECUTED, THERE IS NO PROBLEM");
    }

    @Test
    void testFetchingAuthor1() throws Exception {
        News news = newsRepository.findByNewsTitle("Clean all Code");
        System.out.println(news.getNewsAuthor());
        Assertions.assertEquals(news.getNewsAuthor().getAuthorName(), "AuthorName1");
    }

    @Test
    void testFetchingAuthor2() throws Exception {
        News news = newsRepository.findByNewsTitle("Clean Code");
        System.out.println(news.getNewsAuthor().getAuthorName());
        Assertions.assertNotEquals(news.getNewsAuthor().getAuthorName(), "AuthorName1");
    }

    @Test
    void testFetchingContent() throws Exception {
        News news = newsRepository.findByNewsTitle("Clean Code");
        Assertions.assertNotEquals(news.getNewsTitle(), "Content 1");
    }
}
