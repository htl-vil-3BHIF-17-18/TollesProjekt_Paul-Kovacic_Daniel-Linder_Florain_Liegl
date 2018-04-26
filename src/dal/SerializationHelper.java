package dal;

import java.io.*;
import java.util.Date;


public class SerializationHelper {

    public static void writeSerializedTask(Object obj, String filename) throws IOException {

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            fos.close();

        }
    }

    public static Object readSerializableTask(String filename) throws IOException, ClassNotFoundException {
        Object obj;
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileInputStream fis = new FileInputStream(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        }

        return obj;
    }

    public static Date getTimestampFile(String filepath) {
        File file = new File(filepath);
        if (file.exists())
            return new Date(file.lastModified());
        else
            return new Date(0);
    }
}
