package serializer;

import java.io.*;

public class ObjectLoader {
    public static Object loadObject(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject();  // Deserializes the object from the file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}