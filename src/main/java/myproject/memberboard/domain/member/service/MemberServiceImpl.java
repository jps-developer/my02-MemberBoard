package myproject.memberboard.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.exception.MemberNotFoundException;
import myproject.memberboard.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) {
        if(memberRepository.findById(id).isPresent()){
            return memberRepository.findById(id).get();
        }else{
            throw new MemberNotFoundException(id + " : 는 존재 하지않는 회원입니다.");
        }
    }

    @Override
    public Member findByLoginId(String loginId) {
        if(memberRepository.findByLoginId(loginId).isPresent()){
            return memberRepository.findByLoginId(loginId).get();
        }else{
            throw new MemberNotFoundException(loginId + " : 는 존재 하지않는 회원입니다.");
        }
    }

    @Override
    public void updateMember(Long id, Member member) {
        if(memberRepository.update(id, member).isPresent()){
            //return memberRepository.update(id, member).get();
            log.info("update Member={}",member);
        }else{
            throw new MemberNotFoundException(id + " : 는 존재 하지않는 회원입니다.");
        }
    }

    @Override
    public void deleteMember(Long id) {
        if(memberRepository.delete(id)){
            log.info("delete Member id={}",id);
        }else{
            throw new MemberNotFoundException(id + " : 는 존재 하지않는 회원입니다.");
        }
    }
}
