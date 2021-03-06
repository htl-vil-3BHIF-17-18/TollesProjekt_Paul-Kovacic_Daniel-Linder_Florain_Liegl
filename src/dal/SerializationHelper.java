package dal;

import bll.Settings;

import java.io.*;


public class SerializationHelper {
    private static final Settings defaultSettings = new Settings(true);

    public static void writeSettings(Settings settings, String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(settings);
            objectOutputStream.flush();
        }
    }

    public static Settings readSettings(String filename) throws IOException, ClassNotFoundException {
        Settings settings;
        File file = new File(filename);

        if(!file.exists())
            writeSettings(defaultSettings, filename);

        try (FileInputStream fis = new FileInputStream(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            settings = (Settings) ois.readObject();
        }

        return settings;
    }
}