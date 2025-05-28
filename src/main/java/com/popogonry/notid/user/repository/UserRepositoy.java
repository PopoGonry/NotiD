package com.popogonry.notid.user.repository;

import com.popogonry.notid.user.User;

public interface UserRepositoy {
    void addUserData(User user);
    User getUserData(String userId);
    boolean hasUserData(String userId);
    void removeUserData(String userId);

    void clear();
}

