package cinema.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import cinema.manager.SECManager;
import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

/**
 * SE cinema 예약 프로그램의 업무로직을 관리하는 클래스
 */

public class ServerManager implements SECManager {

	// SECManager 인터페이스로부터 상속받은 메서드를 Overriding한다.
	// 각 메서드 내에는 클라이언트로부터의 요청에 따라 DB에 값을 저장하거나, 검색, 수정등의 작업을 한다.

	@Override
	public ArrayList<MovieVO> listMovie(String strDate) {
		ArrayList<MovieVO> result = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		String sql = "select * from sec_movie where playdate = ?";
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MovieVO mv = new MovieVO(rs.getInt("num"), rs.getDate("playdate"), rs.getString("title"),
						rs.getInt("price"));
				result.add(mv);
			}
			return result;
		} catch (SQLException e) {
			System.out.println("ServerManager> " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("ServerManager> " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<Integer> listReservedNum(int num) {
		ArrayList<Integer> allSeat = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Integer> result = new ArrayList<>();
		String sql = "select seatnum from sec_reserve where num = ? and status = 'reserve'";
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt(1));
			}
			Collections.sort(result);
			for (int i = 1; i < 16; i++) {
				boolean flag=true;
				for (int j=0; j<result.size(); j++) {
					if (i==result.get(j)) {
						flag=false;
					}
				}
				if(flag){
					allSeat.add(i);
				}
			}
			return allSeat;
		} catch (SQLException e) {
			System.out.println("ServerManager> " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("ServerManager> " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean reserve(ReserveVO vo) {
		ArrayList<MovieVO> result = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		String sql = "insert into sec_reserve values (?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setInt(2, vo.getNum());
			pstmt.setInt(3, vo.getSeatnum());
			pstmt.setString(4, vo.getStatus());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("ServerManager> " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("ServerManager> " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<MyListVO> mylist(String id) {
		ArrayList<MyListVO> result = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		String sql = "select sec_movie.num, title, playdate, seatnum, price, status from sec_movie, sec_reserve "
				+ "where sec_movie.num = sec_reserve.num and id = ? ";
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			do {
				MyListVO mlv = new MyListVO(id, rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4),
						rs.getInt(5), rs.getString(6));
				result.add(mlv);
			} while (rs.next());
			return result;
		} catch (SQLException e) {
			System.out.println("ServerManager> " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("ServerManager> " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean cancel(MyListVO vo) {
		Connection conn = ConnectionManager.getConnection();
		String sql = "update sec_reserve set status = 'cancel' where id = ? and num = ? and seatnum = ? and status = 'reserve'";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setInt(2, vo.getNum());
			pstmt.setInt(3, vo.getSeatnum());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("ServerManager> " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("ServerManager> " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
