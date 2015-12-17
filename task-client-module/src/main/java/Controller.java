import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Controller {
    private Socket socket;
    private View thisView = new View();
    private User thisUser;
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
                    user = new User(Integer.parseInt(command));
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
                    oos.reset();
                    ServerAnswer answer = null;
                    try {
                        answer = (ServerAnswer) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Task> tasks =(ArrayList<Task>) answer.getObject();
                    thisView.printTask(tasks);

                }else if("2".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.GOTOUP);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command);
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

                }else if("3".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command);
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

                }else if("4".equals(command.trim().toLowerCase())){
                    String oldName, newName;
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    oldName = readerConsole.readLine();
                    thisView.printConsole(View.Help.TASKRENAME);
                    thisView.printConsole(View.Help.CONSOLE);
                    newName = readerConsole.readLine();
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

                }else if("5".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command);
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

                }else if("6".equals(command.trim().toLowerCase())){
                    boolean tt=false;
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command);
                    ClientCommand clientCommand = new ClientCommand(ClientCommand.Action.TASKACTION, task);
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
                            tt=true;
                            break;
                        default:
                            thisView.printConsole(View.Help.ERROR);
                    }
                    //Ждем ввода СТОП

                    while (!tt){
                        thisView.printConsole(View.Help.TASKACTION);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = readerConsole.readLine();
                        if("stop".equals(command.trim().toLowerCase())){
                            clientCommand = new ClientCommand(ClientCommand.Action.STOP);
                            oos.writeObject(clientCommand);
                            oos.reset();
                            answer = null;
                            try {
                                answer = (ServerAnswer) ois.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            switch (answer.getType()){
                                case SUCCESS:
                                    System.out.println(answer.getMessage());
                                    tt=true;
                                    break;
                                case FAILURE:
                                    System.out.println(answer.getMessage());
                                    break;
                                default:
                                    thisView.printConsole(View.Help.ERROR);
                            }
                        }
                    }

                }else if("7".equals(command.trim().toLowerCase())){
                    thisView.printConsole(View.Help.TASKCREATE);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = readerConsole.readLine();
                    Task task = new Task(command);
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

                }else if("8".equals(command.trim().toLowerCase())){
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
