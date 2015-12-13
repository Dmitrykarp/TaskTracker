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
                                    oos.writeObject(ServerAnswer.failure("Пользователь создан!"));
                                    oos.flush();
                                    user = tempUser;
                                    model.addUser(user);
                                }
                                //Поиск юзера, если нет то создаем.

                                break;
                            case SIGNOUT:
                                break;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // Все действия тут
            }

        }catch (SocketException e) {
            System.out.println("Connect loss...");

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
