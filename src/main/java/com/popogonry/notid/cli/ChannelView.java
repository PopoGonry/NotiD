package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ChannelView {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Config config = new Config();

    private static final MainView mainView = new MainView();
    private static final NoticeView noticeView = new NoticeView();
    private static final ReplyView replyView = new ReplyView();

    private static final NoticeRepository noticeRepository = config.noticeRepository();
    private static final ReplyRepository replyRepository = config.replyRepository();


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

    public void joinChannel() {

    }

    public void channelToAdmin(Channel channel, User user) {
        System.out.println("--- " + channel.getName() + " 채널 소유자 메뉴 ---");
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
                channelInfo(channel, user);
                break;

            case 2:
                noticeList(channel, user);
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

    public void channelToManager(Channel channel, User user) {
        System.out.println("--- " + channel.getName() + " 채널 관리자 메뉴 ---");
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
                channelInfo(channel, user);
                break;

            case 2:
                noticeList(channel, user);
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
                mainView.mainViewMain(user);
        }


    }

    public void channelToNormal(Channel channel, User user) {
        System.out.println("--- " + channel.getName() + " 채널 메뉴 ---");
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
                channelInfo(channel, user);
                break;

            case 2:
                noticeList(channel, user);
                break;

            case 3:
                userReplyList(channel, user);
                break;

            case 4:
                mainView.mainViewMain(user);
        }
    }

    public void channelInfo(Channel channel, User user) {

        System.out.println("--- " + channel.getName() + " 채널 상세 정보 ---");
        System.out.println("- 채널 이름: " + channel.getName());
        System.out.println("- 채널 설명: " + channel.getDescription());
        if(!channel.getAffiliation().isEmpty() && channel.getAffiliation() != null)
            System.out.println("- 채널 조직: " + channel.getAffiliation());
        System.out.println("- 가입 방식: " + ((channel.getJoinType() == ChannelJoinType.ACCEPT) ? "신청 가입" : "자유 가입"));

        System.out.println();
        channelViewMain(channel, user);
    }

    public void noticeList(Channel channel, User user) {

        if (!noticeRepository.hasChannelNoticeSetData(channel.getName())) {
            System.out.println("채널에 공지가 없습니다.");
            channelViewMain(channel, user);
            return;
        }

        System.out.println("--- " + channel.getName() + " 공지 리스트 ---");


        ArrayList<Long> channelNoticeList = new ArrayList<>(noticeRepository.getChannelNoticeSetData(channel.getName()));

        int i = 1;
        for (Long noticeId : channelNoticeList) {
            System.out.println(i + ". " + noticeRepository.getNoticeData(noticeId).getTitle());
            i++;
        }

        String value;
        do {
            System.out.print("선택해주세요 (돌아가기 : " + i + "): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        if (Integer.parseInt(value) == i) {
            channelViewMain(channel, user);
            return;
        }

        Notice notice = noticeRepository.getNoticeData(channelNoticeList.get(Integer.parseInt(value) - 1));
        noticeView.noticeViewMain(notice);

    }

    public void userReplyList(Channel channel, User user) {
        if (!noticeRepository.hasChannelNoticeSetData(channel.getName())) {
            System.out.println("채널에 공지가 없습니다.");
            channelViewMain(channel, user);
            return;
        }


        System.out.println("--- " + channel.getName() + " 답장 리스트 ---");


        ArrayList<Long> replyIdList = new ArrayList<>();

        int i = 1;
        for (Long noticeId : noticeRepository.getChannelNoticeSetData(channel.getName())) {
            for (Long replyId : replyRepository.getNoticeReplySetData(noticeId)) {
                System.out.println(i + ". " + replyRepository.getReplyData(replyId).getTitle());
                i++;
                replyIdList.add(replyId);
            }
        }

        String value;
        do {
            System.out.print("선택해주세요 (돌아가기 : " + i + "): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        if (Integer.parseInt(value) == i) {
            channelViewMain(channel, user);
            return;
        }

        Reply reply = replyRepository.getReplyData(replyIdList.get(Integer.parseInt(value) - 1));
        replyView.replyViewMain(reply, user);
    }

    public void updateChannel(Channel channel, User user) {

    }

    public void createNotice(Channel channel, User user) {

    }

    public void manageUser(Channel channel, User user) {

    }
}
