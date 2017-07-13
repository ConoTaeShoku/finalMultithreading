package cinema.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

/**
 * 서버의 다중접속 환경을 구현하기 위한 스레드 클래스 Server에서 사용자의 접속이 이루어지면 ServerThread 객체를 생성하여
 * run() 메서드에서 ObjectInputStream 와 ObjectOutputStream을 이용하여 클라이언트와 독립적인 통신을
 * 수행한다.
 */
public class ServerThread implements Runnable {

	private ServerManager sm = new ServerManager();
	private ObjectInputStream nois;
	private ObjectOutputStream noos;
	private boolean exit = false;

	public ServerThread(ObjectInputStream nois, ObjectOutputStream noos) {
		this.nois = nois;
		this.noos = noos;
	}

	@Override
	public void run() {
		while (!exit) {
			try {
				// 클라이언트로부터의 요청(r=request)을 받고 요청의 종류를 구분하여 처리한다.
				// 요청 종류에 따라 전달된 값을 ServerManager클래스의 메서드로 전달하여 처리.
				Object[] r = (Object[]) nois.readObject();
				String protocol = (String) r[0];

				switch (protocol) {
				
				case "getListMovie":
					r[1] = (ArrayList<MovieVO>) sm.listMovie((String) r[1]);
					break;
					
				case "listReservedNum":
					r[1] = (ArrayList<Integer>) sm.listReservedNum((int) r[1]);
					break;
					
				case "reserve":
					r[1] = (boolean) sm.reserve((ReserveVO) r[1]);
					break;
					
				case "getMyList":
					r[1] = (ArrayList<MyListVO>) sm.mylist((String) r[1]);
					break;
					
				case "cancel":
					r[1] = (boolean) sm.cancel((MyListVO) r[1]);
					break;
				}
				noos.writeObject(r);
				
			} catch (IOException ioe) {
				exit = true;
				System.out.println("ServerThread> "+ioe.getMessage());
				
			} catch (ClassNotFoundException cce) {
				exit = true;
				System.out.println("ServerThread> "+cce.getMessage());
			} // catch
		} // while
	}
}
