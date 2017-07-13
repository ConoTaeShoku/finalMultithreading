package cinema.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//���� ���� ��Ȳ ��¿� VO
public class MyListVO implements Serializable {
	private static final long serialVersionUID = -1184567727435235199L;

	String id;				//����� ���̵�
	int num;				//��ȭ���� ��ȣ
	String title;			//��ȭ ����
	Date playdate;			//�� ��¥
	int seatnum;			//�¼� ��ȣ
	int price;				//����
	String status;			//���� ���� (����, ���)
	
	public MyListVO() {
	}

	public MyListVO(String id, int num, String title, Date playdate, int seatnum, int price, String status) {
		this.id = id;
		this.num = num;
		this.title = title;
		this.playdate = playdate;
		this.seatnum = seatnum;
		this.price = price;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPlaydate() {
		return playdate;
	}
	public void setPlaydate(Date playdate) {
		this.playdate = playdate;
	}
	public int getSeatnum() {
		return seatnum;
	}
	public void setSeatnum(int seatnum) {
		this.seatnum = seatnum;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy�� MM�� dd��");
		String strDate = df.format(playdate);
		String strStatus = status.equals("reserve") ? "����" : "���";
		
		return "����:" + title + " / ����:" + strDate + " / �¼���ȣ:" + seatnum
				+ " / ����:" + price + " / ����:" + strStatus;
	}
}
