package network.tcp.v6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV6 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        SessionManagerV6 sessionManager = new SessionManagerV6();
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        // ShutdownHook 등록
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        // 자바가 종료될 때 shutdown 스레드가 동작 후 종료
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));

        try {
            while (true) {
                // 블로킹
                // 블로킹 되다보니 accept() 실행 중 serverSocket을 다른 스레드에서 close 하면
                // 메인스레드에서 이걸 기다리다가 SocketClosedException을 만난다.
                // 때문에 try-catch 추가
                Socket socket = serverSocket.accept();
                log("소켓 연결: " + socket);

                SessionV6 session = new SessionV6(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            log("서버 소켓 종료: " + e);
        }
    }

    // 자바가 종료가 될 때 자동으로 호출되는 Hook 생성
    static class ShutdownHook implements Runnable {

        private final ServerSocket serverSocket;
        private final SessionManagerV6 sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManagerV6 sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
                sessionManager.closeAll();
                serverSocket.close();

                Thread.sleep(1000); // 자원 정리 대기
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }
        }
    }

    // 자원 종료 대기 이유
    // 보통 모든 non 데몬 스레드의 실행이 완료되면 자바 프로세스가 정상 종료된다.
    // 하지만 사용자가 ctrl + c, kill -9 등으로 종료할 수도 있다.
    // 이러면 스레드가 살아있던 아니던 강제로 다 종료된다.
    // 단, 셧다운 훅이 있으면 그것의 실행을 기다려준다.
    // 셧다운 훅의 실행이 끝나면 non 데몬 스레드의 실행 여부와 상관없이 자바 프로세스는 종료
    // 따라서 다른 스레드가 자원을 정리하거나 로그를 남길 수 있도록 잠시 대기해준다.
}
