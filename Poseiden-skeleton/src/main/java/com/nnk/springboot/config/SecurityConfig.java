package com.nnk.springboot.config;

import com.nnk.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CustomOAuthUserService customOAuthUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Override
    protected void configure(final HttpSecurity http) throws Exception {
  //      CustomAuthenticationFilter customAuthenticationFilter= new CustomAuthenticationFilter(authenticationManagerBean());

        http.cors().and().csrf().disable();
        http.authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/validate").permitAll()
                .antMatchers("/Index").permitAll()
                .antMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**", "/curvePoint/**").hasAnyAuthority("ADMIN", "USER", "ROLE_USER")
                .antMatchers("/user/**").hasAuthority("ADMIN").anyRequest().authenticated();

        http.formLogin()
             //   .loginPage("/app-login")
               // .loginProcessingUrl("/login")
                .defaultSuccessUrl("/bidList/list", true).permitAll()
               .failureUrl("/login?error").and().rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe");
              http.oauth2Login()
                      //.userInfoEndpoint().userService(customOAuthUserService).and()
             .defaultSuccessUrl("/bidList/list").permitAll()
                      .failureUrl("/login?error");

        http.logout()
                .deleteCookies("JSESSIONID").logoutUrl("/app-logout")
                .logoutSuccessUrl("/app-login")
                .clearAuthentication(true)
                .invalidateHttpSession(true);
      //  http.addFilter(customAuthenticationFilter);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(
            final WebSecurity web) throws Exception {
   	web.ignoring().antMatchers( "/**/*.*" );
        web.ignoring().antMatchers("/**/*.png", "/**/*.PNG",
                "/**/*.GIF", "/**/*.gif", "/static/**",
                "error.html", "/403.html", "/home.html",
                "/login.html");
    }


}
