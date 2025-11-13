package annotation.basic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface AnnoMeta {
}

// @Retention: 에노테이션의 생존 기간을 지정한다.
// RetentionPolicy.SOURCE: 소스 코드에만 남아있고 컴파일 시점에 제거됨
// RetentionPolicy.CLASS: 컴파일 후 class 파일까지는 남아있지만 자바 실행 시점에 제거 됨 (default)
// RetentionPolicy.RUNTIME: 자바 실행 중에도 남아있음. 대부분 이거 사용

// @Target: 에노테이션을 적용할 수 있는 위치를 지정한다.
// ElementType.TYPE: 타입(클래스, 인터페이스, enum...) 레벨
// ElementType.METHOD: 메서드 레벨
// ElementType.FIELD: 필드 레벨

// @Documented: 자바 API 문서를 만들 때 해당 에노테이션이 함께 포함되는지 지정. 보통 함께 사용

// @Inherited: 자식 클래스가 에노테이션을 상속받을 수 있다.