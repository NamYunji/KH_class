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
			// board가 insert될 때 생성된 board고유번호가 attachment의 boardno에 세팅되어야만 attachment테이블에 추가 가능
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
			e.printStackTrace();
			// 예외가 발생하면 selectLastBoardNo(), insertAttachment()가 같이 롤백됨
			// insertBoard()마저 취소시킴
			rollback(conn);
			result = 0;
		} finally {
			close(conn);			
		}
//		if(result > 0) commit(conn);
//		else rollback(conn);
		return result;
	}

	public Board selectOne(int no) {
		Connection conn = getConnection();
		Board board = boardDao.selectOne(conn, no);
		Attachment attach = boardDao.selectOneAttachment(conn, no);
		// 조회된 board에 attach 세팅
		board.setAttach(attach);
		close(conn);
		return board;
	}

}
