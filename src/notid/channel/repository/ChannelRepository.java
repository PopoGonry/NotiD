package notid.channel.repository;

import notid.channel.Channel;

import java.util.HashSet;

public interface ChannelRepository {
    void addChannelData(Channel channel);
    Channel getChannelData(String channelName);
    boolean hasChannelData(String channelName);
    void removeChannelData(String channelName);

    void addUserChannelData(String userId, Channel channel);
    boolean hasUserChannelData(String userId, String channelName);
    void removeUserChannelData(String userId, String channelName);

    void addUserChannelSetData(String userId, HashSet<Channel> channelSet);
    HashSet<String> getUserChannelSetData(String userId);
    boolean hasUserChannelSetData(String userId);
    void removeUserChannelSetData(String userId);

}
