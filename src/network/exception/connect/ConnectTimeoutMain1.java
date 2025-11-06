package network.exception.connect;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class ConnectTimeoutMain1 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        try {
            // 사설 IP 대역 (주로 공유기에서 사용하는 IP 대역)
            Socket socket = new Socket("192.168.1.250", 45678);
        } catch (ConnectException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("end = " + (end - start));
    }

    // OS 기본 대기 시간
    // TCP 연결을 시도했는데 연결 응답이 없다면 OS에는 연결 대기 타임아웃이 설정되어있다.
    // Windows: 약 21초 (21042)
    // Linux: 약 75~180초 사이
    // Mac: 약 75초

    // 연결 자체가 문제
    // java.net.ConnectException: Connection timed out: connect 발생
    // java.net.ConnectException: Operation timed out: connect 도 나올수도
}
