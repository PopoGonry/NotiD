package com.popogonry.notid.user;

import com.popogonry.notid.user.repository.UserRepositoy;

import java.util.Date;

public class UserService {
    private final UserRepositoy userRepositoy;

    public UserService(UserRepositoy userRepositoy) {
        this.userRepositoy = userRepositoy;
    }

    public boolean createUser(String id, String password, String name, Date birthdate, String phoneNumber, UserGrade grade) {
        // id의 유저가 있을 때, 예외
        if(userRepositoy.hasUserData(id)) return false;

        User user = new User(id, password, name, birthdate, phoneNumber, grade);
        userRepositoy.addUserData(user);
        return true;
    }

    public boolean updateUser(String id, User newUser) {
        // id의 유저가 없을 때, 예외
        if(!userRepositoy.hasUserData(id)) return false;

        // id와 newUser의 id가 같지 않을 때, 예외
        if(!id.equals(newUser.getId())) return false;

        userRepositoy.removeUserData(id);
        userRepositoy.addUserData(newUser);
        return true;
    }

    public boolean deleteUser(String id) {
        // id의 유저가 없을 때, 예외
        if(!userRepositoy.hasUserData(id)) return false;

        userRepositoy.removeUserData(id);
        return true;
    }
}
