package io.member.impl;

import io.member.Member;
import io.member.MemberRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataMemberRepository implements MemberRepository {

    private static final String FILE_PATH = "temp/members-data.dat";

    @Override
    public void add(Member member) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH, true))) {
            dos.writeUTF(member.getId());
            dos.writeUTF(member.getName());
            dos.writeInt(member.getAge());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_PATH))) {
            while (dis.available() > 0) {
                String id = dis.readUTF();
                String name = dis.readUTF();
                int age = dis.readInt();
                members.add(new Member(id, name, age));
            }
            return members;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 어떻게 id1id2 이런식으로 저장이 되는데 제대로 읽어오는가?
    // writeUTF()는 문자 저장 시 2byte를 추가로 사용해서 앞에 글자의 길이를 저장 (65535 길이까지만)
    // 3id1 (2byte 문자길이 + 3byte 실제 데이터)
    // 자바의 Int는 4byte를 사용하기 때문에 4byte를 사용해서 파일 저장, 읽을 때도 4byte를 읽음
    // 따라서 readUTF()로 읽어들일 때 먼저 앞의 2byte로 글자의 길이를 확인하고 해당 길이만큼 글자를 읽는다.
    
    // 문제
    // 편리하게 저장할 수 있지만, 회원의 필드 하나하나를 각 타입에 맞도록 저장해야한다.
    // Member의 필드가 몇십개라면?
    // 현재는 객체를 저장한다기보단 Member의 필드를 각각 저장한 것
}
