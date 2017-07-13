package cinema.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

/**
 * ������ �������� ȯ���� �����ϱ� ���� ������ Ŭ���� Server���� ������� ������ �̷������ ServerThread ��ü�� �����Ͽ�
 * run() �޼��忡�� ObjectInputStream �� ObjectOutputStream�� �̿��Ͽ� Ŭ���̾�Ʈ�� �������� �����
 * �����Ѵ�.
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
				// Ŭ���̾�Ʈ�κ����� ��û(r=request)�� �ް� ��û�� ������ �����Ͽ� ó���Ѵ�.
				// ��û ������ ���� ���޵� ���� ServerManagerŬ������ �޼���� �����Ͽ� ó��.
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
