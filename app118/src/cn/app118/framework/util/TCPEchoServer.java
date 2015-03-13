package cn.app118.framework.util;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPEchoServer
{

	public static void main(String[] args) {
		
		try {
			// 获取服务器的端口
			int serverPort = 2317;
			// 创建用于客户端连接的SocketServer实例
			ServerSocket server = new ServerSocket(serverPort);
			
			while (true){
				System.out.println("等待客户端发送数据...");
				Socket socket = server.accept();
				SocketAddress address = socket.getRemoteSocketAddress();
				System.out.println("客户端IP地址： " + address);
				
				// 组合输入流
				DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				/*// 读取一个double值
				double value = dis.readDouble();
				System.out.println("收到客服端：Server received Double Value=" + value);
				
				// 读取一个Int值
				int value2 = dis.readInt();
				System.out.println("收到客服端：Server received Int Value=" + value2);
				
				// 读取一个Long值
				long value3 = dis.readLong();
				System.out.println("收到客服端：Server received Long Value=" + value3);*/
				
				String s=dis.readUTF();
				System.out.println("收到客服端请求：" + s);
			
				System.out.println("---------------------");
				System.out.println("服务器端正在发送数据.....");
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				/*out.writeLong(value3);
				out.writeDouble(value);
				out.writeInt(value2);*/
				String responseStr="server checked success:"+s;
				out.writeUTF(responseStr);
				System.out.println("向客服端返回数据："+responseStr);
				//out.writeChars("wRithcie's Server!");
				System.out.println("服务器端发送数据结束。");
				out.flush();

				socket.close();
			}
		} catch (Exception e) {
			System.out.println("TCP Server异常：");
			e.printStackTrace();
		}
	}

	
	

}
