package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    Content findByContentName(String contentName);
}
