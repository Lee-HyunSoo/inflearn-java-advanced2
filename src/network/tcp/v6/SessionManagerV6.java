package network.tcp.v6;

import java.util.ArrayList;
import java.util.List;

// 동시성 처리
// synchronized 키워드가 붙은 메서드를 호출 시 해당 객체(this)의 모니터락을 들고 들어가 실행한다.
// 그래서 다른 스레드에서 synchronized가 붙은 메서드들을 호출 불가능해진다.
// synchronized가 없는 메서드는 자유롭게 호출 가능하다.
public class SessionManagerV6 {

    private List<SessionV6> sessions = new ArrayList<>();

    public synchronized void add(SessionV6 session) {
        sessions.add(session);
    }

    public synchronized void remove(SessionV6 session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (SessionV6 session : sessions) {
            session.close();
        }
        sessions.clear();
    }
}
