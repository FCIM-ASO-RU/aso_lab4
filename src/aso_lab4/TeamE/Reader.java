package aso_lab4.TeamE;

import java.util.ArrayList;
import java.util.Random;

public class Reader extends Thread {
    final Random random;

    final Library library;
    final ArrayList<Book> read;
    
    public Reader(Library _library, String name) {
        library = _library;
        read = new ArrayList<>();
        random = new Random();
        setName(name);
    }
    
    @Override
    public void run() {
        while(read.size() < library.max_books)
        {
            try
            {
                if (library.rwl.isWriteLocked()) {
                    System.out.println(getName() + ": writer is in library");
                }

                library.readLock.lock();

                int bookIndex = random.nextInt(library.max_books);

                if (bookIndex < library.books.size()) {
                    Book book = library.books.get(bookIndex);

                    if(read.size() < library.books.size()) {
                        if (!read.contains(book)) {
                            Thread.sleep(300);

                            read.add(book);
                            System.out.println(getName() + " is reading book named " + book.title);
                        }
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                library.readLock.unlock();
            }

            if (read.size() == library.max_books) {
                System.out.println("Reader: " + getName() + " have read " + library.max_books + " books.");
            }
        }
    }
}