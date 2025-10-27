package io.member.impl;

import io.member.Member;
import io.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();

    @Override
    public void add(Member member) {
        members.add(member);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }

    // 문제
    // 자바를 끄면 데이터가 다 날아간다.
}
