package aso_lab4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Writer extends Thread {

    final Library library;
    final ArrayList<Book> written;
    private final ArrayList<String> titles;
    private final Random random;

    public Writer(Library _library, String name) {
        library = _library;
        written = new ArrayList<>();
        setName(name);
        random = new Random();
        titles = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    sleep(random.nextInt(50));
                    library.writeLock.lock();
                    String filePath = "src/resource/books.csv";
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            titles.add(line);
                        }
                    }
                    //System.out.println(getName() + ": titles - " + getCurrentBooksString());
                    ArrayList<Book> addedLib = library.getAddedBooks();
                    if (addedLib.size() != library.getMaxBooks()) {
                        for(Book book : addedLib) {
                            titles.remove(book.getTitle());
                        }
                        int tempInt = random.nextInt(titles.size() * 100);
                        tempInt = tempInt / 100;
                        String title = titles.get(tempInt);
                        Book newBook = new Book(title);
                        System.out.printf("%s: write book - %s\n", getName(), title);
                        library.addBook(newBook);
                        library.writeLock.unlock();
                    } else {
                        library.writeLock.unlock();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}