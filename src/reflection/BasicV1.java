package reflection;

import reflection.data.BasicData;

public class BasicV1 {

    public static void main(String[] args) throws ClassNotFoundException {
        // 클래스 메타데이터 조회 방법 3가지
        // 클래스 메타데이터는 Class 라는 객체로 표현된다.

        // 1. 클래스에서 찾기
        Class<BasicData> basicDataClass1 = BasicData.class;
        System.out.println("basicDataClass1 = " + basicDataClass1);

        // 2. 인스턴스에서 찾기
        BasicData basicInstance = new BasicData();
        Class<? extends BasicData> basicDataClass2 = basicInstance.getClass();
        System.out.println("basicDataClass2 = " + basicDataClass2);

        // 3. 문자로 찾기
        String className = "reflection.data.BasicData"; // 패키지명 주의
        Class<?> basicDataClass3 = Class.forName(className);
        System.out.println("basicDataClass3 = " + basicDataClass3);
    }

    // Parent parent = new Child();
    // Class<? extends Parent> parentClass = parent.getClass();
    // 이런식으로 Parent 타입을 통해 getClass()를 호출했는데
    // 실제 인스턴스는 Child 일 수 있기 때문에,
    // 자식 타입도 허용하도록 ? extends Parent를 사용한다.
}
