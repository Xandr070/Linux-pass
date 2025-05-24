package com.library.controller.mvc;

import com.library.config.DataLoader;
import com.library.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/web/books")
public class WebBookController {
    private final DataLoader dataLoader;

    public WebBookController(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> bookList = new ArrayList<>(dataLoader.getBooks().values());
        model.addAttribute("books", bookList);
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", dataLoader.getAuthors().values());
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        Long newId = dataLoader.getBooks().size() + 1L;
        book.setId(newId);
        dataLoader.getBooks().put(newId, book);
        return "redirect:/web/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = dataLoader.getBooks().get(id);
        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("authors", dataLoader.getAuthors().values());
            return "books/edit";
        }
        return "redirect:/web/books";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        book.setId(id);
        dataLoader.getBooks().put(id, book);
        return "redirect:/web/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        dataLoader.getBooks().remove(id);
        return "redirect:/web/books";
    }
} 