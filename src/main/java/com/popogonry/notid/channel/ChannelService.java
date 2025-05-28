package com.popogonry.notid.channel;

import com.popogonry.notid.channel.repository.ChannelRepository;

public class ChannelService {
    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
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

        channelRepository.removeChannelData(name);
        channelRepository.addChannelData(newChannel);

        return true;
    }

    public boolean deleteChannel(String name) {

        // 채널이 존재 하지 않을 시,
        if(!channelRepository.hasChannelData(name)) return false;

        channelRepository.removeChannelData(name);

        return true;
    }
}
