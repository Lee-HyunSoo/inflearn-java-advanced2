package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class BasicV2 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<BasicData> basicData = BasicData.class;

        // basicData.getName() = reflection.data.BasicData
        System.out.println("basicData.getName() = " + basicData.getName());
        // basicData.getSimpleName() = BasicData
        System.out.println("basicData.getSimpleName() = " + basicData.getSimpleName());
        // basicData.getPackage() = package reflection.data
        System.out.println("basicData.getPackage() = " + basicData.getPackage());
        // basicData.getSuperclass() = class java.lang.Object
        System.out.println("basicData.getSuperclass() = " + basicData.getSuperclass());
        // 상속받은 인터페이스가 없어서 공백 []
        System.out.println("basicData.getInterfaces() = " + Arrays.toString(basicData.getInterfaces()));
        // false
        System.out.println("basicData.isInterface() = " + basicData.isInterface());
        // false
        System.out.println("basicData.isEnum() = " + basicData.isEnum());
        // false
        System.out.println("basicData.isAnnotation() = " + basicData.isAnnotation());
        // 자바에서 정한 여러 수정자에 대한 정보를 조합한 것을 사용
        int modifiers = basicData.getModifiers();
        // 1
        System.out.println("basicData.getModifiers() = " + modifiers);
        // true
        System.out.println("isPublic = " + Modifier.isPublic(modifiers));
        // Modifier.toString() = public
        System.out.println("Modifier.toString() = " + Modifier.toString(modifiers));
    }

    // 수정자는 접근 제어자와 비 접근 제어자(기타 수정자)로 나눌 수 있다.
    // 접근 제어자: public, protected, default(package-private), private
    // 비 접근 제어자: static, final, abstract, synchronized, volatile 등
    // getModifiers()를 통해 수정자가 조합된 숫자를 얻고, Modifier 클래스를 통해
    // 실제 수정자 정보를 확인할 수 있다.
}
