package notid.user.repository;

import notid.user.User;

public interface UserRepositoy {
    void setUserData(User user);
    User getUserData(String userId);
    boolean hasUserData(String userId);
}
