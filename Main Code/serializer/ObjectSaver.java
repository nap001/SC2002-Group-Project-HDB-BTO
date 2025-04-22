package serializer;

import java.io.*;

public class ObjectSaver {
    public static void saveObjects(Object object, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(object);  // Serializes the object to the file
            System.out.println("Object saved to " + filename + " successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}