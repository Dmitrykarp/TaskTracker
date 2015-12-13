import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Support on 06.12.2015.
 */
public class ServerMain {
    static final int PORT=37777;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server RUN");
        Model thisModel = new Model();

        try {
            while (true){
                Socket socket = s.accept();
                System.out.println("Connecting SIGN");
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
