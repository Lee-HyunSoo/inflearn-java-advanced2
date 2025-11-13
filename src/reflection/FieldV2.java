package reflection;

import reflection.data.User;

import java.lang.reflect.Field;

public class FieldV2 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        User user = new User("id1", "userA", 20);
        System.out.println("기존 이름 = " + user.getName());

        Class<? extends User> aClass = user.getClass();
        Field nameField = aClass.getDeclaredField("name");

        // private 필드에 접근 허용 후 변경. private 메서드도 이렇게 호출 가능
        nameField.setAccessible(true);
        nameField.set(user, "userB");
        System.out.println("변경 된 이름 = " + user.getName());
    }
    
    // 리플렉션과 주의사항
    // 리플렉션을 사용하면 private 접근 제어자에도 접근해 값을 변경할 수 있다.
    // 이는 객체 지향 프로그래밍의 원칙을 위반하는 행위이고, 캡슐화 및 유지보수성에 악영향이 있다.
    // 1. 클래스의 내부 구조나 구현 세부 사항이 변경될 경우 리플렉션을 사용한 코드는 쉽게 깨질 수 있다.
    // ex) 위 코드 필드명이 name -> username 으로 바뀐다면? 실행 불가능하고,
    // 2. String 값으로 호출해오는 것이기 때문에 컴파일러가 잡을 수가 없다.
    // ex) 클래스 메서드호출은 오타가 나면 빨간줄이 뜨지만, name을 names로 오타를 내도 모름
}
