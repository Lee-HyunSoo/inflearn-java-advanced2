package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConstructV2 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName("reflection.data.BasicData");

        // 생성자 리플렉션을 통한 객체 생성 (동적)
        Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance("hello");
        System.out.println("instance = " + instance);

        // 생성한 객체로 메서드 호출 (동적)
        Method method1 = aClass.getDeclaredMethod("call");
        method1.invoke(instance);
    }
    
    // 스프링 같은데에선 내가 만든 클래스를 프레임워크가 대신 생성해줌
    // 다 리플렉션으로 만드는 것
}
