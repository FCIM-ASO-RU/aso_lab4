package aso_lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static aso_lab4.logger.Log.info;

public class Writer extends Thread {

    private final Library library;
    private final ArrayList<Book> written;
    private final Semaphore writeSemaphore;
    private final File configFile = new File("books.txt");
    private final ArrayList<String> bookNames;

    public Writer(Library _library, String name, Semaphore _writeSemaphore) {
        library = _library;
        written = new ArrayList<>();
        setName(name);
        writeSemaphore = _writeSemaphore;
        bookNames = new ArrayList<>();

        try {
            final Scanner myReader = new Scanner(configFile);
            while (myReader.hasNextLine()) {
                bookNames.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        info("METHOD: {" + new Object() {
        }.getClass().getEnclosingMethod().getName() + "} IS RUNNING");
        while (true) {
            try {
                writeSemaphore.acquire(); // Запрашиваем семафор на запись

                if (written.size() >= library.max_books) { // Проверяем, написал ли писатель все доступные книги
                    System.out.println(getName() + " has finished writing all books!");
                    writeSemaphore.release(); // Освобождаем семафор на запись, чтобы другие писатели могли начать писать
                    break; // Выходим из цикла
                }

                Book book = new Book("Book " + bookNames.get(library.books.size())); // Создаем новую книгу
                library.writeLock.lock(); // Захватываем эксклюзивную блокировку на запись
                library.books.add(book); // Добавляем книгу в библиотеку
                written.add(book); // Добавляем книгу в список написанных книг
                System.out.println(getName() + " wrote a new book: " + book.title);
                library.writeLock.unlock(); // Освобождаем блокировку на запись
                writeSemaphore.release(); // Освобождаем семафор на запись
                Thread.sleep(500); // Ждем некоторое время перед написанием следующей книги
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void write() {
        info("METHOD: {" + new Object() {
        }.getClass().getEnclosingMethod().getName() + "} IS RUNNING");
        library.writeLock.lock();
        try {
            if (library.books.size() < library.max_books) {
                Book book = new Book("Book " + (library.books.size() + 1));
                library.books.add(book);
                written.add(book);
                System.out.println(getName() + " wrote " + book.title);
            } else {
                System.out.println(getName() + " tried to write, but the library is full");
            }
        } finally {
            library.writeLock.unlock();
        }
    }
}