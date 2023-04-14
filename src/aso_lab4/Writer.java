package aso_lab4;

import java.util.ArrayList;

public class Writer extends Thread {
    
    final Library library;
    final ArrayList<Book> written;
    
    public Writer(Library _library, String name) {
        library = _library;
        written = new ArrayList<>();
        setName(name);
    }
    
    @Override
    public void run() {
        
    }
    
}