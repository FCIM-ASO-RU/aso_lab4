package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Library {
    
    final Writer[] writers;
    final Reader[] readers;
    
    public final int max_books;
    public final ArrayList<Book> books;
    public final Lock writeLock;
    public final Lock readLock;
    
    public Library(int _writers, int _readers, int _max_books) {
        writers = new Writer[_writers];
        readers = new Reader[_readers];
        
        max_books = _max_books;
        books = new ArrayList<>();
        
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
        writeLock = rwl.writeLock();
        readLock = rwl.readLock();
        
        for(int i = 0; i < _writers; i++)
            writers[i] = new Writer(this, "Writer "+(i+1));
    }
    
}