import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMain {
    static final int PORT = 37777;
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName(null);

        Socket socket = new Socket(address,PORT);
        try{
            System.out.println("Connect to Server: OK!");
            Controller controller = new Controller(socket);

            controller.run();
        }
        finally {
            socket.close();
        }
    }
}
