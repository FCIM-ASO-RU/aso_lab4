package aso_lab4.TeamE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Writer extends Thread {
    final RandomBookGenerator randomBookGenerator;
    final Random random;

    final Library library;
    final ArrayList<Book> written;
    
    public Writer(Library _library, String name) throws IOException {
        library = _library;
        written = new ArrayList<>();

        randomBookGenerator = new RandomBookGenerator();
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
                    Book newBook = randomBookGenerator.books.get(random.nextInt(50));

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
                System.out.println(getName() + ". Book list: \n" + written);
            }
        }
    }
}