import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpSelect {

	public static void main(String[] args) {
		//@emp테이블의 데이터를 변수에 저장해서 읽기 
		//@숫자 컬럼인 empno, 문자열인 ename, 날짜 형식인 hiredate만 읽기 
		
		//@emp 테이블에서 empno, ename, hiredate 컬럼의 값만 가져오기 

		
		//@파일을 읽고 쓰기를 하거나 네트워크 작업 또는 데이터베이스 연동할 때는 
		//반드시 예외처리 해야함 
		try(Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1","user08","user08"); 
				PreparedStatement pstmt = con.prepareStatement(
						"select EMPNO, ENAME, HIREDATE from EMP");){
			
			//@sql 문장에 ?가 있었다면 실제 데이터로 치환: Data Binding 
			
			//@sql 실행
			ResultSet rs = pstmt.executeQuery();
			//@결과 사용 
			while(rs.next()) {
				//전부 문자열로 읽는 것이 가능함 
				String empno = rs.getString("empno");
				String ename = rs.getString("ename");
				//String hiredate = rs.getString("hiredate");
				//@날짜로 받기 
				//java.util-날짜와 시간 모두 저장 
				//java.sql-날짜만 저장 
				java.sql.Date hiredate = rs.getDate("hiredate");
				//@데이터 출력 
				System.out.printf("사번:%s 이름:%s 입사일:%s\n", empno, ename, hiredate);
			}
			
		}
		catch(Exception e) {
			System.out.printf("읽기 예외:%s\n", e.getMessage());
			e.printStackTrace();
		}

		
	}

}
