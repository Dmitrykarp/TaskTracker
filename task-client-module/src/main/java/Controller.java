import java.io.*;
import java.net.Socket;

/**
 * Created by Support on 06.12.2015.
 */
public class Controller {
    private Socket socket;
    private View thisView = new View();
    BufferedInputStream bis = null;
    ObjectInputStream ois = null;
    BufferedOutputStream bos = null;
    ObjectOutputStream oos = null;

    public Controller(Socket socket) {
        this.socket=socket;
    }

    public void run() throws IOException {

        try {
            bis = new BufferedInputStream(socket.getInputStream());
            ois = new ObjectInputStream(bis);

            bos = new BufferedOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(bos);
            boolean exit = false;
            String command;
            BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));

            // Аутентификация User'а
            while (!exit){
                thisView.printConsole(View.Help.USERACCOUNT);
                thisView.printConsole(View.Help.CONSOLE);
                command = readerConsole.readLine();
                if("1".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.USERCREATE);
                    command = readerConsole.readLine();

                    //Введите ID
                } else if("2".equals(command.trim().toLowerCase())){
                    //Регистрация нового ID
                } else thisView.printConsole(View.Help.ERROR);
            }

            exit = false;

            while (!exit){
                //Основная часть после аутентификации User'a
            }
        }catch (IOException e) {
            // При ошибке ввода-вывода
        } finally {
            //Закрываем потоки
        }

    }
}
