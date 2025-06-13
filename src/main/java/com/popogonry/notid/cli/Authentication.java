package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Authentication {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Config config = new Config();

    private static final UserRepositoy userRepositoy = config.userRepositoy();

    public static void authenticate() {
        System.out.println("--- 사용자 인증 ----");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("10. 프로그램 종료");
        String value;
        do {
            System.out.print("선택해주세요: ");
             value = scanner.nextLine().trim();
             if(value.equals("10")) {
                 System.out.println("프로그램을 종료합니다.");
                 return;
             }
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        int intValue = Integer.parseInt(value);
        Common.clear();
        switch (intValue) {
            case 1:
                signup();
                break;

            case 2:
                login();
                break;
        }
    }

    public static void login() {
        System.out.println("--- 로그인 ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        if(!userRepositoy.hasUserData(id)) {
            Common.clear();
            System.out.println("존재하지 않는 회원 정보입니다.");
            authenticate();
            return;
        }
        if(!userRepositoy.getUserData(id).getPassword().equals(password)) {
            Common.clear();
            System.out.println("비밀번호가 틀렸습니다.");
            authenticate();
            return;
        }
        Common.clear();
        System.out.println("로그인에 성공하였습니다. Id: " + id);
        MainView.mainViewMain(userRepositoy.getUserData(id));
    }

    public static void signup() {

        System.out.println("--- 회원가입 ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        while (true) {
            if(userRepositoy.hasUserData(id)) {
                System.out.println("이미 존재하는 ID 입니다.");
            }
            else if(id.isEmpty()) {
                System.out.println("유효 하지 않은 값입니다.");
            }
            else {
                break;
            }
            System.out.print("ID: ");
            id = scanner.nextLine().trim();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();

        while (true) {
            if(!password.equals(confirmPassword)) {
                System.out.println("비밀번호가 동일하지 않습니다.");
            }
            else if(password.isEmpty()) {
                System.out.println("유효 하지 않은 값입니다.");
            }
            else {
                break;
            }
            System.out.print("Password: ");
            password = scanner.nextLine().trim();
            System.out.print("Confirm Password: ");
            confirmPassword = scanner.nextLine().trim();
        }

        System.out.print("이름: ");
        String name = scanner.nextLine().trim();

        System.out.print("생년월일(xxxx-xx-xx): ");
        String birthdate = scanner.nextLine().trim();
        while(!ValidationCheck.isValidBirthdate(birthdate)) {
            System.out.println("생년월일 형식이 맞지 않습니다.");
            System.out.print("생년월일(xxxx-xx-xx): ");
            birthdate = scanner.nextLine().trim();
        }
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(birthdate);
        }
        catch(Exception ignored) {

        }

        System.out.print("휴대폰 번호(xxx-xxxx-xxxx): ");
        String hp = scanner.nextLine().trim();
        while(!ValidationCheck.isValidPhoneNumber(hp)) {
            System.out.println("생년월일 형식이 맞지 않습니다.");
            System.out.print("휴대폰 번호(xxx-xxxx-xxxx): ");
            hp = scanner.nextLine().trim();
        }
        User user = new User(id, password, name, date, hp, UserGrade.NORMAL);
        userRepositoy.addUserData(user);
        Common.clear();
        System.out.println("회원가입 완료되었습니다.");
        authenticate();
    }
}
