package was.httpserver;

import java.io.IOException;

// Servlet = HTTP + Server + Applet
// HTTP 서버에서 실행되는 작은 자바 프로그램(애플릿)
public interface HttpServlet {

    void service(HttpRequest request, HttpResponse response) throws IOException;
}
