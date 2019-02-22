package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.KwetterDao;
import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.KwetterDaoImp;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesntExist;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IKwetterService;
import nl.fontys.kwetter.utilities.ModelValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class KwetterService implements IKwetterService {
    private ModelValidator validator;
    private UserDao userDao;
    private KwetterDao kwetterDao;
    private Calendar calendar;

    public KwetterService(){
        userDao = new UserDaoImp();
        kwetterDao = new KwetterDaoImp();
        validator = new ModelValidator();
        calendar = Calendar.getInstance();
    }

    @Override
    public Kwetter searchForKwetter(String searchTerm) {
        throw new NotImplementedException();
    }

    @Override
    public Kwetter createKwetter(Long userId, String text, Set<String> tags, Set<Long> mentionIds) throws UserDoesntExist, InvalidModelException {
        User owner = getUserById(userId);

        Set<User> mentions = new HashSet<>();
        if (mentionIds != null){
            for (Long mentionUserId: mentionIds) {
                mentions.add(getUserById(mentionUserId));
            }
        }

        Kwetter kwetter = new Kwetter(text, tags, mentions, owner, calendar.getTime());
        validator.validate(kwetter);

        kwetterDao.createNewKwetter(kwetter);
        return kwetter;
    }

    @Override
    public void removeKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User owner = getUserById(kwetter.getOwner().getId());

        if (kwetter.getOwner().getId().equals(userId)){
            owner.removeCreatedKwetter(kwetter);
            userDao.updateUser(owner);
            kwetterDao.updateKwetter(kwetter);
        } else{
            throw new KwetterDoesntExist();
        }
    }

    @Override
    public void heartKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.addHeartedKwetter(kwetter);

        userDao.updateUser(user);
        kwetterDao.updateKwetter(kwetter);
    }

    @Override
    public void removeHeartKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.removeHeartedKwetter(kwetter);

        userDao.updateUser(user);
        kwetterDao.updateKwetter(kwetter);
    }

    @Override
    public void reportKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.addReportedKwetter(kwetter);

        userDao.updateUser(user);
        kwetterDao.updateKwetter(kwetter);
    }

    @Override
    public void removeReportKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist {
        Kwetter kwetter = getKwetterById(kwetterId);
        User user = getUserById(userId);

        user.removeReportedKwetter(kwetter);

        userDao.updateUser(user);
        kwetterDao.updateKwetter(kwetter);
    }

    @Override
    public List<Kwetter> getMentionedKwetters(Long userId) throws UserDoesntExist {
        User user = getUserById(userId);
        throw new NotImplementedException();
    }

    @Override
    public List<Kwetter> getMostRecentKwetters(Long userId) throws UserDoesntExist {
        User user = getUserById(userId);

        List<Kwetter> a = new ArrayList<>(user.getCreatedKwetters());
        ListIterator<Kwetter> li = a.listIterator(a.size());
        List<Kwetter> lastKwetters = new ArrayList<>();

        int i = 0;
        while(li.hasPrevious() || i < 10) {
            lastKwetters.add(li.previous());
            i++;
        }
        return lastKwetters;
    }

    @Override
    public List<Kwetter> getHeartedKwetters(Long userId) throws UserDoesntExist {
        User user = getUserById(userId);
        return new ArrayList<>(user.getHeartedKwetters());
    }

    private User getUserById(Long userID) throws UserDoesntExist {
        User user = userDao.getUserById(userID);
        if (user == null){
            throw new UserDoesntExist();
        }
        return user;
    }

    private Kwetter getKwetterById(Long kwetterId) throws KwetterDoesntExist {
        Kwetter kwetter = kwetterDao.getKwetterById(kwetterId);
        if (kwetter == null){
            throw new KwetterDoesntExist();
        }
        return kwetter;
    }
}
