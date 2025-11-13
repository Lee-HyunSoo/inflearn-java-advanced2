package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Method;

public class MethodV1 {

    public static void main(String[] args) {
        Class<BasicData> helloClass = BasicData.class;

        // 해당 클래스와 상위 클래스에서 상속 된 모든 public 메서드 반환
        System.out.println("===== methods() =====");
        Method[] methods = helloClass.getMethods();
        for (Method method : methods) {
            System.out.println("method = " + method);
        }

        // 해당 클래스에 선언 된 모든 메서드 반환 (접근 제어자 상관 x, 상속 된 메서드 미포함)
        System.out.println("===== declaredMethods() =====");
        Method[] declaredMethods = helloClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println("method = " + method);
        }
    }
}
