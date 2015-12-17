import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Класс <code>Controller</code> взаимодействует с классами <code>ClientCommand</code> и
 * <code>View</code>.
 * Класс обрабатывает все комманды пользователя и манипулирует данными.
 *
 * @author Karpenko Dmitry
 */
public class Controller {
    private Socket socket;
    private View thisView = new View();
    private User thisUser;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    /**
     * Конструктору необходимо явно указать сокет для связи с сервером.
     *
     * @param socket Сокет на стороне клиента
     */
    public Controller(Socket socket) {
        this.socket=socket;
    }

    /**
     * Метод считывает данные, введенные в консоли и манипулирует ими.
     *
     * @throws IOException Возникает при ошибке ввода\вывода.
     */
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
                    try {
                        user = new User(Integer.parseInt(command.trim()));
                    }catch (NumberFormatException e){
                        thisView.printConsole(View.Help.NUMBERFORMAT);
                        continue;
                    }
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.SIGNIN,user);
                    oos.writeObject(clientCommand);
                    oos.reset();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    switch (answer.getType()){
                        case SUCCESS:
                            System.out.println(answer.getMessage());
                            thisUser=user;
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
                    try {
                        user = new User(Integer.parseInt(command.trim()));
                    }catch (NumberFormatException e){
                        thisView.printConsole(View.Help.NUMBERFORMAT);
                        continue;
                    }
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.SIGNUP,user);
                    oos.writeObject(clientCommand);
                    oos.reset();
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
                            thisUser=user;
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
            // Проверка на выход из аутентификации
            if (!subExit) {
                exit = false;
            }
            // Основная работа программы после аутентификации пользователя
            while (!exit){
                thisView.printConsole(View.Help.WORKMENU);
                thisView.printConsole(View.Help.CONSOLE);
                command = readerConsole.readLine();
                // Получение списка всех дочерних задач
                if("1".equals(command.trim().toLowerCase())){
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.GETTASKS);
                    oos.writeObject(clientCommand);
                    oos.reset();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Task> tasks =(ArrayList<Task>) answer.getObject();
                    thisView.printTask(tasks);
                // Выбор родительской задачи
                }else if("2".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.GOTOUP);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command.trim());
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.SELECTTASK, task);
                    oos.writeObject(clientCommand);
                    oos.reset();
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
                // Создание новой задачи
                }else if("3".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command.trim());
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.CREATETASK, task);
                    oos.writeObject(clientCommand);
                    oos.reset();
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
                // Переименование существующей задачи
                }else if("4".equals(command.trim().toLowerCase())){
                    String oldName, newName;
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    oldName = readerConsole.readLine().trim();
                    thisView.printConsole(View.Help.TASKRENAME);
                    thisView.printConsole(View.Help.CONSOLE);
                    newName = readerConsole.readLine().trim();
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.RENAMETASK,oldName, newName);
                    oos.writeObject(clientCommand);
                    oos.reset();
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
                // Удаление задачи и всех ее подзадач
                }else if("5".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command.trim());
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.DELETETASK, task);
                    oos.writeObject(clientCommand);
                    oos.reset();
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
                // Вывод статистики
                }else  if("6".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command.trim());
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.TASKSTAT, task);
                    oos.writeObject(clientCommand);
                    oos.reset();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Task taskStat = (Task) answer.getObject();
                    thisView.printStat(thisUser,taskStat);
                    thisView.printAllStat(taskStat);
                // Выход
                }else if("7".equals(command.trim().toLowerCase())){
                    exit=true;
                }else thisView.printConsole(View.Help.ERROR);

            }
        }catch (IOException e) {
            thisView.printConsole(View.Help.DISCONNECT);
        } finally {
            socket.close();
        }

    }
}
