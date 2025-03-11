package com.example.springIntro;

import com.example.springIntro.model.entity.Book;
import com.example.springIntro.service.AuthorService;
import com.example.springIntro.service.BookService;
import com.example.springIntro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final BufferedReader bufferedReader;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;


    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.println("Which query you want to run?");
        int exNum = Integer.parseInt(bufferedReader.readLine());
        switch (exNum){
            case 1:
                printAllBookAfter2000(2000);
                break;
            case 2:
                printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
                break;
            case 3:
                printAllAuthorsAndNumberOfTheirBooks();
                break;
            case 4:
                printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");
                break;
            default:
                System.out.println("Invalid query number");
                run();
        }
        checkIfUserWantsToContinue();
    }

    private void checkIfUserWantsToContinue() throws Exception {
        System.out.println("----------");
        System.out.println("Do you want to check another query? Yes/No");
        String answer = bufferedReader.readLine();
        if (answer.toLowerCase(Locale.ROOT).equals("yes")) {
            run();
        } else {
            System.out.println("Bye");
        }
    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService.findAllBooksByAuthorNamesOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService.getAllAuthorsOrderByCountOfTheirBooks()
        .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService.findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBookAfter2000(int year) {
        bookService.findAllBookAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
