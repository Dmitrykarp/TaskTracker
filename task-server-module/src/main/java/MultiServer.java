import java.io.*;
import java.net.Socket;

/**
 * Created by Support on 06.12.2015.
 */
public class MultiServer extends Thread {
    private Socket socket;
    private User user;


    public MultiServer(Socket s) throws IOException{
        socket = s;

        start();
    }

    public void run(){
        BufferedInputStream bis = null;
        ObjectInputStream ois = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream oos = null;

        try {
            bis = new BufferedInputStream(socket.getInputStream());
            ois = new ObjectInputStream(bis);
            bos = new BufferedOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(bos);

            while (true) {
                try {
                    Object readed = ois.readObject();
                    if(readed instanceof ClientCommand) {
                        ClientCommand command = (ClientCommand) readed;
                        switch (command.getAction()) {
                            case SIGNIN:
                                user = (User) command.getObject();
                                //Поиск юзера, если да - то продолжаем.

                                break;
                            case SIGNUP:
                                User tempUser = (User) command.getObject();
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

            } catch (IOException e) {
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
