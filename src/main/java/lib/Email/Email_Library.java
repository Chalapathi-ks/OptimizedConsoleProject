package lib.Email;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SubjectTerm;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email_Library {

	//************* EMAIL ACTIONS *******************

	  public void openEmail(Message message) throws Exception {
	    message.getContent();
	  }

	  public int getNumberOfMessages(Folder folder) throws MessagingException {
	    return folder.getMessageCount();
	  }

	  public int getNumberOfUnreadMessages(Folder folder)throws MessagingException {
	    return folder.getUnreadMessageCount();
	  }

	  /**
	   * Gets a message by its position in the folder. The earliest message is indexed at 1.
	   */
	  public Message getMessageByIndex(int index,Folder folder) throws MessagingException {
		  folder.open(Folder.READ_WRITE);
	    return folder.getMessage(index);
	  }

	  public Message getLatestMessage(Folder folder) throws MessagingException{
		 // folder.open(Folder.READ_WRITE);
	    return getMessageByIndex(getNumberOfMessages(folder), folder);
	  }

	  /**
	   * Gets all messages within the folder
	   */
	  public Message[] getAllMessages(Folder folder) throws MessagingException {
		  folder.open(Folder.READ_WRITE);
	    return folder.getMessages();
	  }

	  /**
	   * @param maxToGet maximum number of messages to get, starting from the latest. For example, enter 100 to get the last 100 messages received.
	   */
	  public Message[] getMessages(int maxToGet,Folder folder) throws MessagingException {
	    Map<String, Integer> indices = getStartAndEndIndices(maxToGet, folder);
	    return folder.getMessages(indices.get("startIndex"), indices.get("endIndex"));
	  }

	  /**
	   * Searches for messages with a specific subject
	   * @param subject Subject to search messages for
	   * @param unreadOnly Indicate whether to only return matched messages that are unread
	   * @param maxToSearch maximum number of messages to search, starting from the latest. For example, enter 100 to search through the last 100 messages.
	   */
	  public Message[] getMessagesBySubject(String subject, boolean unreadOnly, int maxToSearch, Folder folder) throws Exception {
		  folder.open(Folder.READ_WRITE);

	    Map<String, Integer> indices = getStartAndEndIndices(maxToSearch, folder);
	   

	    Message messages[] = folder.search(
	        new SubjectTerm(subject),
	        folder.getMessages(indices.get("startIndex").intValue(), indices.get("endIndex").intValue()));

	    if(unreadOnly){
	      List<Message> unreadMessages = new ArrayList<Message>();
	      for (Message message : messages) {
	        if(isMessageUnread(message)) {
	          unreadMessages.add(message);
	        }
	      }
	      messages = unreadMessages.toArray(new Message[]{});
	    }

	    return messages;
	  }

	  /**
	   * Returns HTML of the email's content
	   */
	  public String getMessageContent(Message message, Folder folder) throws Exception {
		  
		  
	    StringBuilder builder = new StringBuilder();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
	    String line;
	    while ((line = reader.readLine()) != null) {
	      builder.append(line);
	    }
	    return builder.toString();
	  }

	  /**
	   * Returns all urls from an email message with the linkText specified
	   */
	  public List<String> getUrlsFromMessage(Message message, String linkText, Folder folder) throws Exception {
	    String html = getMessageContent(message, folder);
	    List<String> allMatches = new ArrayList<String>();
	    Matcher matcher = Pattern.compile("(<a [^>]+>)"+linkText+"</a>").matcher(html);
	    while (matcher.find()) {
	      String aTag = matcher.group(1);
	      allMatches.add(aTag.substring(aTag.indexOf("http"), aTag.indexOf("\">")));
	    }
	    return allMatches;
	  }

	  private Map<String, Integer> getStartAndEndIndices(int max, Folder folder) throws MessagingException {
	    int endIndex = getNumberOfMessages(folder);
	    int startIndex = endIndex - max;

	    //In event that maxToGet is greater than number of messages that exist
	    if(startIndex < 1){
	      startIndex = 1;
	    }

	    Map<String, Integer> indices = new HashMap<String, Integer>();
	    indices.put("startIndex", startIndex);
	    indices.put("endIndex", endIndex);

	    return indices;
	  }

	  /**
	   * Gets text from the end of a line.
	   * In this example, the subject of the email is 'Authorization Code'
	   * And the line to get the text from begins with 'Authorization code:'
	   * Change these items to whatever you need for your email. This is only an example.
	   */
	  public String getAuthorizationCode(Folder folder) throws Exception {
	    Message email = getMessagesBySubject("Authorization Code", true, 5, folder)[0];
	    BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));

	    String line;
	    String prefix = "Authorization code:";

	    while ((line = reader.readLine()) != null) {
	      if(line.startsWith(prefix)) {
	        return line.substring(line.indexOf(":") + 1);
	      }
	    }
	    return null;
	  }

	  /**
	   * Gets one line of text
	   * In this example, the subject of the email is 'Authorization Code'
	   * And the line preceding the code begins with 'Authorization code:'
	   * Change these items to whatever you need for your email. This is only an example.
	   */
	  public String getVerificationCode(Folder folder) throws Exception {
	    Message email = getMessagesBySubject("Authorization Code", true, 5, folder)[0];
	    BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));

	    String line;
	    while ((line = reader.readLine()) != null) {
	      if(line.startsWith("Authorization code:")) {
	        return reader.readLine();
	      }
	    }
	    return null;
	  }
	  
	  //************* BOOLEAN METHODS *******************

	  /**
	   * Searches an email message for a specific string
	   */
	  public boolean isTextInMessage(Message message, String text, Folder folder) throws Exception {
	    String content = getMessageContent(message, folder);

	    //Some Strings within the email have whitespace and some have break coding. Need to be the same.
	    content = content.replace("&nbsp;", " ");
	    return content.contains(text);
	  }

	  public boolean isMessageInFolder(String subject, boolean unreadOnly, Folder folder) throws Exception {
	    int messagesFound = getMessagesBySubject(subject, unreadOnly, getNumberOfMessages(folder), folder).length;
	    return messagesFound > 0;
	  }

	  public boolean isMessageUnread(Message message) throws Exception {
	    return !message.isSet(Flags.Flag.SEEN);
	  }
	}

