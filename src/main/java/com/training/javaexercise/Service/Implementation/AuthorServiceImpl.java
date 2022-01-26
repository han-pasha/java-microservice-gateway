package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.Author;
import com.training.javaexercise.Repository.AuthorRepository;
import com.training.javaexercise.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        log.info("Author {} Saved", author.getAuthorName());
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorByName(String authorName) {
        return authorRepository.findByAuthorName(authorName);
    }

    @Override
    public void deleteAuthor(String authorName) {
        Author author = authorRepository.findByAuthorName(authorName);
        if (author != null) {
            log.info("Author {} Deleted", authorName);
            authorRepository.delete(author);
        } else {
            log.error("Author {} is NULL", authorName);
            throw new EntityNotFoundException();
        }
    }
}
