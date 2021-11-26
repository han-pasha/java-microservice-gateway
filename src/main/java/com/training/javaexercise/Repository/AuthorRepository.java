package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByAuthorName(String authorName);

}
