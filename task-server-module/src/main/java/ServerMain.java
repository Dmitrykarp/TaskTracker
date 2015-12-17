import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    static final int PORT=37777;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server RUN");
        Model thisModel = new Model();
        thisModel.addTask(new Task("Не работа", 0));

        try {
            while (true){
                Socket socket = s.accept();
                System.out.println("Client is connected. IP " +socket.getInetAddress());
                try {
                    new MultiServer(socket, thisModel);
                } catch (IOException e){
                    socket.close();
                }
            }
        }
        finally {
            s.close();
        }
    }
}
