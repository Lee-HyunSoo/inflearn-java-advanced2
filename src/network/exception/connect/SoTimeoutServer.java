package network.exception.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SoTimeoutServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket socket = serverSocket.accept();

        // 소켓에 대한 연결은 해주지만 메세지에 대한 응답은 안해주는 서버
        Thread.sleep(100000000);
    }
}
