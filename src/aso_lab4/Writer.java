package aso_lab4;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

// Наследуюясь от класса Thread мы получаем возможность переписать код в методе run и запустить его в новом потоке через start
public class Writer extends Thread {
    String name;
    //В общем всеми писателями будет написано 9 книг
    private final ArrayList<String> bookList ;

    //Здесь не присваевается значение а получается ссылка на статический элемент то есть все что будет происходить будет происходить только с одним элементом
    public final Lock writeLock = Library.writeLock;
    //То же самое
    ArrayList<String> library = Library.listOfBooks;

    //Используется для того что бы определить какую книжку нужно доставать следуцющей
    static int index = 0;

    public Writer(String name) {
        this.name = name;
        //Получаем список книг из файла вызывая статическую переменну.
        //Так как файл находится в той же дирректории то используем ./ --> что используется для сокращения пути и заменяется в системе как директория где находится этот класс
        bookList = BookFile.getListOfBooksFrom("./books.txt");
    }

    @Override
    public void run() {
        // Продолжаем цикл пока количество книг в библиотеке не будет удовлетворенно ( 9 книг нужно)
        while (library.size() < Library.countOfBooks) {
            try {
                //Получаем лок что бы другой thread не изменил ресурс(ArrayList)
                writeLock.lock();
                if (library.size() < Library.countOfBooks) {
                    //Получаем книгу
                    String book = bookList.get(index);
                    sleep(500);
                    //добавляем в библиотеку
                    library.add(book);
                    System.out.println(name + " wrote " + book);
                    index++;
                    sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Освобождаем
                writeLock.unlock();
            }
        }

    }

}
