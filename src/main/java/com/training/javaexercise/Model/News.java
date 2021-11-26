package com.training.javaexercise.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

// this will mark this class as entity
@Entity
// this will map entity to actual table with name "demo_news"
@Table(name = "demo_news")
// this annotation will auto generate getter and setter also equals and hashcode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "NEWS_SEQUENCE")
    @SequenceGenerator(name = "NEWS_SEQUENCE", allocationSize = 1, sequenceName = "NEWS_SEQUENCE")
    // this will generate sequence
    private Long id;

    // this mark column which can be empty/null
    @Column(nullable = false,unique = true)
    private String title;

    // this mark Large Object which correspond to BLOB type
    @Lob
    @Column(nullable = false)
    @Value("content") // DEFAULT VALUE WITH ANNOTATION
    private String content;

    // we can also add default value here
    @Column(nullable = false)
    private String writtenBy = "guest";
}
