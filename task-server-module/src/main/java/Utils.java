import java.io.*;

public class Utils {

    public synchronized void saveModel(Model model) throws IOException {
        FileOutputStream fos = new FileOutputStream("C:\\ServerResourse\\temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
    }

    public Model loadModel() throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        Model ts = new Model();
        try {
            fis = new FileInputStream("C:\\ServerResourse\\temp.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            ts = (Model) oin.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Task noWork = new Task("Не работа", 0);
            ts.addTask(noWork);
            }
        return ts;
    }
}
