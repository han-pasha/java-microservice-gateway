package com.training.javaexercise.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

// this will mark this class as entity
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demo_author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AUTHOR_SEQUENCE")
    @SequenceGenerator(name = "AUTHOR_SEQUENCE", allocationSize = 1, sequenceName = "AUTHOR_SEQUENCE")
    private Long authorId;

    @Column(unique = true, length = 32, nullable = false)
    private String authorName;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    // Same name and referenced name seems stupid.
    @JoinColumn(name = "author_authorId")
    private Collection<News> authorNews = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    private Collection<Awards> authorAwards = new ArrayList<>();
}
