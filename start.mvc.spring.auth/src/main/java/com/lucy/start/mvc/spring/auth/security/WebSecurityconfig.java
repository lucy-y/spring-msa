package com.lucy.start.mvc.spring.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityconfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserInformationService userInformationService;
		
	@Override
    public void configure(HttpSecurity http) throws Exception {
		// h2 �����͸� Ȯ���ϱ����� h2-console url�� ������ permitAll���� �ٲپ� �ݴϴ�. 
		http.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
			.and().csrf().disable()
				.headers().disable();
        
    }
	
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
    	// custom user���� ���񽺸� ����ϱ����� �����Դϴ�. 
    	builder.authenticationProvider(authenticationProvider());
    }


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// authenticationManage �� ��� 
		return super.authenticationManagerBean();
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		// Spring5���� PasswordEncoder ������ �ʼ��� �������־�� �մϴ�. 
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	// custom user���� ���񽺸� ����ϱ����� �����Դϴ�. 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userInformationService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}