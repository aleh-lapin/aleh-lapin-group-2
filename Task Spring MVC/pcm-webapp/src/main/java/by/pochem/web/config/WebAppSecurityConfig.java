package by.pochem.web.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import by.pochem.web.auth.UserDetailsService;
import by.pochem.web.auth.authorize.HandlerSecurityInterceptor;
import by.pochem.web.auth.authorize.WebExpressionVoterAdapter;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService usersManager, PasswordEncoder passwordEncoder) throws Exception {
		auth
			.userDetailsService(usersManager)
			.passwordEncoder(passwordEncoder);
    }
	
	 @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	    	.ignoring()
	        	.antMatchers("/resources/**");
	  }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	    http
	    	.csrf().disable()
    		.authorizeRequests()
		        .antMatchers("/", "/join").permitAll() 
		        .anyRequest().authenticated()
	        .and()
		    .formLogin()
		        .loginPage("/login")
		        .defaultSuccessUrl("/")
		        .failureUrl("/login?error=INVALID_CREDS")
		        .usernameParameter("phoneOrEmail")
		        .passwordParameter("password")
		       	.permitAll()
		    .and()
		    .rememberMe()
		    .and()
		    .logout()                                    
		        .permitAll();
    }

    @Bean
    public HandlerSecurityInterceptor handlerSecurityInterceptor() {
      HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create();
      interceptor.setAccessDecisionManager(handlerAccessDecisionManager());
      interceptor.setAuthenticationManager(authenticationManagerBean());
      return interceptor;
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public AccessDecisionManager handlerAccessDecisionManager() {
      return new AffirmativeBased(Arrays.<AccessDecisionVoter> asList(webExpressionVoterAdapter()));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
      try {
        return super.authenticationManagerBean();
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }

    @Bean
    public WebExpressionVoterAdapter webExpressionVoterAdapter() {
      return new WebExpressionVoterAdapter(webExpressionVoter());
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public AccessDecisionManager webAccessDecisionManager() {
      return new AffirmativeBased(Arrays.<AccessDecisionVoter> asList(webExpressionVoter()));
    }

    @Bean
    public WebExpressionVoter webExpressionVoter() {
      WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
      DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
      expressionHandler.setRoleHierarchy(roleHierarchy());
      webExpressionVoter.setExpressionHandler(expressionHandler);
      return webExpressionVoter;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
      RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
      roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_COMPANY ROLE_ADMIN > ROLE_MANAGER ROLE_COMPANY > ROLE_USER ROLE_MANAGER > ROLE_USER");
      return roleHierarchy;
    }
    
}
