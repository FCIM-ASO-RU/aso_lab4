package aso_lab4;

import aso_lab4.TeamE.Library;

// Группа CR-203 (последняя цифра = 3)
// Макс. кол-во вариантов 17 (17 студентов в группе)
// Номер варианта = 15
// X = номер варианта + последняя цифра группы = 15 + 3 = 18
// Y = номер варианта * 2 = 15 * 2 = 30
// Z = макс. кол-во вариантов + 3 = 17 + 3 = 20

public class Main {

    public static void main(String[] args) {
        System.out.println("Team E. Prutean Stanislav & Neer ");

        int writers = 18;
        int readers = 30;
        int maxBooks = 20;

        Library library = new Library(writers, readers, maxBooks); // указываете в соответствии с вариантом
        library.Start();
    }
}