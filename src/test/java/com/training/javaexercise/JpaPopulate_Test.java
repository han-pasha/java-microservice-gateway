package com.training.javaexercise;

import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
 * To make this run, actually doesn't need these 2.
 * @DataJpaTest
 * @RunWith(SpringRunner.class)
 */
public class JpaPopulate_Test {

    @Autowired
    private NewsRepository newsRepository;

    @BeforeAll
    public void populate() {
        this.newsRepository.save(new News(null,
                "Clean allCode",
                "All About Code",
                "me?"));
    }

    @Test
    void testFetching() throws Exception {
        System.out.println("Fetching broder");
        News news = newsRepository.findByTitle("Clean allCode");
		Assertions.assertEquals(news.getTitle(), "Clean Code");
    }
}
