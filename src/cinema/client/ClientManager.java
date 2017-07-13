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

	// ������. �������� ����.
	public ClientManager() {
		try {
			// ������ �����ϰ� ����� ��Ʈ���� �����Ѵ�.
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

	// �̰��� SECManager �������̽��κ��� ��ӹ��� �޼������ Overriding�Ѵ�.
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

	// �������� ��û ���� �޼���
	public Object sendRequest(Object[] request) {
		Object receive = null;
		try {
			oos.writeObject(request);
			// ������ request�� �����ϰ�
			receive = (Object[]) ois.readObject();
			// �������� ���� ��ü�� �����Ѵ�.
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
