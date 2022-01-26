package com.training.javaexercise.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demo_awards")
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "AWARDS_SEQUENCE")
    @SequenceGenerator(name = "AWARDS_SEQUENCE", allocationSize = 1, sequenceName = "AWARDS_SEQUENCE")
    private Long awardsId;

    @Column(nullable = false, length = 32)
    private String awardsName;

    @Column(nullable = false, length = 1)
    private Integer awardsLevel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_authorAwards",
            joinColumns = @JoinColumn(name = "awards_awardsId"),
            inverseJoinColumns = @JoinColumn(name = "authors_authorsId")
    )
    private Collection<Author> authors = new ArrayList<>();
}
