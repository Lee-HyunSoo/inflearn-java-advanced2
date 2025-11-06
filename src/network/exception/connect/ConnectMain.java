package network.exception.connect;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectMain {

    public static void main(String[] args) throws IOException {
        unknownHostEx1();
        unknownHostEx2();
        connectionRefused();
    }

    // 없는 IP인 경우
    private static void unknownHostEx1() throws IOException {
        try {
            Socket socket = new Socket("999.999.999.999", 80);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // 없는 도메인명인 경우
    private static void unknownHostEx2() throws IOException {
        try {
            Socket socket = new Socket("google.gogo", 80);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // 연결이 거절되었다는 뜻 == 네트워크를 통해 해당 IP의 서버에 접속은 했다는 뜻
    // 없는 포트인 경우
    // 방화벽에서 무단 연결로 인지하고 연결을 막는 경우
    // TCP 통신 중 RST(Reset) 라는 패킷을 받으면 자바가 예외를 터트린다.
    private static void connectionRefused() throws IOException {
        try {
            Socket socket = new Socket("localhost", 45678);
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
