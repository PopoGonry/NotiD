package com.popogonry.notid.user;

import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.UserService;
import com.popogonry.notid.user.repository.MemoryUserRepository;
import com.popogonry.notid.user.repository.UserRepositoy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;
    private UserRepositoy userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
        userRepository.clear();
    }

    @Test
    void createUser_success() {
        boolean result = userService.createUser("user", "pass", "이름", new Date(), "010-1234-5678", UserGrade.NORMAL);

        assertThat(result).isTrue();
        assertThat(userRepository.hasUserData("user")).isTrue();
    }

    @Test
    void createUser_fail_whenUserExists() {
        userService.createUser("user", "pass1", "이름1", new Date(), "010-1111-2222", UserGrade.NORMAL);

        boolean result = userService.createUser("user", "pass2", "이름2", new Date(), "010-2222-3333", UserGrade.MASTER);

        assertThat(result).isFalse();

        User user = userRepository.getUserData("user");
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("이름1");
        assertThat(user.getGrade()).isEqualTo(UserGrade.NORMAL);
    }

    @Test
    void updateUser_success() {
        userService.createUser("user", "pass", "이름", new Date(), "010-9999-8888", UserGrade.NORMAL);

        User updated = new User("user", "newpass", "이름", new Date(), "010-0000-0000", UserGrade.MASTER);
        boolean result = userService.updateUser("user", updated);

        assertThat(result).isTrue();
        assertThat(userRepository.getUserData("user").getPhoneNumber()).isEqualTo("010-0000-0000");
        assertThat(userRepository.getUserData("user").getGrade()).isEqualTo(UserGrade.MASTER);
    }

    @Test
    void updateUser_fail_whenNotExists() {
        User updated = new User("user", "pass", "이름", new Date(), "010-0000-0000", UserGrade.NORMAL);

        boolean result = userService.updateUser("user", updated);

        assertThat(result).isFalse();
    }

    @Test
    void updateUser_fail_whenIdMismatch() {
        userService.createUser("user", "pass", "이름", new Date(), "010-9999-8888", UserGrade.NORMAL);
        User updated = new User("differentId", "pass", "이름", new Date(), "010-0000-0000", UserGrade.NORMAL);

        boolean result = userService.updateUser("user", updated);

        assertThat(result).isFalse();
    }

    @Test
    void deleteUser_success() {
        userService.createUser("user", "pass", "이름", new Date(), "010-5555-6666", UserGrade.MASTER);

        boolean result = userService.deleteUser("user");

        assertThat(result).isTrue();
        assertThat(userRepository.hasUserData("user")).isFalse();
    }

    @Test
    void deleteUser_fail_whenNotExists() {
        boolean result = userService.deleteUser("user");

        assertThat(result).isFalse();
    }
}
