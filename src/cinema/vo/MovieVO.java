package cinema.vo;

import java.io.Serializable;
import java.util.Date;

//��ȭ���� VO
public class MovieVO implements Serializable {
	private static final long serialVersionUID = -8824840748208403740L;
	
	int num;				//�Ϸù�ȣ
	Date playdate;			//�󿵳�¥
	String title;			//��ǰ����
	int price;				//����
	
	public MovieVO() {
	}
	
	public MovieVO(int num, Date playdate, String title, int price) {
		this.num = num;
		this.playdate = playdate;
		this.title = title;
		this.price = price;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Date getPlaydate() {
		return playdate;
	}
	public void setPlaydate(Date playdate) {
		this.playdate = playdate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "MovieVO [num=" + num + ", playdate=" + playdate + ", title=" + title + ", price=" + price + "]";
	}
}
