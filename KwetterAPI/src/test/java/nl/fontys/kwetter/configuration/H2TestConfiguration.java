package nl.fontys.kwetter.configuration;

import nl.fontys.kwetter.repository.memory.*;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.InMemoryDatabaseManager;
import nl.fontys.kwetter.repository.memory.implementation.InMemoryCredentialsRepository;
import nl.fontys.kwetter.repository.memory.implementation.InMemoryKwetterRepository;
import nl.fontys.kwetter.repository.memory.implementation.InMemoryUserRepository;
import nl.fontys.kwetter.service.*;
import nl.fontys.kwetter.service.implementation.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class H2TestConfiguration {
    @Bean
    public IInMemoryKwetterRepository kwetterRepository() {
        return new InMemoryKwetterRepository();
    }

    @Bean
    public IInMemoryUserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public IInMemoryCredentialsRepository credentialsRepository() {
        return new InMemoryCredentialsRepository();
    }

    @Bean
    public IValidatorService validatorService() {
        return new ValidatorService();
    }

    @Bean
    public IAdminService adminService() {
        return new AdminService(userRepository(), kwetterRepository(), credentialsRepository());
    }

    @Bean
    public IProfileService profileService() {
        return new ProfileService(validatorService(), userRepository());
    }

    @Bean
    public ILoginService loginService() {
        return new LoginService(validatorService(), userRepository());
    }

    @Bean
    public IKwetterService kwetterService() {
        return new KwetterService(validatorService(), userRepository(), kwetterRepository());
    }

    @Bean
    public IInMemoryDatabaseManager inMemoryDatabaseManager() {
        return new InMemoryDatabaseManager(userRepository(), kwetterRepository(), credentialsRepository());
    }
}
