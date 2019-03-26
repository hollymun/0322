package usedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageDAOImpl implements MessageDAO {
	// 생성자는 private으로
	private MessageDAOImpl() {
	}

	// 자신의 타입으로 static 변수 생성
	private static MessageDAO messageDAO;

	// 인스턴스를 만들어서 리턴하는 static 메소드를 생성
	public static MessageDAO getInstance() {
		if (messageDAO == null) {
			messageDAO = new MessageDAOImpl();
		}
		return messageDAO;
	}

	@Override
	public int insertMessage(MessageVO vo) {
		// 정수가 리턴 될 때는 음수로 기본값 세팅
		int result = -1;

		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", "user08",
				"user08");
				PreparedStatement pstmt = con.prepareStatement(
						"insert into Message(num, content, name, regdate) " + "values(messageseq.nextval,?,?,?)");) {

			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding
			pstmt.setString(1, vo.getContent());
			pstmt.setString(2, vo.getName());
			pstmt.setDate(3, vo.getRegdate());
			// @sql 실행
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<MessageVO> getAllMessageList() {
		List<MessageVO> list = new ArrayList<MessageVO>();
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", 
				"user08", "user08");
				// 항상 여러 개의 데이터를 가져올 때는 order by 필수!!
				// 데이터베이스는 행렬 구분이 없기 때문에 sort 되어 있지 않음
				PreparedStatement pstmt = con.prepareStatement("select * from message order by regdate desc");) {

			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding

			// @sql 실행
			// select 구문 수행하고 그 결과를 rs에 저장
			ResultSet rs = pstmt.executeQuery();

			// rs를 읽어서 한 줄씩 이동
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getDate("regdate"));
				// list에 추가
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<MessageVO> fiveList() {
		List<MessageVO> list = new ArrayList<MessageVO>();
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", 
				"user08", "user08");
				// 테이블에서 num으로 내림차순 정렬한 후 5개 가져오는 메소드
				PreparedStatement pstmt = con
						.prepareStatement("select * from (select rownum rnum, num, name, content, regdate "
								+ "				from (select * from message order by num desc)) "
								+ "where rnum <= 5");) {
			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding

			// @sql 실행
			// select 구문 수행하고 그 결과를 rs에 저장
			ResultSet rs = pstmt.executeQuery();

			// rs를 읽어서 한 줄씩 이동
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getDate("regdate"));
				// list에 추가
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<MessageVO> modList() {
		List<MessageVO> list = new ArrayList<MessageVO>();
		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", "user08",
				"user08");
				// 테이블에서 num으로 내림차순 정렬한 후 5개 가져오는 메소드
				PreparedStatement pstmt = con
						.prepareStatement("select * from (select rownum rnum, num, name, content, regdate "
								+ "				from (select * from message order by num desc)) "
								+ "where rnum > 5");) {
			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding

			// @sql 실행
			// select 구문 수행하고 그 결과를 rs에 저장
			ResultSet rs = pstmt.executeQuery();

			// rs를 읽어서 한 줄씩 이동
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getDate("regdate"));
				// list에 추가
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	
	@Override
	public List<MessageVO> pageList(Map<String, Object> map) {
		List<MessageVO> list = new ArrayList<MessageVO>();
		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", "user08",
				"user08");
				// 테이블에서 num으로 내림차순 정렬한 후 5개 가져오는 메소드
				PreparedStatement pstmt = con
						.prepareStatement("select * from (select rownum rnum, num, name, content, regdate "
								+ "				from (select * from message order by num desc)) "
								+ "where rnum >= ? and rnum <= ?");) {
			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding
			//Map에서 가져온 이름은 앞에서 만들어줘야 함 
			//이름이 틀리면 NullPointerException 발생 
			int page = (Integer)map.get("page");
			int cnt = (Integer)map.get("cnt");
			//페이지 번호와 페이지당 데이터 개수를 이용해서 
			//가져올 데이터의 시작 번호와 끝 번호를 생성 
			pstmt.setInt(1, cnt * (page-1) + 1);
			//pstmt.setInt(1, cnt * page - 4); //데이터가 5개니까 가능한 식,,, ^^,,,,,,,,,, 
			pstmt.setInt(2, cnt * page);
			// @sql 실행
			// select 구문 수행하고 그 결과를 rs에 저장
			ResultSet rs = pstmt.executeQuery();

			// rs를 읽어서 한 줄씩 이동
			//읽은 데이터가 없으면 list의 size가 0 
			//while문이 한 번도 수행되지 않음 
			while (rs.next()) {
				MessageVO vo = new MessageVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getDate("regdate"));
				// list에 추가
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public MessageVO detailMessage(int num) {
		//num에 해당되는 데이터가 없을 때는 null을 리턴하도록 세팅 
		MessageVO vo = null; 
		
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", 
				"user08", "user08");
				//num을 이용해서 데이터를 찾아오는 SQL 
				PreparedStatement pstmt = con
						.prepareStatement("select * from message where num = ?");) {
			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding
			pstmt.setInt(1, num);
			
			// @sql 실행
			// select 구문 수행하고 그 결과를 rs에 저장
			ResultSet rs = pstmt.executeQuery();

			// rs를 읽어서 한 줄씩 이동
			while (rs.next()) {
				vo = new MessageVO(); 
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getDate("regdate"));
			}
			rs.close();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return vo; 
	}

	
	
	@Override
	public int updateMessage(MessageVO vo) {
		// 정수가 리턴 될 때는 음수로 기본값 세팅
		int result = -1;

		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", "user08",
				"user08");
				PreparedStatement pstmt = con.prepareStatement(
						"update message set name = ?, content = ?, regdate = sysdate "
						+ "where num = ?");) {

			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNum());
		
			// @sql 실행
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	

	@Override
	public int deleteMessage(int num) {
		// 정수가 리턴 될 때는 음수로 기본값 세팅
		int result = -1;

		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1", "user08",
				"user08");
				PreparedStatement pstmt = con.prepareStatement(
						"delete from message where num = ?");) {

			// @sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding
			pstmt.setInt(1, num);
			
			// @sql 실행
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	
	

}