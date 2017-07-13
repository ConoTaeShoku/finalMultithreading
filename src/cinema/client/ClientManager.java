package cinema.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import cinema.manager.SECManager;
import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

public class ClientManager implements SECManager {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	// 생성자. 서버로의 접속.
	public ClientManager() {
		try {
			// 서버로 접속하고 입출력 스트림을 생성한다.
			socket = new Socket("localhost", 7777);
			System.out.println("ClientManager> connected to server!");
			is = socket.getInputStream();
			os = socket.getOutputStream();
			ois = new ObjectInputStream(is);
			oos = new ObjectOutputStream(os);
		} catch (UnknownHostException e) {
			System.out.println("ClientManager> " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ClientManager> " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 이곳에 SECManager 인터페이스로부터 상속받은 메서드들을 Overriding한다.
	@Override
	public ArrayList<MovieVO> listMovie(String strDate) {
		Object[] reqNrec = { "getListMovie", strDate };
		reqNrec = (Object[]) this.sendRequest(reqNrec);
		return (ArrayList<MovieVO>) reqNrec[1];
	}

	@Override
	public ArrayList<Integer> listReservedNum(int num) {
		Object[] reqNrec = { "listReservedNum", num };
		reqNrec = (Object[]) this.sendRequest(reqNrec);
		return (ArrayList<Integer>) reqNrec[1];
	}

	@Override
	public boolean reserve(ReserveVO vo) {
		Object[] reqNrec = { "reserve", vo };
		reqNrec = (Object[]) this.sendRequest(reqNrec);
		return (boolean) reqNrec[1];
	}

	@Override
	public ArrayList<MyListVO> mylist(String id) {
		Object[] reqNrec = { "getMyList", id };
		reqNrec = (Object[]) this.sendRequest(reqNrec);
		return (ArrayList<MyListVO>) reqNrec[1];
	}

	@Override
	public boolean cancel(MyListVO vo) {
		Object[] reqNrec = { "cancel", vo };
		reqNrec = (Object[]) this.sendRequest(reqNrec);
		return (boolean) reqNrec[1];
	}

	// 서버로의 요청 전송 메서드
	public Object sendRequest(Object[] request) {
		Object receive = null;
		try {
			oos.writeObject(request);
			// 서버로 request를 전송하고
			receive = (Object[]) ois.readObject();
			// 응답으로 받은 객체를 리턴한다.
		} catch (ClassNotFoundException e) {
			System.out.println("ClientManager> " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ClientManager> " + e.getMessage());
			e.printStackTrace();
		}
		return receive;
	}
}
