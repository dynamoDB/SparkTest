package Main;

import java.io.File;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
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
	private static final String to = "test";
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
		connection.sendPacket(presence);
	
		
		File file = new File(filePath);
		FileTransferManager transfer = new FileTransferManager(connection);
		OutgoingFileTransfer out = transfer
				.createOutgoingFileTransfer(to+"@"+service
						+ "/tiantian");//

		out.sendFile(file, file.getName());

		System.out.println("等待对方接受文件...");
//		while (!out.isDone()) {
//			if (out.getStatus() == FileTransfer.Status.in_progress) {
//				// 可以调用transfer.getProgress();获得传输的进度　
//				System.out.println(Math.floor((out.getProgress()*100))+"%");
//			}
//		}
		System.out.println("发送文件结束");
	}
}
