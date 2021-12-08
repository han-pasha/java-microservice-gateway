package com.training.javaexercise.Service;

import com.training.javaexercise.Model.Author;

public interface AuthorService {

    Author createAuthor(Author author);
    Author getAuthorByName(String authorName);
    void deleteAuthor(String authorName);

}
