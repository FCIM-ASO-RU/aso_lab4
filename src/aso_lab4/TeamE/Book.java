package aso_lab4.TeamE;

public class Book {
    public final String title;
    
    public Book(String _title) {
        title = _title;
    }

    @Override
    public String toString() {
        return "Book{" + "name='" + title + '\'' + '}';
    }
}