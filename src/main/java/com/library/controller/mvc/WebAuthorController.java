package com.library.controller.mvc;

import com.library.config.DataLoader;
import com.library.model.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/web/authors")
public class WebAuthorController {
    private final DataLoader dataLoader;

    public WebAuthorController(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authorList = new ArrayList<>(dataLoader.getAuthors().values());
        model.addAttribute("authors", authorList);
        return "authors/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author) {
        Long newId = dataLoader.getAuthors().size() + 1L;
        author.setId(newId);
        dataLoader.getAuthors().put(newId, author);
        return "redirect:/web/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = dataLoader.getAuthors().get(id);
        if (author != null) {
            model.addAttribute("author", author);
            return "authors/edit";
        }
        return "redirect:/web/authors";
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        author.setId(id);
        dataLoader.getAuthors().put(id, author);
        return "redirect:/web/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        dataLoader.getAuthors().remove(id);
        return "redirect:/web/authors";
    }
} 