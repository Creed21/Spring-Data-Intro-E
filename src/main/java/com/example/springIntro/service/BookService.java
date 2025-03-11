package com.example.springIntro.service;

import com.example.springIntro.model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> findAllBookAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorNamesOrderByReleaseDate(String firstName,String lastName);

}
