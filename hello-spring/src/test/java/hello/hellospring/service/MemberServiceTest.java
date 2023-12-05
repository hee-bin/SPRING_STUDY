package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("hello");
        Member member2 = new Member();
        member2.setName("hello");

        //when
        memberService.join(member1);
        /*
        IllegalStateException 예외가 발생하는지를 확인하는 테스트 케이스.
      assertThrows 메서드는 예외가 발생하는지 여부를 검증하고,
        예외가 발생하지 않으면 테스트 실패.  예외가 발생해야 테스트 성공*/
        IllegalStateException e  = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
        /*   혹은 try - catch로도 테스트 가능
        try{
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
        }*/
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}