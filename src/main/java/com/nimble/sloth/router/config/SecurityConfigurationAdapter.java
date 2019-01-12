package com.nimble.sloth.router.config;

import com.nimble.sloth.router.auth.AuthProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.nimble.sloth.router.auth.Role.AUTHENTICATED;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final AuthProvider provider;

    public SecurityConfigurationAdapter(final AuthProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .addFilterBefore(new CORSFilter(), SessionManagementFilter.class)
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/status").permitAll()
                .antMatchers(POST, "/new-app").permitAll()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers("/**").hasAnyRole(AUTHENTICATED.asUserRole());
    }
}


