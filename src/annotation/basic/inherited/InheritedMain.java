package annotation.basic.inherited;

import java.lang.annotation.Annotation;

public class InheritedMain {

    public static void main(String[] args) {
        // - InheritedAnnotation
        // - NoInheritedAnnotation
        print(Parent.class);
        // - InheritedAnnotation
        print(Child.class);
        // - InheritedAnnotation
        // - NoInheritedAnnotation
        print(TestInterface.class);
        // 아무것도 없음
        // 인터페이스 구현에서는 부모의 에노테이션을 상속받을 수 없다.
        print(TestInterfaceImpl.class);
    }

    private static void print(Class<?> clazz) {
        System.out.println("class: " + clazz);
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(" - " + annotation.annotationType().getSimpleName());
        }
        System.out.println();
    }

    // @Inherited 가 클래스(+추상클래스) 상속에만 적용되는 이유
    // 1. 클래스 상속과 인터페이스 구현의 차이
    // 클래스 상속은 자식 클래스가 부모 클래스의 속성과 메서드를 상속
    // 즉, 자식 클래스는 부모 클래스의 특성을 이어 받으므로,
    // 부모 클래스에 정의된 에노테이션을 자식 클래스가 자동으로 상속받을 수 있는 논리적 기반이 있다.
    // 하지만 인터페이스는 메서드의 시그니처만 정의할 뿐, 상태나 행위를 가지지 않기 때문에,
    // 인터페이스의 구현체가 에노테이션을 상속한다는 개념이 잘 맞지 않는다.

    // 2. 인터페이스의 다중 구현, 다이아몬드 문제
    // 인터페이스는 다중 구현이 가능하다. 만약 인터페이스의 에노테이션을 구현 클래스에서
    // 상속하게 되면 여러 인터페이스의 에노테이션간의 충돌이나 모호한 상황이 발생할 수 있다.
}
