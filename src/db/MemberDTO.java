package db;

public class MemberDTO {
	private int memberCode;
	private String memberId;
	private String memberName;
	private int memberPassword;

	public MemberDTO() {

	}

	public int getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(int memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(int memberPassword) {
		this.memberPassword = memberPassword;
	}

}