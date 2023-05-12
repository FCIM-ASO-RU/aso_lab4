package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static aso_lab4.logger.Log.info;

public class Reader extends Thread {

    private final Library library;
    private final ArrayList<Book> read;
    private final Semaphore readSemaphore;

    public Reader(Library _library, String name, Semaphore _readSemaphore) { // Конструктор читателя
        library = _library; // Инициализируем библиотеку, к которой принадлежит читатель
        read = new ArrayList<>(); // Инициализируем список прочитанных книг
        setName(name); // Устанавливаем имя читателя
        readSemaphore = _readSemaphore; // Устанавливаем семафор на чтение
    }
    // переопределение метода run() из класса Thread
    @Override
    public void run() {
        info("METHOD: {" + new Object() {
        }.getClass().getEnclosingMethod().getName() + "} IS RUNNING");
        while (true) {
            try {
                readSemaphore.acquire(); // запрос семафора на чтение
                if (read.size() >= library.books.size()) { // проверяем, прочитал ли читатель все книги в библиотеке
                    System.out.println(getName() + " has read all books!");
                    readSemaphore.release(); // освобождаем семафор на чтение, чтобы другие читатели могли начать читать
                    break; // выходим из цикла
                }
                library.readLock.lock(); // захватываем разделяемую блокировку на чтение
                Book book = library.books.get(read.size()); // получаем книгу, которую будем читать
                read.add(book); // добавляем книгу в список прочитанных книг
                System.out.println(getName() + " is reading " + book.title);
                library.readLock.unlock(); // освобождаем блокировку на чтение
                readSemaphore.release(); // освобождаем семафор на чтение
                Thread.sleep(500); // ждем некоторое время перед чтением следующей книги
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // метод read(), который считывает книгу из библиотеки
    private void read() {
        info("METHOD: {" + new Object() {
        }.getClass().getEnclosingMethod().getName() + "} IS RUNNING");
        library.readLock.lock(); // захватываем блокировку на чтение
        try {
            if (!library.books.isEmpty()) { // проверяем, есть ли книги в библиотеке
                int index = (int) (Math.random() * library.books.size()); // получаем случайный индекс книги
                Book book = library.books.get(index); // получаем книгу из списка книг
                library.books.remove(index); // удаляем книгу из списка книг в библиотеке
                read.add(book); // добавляем книгу в список прочитанных книг
                System.out.println(getName() + " read " + book.title); // выводим сообщение о прочитанной книге
            } else {
                System.out.println(getName() + " tried to read, but the library is empty"); // выводим сообщение, если в библиотеке нет книг
            }
        } finally {
            library.readLock.unlock(); // освобождаем блокировку на чтение
        }
    }
}