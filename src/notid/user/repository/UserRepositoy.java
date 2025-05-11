package notid.user.repository;

import notid.user.User;

public interface UserRepositoy {
    void addUserData(User user);
    User getUserData(String userId);
    boolean hasUserData(String userId);
    void removeUserData(String userId);
}
