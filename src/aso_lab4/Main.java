package aso_lab4;

public class Main {

    public static void main(String[] args) {
        int x = 10;//3 + 7 Валик 8 вар
        int y = 11;//3 + 8; Максим 7
        int z = 13;//10 + 3 Жасмина 10
        final Library library = new Library(x, y, z); // указываете в соответствии с вариантом
        library.Start();
    }

}