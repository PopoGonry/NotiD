package com.popogonry.notid.channel;

import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.repository.UserRepositoy;

public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepositoy userRepository;

    public ChannelService(ChannelRepository channelRepository, UserRepositoy userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    public boolean createChannel(String name, String description, String affiliation, ChannelJoinType joinType) {

        // 채널이 존재 할 시,
        if(channelRepository.hasChannelData(name)) return false;

        Channel channel = new Channel(name, description, affiliation, joinType);

        channelRepository.addChannelData(channel);
        return true;
    }

    public boolean updateChannel(String name, Channel newChannel) {

        // 채널이 존재 하지 않을 시,
        if(!channelRepository.hasChannelData(name)) return false;

        // 원래 채널의 이름과 새로운 채널의 이름이 다를 시,
        if(!name.equals(newChannel.getName())) return false;

        Channel channel = channelRepository.getChannelData(name);
        channel.setDescription(newChannel.getDescription());
        channel.setAffiliation(newChannel.getAffiliation());
        channel.setJoinType(newChannel.getJoinType());

        return true;
    }

    public boolean deleteChannel(String name) {

        // 채널이 존재 하지 않을 시,
        if(!channelRepository.hasChannelData(name)) return false;

        channelRepository.removeChannelData(name);

        return true;
    }

    public boolean joinChannel(User user, Channel channel) {
        // 채널이 없을 때,
        if(!channelRepository.hasChannelData(channel.getName())) return false;

        // 유저가 없을 때,
        if(!userRepository.hasUserData(user.getId())) return false;

        if (channel.getJoinType() == ChannelJoinType.ACCEPT) {
            channel.addChannnelJoiningUser(user.getId());
            return true;
        }

        channelRepository.addUserChannelData(user.getId(), channel.getName());
        channel.addChannelUserGrade(user.getId(), ChannelUserGrade.NORMAL);

        return true;
    }

    public boolean leaveChannel(User user, Channel channel) {
        // 채널이 없을 때,
        if(!channelRepository.hasChannelData(channel.getName())) return false;

        // 유저가 없을 때,
        if(!userRepository.hasUserData(user.getId())) return false;

        channelRepository.removeUserChannelData(user.getId(), channel.getName());
        channel.removeChannelUserGrade(user.getId());

        return true;
    }

    public boolean acceptUserJoining(User user, Channel channel) {
        // 채널이 없을 때,
        if(!channelRepository.hasChannelData(channel.getName())) return false;

        // 유저가 없을 때,
        if(!userRepository.hasUserData(user.getId())) return false;

        if(!channel.getChannnelJoiningUserSet().contains(user.getId())) return false;

        channel.getChannnelJoiningUserSet().remove(user.getId());

        channelRepository.addUserChannelData(user.getId(), channel.getName());
        channel.addChannelUserGrade(user.getId(), ChannelUserGrade.NORMAL);

        return true;
    }
    public boolean declineUserJoining(User user, Channel channel) {
        // 채널이 없을 때,
        if(!channelRepository.hasChannelData(channel.getName())) return false;

        // 유저가 없을 때,
        if(!userRepository.hasUserData(user.getId())) return false;

        if(!channel.getChannnelJoiningUserSet().contains(user.getId())) return false;

        channel.getChannnelJoiningUserSet().remove(user.getId());

        return true;
    }

    public boolean requestUserJoining(User user, Channel channel) {
        // 채널이 없을 때,
        if(!channelRepository.hasChannelData(channel.getName())) return false;

        // 이미 가입 신청 중 일때,
        if(channel.getChannnelJoiningUserSet().contains(user.getId())) return false;

        channel.getChannnelJoiningUserSet().add(user.getId());
        return true;
    }

    public boolean canAccessChannel(User user, Notice notice) {

        int userScore = 0;
        switch(notice.getChannel().getChannelUserGrade(user.getId())) {
            case NORMAL:
                userScore = 1;
                break;

            case MANAGER:
                userScore = 2;
                break;

            case ADMIN:
                userScore = 3;
                break;
        }

        int serverScore = 0;
        switch(notice.getUserGrade()) {
            case NORMAL:
                serverScore = 1;
                break;

            case MANAGER:
                serverScore = 2;
                break;

            case ADMIN:
                serverScore = 3;
                break;
        }

        return userScore >= serverScore;

    }

}
