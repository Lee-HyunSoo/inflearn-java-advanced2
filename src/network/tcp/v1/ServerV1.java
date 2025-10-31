package network.tcp.v1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        // 서버는 특정 포트를 열어놔야한다.
        // 그래서 서버는 ServerSocket 이라는 특별한 소켓을 사용한다.
        // 지정한 포트를 사용해 서버 소켓을 생성하면, 클라이언트가 접근 가능하다.
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        // 위 ServerSocket은 클라이언트와 서버의 TCP 연결만 지원하는 특별한 소켓
        // 실제로 클라이언트-서버가 데이터를 주고 받으려면 Socket 객체가 필요하다.
        // 때문에 accept()를 호출하면 OS backlog queue 에서 TCP 연결 정보를 조회 후
        // TCP 연결 정보를 기반으로 Socket 객체를 만들어 반환한다.
        // 사용한 TCP 연결 정보는 OS backlog queue 에서 제거된다.
        // 즉, accept()는 운영체제가 연결해줘서 완료된 연결 정보를 가져오는 것
        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // 클라이언트로부터 문자 받기
        String received = input.readUTF();
        log("client -> server: " + received);

        // 클라이언트에게 문자 보내기
        String toSend = received + " World!";
        output.writeUTF(toSend);
        log("client <- server: " + toSend);

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
        serverSocket.close();
        
        // BindException
        // Exception in thread "main" java.net.BindException: Address already in use
        // 해당 포트를 사용하고 있는 다른 프로세스가 있다는 뜻
        // 주의! 인텔리제이에서 Terminate로 종료하지 않고 Disconnect로 종료하면
        // 포트를 점유하고 있는 서버 프로세스가 그대로 살아있는 상태로 유지된다.

        // 클라이언트가 소켓 객체를 만들어 연결을 시도하면
        // OS 계층에서 3way-handshake가 발생하고, TCP 연결이 완료된다.
        // TCP 연결이 완료되면 서버는 OS backlog queue 라는 곳에서 클라이언트와 서버 연결 정보를 보관
        // 자바에서 연결 정보를 보관하는 것이 아니다.
        // 이 backlog queue를 보면 클라이언트의 IP, PORT / 서버의 IP, PORT 정보가 모두 있다.

        // 클라이언트와 랜덤 포트
        // TCP 연결 시에는 클라이언트, 서버 모두 IP, PORT 정보가 필요하다.
        // 서버는 포트가 명확히 지정되야하지만, 클라이언트는 자신의 포트를 지정한 적이 없다.
        // 클라이언트는 보통 포트를 생략하는데,
        // 생략할 경우 클라이언트 PC에 남아있는 포트 중 하나가 랜덤으로 할당된다.
        // 클라이언트의 포트도 명시적으로 할당할 수는 있지만 잘 사용하지 않는다.

        // ServerSocket 객체는 클라이언트가 서버에 접속 후 접속 정보를 backlog queue 에 담는 것 까지만 담당
        // 실제 클라이언트와 통신하는 것은 Socket 객체
    }
}
