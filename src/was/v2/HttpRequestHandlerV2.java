package was.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.MyLogger.log;

public class HttpRequestHandlerV2 implements Runnable {

    private final Socket socket;

    public HttpRequestHandlerV2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            log(e);
        }
    }

    private void process() throws IOException {
        // stream을 Reader, Writer로 변경 시에는 항상 인코딩을 확인!
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             // autoFlush를 true로 설정하면 println()으로 출력할 때마다 자동으로 flush 되지만,
             // 네트워크 전송 횟수가 많아진다. (= 한 패킷에 데이터양이 적다.)
             // false로 설정하여 데이터를 모아서 전송하여 네트워크 전송 횟수를 효과적으로 줄이고,
             // 한 패킷에 많은 양의 데이터를 담아서 전송할 수 있다.
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8)) {

            String requestString = requestToString(reader);
            // 최근 웹 브라우저는 사용자의 요청 외에 자체적으로
            // 파비콘용 아이콘을 달라고 서버에 계속 요청을 날린다.
            // GET /favicon.ico HTTP/1.1
            if (requestString.contains("/favicon.ico")) {
                log("favicon 요청");
                return;
            }

            log("HTTP 요청 정보 출력");
            System.out.println(requestString);

            log("HTTP 응답 생성중...");
            sleep(5000); // 서버 처리 시간 가정
            responseToClient(writer);
            log("HTTP 응답 전달 완료");
        }
    }

    private static String requestToString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private void responseToClient(PrintWriter writer) {
        // 웹 브라우저에 전달하는 내용
        String body = "<h1>Hello World</h1>";
        int length = body.getBytes(UTF_8).length;

        // HTTP 공식 스펙에서 다음라인은 \r\n(캐리지 리턴 + 라인 피드)로 표현한다.
        // 참고로 \n 만 사용해도 대부분의 웹 브라우저에서 문제없이 작동한다.
        // 캐리지 리턴: 옛날 타자기에서 쓰던 문장의 커서가 문장 맨처음으로 가는 것
        // 라인 피드: 다음줄로
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-type: text/html\r\n");
        sb.append("Content-Length: ").append(length).append("\r\n");
        sb.append("\r\n");
        sb.append(body);

        log("HTTP 응답 정보 출력");
        System.out.println(sb);

        writer.println(sb);
        writer.flush();
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
