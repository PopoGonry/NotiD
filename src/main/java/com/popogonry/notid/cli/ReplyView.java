package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.ReplyService;
import com.popogonry.notid.user.User;

import java.util.Scanner;

public class ReplyView {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Config config = new Config();

    private static final ReplyService replyService = config.replyService();



    public static void replyViewMain(Reply reply, User user) {

        Channel channel = reply.getNotice().getChannel();

        if (!channel.hasChannelUserGrade(user.getId())) {
            Common.clear();
            System.out.println(user.getId() + " 유저는 채널에 존재하지 않습니다.");
        }

        ChannelUserGrade channelUserGrade = channel.getChannelUserGrade(user.getId());
        if (channelUserGrade == ChannelUserGrade.NORMAL) {
            replyToAuthor(reply, user);
        } else if (channelUserGrade == ChannelUserGrade.MANAGER || channelUserGrade == ChannelUserGrade.ADMIN) {
            replyToManager(reply, user);
        }
    }

    public static void replyInfo(Reply reply) {
        System.out.println("--- " + reply.getNotice().getTitle() + " 답장 ---");
        System.out.println("- 제목: " + reply.getTitle());
        System.out.println("- 내용: " + reply.getContent());
        System.out.println("- 작성자: " + reply.getAuthor().getId() + " " + reply.getAuthor().getName());
        System.out.println("- 공지: " + reply.getNotice().getChannel().getName() + "/" + reply.getNotice().getTitle());
    }


    public static void replyToManager(Reply reply, User user) {
        replyInfo(reply);

        System.out.println("--- " + reply.getNotice().getTitle() + " 답장 관리자 메뉴 ---");
        System.out.println("1. 답장 삭제");
        System.out.println("2. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));
        Common.clear();

        switch (Integer.parseInt(value)) {
            case 1:
                updateReply(reply, user);
                break;

            case 2:
                replyViewMain(reply, user);
                break;

        }
    }

    public static void replyToAuthor(Reply reply, User user) {
        replyInfo(reply);

        System.out.println("--- " + reply.getNotice().getTitle() + " 답장 메뉴 ---");
        System.out.println("1. 답장 수정");
        System.out.println("2. 답장 삭제");
        System.out.println("3. 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 3, value));
        Common.clear();

        switch (Integer.parseInt(value)) {
            case 1:
                updateReply(reply, user);
                break;

            case 2:
                deleteReply(reply, user);
                break;

            case 3:
                replyViewMain(reply, user);
                break;

        }

    }

    public static void updateReply(Reply reply, User user) {

        replyInfo(reply);

        System.out.println("--- " + reply.getTitle() + " 답장 수정 메뉴 ---");
        System.out.println("1. 제목 수정");
        System.out.println("2. 내용 수정");


        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        String temp;

        String title = reply.getTitle();
        String content = reply.getContent();

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
        }

        Reply updateReply = new Reply(reply.getId(), title, content, reply.getFile(), reply.getNotice(), reply.getAuthor());
        replyService.updateReply(reply.getId(), updateReply);

        Common.clear();
        System.out.println("수정 완료되었습니다.");

        replyViewMain(reply, user);

    }

    public static void deleteReply(Reply reply, User user) {
        replyInfo(reply);
        System.out.println("--- " + reply.getTitle() + " 답장 삭제 ---");
        System.out.println("1. 삭제하기");
        System.out.println("2. 돌아가기");

        String temp;
        do {
            System.out.print("선택해주세요: ");
            temp = scanner.nextLine();
        } while (!ValidationCheck.intSelectCheck(1, 2, temp));

        Common.clear();

        switch (Integer.parseInt(temp)) {
            case 1:
                replyService.deleteReply(reply.getId());
                System.out.println("공지를 삭제 하였습니다.");
                NoticeView.noticeViewMain(reply.getNotice(), user);
                break;

            case 2:
                replyViewMain(reply, user);
                break;
        }
    }


//    public static void downloadFile(Reply reply, User user) {
//
//    }

}
