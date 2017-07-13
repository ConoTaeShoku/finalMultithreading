package cinema.manager;
import java.util.ArrayList;

import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

/**
 * 서버와 클라이언트 양측에서 구현해야 할 기능들
 */
public interface SECManager {
	
	/**
	 * 날짜를 "년-월-일" 형식으로 전달 받아 해당 날짜의 영화 목록을 리스트로 리턴한다.
	 * @param strDate 검색할 날짜
	 * @return 해당 날짜의 영화 상영 정보 목록
	 * */
	public ArrayList<MovieVO> listMovie(String strDate);

	/**
	 * 해당 영화정보의 예약된 좌석 번호를 배열로 리턴한다.
	 * @param num 영화정보 테이블의 일련번호
	 * @return 이미 예약된 좌석번호
	 */
	public ArrayList<Integer> listReservedNum(int num);
	
	/**
	 * 사용자 아이디와 영화번호, 선택한 좌석번호를 전달받아 예매 처리한다.
	 * @param vo 사용자 아이디, 영화정보 번호, 좌석 번호 정보가 담긴 VO 객체
	 * @return 예매 처리 성공 여부
	 */
	public boolean reserve(ReserveVO vo);

	/**
	 * 해당 사용자의 예매, 예매 취소 내역을 목록으로 리턴한다.
	 * @param id 접속한 사용자 아이디
	 * @return 해당 사용자의 예매&취소 내역
	 */
	public ArrayList<MyListVO> mylist(String id);
	
	/**
	 * 나의 예매 현황에서 예매 항목을 클릭 후 "예매 취소" 버튼을 클릭하면
	 * 예매 정보를 전달하여 취소 상태로 변경한다.
	 * @param vo 취소할 예매 정보
	 * @return 예매 취소 성공 여부
	 */
	public boolean cancel(MyListVO vo);
}
