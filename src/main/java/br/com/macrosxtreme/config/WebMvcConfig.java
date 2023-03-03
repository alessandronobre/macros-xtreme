package br.com.macrosxtreme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AuthInterceptor authInterceptor;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/calculator/**", "/css/login/**", "/css/**")
				.excludePathPatterns("/img/**")
				.excludePathPatterns("/js/calculator/**", "/js/login/**")
				.excludePathPatterns("/calculation")
				.excludePathPatterns("/result")
				.excludePathPatterns("/login")
				.excludePathPatterns("/create")
//				.excludePathPatterns("/teste")
				.excludePathPatterns("/forgot");
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
		
	}
	

}