package nl.fontys.kwetter;

import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Value("${spring.mail.username}")
    private String springMailUsername;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(IKwetterRepository kwetterRepository, IUserRepository userRepository, ICredentialsRepository credentialsRepository) {
        return args -> {
            userRepository.deleteAll();
            kwetterRepository.deleteAll();
            credentialsRepository.deleteAll();

            Collection<User> presetUsers = new ArrayList<>();
            Collection<Credentials> presetCredentials = new ArrayList<>();
            Collection<Kwetter> presetKwetters = new ArrayList<>();

            User verificationUser = new User();
            verificationUser.setName(springMailUsername);
            verificationUser.setBio("Verification Test");
            verificationUser.setWebsite("Verification Test Website");
            verificationUser.setLocation("Verification Test Location");
            userRepository.save(verificationUser);

            Credentials verificationCredentials = new Credentials(springMailUsername, "test", Role.ROLE_ADMIN, verificationUser);
            credentialsRepository.save(verificationCredentials);

            //Create 10 allUsers
            for (int i = 1; i < 11; i++) {
                User user = new User();
                user.setName(i + "Test");
                user.setBio("This is a test bio");
                user.setWebsite("www.google.nl");
                user.setLocation("The Netherlands");
                userRepository.save(user);

                Role role = Role.ROLE_USER;
                if (i == 1) {
                    role = Role.ROLE_ADMIN;
                }

                Credentials credentials = new Credentials(i + "@test.nl", "test", role, user);
                credentials.setVerified(true);

                presetCredentials.add(credentials);
                presetUsers.add(user);

                credentialsRepository.save(credentials);
            }

            // Follow everyone via the first user
            Iterator<User> userIterator = presetUsers.iterator();
            User firstUser = userIterator.next();

            User secondUser = null;
            boolean followBack = false;
            while (userIterator.hasNext()) {
                User nextUser = userIterator.next();
                if (!followBack) {
                    nextUser.follow(firstUser);
                    secondUser = nextUser;
                    followBack = true;
                }
                firstUser.follow(nextUser);
            }
            userRepository.save(firstUser);

            //Create Kwetters for the first user
            Calendar calendar = Calendar.getInstance();
            for (int i = 1; i < 11; i++) {
                Kwetter kwetter = new Kwetter(i + "Test", null, null, firstUser, calendar.getTime());

                presetKwetters.add(kwetter);

                kwetterRepository.save(kwetter);
            }

            // Create a test kwetter for the second user
            if (secondUser != null) {
                Kwetter kwetter = new Kwetter(secondUser.getName() + "Test", null, null, secondUser, calendar.getTime());
                presetKwetters.add(kwetter);

                kwetterRepository.save(kwetter);
            }
        };
    }
}
