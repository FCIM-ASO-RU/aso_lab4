package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Library {
    // Для консант как правило при наименовании используется CASE_SNAKE
    public static int countOfBooks;
    private Writer[] writers;
    private Reader[] readers;
    static ArrayList<String> listOfBooks = new ArrayList<>();
    static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
    static final Lock writeLock = rwl.writeLock();
    static final Lock readLock = rwl.readLock();


    //Создаем указаное количество писателей и читателей а так же сколько книг здесь хранится
    public Library(int writers, int readers, int countOfBooks) {
        //this.writers -> this показывает что в контексте нужно использовать переменную класса а не параметр класса
        this.writers = new Writer[writers];
        this.readers = new Reader[readers];
        //Статическая переменная -> обращение через имя класса
        Library.countOfBooks = countOfBooks;

        for (int i = 0; i < writers; i++) {
            this.writers[i] = new Writer("Writer " + (i + 1));
        }
        for (int i = 0; i < readers; i++) {
            this.readers[i] = new Reader("Reader " + (i + 1));
        }
    }

    public void start() {
        for (Reader reader : readers) {
            reader.start();
        }
        for (Writer writer : writers) {
            writer.start();
        }

    }

}