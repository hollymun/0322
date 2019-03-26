package usedb;

import java.util.List;
import java.util.Map;

public interface MessageDAO {
	//데이터를 삽입하는 메소드 
	public int insertMessage(MessageVO vo);
	//전체 데이터를 가져오는 메소드 
	//List<테이블을 나타내는 DTO를 적어줘야 함> 
	public List<MessageVO> getAllMessageList();
	
	//테이블에서 num으로 내림차순 정렬한 후 5개 가져오는 메소드 
	public List<MessageVO> fiveList(); 
	
	//테이블에서 num으로 내림차순 정렬한 후 6번째 데이터부터 나머지 전부를 가져오는 메소드 
	//더보기를 위한 메소드 
	public List<MessageVO> modList(); 
	
	//페이지 처리를 위한 메소드 
	//리턴타입은 이전과 동일하게 list 
	//매개변수로 "페이지 번호"와 조회할 "데이터 개수"를 매개변수로 받음 =>2개를 함께 저장할 것 생각하기 : Map
	//모바일에서는 데이터 개수를 정해놓고 가져오지만 
	//pc용 웹을 구현할 때는 데이터 개수를 선택하도록 함 
	
	//page - 1 cnt - 5 : Start - 1 end - 5
	//page - 2 cnt - 5 : Start - 6 end - 10 
	//page - 3 cnt - 5 : Start - 11 end - 15 
	
	//Start: cnt*(page-1) + 1
	//end: start + cnt - 1
	
	//특별한 경우가 아니면 매개변수는 1개로 만드는 것을 권장 
	//map은 출력할 페이지 번호와 데이터 개수를 갖는 map 
	public List<MessageVO> pageList(Map<String, Object> map); 
	
	//@데이터 수정/삭제
	//기본키를 가지고 데이터 1개를 찾아오는 메소드 - 상세보기 
	public MessageVO detailMessage(int num);
	
	//데이터를 수정하는 메소드 
	public int updateMessage(MessageVO vo); 
	
	//데이터를 삭제하는 메소드 
	public int deleteMessage(int num); 
	
}
