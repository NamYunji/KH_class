package board.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class BoardExt extends Board implements Serializable {

	private static final long serialVersionUID = 1L;
	private int commentCnt;

	public BoardExt() {
		super();
	}

	public BoardExt(int no, String title, String writer, String content, Date regDate, int readCount, Attachment attach, int commentCnt) {
		super(no, title, writer, content, regDate, readCount, attach);
		this.commentCnt = commentCnt;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	@Override
	public String toString() {
		return "BoardExt [commentCnt=" + commentCnt + ", Board=" + super.toString() + "]";
	}
}
