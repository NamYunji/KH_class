package board.model.vo;

import java.util.Date;

public class BoardComment {

	private int no; // pk
	private int commentLevel; // 기본값 : 1 / 댓글 1, 대댓글 2
	private String writer; // member의 memberId참조
	private String content;
	private int boardNo; // board의 no참조
	private int commentRef; // 해당 테이블의 no참조, 대댓글인 경우 참조댓글 no, 댓글인 경우 null
	private Date regDate;

	public BoardComment() {
		super();
	}
	
	public BoardComment(int no, int commentLevel, String writer, String content, int boardNo, int commentRef,
			Date regDate) {
		super();
		this.no = no;
		this.commentLevel = commentLevel;
		this.writer = writer;
		this.content = content;
		this.boardNo = boardNo;
		this.commentRef = commentRef;
		this.regDate = regDate;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getCommentLevel() {
		return commentLevel;
	}
	public void setCommentLevel(int commnetLevel) {
		this.commentLevel = commnetLevel;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getCommentRef() {
		return commentRef;
	}
	public void setCommentRef(int commentRef) {
		this.commentRef = commentRef;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "BoardComment [no=" + no + ", commnetLevel=" + commentLevel + ", writer=" + writer + ", content="
				+ content + ", boardNo=" + boardNo + ", commentRef=" + commentRef + ", regDate=" + regDate + "]";
	}
}
