package com.example.library.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

 /*
    Project     : Library Service
    Class       : Book.java
    Author      : Johan Eka Santosa
    Created On  : Sat, 05-06-2021

    Version History:
        v1.0.0 => Initial Work (Sat, 05-06-2021 by Johan Eka Santosa)
 */

@Entity
@Data
@ToString
@EqualsAndHashCode
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private String bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_desc")
    private String bookDesc;

    //New Properties - 20210606
    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "book_img")
    private String bookImg;
    //End new properties - 20210606

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Book() { }

    public Book(String bookName, String bookDesc) { //Change from snake case to camel case - 20210606
        Timestamp ts = new Timestamp((new Date()).getTime());

        this.bookId = UUID.randomUUID().toString().replace("-", "");
        this.bookName = bookName; //Change from book_name to bookName - 20210606
        this.bookDesc = bookDesc; //Change from book_desc to bookDesc - 20210606
        this.createdAt = ts;
        this.updatedAt = ts;
    }

    //New Constructor - 20210606
    public Book(String bookName, String bookDesc, Integer publishedYear, String bookIsbn, String bookImg) {
        Timestamp ts = new Timestamp((new Date()).getTime());

        this.bookId = UUID.randomUUID().toString().replace("-", "");
        this.bookName = bookName;
        this.bookDesc = bookDesc;
        this.bookIsbn = bookIsbn;
        this.publishedYear = publishedYear;
        this.bookImg = bookImg;
        this.createdAt = ts;
        this.updatedAt = ts;
    }
}
