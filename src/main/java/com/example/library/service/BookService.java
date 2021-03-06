package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

 /*
    Project     : Library Service
    Class       : BookService.java
    Author      : Johan Eka Santosa
    Created On  : Sat, 05-06-2021

    Version History:
        v1.0.0 => Initial Work (Sat, 05-06-2021 by Johan Eka Santosa)
 */

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/books")
public class BookService {

    @Autowired
    private BookRepository bookInterface;

    private final String ServiceName = "BookService";

    // Method for handling get all books
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity getAllBooks() {
        ResponseEntity entity = null;

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");

        System.out.println("[" + ServiceName + "] START METHOD getAllBooks");

        System.out.println("[" + ServiceName + " - getAllBooks] Invoking DB");
        System.out.println("[" + ServiceName + " - getAllBooks] Looking for all books on DB");
        List<Book> books = bookInterface.findAll();

        System.out.println("[" + ServiceName + " - getAllBooks] Found " + books.size() + " on DB");

        if(books.size() == 0) {
            HashMap<String, String> hsOutput = new HashMap<>();

            hsOutput.put("error_key", "ER-00-204");
            hsOutput.put("error_msg", "No Book in Database");

            entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);

        } else {
            entity = ResponseEntity.status(HttpStatus.OK).body(books);
        }

        System.out.println("[" + ServiceName + " - getAllBooks] Returned with message: " + entity);
        System.out.println("=== FINISH METHOD [ getAllBooks ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");

        return entity;
    }

    // Method for handling find book by name
    @GetMapping("/find")
    public ResponseEntity getBooksByName(@RequestParam String bookName) {
        ResponseEntity entity = null;

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");

        System.out.println("[" + ServiceName + "] START METHOD getBooksByName");

        System.out.println("[" + ServiceName + " - getBooksByName] Input: " + bookName);

        System.out.println("[" + ServiceName + " - getBooksByName] Replacing '-' in bookName with ' ' (Space)");
        bookName = bookName.replace("-", " ");
        System.out.println("[" + ServiceName + " - getBooksByName] New Input: " + bookName);

        System.out.println("[" + ServiceName + " - getBooksByName] Invoking DB");
        System.out.println("[" + ServiceName + " - getBooksByName] Looking for books with name ["+ bookName +"] on DB");

        List<Book> books = bookInterface.findBooksByBookNameContaining(bookName);

        if(books.size() == 0) {
            System.out.println("[" + ServiceName + " - getBooksByName] No books found");

            HashMap<String, String> hsOutput = new HashMap<>();

            hsOutput.put("error_key", "ER-00-204");
            hsOutput.put("error_msg", "No Book in Database");

            entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);

        } else {
            System.out.println("[" + ServiceName + " - getBooksByName] DB Output: " + books);
            entity = ResponseEntity.status(HttpStatus.OK).body(books);
        }

        System.out.println("[" + ServiceName + " - getBooksByName] Returned with message: " + entity);
        System.out.println("=== FINISH METHOD [ getBooksByName ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");

        return entity;
    }

    // Method for handling get book detail by ID
    @GetMapping("/{bookId}/detail")
    public ResponseEntity getBookDetail(@PathVariable String bookId) {
        ResponseEntity entity = null;

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");

        System.out.println("[" + ServiceName + "] START METHOD getBookDetail");

        System.out.println("[" + ServiceName + " - getBookDetail] Input: " + bookId);
        if(bookId == null || bookId.equals("") || bookId.equals("null")) {
            System.out.println("[" + ServiceName + " - getBookDetail] Book ID is null. Terminating service");

            HashMap<String, String> hsOutput = new HashMap<>();
            hsOutput.put("error_key", "ER-00-400");
            hsOutput.put("error_msg", "ID can't be null");

            entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);
        } else {
            System.out.println("[" + ServiceName + " - getBookDetail] Invoking DB");
            System.out.println("[" + ServiceName + " - getBookDetail] Looking for book with id: [" + bookId + "] on DB");

            Optional<Book> res = bookInterface.findById(bookId);

            if(res.isEmpty()) {
                System.out.println("[" + ServiceName + " - getBookDetail] No Book found with ID [" + bookId + "] on DB");

                HashMap<String, String> hsOutput = new HashMap<>();
                hsOutput.put("error_key", "ER-00-204");
                hsOutput.put("error_msg", "ID not found on Database");

                entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);
            } else {
                System.out.println("[" + ServiceName + " - getBookDetail] DB Output: " + res);

                entity = ResponseEntity.status(HttpStatus.OK).body(res);
            }
        }

        System.out.println("[" + ServiceName + " - getBookDetail] Returned with message: " + entity);
        System.out.println("=== FINISH METHOD [ getBookDetail ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");

        return entity;
    }

    // Method for handling create new resource (book)
    @PostMapping("/create")
    public ResponseEntity createNewBook(@RequestBody Book book) {
        ResponseEntity entity = null;

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");

        System.out.println("[" + ServiceName + "] START METHOD createNewBook");

        System.out.println("[" + ServiceName + " - createNewBook] Input: " + book.toString());

        System.out.println("[" + ServiceName + " - createNewBook] Invoking DB");
        System.out.println("[" + ServiceName + " - createNewBook] Looking for book with name: [" + book.getBookName() + "] on DB");

        Book res = bookInterface.findBookByBookName(book.getBookName());

        if(res != null) {
            System.out.println("[" + ServiceName + " - createNewBook] Book found");

            HashMap<String, String> hsOutput = new HashMap<>();

            hsOutput.put("error_key", "ER-00-400");
            hsOutput.put("error_msg", "Book already exist!");

            entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);
        } else {
            System.out.println("[" + ServiceName + " - createNewBook] Saving book to DB");

            Book temp = new Book(book.getBookName(), book.getBookDesc(), book.getPublishedYear(), book.getBookIsbn(), book.getBookImg());

            bookInterface.save(temp);

            temp = bookInterface.findBookByBookName(book.getBookName());

            entity = ResponseEntity.status(HttpStatus.OK).body(temp);

            System.out.println("[" + ServiceName + " - createNewBook] Output from DB: " + book.toString());
        }

        System.out.println("[" + ServiceName + " - createNewBook] Returned with message: " + entity);
        System.out.println("=== FINISH METHOD [ createNewBook ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");

        return entity;
    }

    // Method for handling update current resource

    // Method for handling delete resource
    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity deleteResource(@PathVariable String bookId) {
        ResponseEntity entity = null;
        HashMap<Object, Object> hsOutput = new HashMap<>();

        System.out.println("=== START SERVICE [" + ServiceName + "] ===");

        System.out.println("[" + ServiceName + "] START METHOD deleteResource");

        System.out.println("[" + ServiceName + " - deleteResource] Input: " + bookId);

        if(bookId == null || bookId.equals("") || bookId.equals("null")) {
            System.out.println("[" + ServiceName + " - deleteResource] Book ID is null. Terminating service");

            hsOutput.put("error_key", "ER-00-400");
            hsOutput.put("error_msg", "ID can't be null");

            entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);
        } else {
            System.out.println("[" + ServiceName + " - createNewBook] Invoking DB");
            System.out.println("[" + ServiceName + " - createNewBook] Looking for book with id: [" + bookId + "] on DB");
            Optional<Book> res = bookInterface.findById(bookId);

            if(res.isEmpty()) {
                System.out.println("[" + ServiceName + " - getBookDetail] No Book found with ID [" + bookId + "] on DB");

                hsOutput.put("error_key", "ER-00-204");
                hsOutput.put("error_msg", "Book not found");

                entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hsOutput);
            } else {
                System.out.println("[" + ServiceName + " - getBookDetail] DB Output: " + res);

                bookInterface.delete(res.get());

                hsOutput.put("error_key", "ERR-00-000");
                hsOutput.put("error_msg", "Object deleted");
                hsOutput.put("deleted_obj", res.get());

                entity = ResponseEntity.status(HttpStatus.OK).body(hsOutput);
            }
        }

        System.out.println("[" + ServiceName + " - createNewBook] Returned with message: " + entity);
        System.out.println("=== FINISH METHOD [ createNewBook ] ===");
        System.out.println("=== FINISH SERVICE [" + ServiceName + "] ===");

        return entity;
    }
}
