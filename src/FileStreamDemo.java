import java.io.*;

public class FileStreamDemo {
    private FileInputStream fin, fin2;
    private FileOutputStream fout;
    private SequenceInputStream seq;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Writer writer;
    private StringBuffer stringBuffer = new StringBuffer();
    private Reader reader;
    private PrintStream printStream;

    /**
     * @param file takes file as an arguments
     * @param Data takes data to write in the file
     * @throws IOException
     */
    void fileoutputstream(File file, String Data) throws IOException {
        try {
            fout = new FileOutputStream(file);
            String s = Data;
            byte b[] = s.getBytes();//converting string into byte array
            fout.write(b);
            fout.flush();
            fout.close();
            System.out.println("success...");
        } catch (Exception e) {
            throw new IOException("File Write Error");
        }
    }

    void transferObject(GamePlay.Team user, File file) {
        try {
            fout = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fout);
            try {
                objectOutputStream.writeObject(user);
            } catch (Exception e) {
                System.out.println("Object parsing Err: " + e.getMessage());
            } finally {
                objectOutputStream.flush();
                objectOutputStream.close();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println("File parsing Err: " + e.getMessage());
        }
    }

    GamePlay.Team readObject(File file) {
        GamePlay.Team user = null;
        try {
            fin = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fin);
            try {
                user = ( GamePlay.Team ) objectInputStream.readObject();
            } catch (Exception e) {
                System.out.println("Object parsing Err: " + e.getMessage());
            } finally {
                objectInputStream.close();
                fin.close();
            }
        } catch (Exception e) {
            System.out.println("File parsing Err: " + e.getMessage());
        }
        return user;
    }
}