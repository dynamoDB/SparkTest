package Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class login {

	private static final String service = "wswincnhz0860";

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
		connection.login("sl", "carrot", "tiantian");
		System.out.println(connection.getUser());
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);

		connection.getChatManager().createChat("leishi@" + service, null)
				.sendMessage("Hello word!");

	}
}
