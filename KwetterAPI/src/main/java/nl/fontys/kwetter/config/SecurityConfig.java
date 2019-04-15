package nl.fontys.kwetter.config;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.service.IAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final IAdminService adminService;

    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    public SecurityConfig(IAdminService adminService) {
        this.adminService = adminService;
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Override
    public void configure(WebSecurity web) {
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Angular cors config
        //https://stackoverflow.com/questions/40418441/spring-security-cors-filter
        http.cors();
        //Bypass session based authentication for all token based authentications
        http.authorizeRequests().antMatchers("/api/token/**").permitAll();

        // require all requests to be authenticated except for the resources
        http.authorizeRequests().antMatchers("/javax.faces.resource/**")
                .permitAll().anyRequest().authenticated();

        // login
        http.formLogin().loginPage("/login.xhtml").permitAll()
                .failureUrl("/login.xhtml?error=true").successForwardUrl("/main.xhtml");
        // logout
        http.logout().logoutSuccessUrl("/login.xhtml");
        // not needed as JSF 2.2 is implicitly protected against CSRF
        http.csrf().disable();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/token/secure/*");

        return registrationBean;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                logger.info("Login attempt for " + username);
                List<Credentials> allCredentials = adminService.getAllCredentials();
                for (Credentials credentials : allCredentials) {
                    if (username.equals(credentials.getEmail())) {
                        logger.info("Login successful for " + username);
                        return createSecurityUser(credentials);
                    }
                }
                throw new UsernameNotFoundException("Invalid username");
            } catch (Exception e) {
                throw new UsernameNotFoundException("Invalid username");
            }
        });
    }

    private User createSecurityUser(Credentials credentials) {
        Set<SimpleGrantedAuthority> securityRoles = Collections.singleton(new SimpleGrantedAuthority(credentials.getRole().toString()));
        return new User(credentials.getEmail(), "{noop}" + credentials.getPassword(), securityRoles);
    }
}
