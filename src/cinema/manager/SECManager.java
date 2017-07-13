package cinema.manager;
import java.util.ArrayList;

import cinema.vo.MovieVO;
import cinema.vo.MyListVO;
import cinema.vo.ReserveVO;

/**
 * ������ Ŭ���̾�Ʈ �������� �����ؾ� �� ��ɵ�
 */
public interface SECManager {
	
	/**
	 * ��¥�� "��-��-��" �������� ���� �޾� �ش� ��¥�� ��ȭ ����� ����Ʈ�� �����Ѵ�.
	 * @param strDate �˻��� ��¥
	 * @return �ش� ��¥�� ��ȭ �� ���� ���
	 * */
	public ArrayList<MovieVO> listMovie(String strDate);

	/**
	 * �ش� ��ȭ������ ����� �¼� ��ȣ�� �迭�� �����Ѵ�.
	 * @param num ��ȭ���� ���̺��� �Ϸù�ȣ
	 * @return �̹� ����� �¼���ȣ
	 */
	public ArrayList<Integer> listReservedNum(int num);
	
	/**
	 * ����� ���̵�� ��ȭ��ȣ, ������ �¼���ȣ�� ���޹޾� ���� ó���Ѵ�.
	 * @param vo ����� ���̵�, ��ȭ���� ��ȣ, �¼� ��ȣ ������ ��� VO ��ü
	 * @return ���� ó�� ���� ����
	 */
	public boolean reserve(ReserveVO vo);

	/**
	 * �ش� ������� ����, ���� ��� ������ ������� �����Ѵ�.
	 * @param id ������ ����� ���̵�
	 * @return �ش� ������� ����&��� ����
	 */
	public ArrayList<MyListVO> mylist(String id);
	
	/**
	 * ���� ���� ��Ȳ���� ���� �׸��� Ŭ�� �� "���� ���" ��ư�� Ŭ���ϸ�
	 * ���� ������ �����Ͽ� ��� ���·� �����Ѵ�.
	 * @param vo ����� ���� ����
	 * @return ���� ��� ���� ����
	 */
	public boolean cancel(MyListVO vo);
}
