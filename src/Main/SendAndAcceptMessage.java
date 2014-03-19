package Main;

import java.awt.Color;
import java.util.Scanner;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class SendAndAcceptMessage {

	private static final String service = "wswincnhz0860";
	private static final String to = "leishi";
	private static final String from="sl";

	public static void main(String[] args) throws XMPPException {
		XMPPConnection.DEBUG_ENABLED = true;
		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(service);
		connectionConfig.setReconnectionAllowed(true);
		connectionConfig.setSendPresence(true);

		Connection connection = new XMPPConnection(connectionConfig);
		try {
			connection.connect();// 开启连接
		} catch (XMPPException e) {
			throw new IllegalStateException(e);
		}

		// 登录
		connection.login(from, "carrot", "tiantian");
		//System.out.println(connection.getUser());
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);

		ChatManager chatmanager = connection.getChatManager();
		chatmanager.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				// TODO Auto-generated method stub
				chat.addMessageListener(new MessageListener() {
					public void processMessage(Chat arg0, Message message) {
						System.out.println(to+": "+message.getBody());
					}
				});

			}
		});

		Chat newChat = chatmanager.createChat(to + "@" + service, null);

		Scanner input = new Scanner(System.in);
		while (true) {
			Message message=new Message();
			message.setBody(input.nextLine());
			message.setProperty("favoriteColor", new Color(0, 0, 0)); 
			newChat.sendMessage(message);
		}

	}
}
