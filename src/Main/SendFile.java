package Main;

import java.io.File;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

public class SendFile {

	private static final String service = "wswincnhz0860";
	private static final String userName = "sl";
	private static final String password = "carrot";
	private static final String to = "leishi";
	private static final String filePath = "E:\\sendImageTest.jpg";

	public static void main(String[] args) throws XMPPException {
		XMPPConnection.DEBUG_ENABLED = true;

		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
				service);

		// 允许自动连接
		connectionConfig.setReconnectionAllowed(true);
		connectionConfig.setSendPresence(true);

		Connection connection = new XMPPConnection(connectionConfig);
		try {
			connection.connect();// 开启连接

		} catch (XMPPException e) {

			throw new IllegalStateException(e);
		}
		connection.login(userName, password, "tiantian");
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);// 上线了
		Presence pre = connection.getRoster().getPresence(userName + "@" + service);
		System.out.println(pre);
		Roster rosters=connection.getRoster();
	
		if (pre.getType() != Presence.Type.unavailable) {
			// 创建文件传输管理器
			FileTransferManager manager = new FileTransferManager(connection);
			// 创建输出的文件传输
			OutgoingFileTransfer transfer = manager
					.createOutgoingFileTransfer(pre.getFrom());
			// 发送文件
			transfer.sendFile(new File(filePath), "Image");
			while (!transfer.isDone()) {
				if (transfer.getStatus() == FileTransfer.Status.in_progress) {
					// 可以调用transfer.getProgress();获得传输的进度　
					System.out.println(transfer.getStatus());
					System.out.println(transfer.getProgress());
					System.out.println(transfer.isDone());
				}
			}
		}
	}
}
