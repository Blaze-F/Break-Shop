package com.project.breakshop.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/static/js/**","/static/css/**","/static/img/**"
                        ,"/swagger-ui/**","/api-docs/**", "/users/signup", "/users/login", "/users/logout", "/users/").permitAll()    // LoadBalancer Chk
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login") //Post 요청
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error")
                .permitAll();

        //로그아웃 설정
        http.logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/");

        //비인가자 요청시 보낼 Api URI
        http.exceptionHandling().accessDeniedPage("/forbidden");


//                .and().antMatchers("/manage").hasAuthority("ROLE_ADMIN")
//                .formLogin()
//                .loginPage("/view/login")
//                .loginProcessingUrl("/loginProc")
//                .usernameParameter("id")
//                .passwordParameter("pw")
//                .defaultSuccessUrl("/view/dashboard", true)
//                .permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc"));


    }

}
