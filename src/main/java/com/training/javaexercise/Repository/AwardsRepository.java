package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.Awards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardsRepository extends JpaRepository<Awards, Long> {

    Awards findByAwardsName(String awardsName);
}
