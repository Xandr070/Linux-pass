package com.library.controller;

import com.library.model.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final ConcurrentHashMap<Long, Author> authors = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @GetMapping
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authors.get(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        author.setId(idGenerator.incrementAndGet());
        authors.put(author.getId(), author);
        return author;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        if (!authors.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        author.setId(id);
        authors.put(id, author);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (!authors.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        authors.remove(id);
        return ResponseEntity.ok().build();
    }
} 