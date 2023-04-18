package aso_lab4;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class Reader extends Thread {
    public final Lock readLock = Library.readLock;
    ArrayList<String> readBooks = new ArrayList<>();
    ArrayList<String> library = Library.listOfBooks;
    String name;

    public Reader(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (readBooks.size() < Library.countOfBooks) {
            try {
                if (Library.rwl.isWriteLocked()) {
                    System.out.println(name + ": writer is in library");
                }
                //Получаем Lock на чтение книги, что бы ее не изменили во время наших операций
                readLock.lock();
                int random = (int) (Math.random() * library.size());
                if (random < library.size()) {
                    String randomBook = library.get(random);

                    if (readBooks.size() < Library.countOfBooks) {
                        if (!readBooks.contains(randomBook)) {
                            sleep(300);
                            readBooks.add(randomBook);
                            System.out.println(name + " read book " + randomBook);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //finally выполняется всегда в конце try catch (если конечно в try catch не написан код по завершению самой программы) = > всегда лок на ресурс быдет освобождаться
                readLock.unlock();
            }
        }
        System.out.println(name + " finished reading \n" + readBooks);

    }

}

