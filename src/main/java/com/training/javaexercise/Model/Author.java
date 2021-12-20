package com.training.javaexercise.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;

// this will mark this class as entity
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demo_author")
public class Author implements Externalizable {

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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(authorId);
        out.writeUTF(authorName);
        out.writeObject(authorNews);
        out.writeObject(authorAwards);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.authorId = in.readLong();
        this.authorName = in.readUTF();
        this.authorNews = (Collection<News>) in.readObject();
        this.authorAwards = (Collection<Awards>) in.readObject();
    }


}
