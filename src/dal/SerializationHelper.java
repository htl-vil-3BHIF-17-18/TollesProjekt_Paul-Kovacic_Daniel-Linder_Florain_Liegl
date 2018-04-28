package dal;

import bll.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SerializationHelper {

    public static void writeSerializedTask(List<Task> tasks, String filename) throws IOException {

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new ArrayList<>(tasks));
            objectOutputStream.flush();
        }
    }

    public static List<Task> readSerializableTask(String filename) throws IOException, ClassNotFoundException {
        List<Task> tasks;
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileInputStream fis = new FileInputStream(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (List<Task>) ois.readObject();
        }

        return tasks;
    }
}
