package cinema.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//서버의 실행 클래스 
public class Server {

	public static void main(String[] args) {

		ObjectOutputStream oos;
		ObjectInputStream ois;

		try {
			ServerSocket ssocket = new ServerSocket(7777);
			System.out.println("Server> Waiting...");

			while (true) {
				// 클라이언트로부터의 접속이 이루어지면 입출력 스트림을 생성하고
				Socket socket = ssocket.accept();
				System.out.println("Server> connected!");
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());

				// 쓰레드 클래스 생성
				new Thread(new ServerThread(ois, oos)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
