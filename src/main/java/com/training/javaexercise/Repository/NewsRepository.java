package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//@Repository // THIS IS ACTUALLY UNNECESSARY
public interface NewsRepository extends JpaRepository<News, Long> {

    News findByNewsTitle(String title);
//    List<News> findAllNewsByNewsTitle(String title, Pageable pageable);
    News findNewsByNewsId(Long id);

}
