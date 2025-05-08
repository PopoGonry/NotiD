package notid.user.repository;

import notid.Config;
import notid.user.User;
import notid.user.UserGrade;

import java.io.ObjectInputFilter;
import java.util.Date;

public class UserService {
    UserRepositoy userRepositoy = Config.getUserRepositoy();

    boolean createUser(String id, String password, String name, Date birthdate, String phoneNumber, UserGrade grade) {
        // 같은 id의 유저가 있을 때, 예외
        if(userRepositoy.hasUserData(id)) return false;

        User user = new User(id, password, name, birthdate, phoneNumber, grade);
        userRepositoy.addUserData(user);
        return true;
    }

    void getUser() {

    }

    void updateUser() {

    }
    void deleteUser() {

    }







}
