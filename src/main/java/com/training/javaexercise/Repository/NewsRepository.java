package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository // THIS IS ACTUALLY UNNECESSARY
public interface NewsRepository extends JpaRepository<News, Long> {

    News findByTitle(String title);
    List<News> findAllNewsByTitle(String title, Pageable pageable);
    News findNewsById(Long id);
}
