package cinema.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

public class ClientUI extends JFrame {
	private int SEAT_CNT = 15; // 좌석 수
	private JButton seatBt[]; // 좌석 선택 버튼 배열
	private JButton reserveBt; // 예매 버튼
	private JButton mylistBt; // 나의 예매 현황 버튼
	private JComboBox comboYear; // 상영날짜 연도
	private JComboBox comboMonth; // 상영날짜 월
	private JComboBox comboDate; // 상영날짜 일
	private JComboBox comboTitle; // 영화제목
	private int selectedNum; // 선택한 좌석 번호
	private String id; // 회원 아이디

	private ClientManager manager; // 클라이언트 기능 처리 클래스
	ArrayList<MovieVO> movieList; // 해당 날짜의 예매 가능 영화 목록

	public static void main(String[] args) {
		ClientUI frame = new ClientUI();
	}

	public ClientUI() {
		// 화면 레이아웃 배치
		setTitle("SE CINEMA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 300, 600, 400);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(20, 20));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label_3 = new JLabel("영화 예매 시스템");
		label_3.setFont(new Font("굴림", Font.BOLD, 18));
		panel.add(label_3);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		contentPane.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(10, 10));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new GridLayout(0, 5, 10, 10));
		panel_6.add(panel_1, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("좌석배치도");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSkf = new JLabel("상영 날짜");
		lblSkf.setFont(new Font("굴림", Font.BOLD, 13));
		panel_3.add(lblSkf);

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);

		JLabel lblWpahr = new JLabel("영화 제목");
		lblWpahr.setFont(new Font("굴림", Font.BOLD, 13));
		panel_3.add(lblWpahr);

		// 좌석 번호 선택 버튼 생성
		seatBt = new JButton[SEAT_CNT];
		for (int i = 0; i < SEAT_CNT; i++) {
			seatBt[i] = new JButton(Integer.toString(i + 1));
			panel_1.add(seatBt[i]);
		}

		// 예매 현황 버튼
		mylistBt = new JButton("나의 예매 현황");
		mylistBt.setFont(new Font("굴림", Font.BOLD, 13));
		panel_2.add(mylistBt);

		// 날짜 선택 콤보박스
		comboYear = new JComboBox();
		panel_4.add(comboYear);
		comboYear.addItem("2016");

		comboMonth = new JComboBox();
		panel_4.add(comboMonth);
		comboMonth.addItem("12");

		comboDate = new JComboBox();
		panel_4.add(comboDate);
		comboDate.addItem("선택");
		comboDate.addItem("22");
		comboDate.addItem("23");
		comboDate.addItem("24");

		// 영화 선택 콤보박스
		comboTitle = new JComboBox();
		comboTitle.setOpaque(false);
		comboTitle.setBackground(Color.WHITE);
		panel_3.add(comboTitle);
		movieList = new ArrayList<>();

		panel_3.add(new JLabel(""));

		// 예매 버튼
		reserveBt = new JButton("예매");
		reserveBt.setFont(new Font("굴림", Font.BOLD, 13));
		panel_3.add(reserveBt);

		// 이벤트 리스너 연결
		ComboBoxHandler ch = new ComboBoxHandler();
		comboDate.addItemListener(ch);
		comboTitle.addItemListener(ch);

		SeatSelectHandler sh = new SeatSelectHandler();
		for (int i = 0; i < SEAT_CNT; i++) {
			seatBt[i].addActionListener(sh);
		}

		ButtonHandler bh = new ButtonHandler();
		reserveBt.addActionListener(bh);
		mylistBt.addActionListener(bh);

		// 매니저 생성 및 화면 띄우기
		manager = new ClientManager();
		initTitle(); // 영화제목 콤보박스 초기화
		initSeatNum(); // 좌석번호버튼 초기화
		setVisible(true);

		// 회원 아이디 입력
		String inputText = "";
		do {
			inputText = JOptionPane.showInputDialog("회원 아이디를 입력하세요.");
		} while (inputText == null || inputText.equals(""));
		id = inputText;
	}

	// 콤보박스 (일, 영화제목) 선택 이벤트 처리
	class ComboBoxHandler implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox source = (JComboBox) e.getSource();

			// 특정 값이 선택되었을 때
			if (e.getStateChange() == ItemEvent.SELECTED) {

				// 날짜(일) 선택
				if (source == comboDate) {
					if (comboDate.getSelectedIndex() != 0) {
						// 날짜를 변경했을 때는 해당 날짜를 "년-월-일" 형식의 String으로 만들어
						// ClientManager의 listMovie()로 전달한다.
						int date = 21 + comboDate.getSelectedIndex();
						String strDate = "2016-12-" + date;

						// 리턴된 영화목록을 movieList에 대입하고
						movieList = manager.listMovie(strDate);

						// 영화 제목은 comboTitle 콤보박스에 출력한다.
						initTitle();
						for (MovieVO mv : movieList) {
							comboTitle.addItem((String) mv.getTitle());
						}
					} else {
						initSeatNum();
					}
				}

				// 영화 제목 선택
				else if (source == comboTitle) {

					// 영화제목을 선택하면 해당 영화의 num을 ClientManager의 listReservedNum()로
					// 전달한다.
					String title = (String) comboTitle.getSelectedItem();
					int num = getMovieNum(title);

					if (num != -1) {

						// 리턴된 이미 예약된 좌석번호 목록과 비교하여 해당 좌석을 비활성화 하고 나머지 좌석들을 활성화
						// 한다.
						ArrayList<Integer> unreserved = new ArrayList<>();
						unreserved = manager.listReservedNum(num);
						initSeatNum();
						for (int empty : unreserved) {
							seatBt[empty - 1].setForeground(Color.BLACK);
							seatBt[empty - 1].setEnabled(true);
						}
					} else {
						initSeatNum();
					}
				}
			}
		}
	}

	public int getMovieNum(String title) {
		int num = -1;
		switch (title) {
		case "스노우타임":
			num = 1;
			break;
		case "판도라":
			num = 2;
			break;
		case "라라랜드":
			num = 3;
			break;
		case "형":
			num = 4;
			break;
		case "실버벨":
			num = 5;
			break;
		case "밀정":
			num = 6;
			break;
		case "신비한 동물사전":
			num = 7;
			break;
		case "공조":
			num = 8;
			break;
		}
		return num;
	}

	// 좌석 선택 이벤트 처리
	class SeatSelectHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < SEAT_CNT; i++) {
				seatBt[i].setForeground(Color.BLACK);
			}
			JButton selectedBt = (JButton) e.getSource();
			selectedBt.setForeground(Color.RED);
			selectedNum = Integer.parseInt(selectedBt.getText());
		}
	}

	// 버튼 (예매, 나의 예매현황보기) 이벤트 처리
	class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			// 예매 버튼
			if (source == reserveBt) {
				if (selectedNum == 0) {
					JOptionPane.showMessageDialog(ClientUI.this, "좌석을 선택해주세요.");
					return;
				}

				// 사용자의 아이디 및 사용자가 선택한 영화번호, 좌석번호 를 ClientManager의 reserve()로
				// 전달하여예매 처리한다.
				String title = (String) comboTitle.getSelectedItem();
				int num = getMovieNum(title);
				ReserveVO rv = new ReserveVO(ClientUI.this.id, num, selectedNum, "reserve");

				// reserve()메서드의 리턴 결과에 따라 다음 메시지를 띄운다.
				if (manager.reserve(rv)) {
					JOptionPane.showMessageDialog(ClientUI.this, "예매 완료");
					// refresh
					ArrayList<Integer> unreserved = new ArrayList<>();
					unreserved = manager.listReservedNum(num);
					initSeatNum();
					for (int empty : unreserved) {
						seatBt[empty - 1].setForeground(Color.BLACK);
						seatBt[empty - 1].setEnabled(true);
					}
				} else {
					JOptionPane.showMessageDialog(ClientUI.this, "예매 실패");
				}
			}
			// 나의 예매 현황 버튼
			else if (source == mylistBt) {
				MyListDialog mydialog = new MyListDialog();
			}
		}
	}

	// 영화제목 콤보박스 초기화
	void initTitle() {
		comboTitle.removeAllItems();
		comboTitle.addItem("영화제목 선택");
	}

	// 좌석 선택 버튼 초기화
	void initSeatNum() {
		for (int i = 0; i < SEAT_CNT; i++) {
			seatBt[i].setForeground(Color.BLACK);
			seatBt[i].setEnabled(false);
		}
	}

	// 나의 예매 현황 대화상자
	class MyListDialog extends JDialog {
		JList list;
		JButton cancelBt;
		ArrayList<MyListVO> myList;
		MyListVO selectedValue;

		public MyListDialog() {
			// 화면 레이아웃 배치
			setTitle("나의 예매 현황");
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setBounds(450, 300, 500, 300);

			list = new JList<>();
			cancelBt = new JButton("예매 취소");

			getContentPane().add(BorderLayout.CENTER, list);
			getContentPane().add(BorderLayout.SOUTH, cancelBt);

			// 해당 회원의 예매 내역 검색
			myList = manager.mylist(ClientUI.this.id);
			if (myList != null) {
				Object listData[] = myList.toArray();
				list.setListData(listData);
			}

			// 나의 예매 현황 리스트에서 취소할 항목 선택 시 이벤트 처리
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					selectedValue = (MyListVO) list.getSelectedValue();
				}
			});

			// 예매 취소 버튼 클릭 시 이벤트 처리
			cancelBt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (selectedValue == null) {
						JOptionPane.showMessageDialog(MyListDialog.this, "먼저 선택을 해주십시오.");
						return;
					}
					if (selectedValue.getStatus().equals("cancel")) {
						JOptionPane.showMessageDialog(MyListDialog.this, "이미 취소가 완료된 내역입니다.");
						return;
					}
					boolean result = manager.cancel(selectedValue);
					if (result) {
						JOptionPane.showMessageDialog(MyListDialog.this, "예매가 취소되었습니다.");
						// refresh
						myList = manager.mylist(ClientUI.this.id);
						Object listData[] = myList.toArray();
						list.setListData(listData);
					} else {
						JOptionPane.showMessageDialog(MyListDialog.this, "예매 취소가 실패하였습니다.");
					}
				}
			});

			setVisible(true);
		}
	}
}
