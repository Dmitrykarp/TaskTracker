import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Support on 06.12.2015.
 */
public class Controller {
    private Socket socket;
    private View thisView = new View();
    private ArrayList<Task> tasks = null;
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
            boolean subExit = false;
            String command;
            BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));
            // Аутентификация User'а
            while (!exit){
                thisView.printConsole(View.Help.USERACCOUNT);
                thisView.printConsole(View.Help.CONSOLE);
                command = readerConsole.readLine();
                if("1".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.USERCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
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
                    thisView.printConsole(View.Help.CONSOLE);
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
                } else if("3".equals(command.trim().toLowerCase())){
                    subExit= true;
                    exit=true;
                } else thisView.printConsole(View.Help.ERROR);
            }

            if (!subExit) {
                exit = false;
            }


            while (!exit){
                thisView.printConsole(View.Help.WORKMENU);
                thisView.printConsole(View.Help.CONSOLE);
                command = readerConsole.readLine();
                if("1".equals(command.trim().toLowerCase())){
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.GETTASKS);
                    oos.writeObject(clientCommand);
                    oos.flush();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    tasks =(ArrayList<Task>) answer.getObject();
                    for (Task task:tasks){
                        System.out.println(task.getId() +" " + task.getName());
                    }
                    //TODO Распечатать все команды


                }else if("2".equals(command.trim().toLowerCase())){

                }else if("3".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command);
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.CREATETASK, task);
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
                            break;
                        case FAILURE:
                            System.out.println(answer.getMessage());
                            break;
                        default:
                            thisView.printConsole(View.Help.ERROR);
                    }

                }else if("4".equals(command.trim().toLowerCase())){

                }else if("5".equals(command.trim().toLowerCase())){

                }else if("6".equals(command.trim().toLowerCase())){

                }else if("7".equals(command.trim().toLowerCase())){

                }else if("8".equals(command.trim().toLowerCase())){
                    exit=true;
                }else thisView.printConsole(View.Help.ERROR);

                //Основная часть после аутентификации User'a
            }
        }catch (IOException e) {
            System.out.println("Ошибка");
        } finally {
            //Закрываем потоки
        }

    }
}
