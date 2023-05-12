package aso_lab4;

public class Main {

    public static void main(String[] args) {
        int x = 10;//3 + 7 Вадим 8
        int y = 11;//3 + 8; Дима 7
        int z = 13;//10 + 3 Ярик 10
        final Library library = new Library(x, y, z); // указываете в соответствии с вариантом
        library.Start();
    }

}