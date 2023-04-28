package aso_lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.ArrayList;
import java.util.Random;

public class Reader extends Thread {

    final Library library;
    final ArrayList<Book> read;
    private final Random random;

    private static final Logger logger = LogManager.getLogger(Reader.class);

    public Reader(Library _library, String name) {
        library = _library;
        read = new ArrayList<>();
        setName(name);
        random = new Random();
        Configurator.initialize(null, "src/resource/log4j2.xml");
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    //sleep(random.nextInt(50));
                    library.readLock.lock();
                    logger.info("readLock - locked.");
                    ArrayList<Book> currentLib = (ArrayList<Book>) library.getCurrentBooks().clone();
                    if (read.size() != library.getMaxBooks()) {
                        if ( currentLib.size() != 0 ) {
                            for(Book book : read) {
                                currentLib.remove(book);
                            }
                            if (currentLib.size() != 0) {
                                int index = random.nextInt(currentLib.size() * 100);
                                index = index / 100;
                                Book readBook = library.getBook(index);
                                read.add(readBook);
                                //System.out.printf("%s: start to read book - %s\n", getName(), readBook.getTitle());
                                logger.info("start to read book - {}", readBook.getTitle());
                                sleep(1000);
                                //System.out.printf("%s: finish to read book - %s\n", getName(), readBook.getTitle());
                                logger.info("finish to read book - {}", readBook.getTitle());
                                library.putBook(readBook);
                                library.readLock.unlock();
                                logger.info("readLock - unlocked.");
                                sleep(10);
                            } else {
                                //System.out.printf("%s: nothing to read.\n", getName());
                                logger.warn("nothing to read.");
                                library.readLock.unlock();
                                logger.info("readLock - unlocked.");
                                sleep(300);
                            }
                        } else {
                            //System.out.printf("%s: library empty.\n", getName());
                            logger.warn("library empty.");
                            library.readLock.unlock();
                            logger.info("readLock - unlocked.");
                            sleep(300);
                        }
                    } else {
                        //System.out.printf("%s: read all books.\n", getName());
                        logger.info("read all books.");
                        library.readLock.unlock();
                        logger.info("readLock - unlocked.");
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