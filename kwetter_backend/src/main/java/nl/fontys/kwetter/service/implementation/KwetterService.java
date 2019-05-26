package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.CouldNotDeleteModelException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.NotImplementedException;
import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.IKwetterService;
import nl.fontys.kwetter.service.IValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for handling model operations regarding the kwetter tasks.
 */
@Service
public class KwetterService implements IKwetterService {

    private Logger logger = LoggerFactory.getLogger(KwetterService.class);

    private IValidatorService validator;
    private IUserRepository userRepository;
    private IKwetterRepository kwetterRepository;

    @Autowired
    public KwetterService(IValidatorService validator, IUserRepository userRepository, IKwetterRepository kwetterRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.kwetterRepository = kwetterRepository;
    }

    /**
     * Search for a specific kwetter via the text.
     *
     * @param searchTerm The text to search for.
     * @return The corresponding Kwetter
     */
    @Override
    public List<Kwetter> searchForKwetter(String searchTerm) {
        return kwetterRepository.findAllByTextContains(searchTerm);
    }

    /**
     * Create a new kwetter
     *
     * @param userId  Id of the User
     * @param kwetter The kwetter to be created
     * @return The created kwetter
     * @throws ModelInvalidException  Thrown when an invalid input is given for the model.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    @Override
    public Kwetter createKwetter(UUID userId, KwetterDTO kwetter) throws ModelNotFoundException, ModelInvalidException {
        User owner = getUserById(userId);
        if (logger.isDebugEnabled()) {
            logger.info("Creating Kwetter");
            logger.info(String.valueOf(userId));
            logger.info(String.valueOf(kwetter));
            logger.info(String.valueOf(owner.getId()));
            logger.info(owner.getName());
            logger.info(String.valueOf(owner));
        }

        Set<User> mentions = new HashSet<>();
        if (kwetter.getMentions() != null) {
            for (User kwetterMentions : kwetter.getMentions()) {
                mentions.add(getUserById(kwetterMentions.getId()));
            }
        }

        Kwetter newKwetter = new Kwetter();

        newKwetter.setText(kwetter.getText());
        newKwetter.setMentions(mentions);
        newKwetter.setDateTime(Calendar.getInstance().getTime());
        newKwetter.setOwner(owner);

        validator.validate(newKwetter);

        if (logger.isDebugEnabled()) {
            logger.info(String.valueOf(newKwetter));
        }
        
        kwetterRepository.save(newKwetter);
        userRepository.save(owner);

        if (logger.isDebugEnabled()) {
            logger.info("Created Kwetter");
        }
        return newKwetter;
    }

    /**
     * Remove a kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void removeKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException, CouldNotDeleteModelException {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        if (!user.removeCreatedKwetter(kwetter)) {
            throw new CouldNotDeleteModelException(user.getId() + " " + kwetter.getUuid());
        }

        userRepository.save(user);
        kwetterRepository.delete(kwetter);
    }

    /**
     * Heart a kwetter
     *
     * @param userId    Id of the User
     * @param kwetterId Id of the Kwetter
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void heartKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException {
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
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void removeHeartKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException {
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
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void reportKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException {
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
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public void removeReportKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException {
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
     * @throws NotImplementedException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public List<Kwetter> getMentionedKwetters(UUID userId) throws NotImplementedException {
        throw new NotImplementedException();
    }

    /**
     * Get the most recent Kwetters of a User
     *
     * @param userId Id of the User
     * @return List of the most recent Kwetters
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public List<Kwetter> getMostRecentKwetters(UUID userId) throws ModelNotFoundException {
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

    @Override
    public List<Kwetter> getTimeline(UUID userId) throws ModelNotFoundException {
        User user = getUserById(userId);

        List<Kwetter> kwetters = new ArrayList<>(user.getCreatedKwetters());
        for (User follower : user.getUsersFollowed()) {
            kwetters.addAll(follower.getCreatedKwetters());
        }
        Collections.sort(kwetters);
        return kwetters;
    }

    /**
     * Get a list of the Kwetter a user hearted.
     *
     * @param userId Id of the User
     * @return List of the hearted kwetters
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding User.
     */
    @Override
    public List<Kwetter> getHeartedKwetters(UUID userId) throws ModelNotFoundException {
        User user = getUserById(userId);
        return new ArrayList<>(user.getHeartedKwetters());
    }

    /**
     * Get the user via its Id
     *
     * @param userID Id of the User
     * @return The User
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    private User getUserById(UUID userID) throws ModelNotFoundException {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ModelNotFoundException("User with the uuid:" + userID + " could not be found.");
    }

    /**
     * Get the Kwetter via its Id
     *
     * @param kwetterId Id of the User
     * @return The Kwetter
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     */
    private Kwetter getKwetterById(UUID kwetterId) throws ModelNotFoundException {
        Optional<Kwetter> kwetter = kwetterRepository.findById(kwetterId);
        if (kwetter.isPresent()) {
            return kwetter.get();
        }
        throw new ModelNotFoundException();
    }
}
