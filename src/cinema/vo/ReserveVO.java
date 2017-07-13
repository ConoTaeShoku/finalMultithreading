package cinema.vo;

import java.io.Serializable;

//�������� VO
public class ReserveVO implements Serializable {
	private static final long serialVersionUID = -1298452200045104533L;

	String id;		//ȸ�� ���̵�
	int num;		//��ȭ ���� �Ϸù�ȣ
	int seatnum;	//�¼���ȣ
	String status;	//���� ���� (reserve:���󿹸�, cancel:�������)
	
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
