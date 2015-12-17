import java.io.*;

/**
 * Утилитный класс для работы с файловой системой.
 *
 * @author Karpenko Dmitry
 */
public class Utils {
    /**
     * Метод сохраняет экземпляр модели на диск С.
     * Путь: C:\ServerResourсe\temp.out
     *
     * @param model Экземпляр модели для сохранения.
     *
     * @throws IOException Возникает при ошибках Ввода\Вывода.
     */
    public synchronized void saveModel(Model model) throws IOException {
        FileOutputStream fos = new FileOutputStream("C:\\ServerResourсe\\temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
    }

    /**
     * Метод загружает экземпляр модели с файла.
     * Путь: C:\ServerResourсe\temp.out
     *
     * @return Загруженный экземпляр модели.
     *
     * @throws IOException Возникает при ошибках Ввода\Вывода.
     * @throws ClassNotFoundException Возникает при ошибке загрузки данных.
     */
    public Model loadModel() throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        Model ts = new Model();
        try {
            fis = new FileInputStream("C:\\ServerResourсe\\temp.out");
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
