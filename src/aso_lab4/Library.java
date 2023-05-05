package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Library {

    final Writer[] writers; // Массив писателей
    final Reader[] readers; // Массив читателей

    public final int max_books; // Максимальное количество книг в библиотеке
    public final ArrayList<Book> books; // Список книг в библиотеке
    public final Lock writeLock; // Общий lock на запись
    public final Lock readLock; // Общий lock на чтение

    final Semaphore writeSemaphore; // Семафор на запись
    final Semaphore readSemaphore; // Семафор на чтение

    public Library(int _writers, int _readers, int _max_books) {
        writers = new Writer[_writers]; // Создание массива писателей
        readers = new Reader[_readers]; // Создание массива читателей

        max_books = _max_books; // Задание максимального количества книг в библиотеке
        books = new ArrayList<>(); // Создание списка книг в библиотеке

        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true); // Создание общего lock на чтение/запись
        writeLock = rwl.writeLock(); // Получение lock на запись
        readLock = rwl.readLock(); // Получение lock на чтение

        writeSemaphore = new Semaphore(1); // Создание семафора на запись
        readSemaphore = new Semaphore(5); // Создание семафора на чтение, максимум 5 читателей одновременно

        for (int i = 0; i < _writers; i++) // Создание и запуск писателей
            writers[i] = new Writer(this, "Writer " + (i + 1), writeSemaphore);

        for (int i = 0; i < _readers; i++) // Создание и запуск читателей
            readers[i] = new Reader(this, "Reader " + (i + 1), readSemaphore);
    }
}