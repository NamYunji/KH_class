package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;

public class BoardService {
	private BoardDao boardDao = new BoardDao();

	public List<Board> selectList(int start, int end) {
		Connection conn = getConnection();
		List<Board> list = boardDao.selectList(conn, start, end);
		close(conn);
		return list;
	}

	public int selectBoardCount() {
		Connection conn = getConnection();
		int totalContents = boardDao.selectBoardCount(conn);
		close(conn);
		return totalContents;
	}

	/**
	 * 첨부파일이 있는 경우, attach객체를 attachment테이블에 등록한다.
	 * 이 작업을 insertBoard에 함께 해야함
	 * why? board등록, attachment등록은 하나의 트랜잭션으로 처리되어야 한다.
	 * 성공 시, 둘다 커밋, 실패하면 둘다 롤백
	 * attachment를 추가했는데, board등록이 실패한다면 안되니까
	 * -> 하나의 Connection으로 묶어서 처리되어야 한다.
	 */
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.insertBoard(conn, board);
			// 아직 여기에는 no가 없음. seq_board_no.nextVal해야만 발급되는 번호가 db에 들어감
			// board가 insert될 때 생성된 board고유번호가 attachment의 boardno에 세팅되어야만, attachment테이블에 추가 가능
			// -> 생성된 board_no 가져오기
			int boardNo = boardDao.selectLastBoardNo(conn);
			//redirect location설정
			board.setNo(boardNo);
			System.out.println("boardNo@service = " + boardNo);
			
			if(board.getAttach() != null) {
				// *** attach객체에 참조할 boardNo 세팅 ***
				board.getAttach().setBoardNo(boardNo);
				// attachment객체를 같이 보내기
				result = boardDao.insertAttachment(conn, board.getAttach());
				// boardNo는 fk를 걸어둠 - board의 no를 참조해야 함
			}
			// 예외가 발생하지 않으면 무조건 commit
			commit(conn);
		} catch(Exception e) {
			// e.printStackTrace();
			// 예외가 발생하면 selectLastBoardNo(), insertAttachment()가 같이 롤백됨
			// insertBoard()마저 취소시킴
			rollback(conn);
			// result = 0;
			throw e;
		} finally {
			close(conn);			
		}
//		if(result > 0) commit(conn);
//		else rollback(conn);
		return result;
	}

	// board no로 board 조회 (첨부파일도 함께 조회)
	// 트랜잭션 처리할 부분이 아니고, 런타임 예외로 전환해서 던졌기 때문에 throws 필요 없음
	// 메소드 호출하다가 예외가 던져짐 -> selectOne 호출한 쪽으로 다시 예외가 던져짐
	public Board selectOne(int no) {
		Connection conn = getConnection();
		Board board = boardDao.selectOne(conn, no);
		Attachment attach = boardDao.selectOneAttachment(conn, no);
		// 조회된 board에 attach 세팅
		board.setAttach(attach);
		close(conn);
		return board;
	}

	// board no로 attachment 행 조회
	public Attachment selectOneAttachment(int no) {
		Connection conn = getConnection();
		Board board = boardDao.selectOne(conn, no);
		Attachment attach = boardDao.selectOneAttachment(conn, no);
		// 조회된 board에 attach 세팅
		board.setAttach(attach);
		close(conn);
		return attach;
	}

	// 게시물 삭제, baord를 삭제하면 attachment도 함께 삭제됨 (on delete cascade)
	public int deleteBoard(int no) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.deleteBoard(conn, no);
			// 존재하지 않는 no가 아닌 다른 번호가 들어온다면? 
			// 존재하지 않는 글을 삭제한다면, result는 0
			// delete from board where no = 300 -> 0개 행이 삭제되었습니다. -> 0 리턴
			// result == 0 -> 예외 던지기
			if(result == 0) {
				throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. : " + no);
			}
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e; // controller가 예외처리를 결정할 수 있도록 넘김
		} finally {
		// if(result > 0) commit(conn);
		// else rollback(conn);
		// -> try, catch로 바꾸기 
		close(conn);
		}
		return result;
	}

	public int updateBoard(Board board) {
		Connection conn = getConnection();
		int result = 0;
		try {
			// 1. board update
			// 원래 있던 것들을 수정하는거니까 update
			result = boardDao.updateBoard(conn, board);
			// 2. attachment insert
			// 첨부파일이 애초에 없었으니 insert
			if(board.getAttach() != null)
				result = boardDao.insertAttachment(conn, board.getAttach());
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		}
		return result;
	}

}
