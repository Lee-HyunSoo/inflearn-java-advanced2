package was.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

public class HttpRequest {

    // GET ...
    private String method;
    // /search ...
    private String path;
    // q=hello ...
    private final Map<String, String> queryParameters = new HashMap<>();
    // Content-Type: text/html; charset=UTF-8 ...
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequest(BufferedReader reader) throws IOException {
        parseRequestLine(reader);
        parseHeaders(reader);
        // 메세지 바디는 이후에 처리
    }

    private void parseRequestLine(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null) {
            // 일부 브라우저의 경우 성능 최적화를 위해 TCP 연결을 추가로 하나 더 하는 경우가 있다.
            // (크롬의 경우 URL 쓰고 엔터 안쳐도 요청이 간다던가...)
            // 이 때 추가 연결을 사용하지 않고 그대로 종료하면, TCP 연결은 하지만 데이터는 전송하지 않고 연결을 끊게 된다.
            // 즉, 추가 연결쪽을 사용하지 않고 종료되면 아무 메세지도 날리지 않고 종료될 때가 있기에
            // 메세지를 하나 추가해 준 것
            throw new IOException("EOF: No request line received");
        }

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            throw new IOException("Invalid request line: " + requestLine);
        }

        method = parts[0]; // GET
        String[] pathParts = parts[1].split("\\?");
        path = pathParts[0]; // /search
        if (pathParts.length > 1) {
            parseQueryParameters(pathParts[1]);
        }
    }

    // key1=value1&key2=value2 ...
    private void parseQueryParameters(String queryString) {
        for (String param : queryString.split("&")) {
            String[] keyValue = param.split("=");
            String key = URLDecoder.decode(keyValue[0], UTF_8);
            // key= 하고 값이 없이 올 수 있기 때문에 한번 더 체크
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], UTF_8) : "";
            queryParameters.put(key, value);
        }
    }

    // Host: localhost:12345
    // Connection: keep-alive
    // ...
    private void parseHeaders(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()).isEmpty()) {
            String[] headerParts = line.split(":");
            // Connection: keep-alive 과 같이 : 뒤에 공백이 있기에 trim
            headers.put(headerParts[0].trim(), headerParts[1].trim());
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String name) {
        return queryParameters.get(name);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                ", headers=" + headers +
                '}';
    }
}
