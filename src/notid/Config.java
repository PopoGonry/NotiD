package notid;

import notid.user.repository.MemoryUserRepository;
import notid.user.repository.UserRepositoy;
import notid.user.UserService;

public class Config {


    public UserService userService() {
        return new UserService(userRepositoy());
    }

    public UserRepositoy userRepositoy() {
        return new MemoryUserRepository();
    }
}
