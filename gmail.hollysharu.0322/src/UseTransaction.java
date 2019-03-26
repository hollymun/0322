import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UseTransaction {

	public static void main(String[] args) {
		try(Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1","user08","user08"); 
				PreparedStatement pstmt = con.prepareStatement(
						"insert into sample(num, message) values(?,?)");)
		{
		//commit이나 rollback을 직접 수행할 수 있도록 설정 
			con.setAutoCommit(false);
			pstmt.setInt(1, 11);
			pstmt.setString(2, "후애ㅐㅐㅇ");
			
			pstmt.executeUpdate();
			System.out.printf("삽입 성공");
		//현재까지의 작업 내용을 바로 반영 
			con.commit();
			Thread.sleep(30000);
		//작업 취소 
		//	con.rollback();
		
		}
		catch(Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}

	}

}
