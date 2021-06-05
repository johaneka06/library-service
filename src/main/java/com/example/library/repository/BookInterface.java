package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInterface extends JpaRepository<Book, String> {
    Book findBookByBookName(String book_name);
}
