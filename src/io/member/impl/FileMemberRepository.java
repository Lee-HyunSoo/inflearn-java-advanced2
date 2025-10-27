package io.member.impl;

import io.member.Member;
import io.member.MemberRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileMemberRepository implements MemberRepository {

    private static final String FILE_PATH = "temp/members-txt.dat";
    private static final String DELIMITER = ","; // 구분자

    @Override
    public void add(Member member) {
        // try-with-resources를 통해 bw.close() 자동 호출
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, UTF_8, true))) {
            bw.write(member.getId() + DELIMITER + member.getName() + DELIMITER + member.getAge());
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH, UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // id1,name1,30
                String[] memberData = line.split(DELIMITER);
                members.add(new Member(memberData[0], memberData[1], Integer.valueOf(memberData[2])));
            }
            return members;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 문제
    // 모든 타입을 문자로 저장하기 때문에 타입 변경 코드가 들어간다.
    // 입력할 때, 조회할 때 모두 구분자 관련 코드가 필요하다.
}
