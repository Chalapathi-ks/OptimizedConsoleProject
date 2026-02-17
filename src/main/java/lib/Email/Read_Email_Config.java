package lib.Email;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Read_Email_Config {
	
	public static Properties CONFIG;
	
	public Read_Email_Config() throws IOException {
		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/lib/EmailAutomation/email_config.properties");
		CONFIG.load(fs);
	}
	
	//************* GET EMAIL PROPERTIES *******************

	  public String getEmailAddressFromProperties(){
	    return CONFIG.getProperty("email.address");
	  }

	  public String getEmailUsernameFromProperties(){
	    return CONFIG.getProperty("email.username");
	  }

	  public String getEmailPasswordFromProperties(){
	    return CONFIG.getProperty("email.password");
	  }

	  public String getEmailProtocolFromProperties(){
	    return CONFIG.getProperty("email.protocol");
	  }

	  public int getEmailPortFromProperties(){
	    return Integer.parseInt(CONFIG.getProperty("email.port"));
	  }

	  public String getEmailServerFromProperties(){
	    return CONFIG.getProperty("mail.smtp.host");
	  }

}
