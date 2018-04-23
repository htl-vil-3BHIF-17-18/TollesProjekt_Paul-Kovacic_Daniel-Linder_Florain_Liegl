package dal;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializationHelper {

    public static void writeSerializedPersons( Object obj, String filename) throws IOException, ClassNotFoundException{

        try( FileOutputStream fos = new FileOutputStream( filename )){
            ObjectOutputStream oos = new ObjectOutputStream( fos );
            oos.writeObject(obj);
            oos.flush();
        }
    }

    public static Object readSerializablePerson( String filename) throws IOException, ClassNotFoundException{
        Object obj = null;
        try( FileInputStream fis = new FileInputStream( filename )){
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();

        }
        return obj;
    }
}
