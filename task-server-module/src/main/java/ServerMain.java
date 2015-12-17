import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Точка входа на стороне сервера.
 *
 * @author Karpenko Dmitry
 */
public class ServerMain {
    static final int PORT=37777;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server RUN");
        Model thisModel = new Utils().loadModel();


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
