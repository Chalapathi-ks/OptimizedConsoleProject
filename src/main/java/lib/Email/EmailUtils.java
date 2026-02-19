package lib.Email;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;


public  class EmailUtils {
	
	 private Folder folder;

	  public enum EmailFolder {
	    INBOX("INBOX"),
	    SPAM("SPAM");

	    private String text;

	    private EmailFolder(String text){
	      this.text = text;
	    }

	    public String getText() {
	      return text;
	    }
	  }
 

	  /**
	   * Connects to email server with credentials provided to read from a given folder of the email application
	   * @param username Email username (e.g. janedoe@email.com)
	   * @param password Email password
	   * @param server Email server (e.g. smtp.email.com)
	   * @param emailFolder Folder in email application to interact with
	   */
	  public  Folder setConnection(String username, String password, String server, EmailFolder emailFolder) throws MessagingException {
	    Properties props = System.getProperties();
	    
	    props.put("mail.store.protocol", "imaps");
	    Session session = Session.getInstance(props);
	    Store store = session.getStore("imaps");
	    store.connect(server, username, password);


	    folder = store.getFolder(emailFolder.getText());
	    return folder;
	    
	  }
	  
	 


}
