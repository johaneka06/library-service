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

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Book() { }

    public Book(String book_name, String book_desc) {
        Timestamp ts = new Timestamp((new Date()).getTime());

        this.bookId = UUID.randomUUID().toString().replace("-", "");
        this.bookName = book_name;
        this.bookDesc = book_desc;
        this.createdAt = ts;
        this.updatedAt = ts;
    }
}
