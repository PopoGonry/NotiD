package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.NoticeService;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.repository.UserRepositoy;

import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChannelView {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Config config = new Config();

    private static final NoticeRepository noticeRepository = config.noticeRepository();
    private static final ReplyRepository replyRepository = config.replyRepository();
    private static final ChannelRepository channelRepository = config.channelRepository();
    private static final UserRepositoy userRepositoy = config.userRepositoy();
    private static final ChannelService channelService = config.channelService();
    private static final NoticeService noticeService = config.noticeService();


    public static void channelViewMain(Channel channel, User user) {
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

    public static void joinChannel(Channel channel, User user) {
        System.out.println("--- " + channel.getName() + " 채널 상세 정보 ---");
        System.out.println("- 채널 이름: " + channel.getName());
        System.out.println("- 채널 설명: " + channel.getDescription());
        if(!channel.getAffiliation().isEmpty() && channel.getAffiliation() != null)
            System.out.println("- 채널 조직: " + channel.getAffiliation());
        System.out.println("- 가입 방식: " + ((channel.getJoinType() == ChannelJoinType.ACCEPT) ? "신청 가입" : "자유 가입"));

        System.out.println();

        if(channel.getJoinType() == ChannelJoinType.ACCEPT) {
            System.out.println("1. 가입 신청");
        }
        else {
            System.out.println("1. 즉시 가입");
        }

        System.out.println("2. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        switch (Integer.parseInt(value)) {
            case 1:
                if (channel.getJoinType() == ChannelJoinType.ACCEPT) {
                    if(channelService.requestUserJoining(user, channel)) {
                        System.out.println("가입 신청 완료되었습니다.");
                        channelViewMain(channel, user);
                        return;
                    }
                    else {
                        System.out.println("이미 신청 중인 채널입니다.");
                        MainView.mainViewMain(user);
                        return;
                    }
                } else {
                    channelService.joinChannel(user, channel);
                    System.out.println("가입 완료되었습니다.");
                    channelViewMain(channel, user);
                }
                break;

            case 2:
                MainView.mainViewMain(user);
                break;
        }


    }

    public static void channelToAdmin(Channel channel, User user) {
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

        switch (Integer.parseInt(value)) {
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
                MainView.mainViewMain(user);
        }

    }

    public static void channelToManager(Channel channel, User user) {
        System.out.println("--- " + channel.getName() + " 채널 관리자 메뉴 ---");
        System.out.println("1. 채널 상세 정보");
        System.out.println("2. 공지 리스트");
        System.out.println("3. 내 답장 리스트");
        System.out.println("4. 공지 생성");
        System.out.println("5. 유저 관리");
        System.out.println("6. 채널 수정");
        System.out.println("7. 채널 나가기");
        System.out.println("8. 돌아가기");

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
                channelService.leaveChannel(user, channel);

            case 8:
                MainView.mainViewMain(user);
                break;
        }


    }

    public static void channelToNormal(Channel channel, User user) {
        System.out.println("--- " + channel.getName() + " 채널 메뉴 ---");
        System.out.println("1. 채널 상세 정보");
        System.out.println("2. 공지 리스트");
        System.out.println("3. 내 답장 리스트");
        System.out.println("4. 채널 나가기");
        System.out.println("5. 돌아가기");

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
                channelService.leaveChannel(user, channel);

            case 5:
                MainView.mainViewMain(user);
                break;
        }
    }

    public static void channelInfo(Channel channel, User user) {

        System.out.println("--- " + channel.getName() + " 채널 상세 정보 ---");
        System.out.println("- 채널 이름: " + channel.getName());
        System.out.println("- 채널 설명: " + channel.getDescription());
        if(!channel.getAffiliation().isEmpty() && channel.getAffiliation() != null)
            System.out.println("- 채널 조직: " + channel.getAffiliation());
        System.out.println("- 가입 방식: " + ((channel.getJoinType() == ChannelJoinType.ACCEPT) ? "신청 가입" : "자유 가입"));

        System.out.println();
        channelViewMain(channel, user);
    }

    public static void noticeList(Channel channel, User user) {

        if (!noticeRepository.hasChannelNoticeSetData(channel.getName()) || noticeRepository.getChannelNoticeSetData(channel.getName()).isEmpty()) {
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

        System.out.println(i + ". 돌아가기");

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
        NoticeView.noticeViewMain(notice, user);

    }

    public static void userReplyList(Channel channel, User user) {
        if (!noticeRepository.hasChannelNoticeSetData(channel.getName()) || noticeRepository.getChannelNoticeSetData(channel.getName()).isEmpty()) {
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

        System.out.println(i + ". 돌아가기");

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
        ReplyView.replyViewMain(reply, user);
    }

    public static void updateChannel(Channel channel, User user) {

        System.out.println("--- 채널 수정 ---");

        System.out.println("1. 설명 수정");
        System.out.println("2. 조직 수정");
        System.out.println("3. 가입 방식 수정");
        System.out.println("4. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 4, value));


        String description = channel.getDescription();
        String affiliation = channel.getAffiliation();
        ChannelJoinType channelJoinType = channel.getJoinType();

        switch (Integer.parseInt(value)) {
            case 1:
                System.out.print("설명(미 입력시 취소): ");
                description = scanner.nextLine();
                break;

            case 2:
                System.out.print("조직(미 입력시 취소, \' 삭제 \' 입력 시 삭제): ");
                affiliation = scanner.nextLine();
                if(affiliation.equals("삭제")) affiliation = "";
                break;

            case 3:
                String value2;
                do {
                    System.out.print("가입 형식 (1. 자유, 2. 수락, 미 입력시 취소): ");
                    value2 = scanner.nextLine();
                    if(value2.isEmpty()) break;
                } while (!ValidationCheck.intSelectCheck(1, 2, value2));

                if(!value2.isEmpty()) {
                    switch (Integer.parseInt(value2)) {
                        case 1:
                            channelJoinType = ChannelJoinType.FREE;
                            break;

                        case 2:
                            channelJoinType = ChannelJoinType.ACCEPT;
                            break;
                    }
                }
                break;

            case 4:
                channelViewMain(channel, user);
                return;
        }
        channelService.updateChannel(channel.getName(), new Channel(channel.getName(), description, affiliation, channelJoinType));

        System.out.println("수정 완료되었습니다.");

        channelViewMain(channel, user);
    }

    public static void createNotice(Channel channel, User user) {
        System.out.println("--- 공지 생성 ---");

        System.out.print("제목: ");
        String title = scanner.nextLine();
        System.out.print("내용: ");
        String content = scanner.nextLine();

        boolean isReplyAllowed = false;
        String isReplyInput;
        do {
            System.out.print("답장 허가 여부 (1. 허가, 2. 미허가): ");
            isReplyInput = scanner.nextLine();
        } while (!ValidationCheck.intSelectCheck(1, 2, isReplyInput));
        switch (Integer.parseInt(isReplyInput)) {
            case 1:
                isReplyAllowed = true;
                break;

            case 2:
                isReplyAllowed = false;
                break;
        }

        ChannelUserGrade userGrade = ChannelUserGrade.NORMAL;
        String userGradeInput;
        do {
            System.out.print("공지 접근 권한 (1. 채널 소유자, 2. 채널 관리자, 3. 일반 유저): ");
            userGradeInput = scanner.nextLine();
        } while (!ValidationCheck.intSelectCheck(1, 3, userGradeInput));
        switch (Integer.parseInt(userGradeInput)) {
            case 1:
                userGrade = ChannelUserGrade.ADMIN;
                break;

            case 2:
                userGrade = ChannelUserGrade.MANAGER;

                break;

            case 3:
                userGrade = ChannelUserGrade.NORMAL;
                break;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String scheduledTimeInput;
         do {
             System.out.print("공지 공개 시간(yyyy-MM-dd HH:mm, 미 입력시 바로 공개): ");
             scheduledTimeInput = scanner.nextLine().trim();
             if(scheduledTimeInput.isEmpty()) break;
        } while(!ValidationCheck.isValidDateAndTime(scheduledTimeInput));
        Date scheduledTime = new Date();
        try {
            scheduledTime = formatter.parse(scheduledTimeInput);
        }
        catch(Exception e) {

        }

        String replyDeadlineInput;
        do {
            System.out.print("답장 제한 시간(yyyy-MM-dd HH:mm, 미 입력시 제한 X): ");
            replyDeadlineInput = scanner.nextLine().trim();
            if(scheduledTimeInput.isEmpty()) break;
        } while(!ValidationCheck.isValidDateAndTime(replyDeadlineInput));
        Date replyDeadline = new Date();
        try {
            replyDeadline = formatter.parse(replyDeadlineInput);
        }
        catch(Exception e) {

        }

//        List<File> attachments;
        long noticeId = noticeService.createNotice(title, content, isReplyAllowed, userGrade, scheduledTime, replyDeadline, Collections.emptyList(), channel.getName());
        Notice notice = noticeRepository.getNoticeData(noticeId);

        System.out.println("공지가 생성되었습니다.");
        NoticeView.noticeViewMain(notice, user);

    }

    public static void manageUser(Channel channel, User user) {
        System.out.println("--- 채널 멤버 리스트 ---");
        int i = 1;
        List<String> userList = new ArrayList<>(channel.getChannelUserGradeHashMap().keySet());

        for (String userId : userList) {
            String userGrade = "존재하지 않음";
            switch (channel.getChannelUserGrade(userId)) {
                case ChannelUserGrade.NORMAL:
                    userGrade = "일반 유저";
                    break;
                case ChannelUserGrade.MANAGER:
                    userGrade = "관리자";
                    break;
                case ChannelUserGrade.ADMIN:
                    userGrade = "소유자";
                    break;
            }
            System.out.println(i + ". " + userId + " : " + userGrade);
            i++;
        }
        System.out.println(i + ". 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요 (돌아가기 : " + i + "): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        if (Integer.parseInt(value) == i) {
            channelViewMain(channel, user);
            return;
        }

        User selectUser = userRepositoy.getUserData(userList.get(Integer.parseInt(value) - 1));

        if(channel.getChannelUserGrade(selectUser.getId()) == ChannelUserGrade.ADMIN) {
            System.out.println("채널 소유자의 권한은 변경 불가능합니다.");
            channelViewMain(channel, user);
        }

        System.out.println("--- " + selectUser.getId() + " 유저 권한 변경 ---");
        System.out.println(selectUser.getId() + "의 현재 권한: " + channel.getChannelUserGrade(selectUser.getId()));
        System.out.println("1. 관리자");
        System.out.println("2. 일반유저");
        System.out.println("3. 돌아가기");

        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 3, value));

        switch (Integer.parseInt(value)) {
            case 1:
                channel.setChannelUserGrade(selectUser.getId(), ChannelUserGrade.MANAGER);
                break;

            case 2:
                channel.setChannelUserGrade(selectUser.getId(), ChannelUserGrade.NORMAL);
                break;

        }

        System.out.println("변경 완료 되었습니다.");
        channelViewMain(channel, user);
    }
}
