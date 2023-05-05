package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Writer extends Thread {

    private final Library library;
    private final ArrayList<Book> written;
    private final Semaphore writeSemaphore;
    public Writer(Library _library, String name, Semaphore _writeSemaphore) {
        library = _library;
        written = new ArrayList<>();
        setName(name);
        writeSemaphore = _writeSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                writeSemaphore.acquire(); // Запрашиваем семафор на запись

                if (written.size() >= library.max_books) { // Проверяем, написал ли писатель все доступные книги
                    System.out.println(getName() + " has finished writing all books!");
                    writeSemaphore.release(); // Освобождаем семафор на запись, чтобы другие писатели могли начать писать
                    break; // Выходим из цикла
                }

                Book book = new Book("Book " + (library.books.size() + 1)); // Создаем новую книгу
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