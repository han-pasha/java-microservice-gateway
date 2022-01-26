package com.training.javaexercise.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


@Entity
@Table(name = "demo_news")
@Getter // USING GETTER AND SETTER BECAUSE @Data is causing toString error in the test.
@Setter //
@AllArgsConstructor
@NoArgsConstructor
public class News implements Externalizable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "NEWS_SEQUENCE")
    @SequenceGenerator(name = "NEWS_SEQUENCE", allocationSize = 1, sequenceName = "NEWS_SEQUENCE")
    private Long newsId;

    @Column(nullable = false,unique = true)
    private String newsTitle;

    @OneToOne
    private Content newsContent;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "author_authorId")
    private Author newsAuthor;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(newsId);
        out.writeUTF(newsTitle);
        out.writeObject(newsContent);
        out.writeObject(newsAuthor);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.newsId = in.readLong();
        this.newsTitle = in.readUTF();
        this.newsContent = (Content) in.readObject();
        this.newsAuthor = (Author) in.readObject();
    }


    /* DOCS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "NEWS_SEQUENCE")
    @SequenceGenerator(name = "NEWS_SEQUENCE", allocationSize = 1, sequenceName = "NEWS_SEQUENCE")
    // this will generate sequence increment for each save
    private Long newsId;

    // this mark column which can't be empty/null
    @Column(nullable = false,unique = true)
    private String title;

    // this mark Large Object which correspond to BLOB type
    // @Lob
    @Column(nullable = false)
    @Value("content") // DEFAULT VALUE WITH ANNOTATION
    private String content;

    // we can also add default value here
    @Column(nullable = false)
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "news_id")
    private Author author;
    */
}
