package notid.user.repository;

import notid.user.User;

import java.util.HashMap;

public class MemoryUserRepository implements UserRepositoy {

    private HashMap<String, User> userHashMap = new HashMap<>();

    @Override
    public void addUserData(User user) {
        userHashMap.put(user.getId(), user);
    }

    @Override
    public User getUserData(String userId) {
        return userHashMap.get(userId);
    }

    @Override
    public boolean hasUserData(String userId) {
        return userHashMap.containsKey(userId);
    }
}
