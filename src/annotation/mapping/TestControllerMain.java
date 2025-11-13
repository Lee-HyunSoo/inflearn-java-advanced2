package annotation.mapping;

import java.lang.reflect.Method;

public class TestControllerMain {

    public static void main(String[] args) {
        TestController testController = new TestController();
        Class<? extends TestController> aClass = testController.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
//            System.out.println("method = " + method);
            SimpleMapping simpleMapping = method.getAnnotation(SimpleMapping.class);
            if (simpleMapping != null) {
                // [/] -> public void annotation.mapping.TestController.home()
                // [/site1] -> public void annotation.mapping.TestController.page1()
                System.out.println("[" + simpleMapping.value() + "] -> " + method);
            }
        }
    }
}
