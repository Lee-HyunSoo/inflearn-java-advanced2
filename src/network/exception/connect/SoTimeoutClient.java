package network.exception.connect;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SoTimeoutClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        InputStream input = socket.getInputStream();

        try {
            socket.setSoTimeout(3000); // 타임아웃 시간 설정
            int read = input.read();
            System.out.println("read = " + read);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TCP 에서 종료의 의미인 FIN 패킷을 상대방에게 전달.
        // FIN 패킷을 받으면 상대방도 socket.close()를 호출해서 FIN 패킷을 상대방에게 전달해야한다.
        // 참고로 ACK 는 OS 단에서 자동으로 던져진다.
        socket.close();
    }

    // 연결은 됐는데 응답이 안오는 경우
    // java.net.SocketTimeoutException: Read timed out
    // 시간을 설정하지 않으면 서버가 멈추거나 그러면 클라이언트는 무한 대기해야한다.
    // 즉, 타임아웃을 설정하지않으면 read() 메서드는 무한 대기한다.
    
    // 실무에서 가장 큰 장애 발생원인 중 하나!
    // 연결 타임아웃, 소켓 타임아웃(read 타임 아웃)을 누락하기 때문에 발생
    
    // 신용카드를 처리하는 3개의 회사가 있다고 가정
    // 고객 -> 주문서버 -> 신용카드 A 회사 (정상)
    // 고객 -> 주문서버 -> 신용카드 B 회사 (정상)
    // 고객 -> 주문서버 -> 신용카드 C 회사 (문제)
    // 처음엔 A, B는 정상 동작하고 C만 오류겠지만
    // 주문서버는 신용카드 C의 결제에 대해 고객에게 응답을 주지 못하고 계속 대기하는 스레드가 늘어남
    // 결국 주문서버에 장애가 발생, A B C 카드 전부 주문을 할 수 없는 문제 발생
    // 만약 주문서버에 연결, 소켓 타임아웃을 적절히 설정했다면 장애가 발생하지 않았을 것

    // 외부 서버와 통신을 하는 경우 연결 타임아웃과 소켓 타임아웃을 반드시 설정.
}
