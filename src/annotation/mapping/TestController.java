package annotation.mapping;

public class TestController {

    @SimpleMapping(value = "/")
    public void home() {
        System.out.println("TestController.home");
    }

    @SimpleMapping(value = "/site1")
    public void page1() {
        System.out.println("TestController.page1");
    }

    // 애노테이션은 프로그램 코드가 아니다.
    // 애노테이션이 붙어있는 home(), page1() 같은 코드를 호출해도 프로그램에는 아무런 영향이 없다.
    // 마치 주석과 유사하다.
    // 다만 일반적인 주석이 아니라, 리플렉션 같은 기술로 실행 시점에 읽어서 활용할 수 있는 특별한 주석이다.

    // Annotation = 주석 / 메모
    // 코드에 추가적인 정보를 주석처럼 제공한다.
    // 하지만 일반 주석과 달리, 컴파일러나 런타임에서 해석될 수 있는 메타데이터를 제공한다.
    // 즉, 애노테이션은 코드에 메모를 달아놓는 것 처럼 특정 정보나 지시를 추가하는 도구로,
    // 코드에 대한 메타데이터를 표현하는 방법이다.
    // 즉, 코드에 대한 추가적인 정보를 주석처럼 달아 놓는다는 뜻
}
