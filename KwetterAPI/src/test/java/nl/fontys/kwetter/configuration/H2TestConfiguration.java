package nl.fontys.kwetter.configuration;

import nl.fontys.kwetter.service.implementation.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class H2TestConfiguration {

    @Bean
    public ValidatorService validatorService(){
        return new ValidatorService();
    }

    @Bean
    public KwetterService kwetterService(){
        return new KwetterService();
    }

    @Bean
    public AdminService adminService() {
        return new AdminService();
    }

    @Bean
    public ProfileService profileService() {
        return new ProfileService();
    }

    @Bean
    public LoginService loginService() {
        return new LoginService();
    }
}
