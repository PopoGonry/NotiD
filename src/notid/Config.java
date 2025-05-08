package notid;

import notid.user.repository.MemoryUserRepository;
import notid.user.repository.UserRepositoy;

public class Config {

    public static UserRepositoy getUserRepositoy() {
        return new  MemoryUserRepository();
    }

}
