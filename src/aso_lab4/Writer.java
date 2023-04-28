package aso_lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Writer extends Thread {

    final Library library;
    final ArrayList<Book> written;
    private final ArrayList<String> titles;
    private final Random random;

    private static final Logger logger = LogManager.getLogger(Writer.class);
    public Writer(Library _library, String name) {
        library = _library;
        written = new ArrayList<>();
        setName(name);
        random = new Random();
        titles = new ArrayList<>();
        Configurator.initialize(null, "src/resource/log4j2.xml");
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    sleep(random.nextInt(50));
                    library.writeLock.lock();
                    logger.info("writeLock - locked.");
                    String filePath = "src/resource/books.csv";
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            titles.add(line);
                        }
                    }
                    logger.info("file: books.csv - read.");
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
                        //System.out.printf("%s: write book - %s\n", getName(), title);
                        logger.info("write book - {}", title);
                        library.addBook(newBook);
                        library.writeLock.unlock();
                        logger.info("writeLock - unlocked.");
                    } else {
                        library.writeLock.unlock();
                        logger.info("writeLock - unlocked.");
                        logger.info("loop - break.");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("catch exception.");
        }
    }
}