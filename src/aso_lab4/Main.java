package aso_lab4;

public class Main {

    public static void main(String[] args) {
        //Y=6*2=12
        int readersCount = 12;
        //X=6+3=9
        int writersCount = 9;
        int booksCount = 9;
        //Создаем объект Библиотеки и передаем параметры в конструктор
        Library library = new Library(readersCount, writersCount, booksCount);

        //Запускаем метод который запустит start() у Читателей и Писателей
        library.start();
    }

}
