package network.exception.close.reset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import static util.MyLogger.log;

public class ResetCloseClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결: " + socket);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // 1. client <- server: FIN
        Thread.sleep(1000); // 서버가 close() 호출할 때까지 잠시 대기

        // 2. client -> server: PUSH[1]
        output.write(1);

        // 3. client <- server: RST (연결이 잘못되었다, 강제로 연결을 끊어야된다는 메세지)
        Thread.sleep(1000); // RST 메세지 전송 대기

        // 클라이언트에서 RST 받은 후 read를 하면?
        // java.net.SocketException: connection reset
        try {
            int read = input.read();
            System.out.println("read = " + read);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // RST 받은 후 write를 하면?
        // java.net.SocketException: Broken pipe
        try {
            output.write(1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    // RST (Reset)
    // TCP 에서 RST 패킷은 연결 상태를 초기화(리셋) 해서 더 이상 현재의 연결을 유지하지 않겠다는 의미
    // 즉, 여기서 Reset은 현재 세션을 강제로 종료하고, 연결을 무효화하라는 뜻

    // RST 패킷은 TCP 연결에 문제가 있는 다양한 상황에 발생
    // 1. TCP 스펙에 맞지 않는 순서로 메세지가 전달될 때
    // 2. TCP 버퍼에 있는 데이터를 아직 다 읽지 않았는데 연결을 종료할 때
    // 3. 방화벽 같은 곳에서 연결을 강제로 종료시킬 때

    // 예외 정리
    // 1. 상대방이 연결을 종료한 경우 데이터를 읽으면 EOF 가 발생
    // -1, null, EOFException 등이 발생 (연결을 끊어야 함)
    // 2. java.net.SocketException: Connection reset
    // RST 패킷을 받은 이후에 read() 호출
    // 3. java.net.SocketException: Broken pipe
    // RST 패킷을 받은 이후에 write() 호출
    // 4. java.net.SocketException: Socket is closed
    // 자기 자신의 소켓을 닫은 이후에 read(), write() 호출

    // 즉, 네트워크의 예외는 모두 IOException의 자식이기 때문에
    // IOException을 잡아 자원을 정리하면 된다.
}
