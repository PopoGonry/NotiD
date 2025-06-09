package com.popogonry.notid.cli;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;

import java.util.Scanner;

public class ChannelViewImpl implements ChannelView {
    Scanner scanner = new Scanner(System.in);

    private final MainView mainView;

    public ChannelViewImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void channelViewMain(Channel channel, User user) {
        if (!channel.hasChannelUserGrade(user.getId())) {
            System.out.println(user.getId() + " 유저는 채널에 존재하지 않습니다.");
        }

        ChannelUserGrade channelUserGrade = channel.getChannelUserGrade(user.getId());
        if (channelUserGrade == ChannelUserGrade.NORMAL) {
            channelToNormal(channel, user);
        } else if (channelUserGrade == ChannelUserGrade.MANAGER) {
            channelToManager(channel, user);
        } else if (channelUserGrade == ChannelUserGrade.ADMIN) {
            channelToAdmin(channel, user);
        }
    }


    @Override
    public void joinChannel() {

    }

    @Override
    public void channelToAdmin(Channel channel, User user) {
        System.out.println("1. 채널 상세 정보");
        System.out.println("2. 공지 리스트");
        System.out.println("3. 내 답장 리스트");
        System.out.println("4. 공지 생성");
        System.out.println("5. 유저 관리");
        System.out.println("6. 채널 수정");
        System.out.println("7. 채널 삭제");
        System.out.println("8. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 8, value));

        int intValue = Integer.parseInt(value);
        switch (intValue) {
            case 1:
                channelInfo(channel);
                break;

            case 2:
                noticeList(channel);
                break;

            case 3:
                userReplyList(channel, user);
                break;

            case 4:
                createNotice(channel, user);
                break;

            case 5:
                manageUser(channel, user);
                break;

            case 6:
                updateChannel(channel, user);
                break;

            case 7:
                // 채널 삭제
            case 8:
                mainView.mainViewMain(user);
        }

    }

    @Override
    public void channelToManager(Channel channel, User user) {
        System.out.println("1. 채널 상세 정보");
        System.out.println("2. 공지 리스트");
        System.out.println("3. 내 답장 리스트");
        System.out.println("4. 공지 생성");
        System.out.println("5. 유저 관리");
        System.out.println("6. 채널 수정");
        System.out.println("7. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 7, value));

        int intValue = Integer.parseInt(value);
        switch (intValue) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
        }


    }

    @Override
    public void channelToNormal(Channel channel, User user) {
        System.out.println("1. 채널 상세 정보");
        System.out.println("2. 공지 리스트");
        System.out.println("3. 내 답장 리스트");
        System.out.println("4. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 4, value));

        int intValue = Integer.parseInt(value);
        switch (intValue) {
            case 1:
            case 2:
            case 3:
            case 4:
        }
    }

    @Override
    public void channelInfo(Channel channel) {

    }

    @Override
    public void noticeList(Channel channel) {

    }

    @Override
    public void userReplyList(Channel channel, User user) {

    }

    @Override
    public void updateChannel(Channel channel, User user) {

    }

    @Override
    public void createNotice(Channel channel, User user) {

    }

    @Override
    public void manageUser(Channel channel, User user) {

    }
}
