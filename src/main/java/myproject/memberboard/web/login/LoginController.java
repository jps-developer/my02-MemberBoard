package myproject.memberboard.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                          HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        if(loginService.login(form.getLoginId(), form.getPassword()).isPresent()){
            Member loginMember = loginService.login(form.getLoginId(), form.getPassword()).get();
            log.info("loginMember={}",loginMember);
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
            return "redirect:/";
        }else{
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }
    }
    @PostMapping("/logout")
    public String logOutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
