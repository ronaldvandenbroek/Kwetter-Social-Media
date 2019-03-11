package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesntExist;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.IKwetterService;
import nl.fontys.kwetter.service.IValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Service for handling model operations regarding the kwetter tasks.
 */
@Service
public class KwetterService implements IKwetterService {

    @Autowired
    private IValidatorService validator;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IKwetterRepository kwetterRepository;

    public KwetterService() {
    }

    /**
     * Search for a specific kwetter via the text.
     *
     * @param searchTerm The text to search for.
     * @return The corresponding Kwetter
     */
    @Override
    public Kwetter searchForKwetter(String searchTerm) {
        throw new NotImplementedException();
    }

    /**
     * Create a new kwetter
     *
     * @param userId  Id of the User
     * @param kwetter The kwetter to be created
     * @return The created kwetter
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
    @Override
    public Kwetter createKwetter(Long userId, Kwetter kwetter) throws UserDoesntExist, InvalidModelException {
        User owner = getUserById(userId);

        Set<User> mentions = new HashSet<>();
        if (kwetter.getMentions() != null) {
            for (User kwetterMentions : kwetter.getMentions()) {
                mentions.add(getUserById(kwetterMentions.getId()));
            }
        }

        kwetter.setMentions(mentions);
        kwetter.setDateTime(Calendar.getInstance().getTime());
        owner.addCreatedKwetter(kwetter);

        validator.validate(kwetter);

        kwetterRepository.save(kwetter);
        userRepository.save(owner);
        return kwetter;
    }

    /**
     * Remove a kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws KwetterDoesntExist Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws UserDoesntExist    Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void removeKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.removeCreatedKwetter(kwetter);

        kwetterRepository.delete(kwetter);
    }

    /**
     * Heart a kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws KwetterDoesntExist Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws UserDoesntExist    Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void heartKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.addHeartedKwetter(kwetter);

        userRepository.save(user);
        kwetterRepository.save(kwetter);
    }

    /**
     * Remove a heart from a Kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws KwetterDoesntExist Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws UserDoesntExist    Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void removeHeartKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.removeHeartedKwetter(kwetter);

        userRepository.save(user);
        kwetterRepository.save(kwetter);
    }

    /**
     * Report a Kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws KwetterDoesntExist Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws UserDoesntExist    Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void reportKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.addReportedKwetter(kwetter);

        userRepository.save(user);
        kwetterRepository.save(kwetter);
    }

    /**
     * Remove a report from a Kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws KwetterDoesntExist Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws UserDoesntExist    Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void removeReportKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.removeReportedKwetter(kwetter);

        userRepository.save(user);
        kwetterRepository.save(kwetter);
    }

    /**
     * Get all Kwetters the user is mentioned in.
     *
     * @param userId Id of the User
     * @return List of all Kwetters the user is mentioned in.
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding User.
     */
    @Override
    public List<Kwetter> getMentionedKwetters(Long userId) throws UserDoesntExist {
        User user = getUserById(userId);
        throw new NotImplementedException();
    }

    /**
     * Get the most recent Kwetters of a User
     *
     * @param userId Id of the User
     * @return List of the most recent Kwetters
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding User.
     */
    @Override
    public List<Kwetter> getMostRecentKwetters(Long userId) throws UserDoesntExist {
        User user = getUserById(userId);

        List<Kwetter> a = new ArrayList<>(user.getCreatedKwetters());
        ListIterator<Kwetter> li = a.listIterator(a.size());
        List<Kwetter> lastKwetters = new ArrayList<>();

        int i = 0;
        while (li.hasPrevious() || i < 10) {
            lastKwetters.add(li.previous());
            i++;
        }
        return lastKwetters;
    }

    /**
     * Get a list of the Kwetter a user hearted.
     *
     * @param userId Id of the User
     * @return List of the hearted kwetters
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding User.
     */
    @Override
    public List<Kwetter> getHeartedKwetters(Long userId) throws UserDoesntExist {
        User user = getUserById(userId);
        return new ArrayList<>(user.getHeartedKwetters());
    }

    /**
     * Get the user via its Id
     *
     * @param userID Id of the User
     * @return The User
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding user.
     */
    private User getUserById(Long userID) throws UserDoesntExist {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserDoesntExist();
    }

    /**
     * Get the Kwetter via its Id
     *
     * @param kwetterId Id of the User
     * @return The Kwetter
     * @throws KwetterDoesntExist Thrown when the kwetterID does not have a corresponding Kwetter.
     */
    private Kwetter getKwetterById(Long kwetterId) throws KwetterDoesntExist {
        Optional<Kwetter> kwetter = kwetterRepository.findById(kwetterId);
        if (kwetter.isPresent()) {
            return kwetter.get();
        }
        throw new KwetterDoesntExist();
    }
}
