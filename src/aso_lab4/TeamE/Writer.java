package aso_lab4.TeamE;

import java.util.ArrayList;
import java.util.Random;

public class Writer extends Thread {
    final Random random;

    final Library library;
    final ArrayList<Book> written;
    
    public Writer(Library _library, String name) {
        library = _library;
        written = new ArrayList<>();
        random = new Random();
        setName(name);
    }
    
    @Override
    public void run() {
        while(library.books.size() < library.max_books)
        {
            try
            {
                library.writeLock.lock();

                if (library.books.size() < library.max_books) {
                    Book newBook = new Book("Book " + (random.nextInt(100)));

                    if (!written.contains(newBook)) {
                        Thread.sleep(1000);

                        library.books.add(newBook);
                        written.add(newBook);

                        System.out.println(getName() + " is writing the book named " + newBook.title);

                        Thread.sleep(100);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                library.writeLock.unlock();
            }

            if (library.books.size() == library.max_books) {
                System.out.println("Writer: " + getName() + ". Book list: \n" + written);
            }
        }
    }
}