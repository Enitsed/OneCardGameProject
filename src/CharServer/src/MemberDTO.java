
import java.util.Date;

public class MemberDTO {
	private String memberId = null;
	private String memberPassword = null;
	private String memberName = null;
	private String memberGender = null;
	private int memberAge;
	private String memberEmail = null;
	private String memberLocation = null;
	private Date memberJoinDate;

	private int wins, loses;
	private float winRate;
	private int rank;
	private int rank_score;
	private String grade;

	public int getRank_score() {
		return rank_score;
	}

	public void setRank_score(int rank_score) {
		this.rank_score = rank_score;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoses() {
		return loses;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

	public float getWinRate() {
		return winRate;
	}

	public void setWinRate(float winRate) {
		this.winRate = winRate;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public MemberDTO() {

	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public int getMemberAge() {
		return memberAge;
	}

	public void setMemberAge(int memberAge) {
		this.memberAge = memberAge;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberLocation() {
		return memberLocation;
	}

	public void setMemberLocation(String memberLocation) {
		this.memberLocation = memberLocation;
	}

	public Date getMemberJoinDate() {
		return memberJoinDate;
	}

	public void setMemberJoinDate(Date memberJoinDate) {
		this.memberJoinDate = memberJoinDate;
	}

}
