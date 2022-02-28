package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){
//        Member findMember = memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다"));
//        if(findMember.getPassword().equals(password)){
//            return findMember;
//        } else{
//            return null;
//        }

        //optional을 사용하여 길이를 줄임
        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }


}
