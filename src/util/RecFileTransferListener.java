package util;

import java.io.File;
import java.util.Arrays;

import javax.swing.JFileChooser;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.junit.Test;

public class RecFileTransferListener implements FileTransferListener  {
		
	private static String downloadDir="";
	
	public String getFileType(String fileFullName)
	{
		if(fileFullName.contains("."))
		{
			return "."+fileFullName.split("\\.")[1];
		}else{
			return fileFullName;
		}
	}
	
	@Override
	public void fileTransferRequest(FileTransferRequest request) {
		System.out.println("接收文件开始.....");
    	final IncomingFileTransfer inTransfer = request.accept();
    	final String fileName = request.getFileName();
        long length = request.getFileSize(); 
        final String fromUser = request.getRequestor().split("/")[0];
        System.out.println("文件大小:"+length + "  "+request.getRequestor());
        System.out.println(""+request.getMimeType());
        
        try { 
        	
        	JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new File(".")); 
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int result = chooser.showOpenDialog(null);
			System.out.println(result);
			if(result==JFileChooser.APPROVE_OPTION)
			{
				final File file = new File(chooser.getSelectedFile(),fileName);
		        downloadDir=file.getAbsolutePath();

				System.out.println("downloadDir: "+downloadDir);
					new Thread(){
						public void run()
						{
						try {

							System.out.println("接受文件: " + fileName);
							inTransfer.recieveFile(file);

							Message message = new Message();
							message.setFrom(fromUser);
							message.setProperty("REC_SIGN", "SUCCESS");
							message.setBody("["+fromUser+"]发送文件: "+fileName+"/r/n"+"存储位置: "+file.getAbsolutePath()+ getFileType(fileName));
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						}
					}.start();
			}else{
				
				System.out.println("拒绝接受文件: "+fileName);
				
				request.reject();
				Message message = new Message();
				message.setFrom(fromUser);
				message.setBody("拒绝"+fromUser+"发送文件: "+fileName);
				message.setProperty("REC_SIGN", "REJECT");
			    request.reject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("接收文件结束.....");
	}
	
	@Test
	public void test(){
		String[] split="sendImageTest.jpg".split("\\.");
		System.out.println(Arrays.toString(split));
	}
}
