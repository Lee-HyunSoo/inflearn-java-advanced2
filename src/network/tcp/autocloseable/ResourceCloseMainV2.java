package network.tcp.autocloseable;

public class ResourceCloseMainV2 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외 처리");
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("CloseException 예외 처리");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = null;
        ResourceV1 resource2 = null;
        try {
            resource1 = new ResourceV1("resource1");
            resource2 = new ResourceV1("resource2");

            resource1.call();
            resource2.callEx(); // CallException 발생
        } catch (CallException e) {
            System.out.println("ex: " + e);
            throw e;
        } finally {
            if (resource2 != null) {
                resource2.closeEx(); // CloseException 발생
            }
            if (resource1 != null) {
                resource1.closeEx(); // 이 코드 호출 안됨!
            }
        }

        // CallException이 터지고 finally로 갔는데 CloseException이 또 터진 상황
        // 이러면 밖으로 CloseException이 나간다.
        // 결국 CallException이 사라지고, resource1.closeEx()가 호출이 안된다.
    }
}
