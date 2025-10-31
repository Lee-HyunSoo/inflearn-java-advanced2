package network.tcp.v2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ClientV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        // 네트워크 연결을 하려면 자바에서는 소켓이라는 객체가 필요하다.
        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결: " + socket);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("전송 문자: ");
            String toSend = scanner.nextLine();

            // 서버에게 문자 보내기
            output.writeUTF(toSend);
            log("client -> server: " + toSend);

            if (toSend.equals("exit")) {
                break;
            }

            // 서버로부터 문자 받기
            String received = input.readUTF();
            log("client <- server: " + received);
        }

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();

        // ClientV2-2를 Edit Configuration 에서 늘려서 실행?
        // ClientV2 던 V2-2던 처음 접속한 것은 정상 동작
        // 근데 중간에 새로 연결한 것은 소켓 연결은 되지만, 메세지를 전송해도 응답이 오지 않음
        // 정확히는, String received = input.readUTF(); 이게 오지않아서 계속 블로킹 됨
        // 또한, 서버는 새로 연결한 클라이언트가 보낸 메세지를 아예 받지 못함

        // 서버가 ServerSocket을 열어 놓고, 클라이언트가 접근 시
        // 3way-handshake 후 OS backlog queue 에 추가
        // 이 시점에 이미 TCP 연결이 완료된 것 (서버의 Socket 객체는 아직 미생성)
        // 이 상태에서 다른 클라이언트가 접근하면?
        // 3way-handshake 후 OS backlog queue 추가
        // 이 이후 ServerSocket.accept() 를 하면 backlog queue 에서 순서대로 꺼내서 소켓 생성

        // 자바는 소켓 객체의 스트림을 통해 서버와 데이터를 주고 받음
        // 클라이언트 : 어플리케이션 -> OS TCP 송신버퍼 -> 클라이언트 네트워크 카드
        // 서버 : 서버 네트워크 카드 -> OS TCP 수신 버퍼 -> 어플리케이션

        // 즉, 추가 된 클라이언트가 보낸 메세지가 서버의 OS TCP 수신 버퍼에와서 대기
        // 이걸 어플리케이션 단에서 InputStream을 통해 빨아들여서 읽어와야하는데
        // backlog queue 에서 첫 연결정보만 빼서 소켓을 생성했지 두번째거는 꺼내서 생성하지 않았고
        // 어플리케이션에서 읽어오지 못하는 중
        // 즉, 소켓 객체 없이 ServerSocket 만으로 TCP 연결은 완료가 된다.
        // 하지만 연결 이후에 서로 메세지를 주고 받으려면 Socket 객체가 필요하다.
    }
}
