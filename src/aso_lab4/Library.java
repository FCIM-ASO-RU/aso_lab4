package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Library {
    
    final Writer[] writers;
    final Reader[] readers;
    
    private final int max_books;
    private final ArrayList<Book> currentBooks;
    private final ArrayList<Book> addedBooks;
    public final Lock writeLock;
    public final Lock readLock;
    
    public Library(int _writers, int _readers, int _max_books) {
        writers = new Writer[_writers];
        readers = new Reader[_readers];
        
        max_books = _max_books;
        currentBooks = new ArrayList<>();
        addedBooks = new ArrayList<>();
        
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
        writeLock = rwl.writeLock();
        readLock = rwl.readLock();
        
        for(int i = 0; i < _writers; i++) {
            writers[i] = new Writer(this, "Writer "+(i+1));
            writers[i].start();
        }
        for(int i = 0; i < _readers; i++) {
            readers[i] = new Reader(this, "Reader "+(i+1));
            readers[i].start();
        }
    }
    public synchronized void addBook(Book _book){
        currentBooks.add(_book);
        addedBooks.add(_book);
        System.out.printf("Library current books: %s \n", getCurrentBooksString());
    }
    public synchronized Book getBook(int _index){
        try {
            return currentBooks.get(_index);
        } finally {
            currentBooks.remove(_index);
            System.out.printf("Library current books: %s \n", getCurrentBooksString());
        }
    }
    public synchronized void putBook(Book _book){
        currentBooks.add(_book);
        System.out.printf("Library current books: %s \n", getCurrentBooksString());
    }
    public synchronized String getCurrentBooksString() {
        String currentTitles = "";
        for(Book book : currentBooks) {
            currentTitles = currentTitles + " " + book.getTitle();
        }
        return currentTitles;
    }
    public synchronized ArrayList<Book> getCurrentBooks(){ return currentBooks; }
    public synchronized ArrayList<Book> getAddedBooks(){ return addedBooks; }
    public synchronized int getMaxBooks(){ return max_books; }
}