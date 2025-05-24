package com.library.config;

import com.library.model.Author;
import com.library.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DataLoader implements CommandLineRunner {

    private final ConcurrentHashMap<Long, Author> authors = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong authorIdGenerator = new AtomicLong();
    private final AtomicLong bookIdGenerator = new AtomicLong();

    @Override
    public void run(String... args) {
        Author tolstoy = createAuthor("Лев Толстой", 1828, "Россия");
        Author dostoevsky = createAuthor("Фёдор Достоевский", 1821, "Россия");
        Author pushkin = createAuthor("Александр Пушкин", 1799, "Россия");

        createBook("Война и мир", tolstoy.getId(), "Роман", 1869);
        createBook("Анна Каренина", tolstoy.getId(), "Роман", 1877);
        createBook("Преступление и наказание", dostoevsky.getId(), "Роман", 1866);
        createBook("Братья Карамазовы", dostoevsky.getId(), "Роман", 1880);
        createBook("Евгений Онегин", pushkin.getId(), "Поэма", 1833);
        createBook("Капитанская дочка", pushkin.getId(), "Повесть", 1836);
    }

    private Author createAuthor(String name, Integer birthYear, String country) {
        Author author = new Author();
        author.setId(authorIdGenerator.incrementAndGet());
        author.setName(name);
        author.setBirthYear(birthYear);
        author.setCountry(country);
        authors.put(author.getId(), author);
        return author;
    }

    private Book createBook(String title, Long authorId, String genre, Integer publishYear) {
        Book book = new Book();
        book.setId(bookIdGenerator.incrementAndGet());
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setGenre(genre);
        book.setPublishYear(publishYear);
        books.put(book.getId(), book);
        return book;
    }

    public ConcurrentHashMap<Long, Author> getAuthors() {
        return authors;
    }

    public ConcurrentHashMap<Long, Book> getBooks() {
        return books;
    }
} 