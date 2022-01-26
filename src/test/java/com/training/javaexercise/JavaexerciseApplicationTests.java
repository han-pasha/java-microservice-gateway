package com.training.javaexercise;

import com.training.javaexercise.Model.News;
import com.training.javaexercise.Repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;

import static org.junit.Assert.assertTrue;

@SpringBootTest
//@RunWith(SpringRunner.class) // THIS COULD DO
class JavaexerciseApplicationTests {

//	private final NewsRepository newsRepository; // Dont know but, final is necessery here
//
//	@BeforeAll
//	public void populate() {
//		this.newsRepository.save(new News(null,
//				"Clean allCode",
//				"All About Code",
//				"me?"));
//	}
//
//	@BeforeEach
//	public void populateDb() {
//		newsRepository.save(new News(null,
//				"Clean Code",
//				"All About Code",
//				"me?"));
//	}
//
//	@Test
//	public void testFindNews() {
////		News news = newsRepository.findNewsById(0L);
////		Assertions.assertEquals(news.getTitle(), "Clean Code");
//		System.out.println("This seems good");

    @Test
    public void testing() {
        final String[] result = {""};
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> result[0] = s);

        assertTrue(result[0].equals("Hello"));
    }

    @Test
    public void testingRxJava() {
        final String[] result = {""};
        String[] letters = {"a", "b", "c"};
        Observable.from(letters)
                .scan(new StringBuilder(), StringBuilder::append)
                .subscribe(total -> result[0] += total.toString());
        assertTrue(result[0].equals("aababc"));
    }
//	}

}
