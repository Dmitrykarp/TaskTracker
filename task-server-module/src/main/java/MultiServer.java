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
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Пользователь не найден!"));
                                    oos.flush();
                                }
                                //Поиск юзера, если да - то продолжаем.

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
                                }


                                break;
                            case GETTASKS:
                                oos.writeObject(ServerAnswer.success(model.getTasks()));
                                oos.flush();
                                break;
                            case CREATETASK:
                                Task newTask = (Task) command.getObject();
                                if(model.findTask(newTask.getName())){
                                    oos.writeObject(ServerAnswer.failure("Задача с таким именем уже существует!"));
                                    oos.flush();
                                } else {
                                    int i = model.findMaxId();
                                    newTask.setId(i +1);
                                    model.addTask(newTask);
                                    oos.writeObject(ServerAnswer.success("Задача успешно создана!"));
                                    oos.flush();
                                }
                                break;
                            case RENAMETASK:
                                String oldName = command.getOldName();
                                String newName = command.getNewName();
                                if(model.findTask(oldName)){
                                    Task task = model.getFindTask(oldName);
                                    task.setName(newName);
                                    oos.writeObject(ServerAnswer.success("Задача переименована"));
                                    oos.flush();
                                }else{
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена"));
                                    oos.flush();
                                }
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
