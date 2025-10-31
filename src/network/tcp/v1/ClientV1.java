package network.tcp.v1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class ClientV1 {

    // ip: 내 컴퓨터 port: 내 컴퓨터의 수많은 어플을 구별
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        // 네트워크 연결을 하려면 자바에서는 소켓이라는 객체가 필요하다.
        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결: " + socket);

        // 서버에게 문자 보내기
        String toSend = "Hello";
        output.writeUTF(toSend);
        log("client -> server: " + toSend);

        // 서버로부터 문자 받기
        String received = input.readUTF();
        log("client <- server: " + received);

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();

        // java.net.ConnectException: Connection refused
        // 서버를 시작하지 않고 클라이언트만 실행한 경우

        // localhost를 통해 자신의 컴퓨터의 12345 포트에 TCP 접속 시도
        // localhost는 IP가 아니므로 해당하는 IP를 먼저 찾는다. (내부에서 InetAddress 사용)
        // IP를 찾고 3way-handshake를 통해 TCP 접속을 시도한다.
        // 연결이 성공적으로 완료되면 Socket 객체를 반환한다.
        // Socket은 서버와 연결되어있는 연결점 (서버와 통신 가능)

        // 이 때 InputStream, OutputStream을 그대로 쓰면 모든 데이터를 byte로 변환해야해서 번거롭다.
        // 그래서 DataInputStream, DataOutputStream 보조 스트림을 이용해 자바 타입의 메세지를 주고받았다.
        // 다른 보조 스트림을 써도 물론 가능하다.
    }
}
