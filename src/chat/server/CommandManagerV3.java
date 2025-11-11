package chat.server;

import chat.server.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandManagerV3 implements CommandManager {

    private static final String DELIMITER = "\\|";
    private final Map<String, Command> commands = new HashMap<>();

    public CommandManagerV3(SessionManager sessionManager) {
        commands.put("/join", new JoinCommand(sessionManager));
        commands.put("/message", new MessageCommand(sessionManager));
        commands.put("/change", new ChangeCommand(sessionManager));
        commands.put("/users", new UsersCommand(sessionManager));
        commands.put("/exit", new ExitCommand());
    }

    @Override
    public void execute(String totalMessage, Session session) throws IOException {
        String[] args = totalMessage.split(DELIMITER);
        String key = args[0];

        Command command = commands.get(key);
        if (command == null) {
            session.send("처리할 수 없는 명령어 입니다: " + totalMessage);
            return;
        }
        command.execute(args, session);
    }

    // command 패턴
    // 각각의 명령어 하나하나를 클래스로 구분
    // 다형성을 활용해서 각각의 명령어가 서로 영향을 끼치지 않으면서 실행할 수 있게 한다.
    
    // 여러 스레드가 동시에 commands Map 객체를 호출한다?
    // 여기서는 문제가 없다.
    // commands는 데이터 초기화 이후 데이터를 변경하지 않기 때문이다.
    // 멀티스레드 이슈가 발생하는 근본적인 원인은 값을 변경하기 때문이다.
}
