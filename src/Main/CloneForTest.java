package Main;

import java.awt.TrayIcon.MessageType;
import java.util.Scanner;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import de.javawi.jstun.attribute.MessageAttributeInterface.MessageAttributeType;

public class CloneForTest {

	private static final String service = "wswincnhz0860";
	private static final String to = "sl";

	public static void main(String[] args) throws XMPPException {
		XMPPConnection.DEBUG_ENABLED = true;

		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
				service);
		connectionConfig.setReconnectionAllowed(true);
		connectionConfig.setSendPresence(true);

		Connection connection = new XMPPConnection(connectionConfig);
		try {
			connection.connect();// 开启连接

		} catch (XMPPException e) {
			throw new IllegalStateException(e);
		}

		// 登录
		connection.login("test", "test", "tiantian");
		System.out.println(connection.getUser());

		ChatManager chatmanager = connection.getChatManager();
		Chat newChat = chatmanager.createChat(to + "@" + service, null);
		Message message=new Message();
		message.setTo(to);
		message.setType(Message.Type.normal);
		for(int i=0;i<20;i++){
			message.setBody("Message: "+i);
			newChat.sendMessage(message);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
