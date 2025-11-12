package was.v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.MyLogger.log;

public class HttpRequestHandlerV3 implements Runnable {

    private final Socket socket;

    public HttpRequestHandlerV3(Socket socket) {
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
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8)) {

            String requestString = requestToString(reader);
            if (requestString.contains("/favicon.ico")) {
                log("favicon 요청");
                return;
            }

            log("HTTP 요청 정보 출력");
            System.out.println(requestString);

            log("HTTP 응답 생성중...");
            if (requestString.startsWith("GET /site1")) {
                site1(writer);
            } else if (requestString.startsWith("GET /site2")) {
                site2(writer);
            } else if (requestString.startsWith("GET /search")) {
                search(writer, requestString);
            } else if (requestString.startsWith("GET / ")) { // 공백 필수!
                home(writer);
            } else {
                notFound(writer);
            }

            log("HTTP 응답 전달 완료");
        }
    }

    private void home(PrintWriter writer) {
        // 원칙적으로는 Content-Length를 계산해서 전달해야 하지만, 예제를 단순하게 설명하기 위해 생략
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>home</h1>");
        writer.println("<ul>");
        writer.println("<li><a href='/site1'>site1</a></li>");
        writer.println("<li><a href='/site2'>site2</a></li>");
        writer.println("<li><a href='/search?q=hello'>검색</a></li>");
        writer.println("</ul>");
        writer.flush();
    }

    private void site1(PrintWriter writer) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>site1</h1>");
        writer.flush();
    }

    private void site2(PrintWriter writer) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>site2</h1>");
        writer.flush();
    }

    private void search(PrintWriter writer, String requestString) {
        // GET /search?q=hello HTTP/1.1 로 요청이 오니 q= ~ 공백까지 자름
        int startIndex = requestString.indexOf("q=");
        int endIndex = requestString.indexOf(" ", startIndex + 2);
        String query = requestString.substring(startIndex + 2, endIndex);
        String decode = URLDecoder.decode(query, UTF_8);

        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>Search</h1>");
        writer.println("<ul>");
        writer.println("<li>query: " + query + "</li>");
        writer.println("<li>decode: " + decode + "</li>");
        writer.println("</ul>");
        writer.flush();
    }

    private void notFound(PrintWriter writer) {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>404 페이지를 찾을 수 없습니다.</h1>");
        writer.flush();
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

    // 인터넷이 처음 설계되던 시기 (1980~1990년 대)
    // 대부분의 컴퓨터 시스템은 ASCII 문자 집합 사용
    // 전 세계 시스템 호환을 위해, URL은 단일 문자 인코딩 체계를 사용해야 했다.
    // 당시 모든 시스템이 비 ASCII 문자를 처리할 수 없어서 ASCII를 선택

    // HTTP URL이 ASCII 만 지원하는 이유는 초기 인터넷의 기술적 제약과
    // 전세계 호환을 위한 선택 (HTTP 스펙은 매우 보수적이고, 호환을 가장 우선시한다.)

    // % 인코딩
    // 한글을 UTF-8로 인코딩하면 3byte를 사용하고, 각각의 바이트 문자 앞에 %를 붙인다.
    // 가 = %EA%B0%80 (이렇게 ASCII 문자를 사용해 16진수로 표현된 UTF-8을 표현)
    // 이렇게 각각의 16진수 byte를 문자로 표현 후 해당 문자 앞에 %를 붙이는 것
    // URLEncoder.encode(), URLDecoder.decode()를 사용해 % 인코딩, 디코딩 처리 가능

    // 단점
    // 가는 3byte가 필요하지만 %인코딩을 사용하면 9byte를 사용하게 된다.
    // 하지만 HTTP는 호환을 제일 1순위로 두기때문에 어쩔 수 없이 사용
    // 용량을 많이 사용하는 문제가 있지만, URL 이나 헤더부분은 내용이 보통 많지 않고,
    // HTTP 메세지 바디는 UTF-8 인코딩을 쓸 수 있기 때문에 크게 문제가 되진 않는다.
}
