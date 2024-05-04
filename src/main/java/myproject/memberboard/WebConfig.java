package myproject.memberboard;

import myproject.memberboard.web.interceptor.LogInterceptor;
import myproject.memberboard.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
/*        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**", "/error", "/*.ico")
                .excludePathPatterns("/css/**");*/
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/join", "/login", "/logout", "/css/**", "/error", "/*.ico");
    }
}
