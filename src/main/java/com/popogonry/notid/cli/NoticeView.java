package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.NoticeService;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.ReplyService;
import com.popogonry.notid.reply.replyRepository.MemoryReplyRepository;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class NoticeView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final Config config = new Config();

    private static final NoticeService noticeService = config.noticeService();
    private static final NoticeRepository noticeRepository = config.noticeRepository();

    private static final ReplyService replyService = config.replyService();
    private static final ReplyRepository replyRepository = config.replyRepository();


    public static void noticeViewMain(Notice notice, User user) {
        Channel channel = notice.getChannel();

        if (!channel.hasChannelUserGrade(user.getId())) {
            System.out.println(user.getId() + " 유저는 채널에 존재하지 않습니다.");
        }

        ChannelUserGrade channelUserGrade = channel.getChannelUserGrade(user.getId());
        if (channelUserGrade == ChannelUserGrade.NORMAL) {
            noticeToMember(notice, user);
        } else if (channelUserGrade == ChannelUserGrade.MANAGER || channelUserGrade == ChannelUserGrade.ADMIN) {
            noticeToManager(notice, user);
        }
    }

    public static void noticeToManager(Notice notice, User user) {
        System.out.println("--- 공지 관리자 메뉴 ---");

        System.out.println("제목: " + notice.getTitle());
        System.out.println("채널: " + notice.getChannel().getName());

        if(notice.getScheduledTime().after(new Date())) {
            System.out.println("공지 공개 시간: " + formatter.format(notice.getScheduledTime()));
        }
        if(notice.getReplyDeadline().after(new Date())) {
            System.out.println("답장 제한 시간: " + formatter.format(notice.getReplyDeadline()));
        }

        String grade = "";
        if (notice.getUserGrade() == ChannelUserGrade.NORMAL) {
            grade = "일반 유저";
        } else if (notice.getUserGrade() == ChannelUserGrade.MANAGER) {
            grade = "채널 관리자";
        } else if (notice.getUserGrade() == ChannelUserGrade.ADMIN) {
            grade = "채널 소유자";
        }
        System.out.println("공지 접근 권한: " + grade);
        System.out.println("답장 허가 여부: " + notice.isReplyAllowed());

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
        System.out.println("--- 공지 메뉴 ---");

        System.out.println("제목: " + notice.getTitle());
        System.out.println("채널: " + notice.getChannel().getName());

        if(notice.getReplyDeadline().after(new Date())) {
            System.out.println("답장 제한 시간: " + formatter.format(notice.getReplyDeadline()));
        }

        System.out.println("내용: " + notice.getContent());

        System.out.println();

        boolean hasReply = false;
        Reply reply = null;
        HashSet<Long> noticeReplySetData = replyRepository.getNoticeReplySetData(notice.getId());
        for (Long replyId : noticeReplySetData) {
            Reply replyData = replyRepository.getReplyData(replyId);
            if(replyData.getAuthor().equals(user)) {
                reply = replyData;
                hasReply = true;
                break;
            }
        }

        if(hasReply) {
            System.out.println("1. 답장 보기");
        }
        else {
            System.out.println("1. 답장 작성");
        }


        System.out.println("2. 돌아가기");


        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        switch (Integer.parseInt(value)) {
            case 1:
                if(hasReply) ReplyView.replyViewMain(reply, user);
                else createReply(notice, user);
                break;

            case 2:
                deleteNotice(notice, user);
                break;

        }
    }

    public static void updateNotice(Notice notice, User user) {
        System.out.println("--- 공지 수정 ---");

        System.out.println("1. 제목 수정");
        System.out.println("2. 내용 수정");
        System.out.println("3. 답장 허가 여부 수정");
        System.out.println("4. 공지 접근 권한 수정");
        System.out.println("5. 공지 공개 시간 수정");
        System.out.println("6. 답장 제한 시간 수정");
        System.out.println("7. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 7, value));

        String title = notice.getTitle();
        String content = notice.getContent();
        boolean isReplyAllowed = notice.isReplyAllowed();
        ChannelUserGrade userGrade = notice.getUserGrade();
        Date scheduledTime = notice.getScheduledTime();
        Date replyDeadline = notice.getReplyDeadline();

        String temp;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        switch (Integer.parseInt(value)) {
            case 1:
                System.out.print("제목(미 입력시 취소): ");
                temp = scanner.nextLine();
                if(!temp.isEmpty()) title = temp;
                break;

            case 2:
                System.out.print("내용(미 입력시 취소): ");
                temp = scanner.nextLine();
                if(!temp.isEmpty()) content = temp;
                break;

            case 3:
                do {
                    System.out.print("답장 허가 여부 (1. 허가, 2. 미허가, 미 입력시 취소): ");
                    temp = scanner.nextLine();
                    if(temp.isEmpty()) break;
                } while (!ValidationCheck.intSelectCheck(1, 2, temp));

                if(!temp.isEmpty()) {
                    switch (Integer.parseInt(temp)) {
                        case 1:
                            isReplyAllowed = true;
                            break;

                        case 2:
                            isReplyAllowed = false;
                            break;
                    }
                }
                break;

            case 4:
                do {
                    System.out.print("공지 접근 권한 (1. 채널 소유자, 2. 채널 관리자, 3. 일반 유저, 미 입력시 취소): ");
                    temp = scanner.nextLine();
                    if(temp.isEmpty()) break;
                } while (!ValidationCheck.intSelectCheck(1, 3, temp));

                if(!temp.isEmpty()) {
                    switch (Integer.parseInt(temp)) {
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
                }
                break;

            case 5:

                String scheduledTimeInput;
                do {
                    System.out.print("공지 공개 시간(yyyy-MM-dd HH:mm, 미 입력시 취소,  \' 삭제 \' 입력 시 공개): ");
                    scheduledTimeInput = scanner.nextLine().trim();
                    if(scheduledTimeInput.isEmpty() || scheduledTimeInput.equals("삭제")) break;
                } while(!ValidationCheck.isValidDateAndTime(scheduledTimeInput));
                if(scheduledTimeInput.equals("삭제")) scheduledTime = new Date();
                try {
                    scheduledTime = formatter.parse(scheduledTimeInput);
                }
                catch(Exception e) {

                }
                break;

            case 6:
                String replyDeadlineInput;
                do {
                    System.out.print("답장 제한 시간(yyyy-MM-dd HH:mm, 미 입력시 취소, \' 삭제 \' 입력 시 제한 X): ");
                    replyDeadlineInput = scanner.nextLine().trim();
                    if(replyDeadlineInput.isEmpty() || replyDeadlineInput.equals("삭제")) break;
                } while(!ValidationCheck.isValidDateAndTime(replyDeadlineInput));
                if(replyDeadlineInput.equals("삭제")) replyDeadline = new Date();
                try {
                    replyDeadline = formatter.parse(replyDeadlineInput);
                }
                catch(Exception e) {

                }
                break;

            case 7:
                noticeViewMain(notice, user);
                return;

        }
        Notice notice1 = new Notice(notice.getId(), title, content, isReplyAllowed, userGrade, scheduledTime, replyDeadline, null, notice.getChannel());
        noticeService.updateNotice(notice.getId(), notice1);

        System.out.println(notice1);

        System.out.println("수정 완료되었습니다.");

        noticeViewMain(notice, user);
    }

    public static void userReplyList(Notice notice, User user) {
        if(!replyRepository.hasNoticeReplySetData(notice.getId()) || replyRepository.getNoticeReplySetData(notice.getId()).isEmpty()) {
            System.out.println("공지에 답장이 없습니다.");
            noticeViewMain(notice, user);
            return;
        }

        System.out.println("--- " + notice.getTitle() + " 답장 ");


        ArrayList<Long> replyIdList = new ArrayList<>(replyRepository.getNoticeReplySetData(notice.getId()));

        int i = 1;
        for (Long replyId : replyIdList) {
                System.out.println(i + ". " + replyRepository.getReplyData(replyId).getTitle() + "(" + user.getId() + ")");
                i++;
        }

        System.out.println(i + ". 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요 (돌아가기 : " + i + "): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        if (Integer.parseInt(value) == i) {
            noticeViewMain(notice, user);
            return;
        }

        Reply reply = replyRepository.getReplyData(replyIdList.get(Integer.parseInt(value) - 1));
        ReplyView.replyViewMain(reply, user);

    }

    public static void deleteNotice(Notice notice, User user) {
        System.out.println("--- " + notice.getTitle() + "공지 삭제 ---");
        System.out.println("1. 삭제하기");
        System.out.println("2. 돌아가기");

        String temp;
        do {
            System.out.print("선택해주세요: ");
            temp = scanner.nextLine();
        } while (!ValidationCheck.intSelectCheck(1, 2, temp));

        switch (Integer.parseInt(temp)) {
            case 1:
                noticeService.deleteNotice(notice.getId());
                System.out.println("공지를 삭제 하였습니다.");
                ChannelView.channelViewMain(notice.getChannel(), user);
                break;

            case 2:
                noticeViewMain(notice, user);
                break;
        }
    }

    public static void sendNoticeAlarm(Notice notice, User user) {
        // 추후 구현
    }

    public static void createReply(Notice notice, User user) {
        System.out.println("--- 답장 작성 ---");
        System.out.print("제목: ");
        String title = scanner.nextLine();

        System.out.print("내용: ");
        String content = scanner.nextLine();

        long replyId = replyService.createReply(title, content, null, notice.getId(), user.getId());

        if(replyId != 0L) {
            System.out.println("답장이 작성되었습니다.");
            ReplyView.replyViewMain(replyRepository.getReplyData(replyId), user);
        }
        else {
            System.out.println("답장 작성 중 문제가 발생했습니다.");
            noticeViewMain(notice, user);
        }
    }
}
