package com.popogonry.notid.user.repository;

import com.popogonry.notid.user.User;

import java.util.HashMap;

public class MemoryUserRepository implements UserRepositoy {

    private static final HashMap<String, User> userHashMap = new HashMap<>();

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

    @Override
    public void removeUserData(String userId) {
        userHashMap.remove(userId);
    }

    @Override
    public void clear() {
        userHashMap.clear();
    }

}
