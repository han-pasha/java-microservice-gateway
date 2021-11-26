package com.training.javaexercise.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demo_content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "CONTENT_SEQUENCE")
    @SequenceGenerator(name = "CONTENT_SEQUENCE", allocationSize = 1, sequenceName = "CONTENT_SEQUENCE")
    private Long contentId;

    @Column(nullable = false)
    private String contentName;

    @Column(nullable = false)
    @Lob // FOR BIG FILE
    private String contentArticle;

    @OneToOne
    private News contentNews;

    @Column
    private Date contentCreatedTime;

}
