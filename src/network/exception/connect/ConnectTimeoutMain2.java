package network.exception.connect;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectTimeoutMain2 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        try {
            // 연결 정보(IP, Port)를 안주면 객체만 생성, 연결 안함
            Socket socket = new Socket();
            // 1초간 연결 안되면 timeout
            socket.connect(new InetSocketAddress("192.168.1.250", 45678), 1000);
        } catch (ConnectException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("end = " + (end - start));

        // 연결 자체가 문제
        // 소켓 생성 시 타임아웃을 직접 설정하면
        // java.net.SocketTimeoutException: Connect timed out 이 발생한다.
    }
}
