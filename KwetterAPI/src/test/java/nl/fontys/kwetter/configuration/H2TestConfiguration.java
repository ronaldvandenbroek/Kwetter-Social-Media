package nl.fontys.kwetter.configuration;

import nl.fontys.kwetter.service.AdminService;
import nl.fontys.kwetter.service.LoginService;
import nl.fontys.kwetter.service.ProfileService;
import nl.fontys.kwetter.service.ValidatorService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class H2TestConfiguration {

    @Bean
    public ValidatorService validatorService(){
        return new ValidatorService();
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
