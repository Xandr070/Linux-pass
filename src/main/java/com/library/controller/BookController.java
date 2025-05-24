package com.library.controller;

import com.library.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @GetMapping
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = books.get(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        book.setId(idGenerator.incrementAndGet());
        books.put(book.getId(), book);
        return book;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        books.put(id, book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        books.remove(id);
        return ResponseEntity.ok().build();
    }
} 