package com.popogonry.notid.cli;

import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class AuthenticationImpl implements Authentication {
    Scanner scanner = new Scanner(System.in);

    private final UserRepositoy userRepositoy;
    private final MainView mainView;

    public AuthenticationImpl(UserRepositoy userRepositoy, MainView mainView) {
        this.userRepositoy = userRepositoy;
        this.mainView = mainView;
    }

    @Override
    public void authenticate() {
        System.out.println("--- 사용자 인증 ----");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        String value;
        do {
            System.out.print("선택해주세요: ");
             value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        int intValue = Integer.parseInt(value);
        switch (intValue) {
            case 1:
                signup();
                break;

            case 2:
                login();
                break;
        }
    }

    @Override
    public void login() {
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if(!userRepositoy.hasUserData(id)) {
            System.out.println("존재하지 않는 회원 정보입니다.");
            authenticate();
            return;
        }
        if(!userRepositoy.getUserData(id).getPassword().equals(password)) {
            System.out.println("비밀번호가 틀렸습니다.");
            authenticate();
            return;
        }
        System.out.println("로그인에 성공하였습니다. Id: " + id);
        mainView.mainViewMain(userRepositoy.getUserData(id));
    }

    @Override
    public void signup() {
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        while (userRepositoy.hasUserData(id)) {
            System.out.println("이미 존재하는 ID 입니다.");
            System.out.print("ID: ");
            id = scanner.nextLine().trim();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();

        while (!password.equals(confirmPassword)) {
            System.out.println("비밀번호가 동일하지 않습니다.");
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
        catch(Exception e) {

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
        System.out.println("회원가입 완료되었습니다.");
        authenticate();
    }
}
