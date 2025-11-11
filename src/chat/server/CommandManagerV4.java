package chat.server;

import chat.server.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandManagerV4 implements CommandManager {

    private static final String DELIMITER = "\\|";
    private final Map<String, Command> commands = new HashMap<>();
    private final Command defaultCommand = new DefaultCommand();

    public CommandManagerV4(SessionManager sessionManager) {
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

        // Null Object Pattern
        Command command = commands.getOrDefault(key, defaultCommand);
        command.execute(args, session);
    }

    // Null Object Pattern
    // null을 객체처럼 다루는 패턴
    // null 대신 사용할 수 있는 특별한 객체를 만들어, null로 인해 발생할 수 있는
    // 예외 상황을 방지하고 코드 간결성을 높인다.
    // Null Object Pattern을 사용하면 null 대신 특정 동작을 하는 객체를 반환해,
    // 클라이언트 코드에서 null 체크를 할 필요가 없어진다.
    // -> 불필요한 조건문을 줄이고 객체의 기본 동작을 정의하는데 유용하다.

    // Command Pattern
    // 요청을 독립적인 객체로 변환해서 처리한다.
    // 분리 : 작업을 호출하는 객체와 작업을 수행하는 객체를 분리
    // 확장성 : 기존 코드를 변경하지 않고 새로운 명령 추가 가능
    // 이를 통해 새로운 커맨드를 쉽게 추가할 수 있고, 수정해야하는 객체가 명확해진다.
}
