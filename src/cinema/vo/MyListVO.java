package cinema.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//나의 예약 현황 출력용 VO
public class MyListVO implements Serializable {
	private static final long serialVersionUID = -1184567727435235199L;

	String id;				//사용자 아이디
	int num;				//영화정보 번호
	String title;			//영화 제목
	Date playdate;			//상영 날짜
	int seatnum;			//좌석 번호
	int price;				//가격
	String status;			//예매 상태 (예매, 취소)
	
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
		String strDate = df.format(playdate);
		String strStatus = status.equals("reserve") ? "예매" : "취소";
		
		return "제목:" + title + " / 상영일:" + strDate + " / 좌석번호:" + seatnum
				+ " / 가격:" + price + " / 상태:" + strStatus;
	}
}
