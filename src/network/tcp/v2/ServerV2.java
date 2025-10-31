package network.tcp.v2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        while (true) {
            // 클라이언트로부터 문자 받기
            String received = input.readUTF();
            log("client -> server: " + received);

            if (received.equals("exit")) {
                break;
            }

            // 클라이언트에게 문자 보내기
            String toSend = received + " World!";
            output.writeUTF(toSend);
            log("client <- server: " + toSend);
        }

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
        serverSocket.close();

        // 현재 ServerV2는 새로운 클라이언트가 접속했을 때 accept()가 호출이 안됨
        // while 문으로는 메세지 송수신만 반복하기 때문
        // 즉, main 스레드 하나가아니라 별도의 스레드가 더 필요하다.
        // 1. accept() : 클라이언트와 서버의 연결을 처리하기 위해 대기
        // 2. read : 클라이언트의 메세지를 받아 처리하기 위해 대기
        // 1, 2번 블로킹 작업은 별도의 스레드에서 처리해야 한다.
        // 안그러면 한쪽의 처리가 끝날 때까지 한쪽이 계속 대기해야하기 때문이다.
    }
}
