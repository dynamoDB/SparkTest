package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.OfflineMessageManager;

public class GetMessageOnLogin {

	private static final String service = "wswincnhz0860";
	private static final String userName = "sl";
	private static final String password = "carrot";

	public static void main(String[] args) throws XMPPException {
		XMPPConnection.DEBUG_ENABLED = true;

		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
				service);

		// 允许自动连接
		connectionConfig.setReconnectionAllowed(true);
		connectionConfig.setSendPresence(false);// 不要告诉服务器自己的状态
		Connection connection = new XMPPConnection(connectionConfig);
		try {
			connection.connect();// 开启连接

		} catch (XMPPException e) {
			throw new IllegalStateException(e);
		}
		connection.login(userName, password, "tiantian");
		OfflineMessageManager offlineManager = new OfflineMessageManager(
				connection);
		try {
			Iterator<org.jivesoftware.smack.packet.Message> it = offlineManager
					.getMessages();

			System.out.println(offlineManager.supportsFlexibleRetrieval());
			System.out.println("离线消息数量: " + offlineManager.getMessageCount());

			Map<String, ArrayList<Message>> offlineMsgs = new HashMap<String, ArrayList<Message>>();

			while (it.hasNext()) {
				org.jivesoftware.smack.packet.Message message = it.next();
				System.out
						.println("收到离线消息, Received from 【" + message.getFrom()
								+ "】 message: " + message.getBody());
				String fromUser = message.getFrom().split("/")[0];

				if (offlineMsgs.containsKey(fromUser)) {
					offlineMsgs.get(fromUser).add(message);
				} else {
					ArrayList<Message> temp = new ArrayList<Message>();
					temp.add(message);
					offlineMsgs.put(fromUser, temp);
				}
			}

			// 在这里进行处理离线消息集合......
			Set<String> keys = offlineMsgs.keySet();
			Iterator<String> offIt = keys.iterator();
			while (offIt.hasNext()) {
				String key = offIt.next();
				ArrayList<Message> ms = offlineMsgs.get(key);

				for (int i = 0; i < ms.size(); i++) {
					System.out.println("-->" + ms.get(i).toString());
				}
			}

			offlineManager.deleteMessages();
		} catch (Exception e) {
			e.printStackTrace();
		}
		offlineManager.deleteMessages();// 删除所有离线消息
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);// 上线了
		// connection.disconnect();//关闭连接
		Presence pre = connection.getRoster().getPresence(
				"leishi@im.hengtiansoft.com");
		System.out.println(pre);

	}
}
