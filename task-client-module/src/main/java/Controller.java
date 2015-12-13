import java.io.*;
import java.net.Socket;

/**
 * Created by Support on 06.12.2015.
 */
public class Controller {
    private Socket socket;
    private View thisView = new View();
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    public Controller(Socket socket) {
        this.socket=socket;
    }

    public void run() throws IOException {

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            User user;
            boolean exit = false;
            String command;
            BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("While...");
            // Аутентификация User'а
            while (!exit){
                thisView.printConsole(View.Help.USERACCOUNT);
                thisView.printConsole(View.Help.CONSOLE);
                command = readerConsole.readLine();
                if("1".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.USERCREATE);
                    command = readerConsole.readLine();
                    user = new User(Integer.parseInt(command));
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.SIGNIN,user);
                    oos.writeObject(clientCommand);
                    oos.flush();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    switch (answer.getType()){
                        case SUCCESS:
                            System.out.println(answer.getMessage());
                            exit=true;
                            break;
                        case FAILURE:
                            System.out.println(answer.getMessage());
                            break;
                        default:
                            thisView.printConsole(View.Help.ERROR);
                    }
                } else if("2".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.USERCREATE);
                    command = readerConsole.readLine();
                    user = new User(Integer.parseInt(command));
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.SIGNUP,user);
                    oos.writeObject(clientCommand);
                    oos.flush();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    switch (answer.getType()){
                        case SUCCESS:
                            System.out.println(answer.getMessage());
                            exit=true;
                            break;
                        case FAILURE:
                            System.out.println(answer.getMessage());
                            break;
                        default:
                            thisView.printConsole(View.Help.ERROR);
                    }
                } else thisView.printConsole(View.Help.ERROR);
            }

            exit = false;

            while (!exit){
                
                //Основная часть после аутентификации User'a
            }
        }catch (IOException e) {
            System.out.println("Ошибка");
        } finally {
            //Закрываем потоки
        }

    }
}
