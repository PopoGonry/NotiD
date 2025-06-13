package com.popogonry.notid;

import com.popogonry.notid.cli.Authentication;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.UserService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Config config = new Config();

        // 관리자 계정 추가
        UserService userService = config.userService();
        userService.createUser("admin", "admin", "admin", new Date(), "010-3727-0770", UserGrade.MASTER);

        System.out.println("=========== NotiD ===========");
        System.out.println("디지털보안학과 2022011869 고성노");
        Authentication.authenticate();
    }
}