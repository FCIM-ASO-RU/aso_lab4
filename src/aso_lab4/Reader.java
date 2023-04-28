package aso_lab4;

import java.util.ArrayList;

public class Reader extends Thread {
    
    final Library library;
    final ArrayList<Book> read;
    
    public Reader(Library _library, String name) {
        library = _library;
        read = new ArrayList<>();
        setName(name);
    }
    
    @Override
    public void run() {
        
    }
    
}