package com.example.springIntro.service.impl;

import com.example.springIntro.model.entity.Author;
import com.example.springIntro.repository.AuthorRepository;
import com.example.springIntro.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private static final String AUTHORS_PATH = "src/main/resources/files/authors.txt";

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(AUTHORS_PATH))
                .stream()
                .filter(line -> !line.isEmpty())
                .forEach(authorName -> {
                    String[] tokens = authorName.split("\\s+");
                    String firstName = tokens[0];
                    String lastName = tokens[1];
                    Author author = new Author(firstName,lastName);

                    authorRepository.save(author);
                });

    }

    @Override
    public Author getRandomAuthor() {

        long randomId = ThreadLocalRandom.current().nextLong(1, authorRepository.count() + 1);
        return authorRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
        return authorRepository.findAllByBooksSizeDesc()
                .stream()
                .map(author -> String.format("%s %s %s",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());

    }
}
