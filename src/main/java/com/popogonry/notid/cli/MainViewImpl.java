package com.popogonry.notid.cli;

import java.util.Scanner;

public class MainViewImpl implements MainView {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void mainViewMain(String id) {
        System.out.println("--- 메인 화면 ---");

        System.out.println("1. 알람 확인");
        System.out.println("2. 내 채널 리스트");
        System.out.println("3. 내 공지 리스트");
        System.out.println("4. 새 채널 찾기");
        System.out.println("5. 새 채널 만들기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 5, value));

        int intValue = Integer.parseInt(value);
        switch (intValue) {
            case 1:
                checkAlarm(id);
                break;

            case 2:
                userChannelList(id);
                break;

            case 3:
                userNoticeList(id);
                break;

            case 4:
                searchChannel(id);
                break;

            case 5:
                createChannel(id);
                break;
        }
    }

    @Override
    public void checkAlarm(String id) {

    }

    @Override
    public void userChannelList(String id) {

    }

    @Override
    public void userNoticeList(String id) {

    }

    @Override
    public void searchChannel(String id) {

    }

    @Override
    public void createChannel(String id) {

    }
}
