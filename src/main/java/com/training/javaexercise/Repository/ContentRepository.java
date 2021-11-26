package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Content findByContentName(String contentName);
}
