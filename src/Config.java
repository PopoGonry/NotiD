import user.repository.MemoryUserRepository;
import user.repository.UserRepositoy;

public class Config {

    UserRepositoy getUserRepositoy() {
        return new MemoryUserRepository();
    }

}
