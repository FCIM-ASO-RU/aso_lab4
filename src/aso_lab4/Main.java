package aso_lab4;

public class Main {

    public static void main(String[] args) {
        Library library = new Library(2, 5, 10); // Создаем библиотеку с 2 писателями, 5 читателями и максимальным количеством книг 10

        for (Writer writer : library.writers) {
            writer.start();
        }

        for (Reader reader : library.readers) {
            reader.start();
        }

        for (Writer writer : library.writers) {
            try {
                writer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Reader reader : library.readers) {
            try {
                reader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}