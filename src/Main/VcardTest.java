package Main;

import javax.xml.bind.util.ValidationEventCollector;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.VCard;

public class VcardTest {
	private static final String service = "im.hengtiansoft.com";
	private static final String userName="leishi";
	private static final String password="carrot2014!";
	private static final String resource="tiantian";

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
		connection.login(userName, password, resource);
		System.out.println(connection.getUser());
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);


		VCard vcard=new VCard();
		vcard.load(connection,"leishi@hengtiansoft.com");
		
		//vcard.setPhoneHome();
		vcard.setFirstName("Lei");
		
		vcard.save(connection);
		
		
	}
}
