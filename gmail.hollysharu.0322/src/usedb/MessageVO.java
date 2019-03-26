package usedb;
import java.sql.Date;

//DTO 클래스 
public class MessageVO {
	//저장할 변수 생성 
	private int num;
	private String name; 
	private String content;
	private Date regdate;
	
	//접근자 메소드 생성 
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	//빠른 디버깅을 위한 toString 재정의 
	@Override
	public String toString() {
		return "MessageVO [num=" + num + ", name=" + name + ", content=" + content + ", regdate=" + regdate + "]";
	} 
	
	

}
