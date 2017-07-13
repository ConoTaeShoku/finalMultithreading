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
	private int SEAT_CNT = 15; // �¼� ��
	private JButton seatBt[]; // �¼� ���� ��ư �迭
	private JButton reserveBt; // ���� ��ư
	private JButton mylistBt; // ���� ���� ��Ȳ ��ư
	private JComboBox comboYear; // �󿵳�¥ ����
	private JComboBox comboMonth; // �󿵳�¥ ��
	private JComboBox comboDate; // �󿵳�¥ ��
	private JComboBox comboTitle; // ��ȭ����
	private int selectedNum; // ������ �¼� ��ȣ
	private String id; // ȸ�� ���̵�

	private ClientManager manager; // Ŭ���̾�Ʈ ��� ó�� Ŭ����
	ArrayList<MovieVO> movieList; // �ش� ��¥�� ���� ���� ��ȭ ���

	public static void main(String[] args) {
		ClientUI frame = new ClientUI();
	}

	public ClientUI() {
		// ȭ�� ���̾ƿ� ��ġ
		setTitle("SE CINEMA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 300, 600, 400);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(20, 20));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label_3 = new JLabel("��ȭ ���� �ý���");
		label_3.setFont(new Font("����", Font.BOLD, 18));
		panel.add(label_3);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		contentPane.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(10, 10));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new GridLayout(0, 5, 10, 10));
		panel_6.add(panel_1, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("�¼���ġ��");
		lblNewLabel.setFont(new Font("����", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSkf = new JLabel("�� ��¥");
		lblSkf.setFont(new Font("����", Font.BOLD, 13));
		panel_3.add(lblSkf);

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);

		JLabel lblWpahr = new JLabel("��ȭ ����");
		lblWpahr.setFont(new Font("����", Font.BOLD, 13));
		panel_3.add(lblWpahr);

		// �¼� ��ȣ ���� ��ư ����
		seatBt = new JButton[SEAT_CNT];
		for (int i = 0; i < SEAT_CNT; i++) {
			seatBt[i] = new JButton(Integer.toString(i + 1));
			panel_1.add(seatBt[i]);
		}

		// ���� ��Ȳ ��ư
		mylistBt = new JButton("���� ���� ��Ȳ");
		mylistBt.setFont(new Font("����", Font.BOLD, 13));
		panel_2.add(mylistBt);

		// ��¥ ���� �޺��ڽ�
		comboYear = new JComboBox();
		panel_4.add(comboYear);
		comboYear.addItem("2016");

		comboMonth = new JComboBox();
		panel_4.add(comboMonth);
		comboMonth.addItem("12");

		comboDate = new JComboBox();
		panel_4.add(comboDate);
		comboDate.addItem("����");
		comboDate.addItem("22");
		comboDate.addItem("23");
		comboDate.addItem("24");

		// ��ȭ ���� �޺��ڽ�
		comboTitle = new JComboBox();
		comboTitle.setOpaque(false);
		comboTitle.setBackground(Color.WHITE);
		panel_3.add(comboTitle);
		movieList = new ArrayList<>();

		panel_3.add(new JLabel(""));

		// ���� ��ư
		reserveBt = new JButton("����");
		reserveBt.setFont(new Font("����", Font.BOLD, 13));
		panel_3.add(reserveBt);

		// �̺�Ʈ ������ ����
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

		// �Ŵ��� ���� �� ȭ�� ����
		manager = new ClientManager();
		initTitle(); // ��ȭ���� �޺��ڽ� �ʱ�ȭ
		initSeatNum(); // �¼���ȣ��ư �ʱ�ȭ
		setVisible(true);

		// ȸ�� ���̵� �Է�
		String inputText = "";
		do {
			inputText = JOptionPane.showInputDialog("ȸ�� ���̵� �Է��ϼ���.");
		} while (inputText == null || inputText.equals(""));
		id = inputText;
	}

	// �޺��ڽ� (��, ��ȭ����) ���� �̺�Ʈ ó��
	class ComboBoxHandler implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox source = (JComboBox) e.getSource();

			// Ư�� ���� ���õǾ��� ��
			if (e.getStateChange() == ItemEvent.SELECTED) {

				// ��¥(��) ����
				if (source == comboDate) {
					if (comboDate.getSelectedIndex() != 0) {
						// ��¥�� �������� ���� �ش� ��¥�� "��-��-��" ������ String���� �����
						// ClientManager�� listMovie()�� �����Ѵ�.
						int date = 21 + comboDate.getSelectedIndex();
						String strDate = "2016-12-" + date;

						// ���ϵ� ��ȭ����� movieList�� �����ϰ�
						movieList = manager.listMovie(strDate);

						// ��ȭ ������ comboTitle �޺��ڽ��� ����Ѵ�.
						initTitle();
						for (MovieVO mv : movieList) {
							comboTitle.addItem((String) mv.getTitle());
						}
					} else {
						initSeatNum();
					}
				}

				// ��ȭ ���� ����
				else if (source == comboTitle) {

					// ��ȭ������ �����ϸ� �ش� ��ȭ�� num�� ClientManager�� listReservedNum()��
					// �����Ѵ�.
					String title = (String) comboTitle.getSelectedItem();
					int num = getMovieNum(title);

					if (num != -1) {

						// ���ϵ� �̹� ����� �¼���ȣ ��ϰ� ���Ͽ� �ش� �¼��� ��Ȱ��ȭ �ϰ� ������ �¼����� Ȱ��ȭ
						// �Ѵ�.
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
		case "�����Ÿ��":
			num = 1;
			break;
		case "�ǵ���":
			num = 2;
			break;
		case "��󷣵�":
			num = 3;
			break;
		case "��":
			num = 4;
			break;
		case "�ǹ���":
			num = 5;
			break;
		case "����":
			num = 6;
			break;
		case "�ź��� ��������":
			num = 7;
			break;
		case "����":
			num = 8;
			break;
		}
		return num;
	}

	// �¼� ���� �̺�Ʈ ó��
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

	// ��ư (����, ���� ������Ȳ����) �̺�Ʈ ó��
	class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			// ���� ��ư
			if (source == reserveBt) {
				if (selectedNum == 0) {
					JOptionPane.showMessageDialog(ClientUI.this, "�¼��� �������ּ���.");
					return;
				}

				// ������� ���̵� �� ����ڰ� ������ ��ȭ��ȣ, �¼���ȣ �� ClientManager�� reserve()��
				// �����Ͽ����� ó���Ѵ�.
				String title = (String) comboTitle.getSelectedItem();
				int num = getMovieNum(title);
				ReserveVO rv = new ReserveVO(ClientUI.this.id, num, selectedNum, "reserve");

				// reserve()�޼����� ���� ����� ���� ���� �޽����� ����.
				if (manager.reserve(rv)) {
					JOptionPane.showMessageDialog(ClientUI.this, "���� �Ϸ�");
					// refresh
					ArrayList<Integer> unreserved = new ArrayList<>();
					unreserved = manager.listReservedNum(num);
					initSeatNum();
					for (int empty : unreserved) {
						seatBt[empty - 1].setForeground(Color.BLACK);
						seatBt[empty - 1].setEnabled(true);
					}
				} else {
					JOptionPane.showMessageDialog(ClientUI.this, "���� ����");
				}
			}
			// ���� ���� ��Ȳ ��ư
			else if (source == mylistBt) {
				MyListDialog mydialog = new MyListDialog();
			}
		}
	}

	// ��ȭ���� �޺��ڽ� �ʱ�ȭ
	void initTitle() {
		comboTitle.removeAllItems();
		comboTitle.addItem("��ȭ���� ����");
	}

	// �¼� ���� ��ư �ʱ�ȭ
	void initSeatNum() {
		for (int i = 0; i < SEAT_CNT; i++) {
			seatBt[i].setForeground(Color.BLACK);
			seatBt[i].setEnabled(false);
		}
	}

	// ���� ���� ��Ȳ ��ȭ����
	class MyListDialog extends JDialog {
		JList list;
		JButton cancelBt;
		ArrayList<MyListVO> myList;
		MyListVO selectedValue;

		public MyListDialog() {
			// ȭ�� ���̾ƿ� ��ġ
			setTitle("���� ���� ��Ȳ");
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setBounds(450, 300, 500, 300);

			list = new JList<>();
			cancelBt = new JButton("���� ���");

			getContentPane().add(BorderLayout.CENTER, list);
			getContentPane().add(BorderLayout.SOUTH, cancelBt);

			// �ش� ȸ���� ���� ���� �˻�
			myList = manager.mylist(ClientUI.this.id);
			if (myList != null) {
				Object listData[] = myList.toArray();
				list.setListData(listData);
			}

			// ���� ���� ��Ȳ ����Ʈ���� ����� �׸� ���� �� �̺�Ʈ ó��
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					selectedValue = (MyListVO) list.getSelectedValue();
				}
			});

			// ���� ��� ��ư Ŭ�� �� �̺�Ʈ ó��
			cancelBt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (selectedValue == null) {
						JOptionPane.showMessageDialog(MyListDialog.this, "���� ������ ���ֽʽÿ�.");
						return;
					}
					if (selectedValue.getStatus().equals("cancel")) {
						JOptionPane.showMessageDialog(MyListDialog.this, "�̹� ��Ұ� �Ϸ�� �����Դϴ�.");
						return;
					}
					boolean result = manager.cancel(selectedValue);
					if (result) {
						JOptionPane.showMessageDialog(MyListDialog.this, "���Ű� ��ҵǾ����ϴ�.");
						// refresh
						myList = manager.mylist(ClientUI.this.id);
						Object listData[] = myList.toArray();
						list.setListData(listData);
					} else {
						JOptionPane.showMessageDialog(MyListDialog.this, "���� ��Ұ� �����Ͽ����ϴ�.");
					}
				}
			});

			setVisible(true);
		}
	}
}
