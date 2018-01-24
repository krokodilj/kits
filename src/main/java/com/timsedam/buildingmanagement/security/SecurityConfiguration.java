package com.timsedam.buildingmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}
	
	private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()

				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests().antMatchers("/index.html").permitAll()

				.antMatchers(AUTH_WHITELIST).permitAll()
				
				.antMatchers("/api/auth/**", "/api/reports/forward/", "/api/reports/comment/",
						"/api/reports/acceptBid/")
				.permitAll()

				.antMatchers("/api/admins/", "/api/companies/", "/api/managers/",  "/api/residents/", "/api/owners/")
				.hasAuthority("REGISTER")
				
				.antMatchers("/api/announcements/").hasAnyAuthority("CREATE_ANNOUNCEMENT")

				.antMatchers("/api/reports/").hasAuthority("CREATE_REPORT")

				.antMatchers("/api/reports/forward").hasAnyAuthority("FORWARD_REPORT")
				
				.antMatchers("/api/reports/bid/").hasAuthority("SEND_BID")
				
				.antMatchers("/api/meetings/").hasAuthority("CREATE_MEETING")
				
				.antMatchers("/api/proposals/").hasAnyAuthority("CREATE_PROPOSAL")
				
				.antMatchers("/api/proposal_votes/").hasAnyAuthority("PROPOSAL_VOTE")

				.antMatchers("/api/residents/**").hasAuthority("UPDATE_RESIDENT")

				.antMatchers("/api/buildings/").hasAuthority("CREATE_BUILDING")

				.antMatchers("/api/residences/").hasAuthority("CREATE_RESIDENCE");

		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

}
