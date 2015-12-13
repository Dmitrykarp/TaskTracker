import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Support on 06.12.2015.
 */
public class MultiServer extends Thread {
    private Socket socket;
    private User user;
    private Model model;
    Task taskUP = new Task("UP",-1);
    private Task taskUser;


    public MultiServer(Socket s, Model m) throws IOException{
        socket = s;
        model = m;

        start();
    }

    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;


        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());


            while (true) {
                try {
                    Object readed = ois.readObject();
                    if (readed instanceof ClientCommand) {
                        ClientCommand command = (ClientCommand) readed;
                        switch (command.getAction()) {
                            case SIGNIN:
                                user = (User) command.getObject();
                                if (model.findEqualsUser(user)) {
                                    oos.writeObject(ServerAnswer.success("Вход выполнен!"));
                                    oos.flush();
                                    taskUser = taskUP;
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Пользователь не найден!"));
                                    oos.flush();
                                }
                                break;

                            case SIGNUP:
                                User tempUser = (User) command.getObject();
                                if (model.findEqualsUser(tempUser)) {
                                    oos.writeObject(ServerAnswer.failure("Такой пользователь существует!"));
                                    oos.flush();
                                } else {
                                    oos.writeObject(ServerAnswer.success("Пользователь создан!"));
                                    oos.flush();
                                    user = tempUser;
                                    model.addUser(user);
                                    taskUser = taskUP;
                                }
                                break;

                            case GETTASKS:
                                if (taskUser.equals(taskUP)) {
                                    oos.writeObject(ServerAnswer.success(model.getTasks()));
                                    oos.flush();
                                } else {
                                    oos.writeObject(ServerAnswer.success(taskUser.getTasks()));
                                    oos.flush();
                                }
                                break;

                            case CREATETASK:
                                Task newTask = (Task) command.getObject();
                                if(model.findTask(newTask.getName(), taskUser)){
                                    oos.writeObject(ServerAnswer.failure("Задача с таким именем уже существует!"));
                                    oos.flush();
                                } else {
                                    int i = model.findMaxId(model.getTasks());
                                    newTask.setId(i +1);
                                    if (taskUser.equals(taskUP)) {
                                        model.addTask(newTask);
                                    } else {
                                        taskUser.addTask(newTask);
                                    }
                                    oos.writeObject(ServerAnswer.success("Задача успешно создана!"));
                                    oos.flush();

                                }
                                break;
                            case SELECTTASK:
                                newTask = (Task) command.getObject();
                                if("UP".equals(newTask.getName())){
                                    taskUser=taskUP;
                                    oos.writeObject(ServerAnswer.success("Выход выполнен!"));
                                    oos.flush();
                                    break;
                                }
                                if(model.findTask(newTask.getName(), taskUser)){
                                    taskUser=model.getFindTask(newTask.getName(), taskUser);
                                    oos.writeObject(ServerAnswer.success("Вход в задачу " +newTask.getName() +" выполнен!"));
                                    oos.flush();
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена!"));
                                    oos.flush();
                                }
                                break;
                            case RENAMETASK:
                                String oldName = command.getOldName();
                                String newName = command.getNewName();
                                if(model.findTask(oldName, taskUser)){
                                    Task task = model.getFindTask(oldName, taskUser);
                                    if (task.equals(model.getFindTask("Не работа",taskUser))){
                                        oos.writeObject(ServerAnswer.failure("Данную задачу нельзя переименовать или удалить!"));
                                        oos.flush();
                                    }else {
                                        task.setName(newName);
                                        oos.writeObject(ServerAnswer.success("Задача переименована"));
                                        oos.flush();
                                    }
                                }else{
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена"));
                                    oos.flush();
                                }
                                break;
                            case DELETETASK:
                                Task t = (Task) command.getObject();
                                if (model.findTask(t.getName(), taskUser)){
                                    Task task = model.getFindTask(t.getName(),taskUser);
                                    if (task.equals(model.getFindTask("Не работа", taskUser))){
                                        oos.writeObject(ServerAnswer.failure("Данную задачу нельзя переименовать или удалить!"));
                                        oos.flush();
                                    }else if (taskUser.equals(taskUP)){
                                        model.dellTask(task);
                                        oos.writeObject(ServerAnswer.success("Задача удалена"));
                                        oos.flush();
                                    }else {
                                        taskUser.dellTask(task);
                                    }

                                }else{
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена"));
                                    oos.flush();
                                }
                                break;
                            case TASKACTION:
                                newTask = (Task) command.getObject();
                                if(model.findTask(newTask.getName(), taskUser)){
                                    Task task = model.getFindTask(newTask.getName(),taskUser);
                                    Timer timer = new Timer();
                                    oos.writeObject(ServerAnswer.success("Задача активированна!"));
                                    oos.flush();
                                    ClientCommand end = (ClientCommand) ois.readObject();
                                    if (end.getAction() == ClientCommand.Action.STOP){
                                        timer.setEndDate();
                                        timer.setLongDate();
                                        task.setOneHistory(user,timer);
                                        oos.writeObject(ServerAnswer.success("Задача остановлена!"));
                                        oos.flush();
                                    } else {
                                        oos.writeObject(ServerAnswer.failure("Неизвестная задача!"));
                                        oos.flush();
                                    }

                                } else {
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена!"));
                                    oos.flush();
                                }
                                break;
                            case TASKSTAT:
                                newTask = (Task) command.getObject();
                                if(model.findTask(newTask.getName(), taskUser)){
                                    Task task = model.getFindTask(newTask.getName(),taskUser);
                                    oos.writeObject(ServerAnswer.success(task));
                                    oos.flush();
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена!"));
                                    oos.flush();
                                }
                                break;
                        }

                        // Все действия тут



                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (EOFException e){
                    throw new SocketException();
                }


            }

        }catch (SocketException e) {
            System.out.println("Client disconnected. IP " + socket.getInetAddress());

        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket not closed");
            }
        }




    }
}
