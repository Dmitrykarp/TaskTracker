import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Support on 06.12.2015.
 */
public class MainApp {
    static final int PORT = 37777;
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName(null);

        Socket socket = new Socket(address,PORT);
        try{
            Controller controller = new Controller(socket);
            controller.run();
        }
        finally {
            socket.close();
        }
    }
}
