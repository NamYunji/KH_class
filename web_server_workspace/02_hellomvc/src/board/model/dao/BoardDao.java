package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.model.exception.BoardException;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.BoardComment;
import board.model.vo.BoardExt;
import member.model.dao.MemberDao;
import member.model.vo.Member;

public class BoardDao {

	private Properties prop = new Properties();
	
	public BoardDao() {
		// board-query.properties의 내용을 읽어와서 prop에 저장
		// resources/sql/board-query.properties가 아니라
		// WEB-INF/classes/sql/board-query.properties에 접근해야 함
		// why? resources하위의 작업파일은 그대로 build path하위로 배포되기 때문
		// 배포되는 파일 = 실제 실행되는 파일
		//member-query.properties의 모든 쿼리를 prop으로 옮겨다 놓는 코드
//		String fileName = BoardDao.class
//				.getResource("/sql/board/board-query.properties").getPath();
		String fileName = "/sql/board/board-query.properties";
		String absPath = BoardDao.class.getResource(fileName).getPath();
		try {
			prop.load(new FileReader(absPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Board> selectList(Connection conn, int start, int end) {
		List<Board> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String  sql = prop.getProperty("selectList");
		
		try {
		// 3. PreparedStatement객체 생성(미완성쿼리)
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, start);
		pstmt.setInt(2, end);
		// 4. 쿼리문 실행
		rset = pstmt.executeQuery();
		// 4.1 ResultSet -> Java객체 옮겨담기
		list = new ArrayList<>();			
		// rset.next() 결과집합이 여러행일 때 다음행 있니? -> 행을 가리키는 포인터를 다음행으로 옮겨주는 역할
		while(rset.next()) {
			// Board board = new Board();
			BoardExt board = new BoardExt();
			board.setNo(rset.getInt("no"));
			board.setTitle(rset.getString("title"));
			board.setWriter(rset.getString("writer"));
			board.setContent(rset.getString("content"));
			board.setRegDate(rset.getDate("reg_date"));
			board.setReadCount(rset.getInt("read_count"));
			board.setCommentCnt(rset.getInt("comment_cnt"));
			
			System.out.println(rset.getInt("attach_no"));
			// rset.getInt("attach_no") != 0 -> 첨부파일이 있는 경우
			if(rset.getInt("attach_no") != 0) {
				Attachment attach = new Attachment();
				attach.setNo(rset.getInt("attach_no"));
				// 따로 보낸 게 없어서 위의 no값을 다시 입력
				attach.setBoardNo(rset.getInt("no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				// db에는 "Y"|"N", vo에는 boolean형으로 되어있음 -> 형변환
				attach.setStatus("Y".equals(rset.getString("status"))? true : false);
				board.setAttach(attach);
			}
			list.add(board);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		// 5. 자원반납(생성역순 rset - pstmt) 
		close(rset);
		close(pstmt);
	}
	return list;
	}

	public int selectBoardCount(Connection conn) {
		int totalContents = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String  sql = prop.getProperty("selectBoardCount");
		try {
			// 3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 4. 쿼리문 실행
			rset = pstmt.executeQuery();
			// 4.1 ResultSet -> Java객체 옮겨담기
			if(rset.next()) {
				totalContents = rset.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5. 자원반납(생성역순 rset - pstmt) 
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}
	//		Board board = new Board(0, title, writer, content, regDate, readCount, attach);
	public int insertBoard(Connection conn, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int selectLastBoardNo(Connection conn) {
		int boardNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectLastBoardNo");
		try {
			// 3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 4. 쿼리문 실행
			rset = pstmt.executeQuery();
			// 4.1 ResultSet -> Java객체 옮겨담기
			if(rset.next()) {
				boardNo = rset.getInt("board_no");
			}
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 번호 조회 오류", e);
		} finally {
			// 5. 자원반납(생성역순 rset - pstmt) 
			close(rset);
			close(pstmt);
		}
		return boardNo;
	}

	public int insertAttachment(Connection conn, Attachment attach) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getOriginalFileName());
			pstmt.setString(3, attach.getRenamedFileName());
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// 예외 던지기
			throw new BoardException("게시물 첨부파일 등록 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	public Board selectOne(Connection conn, int no) {
		Board board = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectOne");
		
		try {
			// 3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			// 3.1. ?에 값대입 -> 쿼리문 완성작업
			pstmt.setInt(1, no);
			// 4. 쿼리문 실행
			rset = pstmt.executeQuery();
			
			// 4.1 ResultSet -> Java객체 옮겨담기
			if(rset.next()){
				board = new Board();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BoardException("게시글 조회 오류", e);
		} finally {
			// 5. 자원반납(생성역순 rset - pstmt) 
			close(rset);
			close(pstmt);
		}
		return board;		
	}

	// board no로 attachment 조회
	public Attachment selectOneAttachment(Connection conn, int no) {
		Attachment attach = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectOneAttachment");
		
		try {
			// 3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			// 3.1. ?에 값대입 -> 쿼리문 완성작업
			pstmt.setInt(1, no);
			// 4. 쿼리문 실행
			rset = pstmt.executeQuery();
			
			// 4.1 ResultSet -> Java객체 옮겨담기
			if(rset.next()){
				attach = new Attachment();
				attach.setNo(rset.getInt("no"));
				attach.setBoardNo(rset.getInt("board_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				attach.setStatus("Y".equals(rset.getString("status")) ?  true : false);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			// catch절에서 예외를 출력하고 끝내버리면 컨트롤러에서는 예외가 발생한지 모르니까 예외를 던져줌
			// Dao -> Service -> Controller로 예외 전달
			// checked 예외는 throws exception을 해야하니까,
			// 간결하게 BoardException을 만들어 애초에 발생한 예외를 함께 던진 것
			throw new BoardException("첨부파일 조회 오류", e);
		} finally {
			// 5. 자원반납(생성역순 rset - pstmt) 
			close(rset);
			close(pstmt);
		}
		return attach;	
	}

	public int deleteBoard(Connection conn, int no) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteBoard");
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("게시물 삭제 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	// 게시글 수정
	public int updateBoard(Connection conn, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNo());
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("게시물 수정 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	// 첨부파일 삭제 - delete의 내용이지만 update로 진행한다
	public int deleteAttachment(Connection conn, String attachNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		// deleteAttachment = update attachment set status = 'N' where no = ?
		String sql = prop.getProperty("deleteAttachment");
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, attachNo); // where절에 no = '2' -> 컬럼타입인 숫자에 맞게 자동형변환됨
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("첨부파일 삭제 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertBoardComment(Connection conn, BoardComment bc) {
		int result = 0;
		PreparedStatement pstmt = null;
		// insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
		// values(seq_board_comment_no.nextval, ?, ?, ?, ?, ?)
		String sql = prop.getProperty("insertBoardComment");
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getCommentLevel()); 
			pstmt.setString(2, bc.getWriter());
			pstmt.setString(3, bc.getContent());
			pstmt.setInt(4, bc.getBoardNo()); 
			// pstmt.setInt(5, bc.getCommentRef());
			// 1) 댓글의 경우, 0으로 고정해둠
			// 댓글은 fk가 걸려있음 -> comment_pk가 0번이 있어야 하는데, 0번이 없으니 작동 안함 -> null
			// pstmt.setInt(5, bc.getCommentRef() == 0 ? null : bc.getCommentRef());
			// 2) null을 넣을 수도 없음
			// why? setInt인데 null값을 넣을 수 없음
			pstmt.setObject(5, bc.getCommentRef() == 0 ? null : bc.getCommentRef());
			// 3) null값을 허용하는 setObject 사용해서 object타입으로 변환하기
			// setObject는 모든 타입 대입 가능
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("댓글등록 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<BoardComment> selectBoardCommentList(Connection conn, int no) {
		List<BoardComment> commentList = null; 
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// selectBoardCommentList = select bc.* from board_comment bc where board_no = ?
		// start with comment_level = 1 connect by prior no = comment_ref order siblings by reg_date asc
		String  sql = prop.getProperty("selectBoardCommentList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no); // 게시글번호로 조회
			rset = pstmt.executeQuery();
			commentList = new ArrayList<>();			
			while(rset.next()) {
				// 한행 한행 boardComment객체로 바꿔서 commentList에 담기
				BoardComment bc = new BoardComment();
				bc.setNo(rset.getInt("no"));
				bc.setCommentLevel(rset.getInt("comment_level"));
				bc.setWriter(rset.getString("writer"));
				bc.setContent(rset.getString("content"));
				bc.setBoardNo(rset.getInt("board_no"));
				bc.setCommentRef(rset.getInt("comment_ref"));
				bc.setRegDate(rset.getDate("reg_date"));
				commentList.add(bc); // list에 boardComment객체 추가
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		close(rset);
		close(pstmt);
	}
	return commentList;
	}

	public int deleteBoardComment(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteBoardComment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("댓글 삭제 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}
} 