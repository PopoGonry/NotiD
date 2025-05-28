package notid.user;

import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;


public class MemoryUserRepositoryTest {


    private MemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryUserRepository();
        repository.clear();
    }

    @Test
    void addUserAndGetUser1() {
        User user = new User("id", "password", "name", new Date(), "010-0000-0000", UserGrade.NORMAL);

        repository.addUserData(user);

        User found = repository.getUserData("id");

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo("id");
        assertThat(found.getName()).isEqualTo("name");

    }
    @Test
    void addUserAndGetUser2() {
        User user1 = new User("id1", "password1", "name1", new Date(), "010-0000-0000", UserGrade.NORMAL);
        User user2 = new User("id2", "password2", "name2", new Date(), "010-0000-0000", UserGrade.NORMAL);

        repository.addUserData(user1);
        repository.addUserData(user2);

        User found1 = repository.getUserData("id1");
        User found2 = repository.getUserData("id2");

        assertThat(found1).isNotNull();
        assertThat(found1.getId()).isEqualTo("id1");
        assertThat(found1.getName()).isEqualTo("name1");

        assertThat(found2).isNotNull();
        assertThat(found2.getId()).isNotEqualTo("id1");
        assertThat(found2.getName()).isNotEqualTo("name1");
    }

    @Test
    void hasUser() {
        User user = new User("id", "password", "name", new Date(), "010-0000-0000", UserGrade.NORMAL);

        repository.addUserData(user);
        assertThat(repository.hasUserData("id")).isTrue();
        assertThat(repository.hasUserData("nonexistent")).isFalse();
    }

    @Test
    void removeUser() {
        User user = new User("id", "password", "name", new Date(), "010-0000-0000", UserGrade.NORMAL);
        repository.addUserData(user);

        assertThat(repository.hasUserData("id")).isTrue();

        repository.removeUserData("id");

        assertThat(repository.hasUserData("id")).isFalse();
        assertThat(repository.getUserData("id")).isNull();

    }



}
