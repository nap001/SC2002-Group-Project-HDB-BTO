package Interfaces;

import java.util.List;

import Application.Application;

public interface CSVHandler<T> {
    /**
     * Loads a list of objects from a CSV file.
     * 
     * @return a list of objects loaded from the CSV file
     */

    /**
     * Saves a list of objects to a CSV file.
     * 
     * @param objects the list of objects to save
     */
    void saveToCSV(List<T> objects,String filePath);

    List<T> loadFromCSV(String filePath);

}
