package network.tcp.autocloseable;

public class ResourceCloseMainV4 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외 처리");
            Throwable[] suppressed = e.getSuppressed();
            for (Throwable throwable : suppressed) {
                // suppressedEx = network.tcp.autocloseable.CloseException: resource2 ex
                // suppressedEx = network.tcp.autocloseable.CloseException: resource1 ex
                System.out.println("suppressedEx = " + throwable);
            }
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("CloseException 예외 처리");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
        try (ResourceV2 resource1 = new ResourceV2("resource1");
             ResourceV2 resource2 = new ResourceV2("resource2")) {
            resource1.call();
            resource2.callEx(); // CallException
        } catch (CallException e) {
            System.out.println("ex: " + e);
            throw e;
        }

        // 현재 try-with-resources를 통해 close()를 자동으로 호출
        // 이러면 try() 안에 선언한 순서의 반대로 close()를 잘 호출해준다.
        // 다만, 현재 close() 내에서 예외가 발생하게 구현한 상황
        // 하지만 CloseException이 외부로나가지 않고 CallException이 나간다.

        // 즉, try-with-resource는 자원 해제 중 예외가 터지면 중요 예외가 아니라 간주하고
        // 중요 예외를 밖으로 내보낸다.
        // 그럼 CloseException은 어디로 갔는가?
        // 사라진 것은 아니고, 자원 정리할 때 발생한 예외는 CallException 내부로 자바가 넣어준다.
        // 즉, e.addSuppressed(); 를 통해 핵심 예외 안에 넣어준다.
    }
}
