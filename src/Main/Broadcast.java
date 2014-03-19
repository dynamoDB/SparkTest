package Main;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Broadcast {
	
	private static final String service="wswincnhz0860";
	
	public static void main(String[] args) throws XMPPException {
		XMPPConnection.DEBUG_ENABLED = true;
		AccountManager accountManager;
		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(service);
		connectionConfig.setReconnectionAllowed(true);
		connectionConfig.setSendPresence(true);

		Connection connection = new XMPPConnection(connectionConfig);
		try {
			connection.connect();// 开启连接
			accountManager = connection.getAccountManager();// 获取账户管理类
		} catch (XMPPException e) {
			throw new IllegalStateException(e);
		}

		// 登录
		connection.login("sl", "carrot", "tiantian");
		System.out.println(connection.getUser());
		
		Message newmsg = new Message(); 
		newmsg.setTo("leishi@"+service);
		newmsg.setSubject("重要通知");
		newmsg.setBody("今天下午2点60分有会！");
		newmsg.setType(Message.Type.headline);// normal支持离线 
		connection.sendPacket(newmsg);
		connection.disconnect();
		return ;
	}
}
