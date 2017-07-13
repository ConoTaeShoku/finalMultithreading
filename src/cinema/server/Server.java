package cinema.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//������ ���� Ŭ���� 
public class Server {

	public static void main(String[] args) {

		ObjectOutputStream oos;
		ObjectInputStream ois;

		try {
			ServerSocket ssocket = new ServerSocket(7777);
			System.out.println("Server> Waiting...");

			while (true) {
				// Ŭ���̾�Ʈ�κ����� ������ �̷������ ����� ��Ʈ���� �����ϰ�
				Socket socket = ssocket.accept();
				System.out.println("Server> connected!");
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());

				// ������ Ŭ���� ����
				new Thread(new ServerThread(ois, oos)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
