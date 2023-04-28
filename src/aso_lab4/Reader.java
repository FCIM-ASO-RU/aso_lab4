package aso_lab4;

import java.util.ArrayList;
import java.util.Random;

public class Reader extends Thread {

    final Library library;
    final ArrayList<Book> read;
    private final Random random;

    public Reader(Library _library, String name) {
        library = _library;
        read = new ArrayList<>();
        setName(name);
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    //sleep(random.nextInt(50));
                    library.readLock.lock();
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
                                System.out.printf("%s: start to read book - %s\n", getName(), readBook.getTitle());
                                sleep(1000);
                                System.out.printf("%s: finish to read book - %s\n", getName(), readBook.getTitle());
                                library.putBook(readBook);
                                library.readLock.unlock();
                                sleep(10);
                            } else {
                                System.out.printf("%s: nothing to read.\n", getName());
                                library.readLock.unlock();
                                sleep(300);
                            }
                        } else {
                            System.out.printf("%s: library empty.\n", getName());
                            library.readLock.unlock();
                            sleep(300);
                        }
                    } else {
                        System.out.printf("%s: read all books.\n", getName());
                        library.readLock.unlock();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}