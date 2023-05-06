package aso_lab4.TeamE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RandomBookGenerator {
    public final List<Book> books;

    public RandomBookGenerator() throws IOException {
        books = getListOfBooks();
    }

    private static List<Book> getListOfBooks() throws IOException {
        String filename = "./src/aso_lab4/books.txt";
        List<String> namesOfBooks = readBookNames(filename);
        return createBooks(namesOfBooks);
    }

    private static List<String> readBookNames(String filename) throws IOException {
        List<String> bookNames = new ArrayList<>();

        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            bookNames.add(line);
        }

        scanner.close();

        return bookNames;
    }

    private static List<Book> createBooks(List<String> bookNames) {
        List<Book> books = new ArrayList<>();

        for (String bookName : bookNames) {
            Book book = new Book(bookName);
            books.add(book);
        }

        return books;
    }
}
