package network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV3 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        while (true) {
            Socket socket = serverSocket.accept(); // 블로킹
            log("소켓 연결: " + socket);

            SessionV3 session = new SessionV3(socket);
            Thread thread = new Thread(session);
            thread.start();
        }

        // 서버, 클라이언트가 둘다 연결된 상태에서 인텔리제이 버튼으로 클라이언트 연결을 직접 종료하면,
        // 클라이언트 프로세스가 종료되며 클라이언트와 서버의 TCP 연결도 종료된다.
        // 이때 서버에서 readUTF()로 메세지를 읽으려하면 EOFException 이 발생한다.
        // (JDK에 따라 java.net.SocketException: Connection reset 가 발생하는 듯?)
        // 근데 이러면 catch로 바로 넘어가고, 자원 정리가 되지 않고 나가버리게 된다.
        // 서버에서 자원 정리 로그도 뜨지 않는다.

        // 자바 객체는 GC가 되지만 자바 외부 자원은 자동으로 GC 되지 않는다.
        // 때문에 꼭 정리를 해야한다. TCP 연결의 경우 OS가 어느정도 정리를 해주지만,
        // 직접 연결을 종료할 때보다 더 많은 시간이 걸릴 수 있다.
    }
}
