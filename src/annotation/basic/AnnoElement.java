package annotation.basic;

import util.MyLogger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnnoElement {

    String value();
    int count() default 0;
    String[] tags() default {};

    // 에노테이션에서는 내가만든 클래스타입은 선언 불가능
    // MyLogger data();
    // 이런식으로 클래스 정보는 가져올 수 있다.
    Class<? extends MyLogger> annoData() default MyLogger.class;
    
    // 예외 선언 불가, void 반환타입 불가
}