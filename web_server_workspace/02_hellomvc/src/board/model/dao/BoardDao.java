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
			Board board = new Board();
			board.setNo(rset.getInt("no"));
			board.setTitle(rset.getString("title"));
			board.setWriter(rset.getString("writer"));
			board.setContent(rset.getString("content"));
			board.setRegDate(rset.getDate("reg_date"));
			board.setReadCount(rset.getInt("read_count"));
			
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
}