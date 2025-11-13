package reflection;

import reflection.data.Team;
import reflection.data.User;

public class FieldV3 {

    public static void main(String[] args) {
        // 리플렉션 활용 예시
        // null 이 많은 객체가 들어왔을 때 어떻게 처리할 것인가?
        // 일반적으로는 getter, setter를 통해 직접 비교, 변경해야한다.
        // -> 리플렉션으로 해결 가능
        User user = new User("id", null, null);
        Team team = new Team("team1", null);

        System.out.println("===== before =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);

        if (user.getId() == null) {
            user.setId("");
        }
        if (user.getName() == null) {
            user.setName("");
        }
        if (user.getAge() == null) {
            user.setAge(0);
        }

        if (team.getId() == null) {
            team.setId("");
        }
        if (team.getName() == null) {
            team.setName("");
        }

        System.out.println("===== after =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);
    }
}
