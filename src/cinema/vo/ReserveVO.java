package cinema.vo;

import java.io.Serializable;

//예약정보 VO
public class ReserveVO implements Serializable {
	private static final long serialVersionUID = -1298452200045104533L;

	String id;		//회원 아이디
	int num;		//영화 정보 일련번호
	int seatnum;	//좌석번호
	String status;	//예매 상태 (reserve:정상예매, cancel:예매취소)
	
	public ReserveVO() {
	}
	
	public ReserveVO(String id, int num, int seatnum, String status) {
		this.id = id;
		this.num = num;
		this.seatnum = seatnum;
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
	public int getSeatnum() {
		return seatnum;
	}
	public void setSeatnum(int seatnum) {
		this.seatnum = seatnum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReserveVO [id=" + id + ", num=" + num + ", seatnum=" + seatnum + ", status=" + status + "]";
	}
}
