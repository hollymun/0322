package usedb;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Controller {

	public static void main(String[] args) {
		//DAO 클래스의 인스턴스를 생성 
		MessageDAO dao = MessageDAOImpl.getInstance();
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.printf("메뉴입력(1:삽입 2:전체보기 3:번호순 5개 보기 4:더보기 5:페이지 6:수정 7:삭제 100:종료):");
			String menu = sc.nextLine();
		//이름, 내용, 날짜를 저장할 임시변수 선언 
		String name = null;
		String content = null; 
		Date regdate = null;
			switch(menu) {
			case "1": 
				System.out.printf("이름을 입력하세요:");
				name = sc.nextLine();
				System.out.printf("내용을 입력하세요:");
				content = sc.nextLine();
				Calendar cal = new GregorianCalendar();
				regdate = new Date(cal.getTimeInMillis());
				
				MessageVO vo = new MessageVO(); 
				vo.setName(name);
				vo.setContent(content);
				vo.setRegdate(regdate);
				
				int result = dao.insertMessage(vo);
				if(result > 0) {
					System.out.printf("삽입 성공\n");
				}
				else {
					System.out.printf("삽입 실패\n");
				}
				System.out.printf("엔터를 누르면 넘어갑니다\n"); 
				sc.nextLine();
				break;
			case "2":
				List<MessageVO> list1 = dao.getAllMessageList();
				for(MessageVO imsi : list1) {
					System.out.printf("%s\n", imsi);
				}
				System.out.printf("엔터를 누르면 넘어갑니다:\n"); 
				sc.nextLine();
				break; 
			case "3":
				List<MessageVO> list2 = dao.fiveList();
				for(MessageVO imsi : list2) {
					System.out.printf("%s\n",  imsi);
				}
				System.out.printf("엔터를 누르면 넘어갑니다:\n"); 
				sc.nextLine();
				break;
			case "4":
				List<MessageVO> list3 = dao.modList();
				for(MessageVO imsi : list3) {
					System.out.printf("%s\n", imsi);
				}
				System.out.printf("엔터를 누르면 넘어갑니다:\n"); 
				sc.nextLine();
				break;
			case "5":
				//페이지 번호와 데이터 개수 입력 받기 
				System.out.printf("페이지 번호를 입력:");
				int page = sc.nextInt();
				System.out.printf("데이터 개수를 입력:");
				int cnt = sc.nextInt();
				
				//파라미터 만들기 
				Map<String, Object>map = new HashMap<String, Object>(); 
				map.put("page", page);
				map.put("cnt", cnt);
				
				//데이터 가져오기 
				List<MessageVO> list4 = dao.pageList(map);
				if(list4.size() == 0) {
					System.out.printf("읽어올 데이터가 없습니다\n");
				}
				else {
					//데이터가 0이 아니라면 전체 읽어오기 
					for(MessageVO imsi : list4) {
						System.out.printf("%s\n", imsi);
					}
				}
				System.out.printf("엔터를 누르면 넘어갑니다:\n"); 
				sc.nextLine();
				break;
			case "6":
				System.out.printf("수정할 글번호 입력:");
				int updatenum = sc.nextInt();
				//데이터가 존재하는지 확인
				MessageVO vo2 = dao.detailMessage(updatenum);
				//데이터가 존재하지 않는 경우
				if(vo2 == null) {
					System.out.printf("없는 번호 입니다\n");
				}
				//데이터가 존재하는 경우 
				else {
					sc.nextLine();
					System.out.printf("수정할 이름 입력:");
					String n = sc.nextLine();
					System.out.printf("수정할 내용 입력:");
					String c = sc.nextLine();
					vo2.setName(n);
					vo2.setContent(c);
					int r = dao.updateMessage(vo2);
					if(r >= 0) {
						JOptionPane.showMessageDialog(null, "수정 성공\n");
					}
					else {
						JOptionPane.showMessageDialog(null, "수정 실패\n");						
					}
				}
				System.out.printf("엔터를 누르면 넘어갑니다\n"); 
				sc.nextLine();
				break; 
			case "7":
				System.out.printf("삭제할 글번호 입력:"); 
				int deletenum = sc.nextInt(); 
				MessageVO vo3 = dao.detailMessage(deletenum);
				System.out.printf("%s\n", vo3);
				if(vo3 == null) {
					System.out.printf("없는 번호 입니다\n");
				}
				else {
					sc.nextLine();
					System.out.printf("정말로 삭제하시겠습니까? (1:삭제 2:취소) : \n");
					int m = sc.nextInt();
					if (m == 1) {
						int r = dao.deleteMessage(deletenum);
						if(r >= 0) {
							System.out.printf("삭제 성공\n");
						}
						else {
							System.out.printf("삭제 실패\n");
						}
					}
				}
				System.out.printf("엔터를 누르면 넘어갑니다\n"); 
				sc.nextLine();
				break; 
			case "100": 
				System.out.printf("프로그램을 종료합니다\n");
				sc.close();
				System.exit(0);
			}
		}
		
		
	}
}
