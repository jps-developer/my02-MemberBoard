package myproject.memberboard.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.exception.MemberNotFoundException;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    @Override
    public void join(Member member) {
        validateDuplicateMember(member.getLoginId());
        log.info("@@@@@@@service join member@@@@@@={}",member);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(String loginId){
        memberRepository.findByLoginId(loginId).ifPresent(
                m -> {throw new IllegalStateException("이미 존재하는 회원입니다.");}
        );
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
    public void updateMember(Long id, UpdateMemberForm updateParam) {
        memberRepository.update(id, updateParam);
        log.info("update Member={}",updateParam);
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
