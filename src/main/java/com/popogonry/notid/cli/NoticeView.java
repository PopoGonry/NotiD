package com.popogonry.notid.cli;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.user.User;

import java.util.Scanner;

public class NoticeView {
    private static final Scanner scanner = new Scanner(System.in);


    public static void noticeViewMain(Notice notice, User user) {
        Channel channel = notice.getChannel();

        if (!channel.hasChannelUserGrade(user.getId())) {
            System.out.println(user.getId() + " 유저는 채널에 존재하지 않습니다.");
        }

        ChannelUserGrade channelUserGrade = channel.getChannelUserGrade(user.getId());
        if (channelUserGrade == ChannelUserGrade.NORMAL) {
            noticeToManager(notice, user);
        } else if (channelUserGrade == ChannelUserGrade.MANAGER || channelUserGrade == ChannelUserGrade.ADMIN) {
            noticeToMember(notice, user);
        }
    }

    public static void noticeToManager(Notice notice, User user) {
        System.out.println("--- " + notice.getTitle() + " ---");
        System.out.println("채널: " + notice.getChannel().getName());
        System.out.println("공지 공개 시간: " + notice.getScheduledTime());
        System.out.println("답장 제한 시간: " + notice.getReplyDeadline());

        String grade = "";
        if (notice.getUserGrade() == ChannelUserGrade.NORMAL) {
            grade = "일반 유저";
        } else if (notice.getUserGrade() == ChannelUserGrade.MANAGER) {
            grade = "채널 관리자";
        } else if (notice.getUserGrade() == ChannelUserGrade.ADMIN) {
            grade = "채널 소유자";
        }
        System.out.println("공지 접근 권한: " + grade);

        System.out.println("내용: " + notice.getContent());

        System.out.println();

        System.out.println("1. 공지 수정");
        System.out.println("2. 공지 삭제");
        System.out.println("3. 답장 리스트");
        System.out.println("4. 공지 알림 보내기");
        System.out.println("5. 돌아가기");


        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 5, value));

        switch (Integer.parseInt(value)) {
            case 1:
                updateNotice(notice, user);
                break;

            case 2:
                deleteNotice(notice, user);
                break;

            case 3:
                userReplyList(notice, user);
                break;

            case 4:
                sendNoticeAlarm(notice, user);
                break;

            case 5:
                ChannelView.channelViewMain(notice.getChannel(), user);
                break;

        }
    }

    public static void noticeToMember(Notice notice, User user) {

    }

    public static void updateNotice(Notice notice, User user) {

    }

    public static void userReplyList(Notice notice, User user) {

    }

    public static void deleteNotice(Notice notice, User user) {

    }

    public static void sendNoticeAlarm(Notice notice, User user) {

    }

    public static void createReply(Notice notice, User user) {

    }
}
