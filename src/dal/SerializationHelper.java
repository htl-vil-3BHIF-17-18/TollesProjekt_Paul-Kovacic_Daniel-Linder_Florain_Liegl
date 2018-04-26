package dal;


import bll.Task;

import java.io.*;
import java.util.Date;
import java.util.List;


public class SerializationHelper {

    public static void writeSerializedTask( Object obj, String filename) throws IOException{

        try( FileOutputStream fos = new FileOutputStream( filename )){
            ObjectOutputStream oos = new ObjectOutputStream( fos );
            oos.writeObject(obj);
            oos.flush();
            fos.close();

        }
    }

    public static Object readSerializableTask(String filename) throws IOException, ClassNotFoundException{
        Object obj ;
        try( FileInputStream fis = new FileInputStream( filename )){
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            fis.close();



        }
        return obj;
    }

    public static Date getTimestampFile(String filepath) {
        File file = new File(filepath);
        return new Date(file.lastModified());
    }
}
