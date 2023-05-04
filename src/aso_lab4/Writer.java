package aso_lab4;

import java.util.*;

public class Writer extends Thread {
    final Random random;

    final String name;

    private final Library library;
    private final List<Book> written;
    private final List<String> authors;
    public Writer(Library _library, String name) {
        library = _library;
        written = new ArrayList<>();
        random = new Random();
        authors = new ArrayList<>();
        authors.add("William Shakespeare");
        authors.add("Jane Austen");
        authors.add("Leo Tolstoy");
        authors.add("Emily Bronte");
        authors.add("Ernest Hemingway");
        authors.add("F. Scott Fitzgerald");
        authors.add("Virginia Woolf");
        authors.add("James Joyce");
        authors.add("Gabriel Garcia Marquez");
        authors.add("Haruki Murakami");
        this.name = name;
        setName(name +" "+authors.get(random.nextInt(10)));
    }

    @Override
    public void run() {

        while(library.books.size() < library.max_books) {
            try {
                library.writeLock.lock();

                if (library.books.size() < library.max_books) {
                    final Book newBook = new Book("Book " + (random.nextInt(100)),random.nextInt(100));

                    if (!written.contains(newBook)) {
                        Thread.sleep(1000);

                        library.books.add(newBook);
                        written.add(newBook);

                        System.out.println(getName() + " is writing the book named " + newBook.title + " that contains " + newBook.pages + " pages");

                        Thread.sleep(100);
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                library.writeLock.unlock();
            }
            if (library.books.size() == library.max_books) {
                System.out.println("Writer: " + getName() + ". Book list: \n" + written);
            }
        }
    }
}