package notid;

import notid.channel.ChannelService;
import notid.channel.repository.ChannelRepository;
import notid.channel.repository.MemoryChannelRepository;
import notid.user.repository.MemoryUserRepository;
import notid.user.repository.UserRepositoy;
import notid.user.UserService;

import java.util.HashMap;
import java.util.HashSet;

public class Config {

    public UserService userService() {
        return new UserService(userRepositoy());
    }

    public UserRepositoy userRepositoy() {
        return new MemoryUserRepository();
    }

    public ChannelService channelService() {
        return new ChannelService(channelRepository());
    }

    public ChannelRepository channelRepository() {
        return new MemoryChannelRepository();
    }
}
