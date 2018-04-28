package dal;

import bll.Settings;

import java.io.*;


public class SerializationHelper {
    private static Settings defaultSettings = new Settings();

    public static void writeSettings(Settings settings, String filename) throws IOException {

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(settings);
            objectOutputStream.flush();
        }
    }

    public static Settings readSettings(String filename) throws IOException, ClassNotFoundException {
        Settings settings;
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
            writeSettings(defaultSettings, file.toString());
        }

        try (FileInputStream fis = new FileInputStream(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            settings = (Settings) ois.readObject();
        }

        return settings;
    }
}
