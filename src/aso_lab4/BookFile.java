package aso_lab4;

import java.io.*;
import java.util.ArrayList;

public class BookFile {
    public static ArrayList<String> getListOfBooksFrom(String filePath){
        BufferedReader reader = null;
        ArrayList<String> bookList = new ArrayList<>();
        try{
            //Создаем класс обертку, который используется для чтения файлов. Он более оптимизирован для этой задачи самими разрабами джавы
            reader = new BufferedReader(new FileReader(filePath));
            String currentLine;
            //Присваеваем значение текущему файлу и смотрим если есть значение значит это не конец файла, если же нулл то дошли до конца
            while((currentLine = reader.readLine()) != null){
                //Так как в файле есть . и , то ищем их индекс в строке (можно представить строку как массив) и при помощи substring
                //вытаскиваем нужную часть, от 0 индекса до найденного
                // если строка не содержит данный знак то метод indexOf возвращает -1
                int indexOfPoint = currentLine.indexOf('.');
                //Если точки нету, значит есть запятая, тернарный оператор
                String modifiedLine = currentLine.substring(0, indexOfPoint == -1 ? currentLine.indexOf(',') : indexOfPoint);
                //добавляем в список ранее инициализированный
                bookList.add(modifiedLine);
            }
            reader.close();
        } catch (Exception ex){
            //Работа с файлами всегда требует catch, так как они выбрасывают "checked exceptions" => ошибки которые могут появиться во время работы, но они ожидаемые
            // К примеру файл может не существовать когда этот код выполняется. Это частый случай, и для такого случая обязательно нужно продумать логику а что будет если не будет файла
            //потому либо трай кетч либо нужно указать что данный метод может выкинуть эту ошибку и уже метод который будет вызывать обязан ее обработать
            // или кинуть по иерархии дальше
            System.out.println("Something went wrong, maybe the file does not exist, or it could not be closed");
        }
       return bookList;
    }
}
