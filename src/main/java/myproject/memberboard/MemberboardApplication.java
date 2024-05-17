package myproject.memberboard;

import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.config.*;
import myproject.memberboard.domain.member.repository.JDBCMemberRepository;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.repository.SQLExceptionTranslatorMemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


//@Import(MemoryRepositoryConfig.class)
//@Import(SQLExceptionTranslatorConfig.class)
//@Import(JDBCTemplateConfig.class)
//@Import(MybatisConfig.class)
@Import(JpaConfig.class)
@SpringBootApplication(scanBasePackages = "myproject.memberboard.web")
public class MemberboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberboardApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(MemberRepository memberRepository, BoardRepository boardRepository){
		return new TestDataInit(memberRepository, boardRepository);
	}
}

