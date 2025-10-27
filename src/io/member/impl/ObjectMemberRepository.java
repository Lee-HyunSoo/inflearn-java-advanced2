package io.member.impl;

import io.member.Member;
import io.member.MemberRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectMemberRepository implements MemberRepository {

    private static final String FILE_PATH = "temp/members-obj.dat";

    @Override
    public void add(Member member) {
        List<Member> members = findAll();
        members.add(member);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            // 객체 직렬화는 객체를 통으로 파일에 저장하는 것
            // 즉, 멤버별로 저장하려하면 멤버 하나에 파일이 하나 생긴다.
            // 그래서 위에서 List findAll로 가져온 것
            oos.writeObject(members);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> findAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object findObject = ois.readObject();
            return (List<Member>) findObject;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 객체 직렬화
    // 자바 객체 직렬화는 메모리에 있는 객체 인스턴스를 바이트 스트림으로 변환하여
    // 파일에 저장하거나 네트워크를 통해 전송할 수 있도록 하는 기능
    // 이 과정에서 객체의 상태를 유지하여 나중에 역직렬화를 통해 원래의 객체로 복원 가능

    // 객체 직렬화를 사용하려면 반드시 Serializable 인터페이스를 구현해야한다.
    // 이 인터페이스는 아무 기능이없고, 직렬화 가능한 클래스라는 것을 표시하기 위한 용도
    // 이렇게 메서드 없이 단지 표시가 목적인 인터페이스를 마커 인터페이스라고 한다.
    // 해당 인터페이스가 없이 직렬화를 하려하면 NotSerializableException 이 터진다.
    
    // 객체 직렬화를 사용하면 객체를 바이트로 변환할 수 있어 모든 종류의 스트림에 전달 가능
    // 파일 저장 뿐만아니라 네트워크를 통해 객체 전송도 가능 -> 초기에 분산 시스템에 활동
    // 객체 직렬화는 1990년대 등장한 기술로 초창기에는 인기가 있었지만 여러 단점이 드러나
    // 대안 기술이 등장하며 현재는 거의 사용하지 않음
    
    // 참고
    // serialVersionUID: 객체 직렬화 버전을 관리
    // transient 키워드: transient가 붙어있는 필드는 직렬화 하지 않고 무시

    // 객체 직렬화의 한계
    // 1. 버전 관리의 어려움
    // 클래스 구조가 변경되면 (필드 추가 등...) 이전에 직렬화된 객체와의 호환성 문제 발생
    // serialVersionUID 관리가 복잡
    // 2. 플랫폼 종속성
    // 자바 직렬화는 자바 플랫폼에 종속적이어서 다른 언어나 시스템과의 상호 운용성이 떨어진다.
    // 3. 성능 이슈
    // 직렬화, 역직렬화 과정이 상대적으로 느리고 리소스를 많이 사용한다.
    // 4. 유연성 부족
    // 직렬화 된 형식을 커스터마이즈하기 어렵다.
    // 5. 크기 효율성
    // 직렬화 된 데이터의 크기가 상대적으로 크다. (파일이 길다)

    // 객체 직렬화의 대안
    // 1. XML
    // 유연하고 강력했지만, 복잡성과 무거움
    // 태그를 포함한 XML 문서의 크기가 커서 네트워크 전송 비용 증가

    // 2. JSON
    // 가볍고 간결하며, JS와의 자연스러운 호환성 때문에 웹 개발자들 사이에서 빠르게 확산
    // 2000년대 후반, 웹 API와 RESTful 서비스가 대중화되며,
    // 사실상 표준 데이터 교환 포맷으로 자리 잡음

    // 3. Protobuf, Avro - 더 작은 용량, 더 빠른 성능
    // JSON은 거의 모든 곳에서 호환이 가능하고, 사람이 읽고 쓰기 쉬운 텍스트 기반 포맷이라
    // 디버깅과 개발이 쉽다.
    // 만약 매우 작은 용량으로 더 빠른 속도가 필요하다면 Protobuf, Avro 같은 대안 기술
    // 호환성은 떨어지지만 byte 기반에, 용량과 성능 최적화가 되어있어 매우 빠르다.
    // 다만 byte 기반이라 사람이 직접 읽기 어렵다.
}
