package lib;

import com.ullink.slack.simpleslackapi.*;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

public class SlackNotify {
    SlackSession slackSession;
      private String slackAuthToken;

    public SlackNotify(String slackAuthToken){
     this.slackAuthToken = slackAuthToken;
    }

    public void makeSlackConnection() throws IOException {
            slackSession = SlackSessionFactory.createWebSocketSlackSession(slackAuthToken);
        slackSession.connect();

    }

    public void sendMessageToAChannel(String channelName) throws IOException {
        try {
            if(slackSession.isConnected()) {
                SlackChannel channel = slackSession.findChannelByName(channelName);
                slackSession.sendMessage(channel, "All Test Passed", null);
            }
        }catch(Exception se){
            se.getMessage();
            makeSlackConnection();
            SlackChannel channel = slackSession.findChannelByName(channelName);
            slackSession.sendMessage(channel, "All Test Passed", null);
        }
    }

    public void sendDirectMessageToAUser(String userName)
    {
        SlackUser user = slackSession.findUserByUserName(userName);
        slackSession.sendMessageToUser(user, "All Test Passed", null);

    }

    /**
     * This method shows how to send a message using the PreparedMessage builder (allows for multiple attachments)
     */
    public void sendUsingPreparedMessage(SlackSession session)
    {
        //get a channel
        SlackChannel channel = session.findChannelByName("achannel");

        //build a message object
        SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
                .withMessage("Hey, this is a message")
                .withUnfurl(false)
                .addAttachment(new SlackAttachment())
                .addAttachment(new SlackAttachment())
                .build();

        session.sendMessage(channel, preparedMessage);
    }
}
