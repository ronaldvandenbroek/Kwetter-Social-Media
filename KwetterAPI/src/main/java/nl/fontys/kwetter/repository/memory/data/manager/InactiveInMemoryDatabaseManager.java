package nl.fontys.kwetter.repository.memory.data.manager;

import org.springframework.stereotype.Service;

@Service
public class InactiveInMemoryDatabaseManager implements IInMemoryDatabaseManager {

    @Override
    public void reset() {
        //Inactive reset
    }
}
