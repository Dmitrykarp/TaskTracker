import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Support on 06.12.2015.
 */
public class MainApp {
    static final int PORT=37777;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server RUN");

        try {
            while (true){
                Socket socket = s.accept();
                try {
                    new MultiServer(socket);
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
