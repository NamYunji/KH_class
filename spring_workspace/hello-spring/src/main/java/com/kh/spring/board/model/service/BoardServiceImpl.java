package com.kh.spring.board.model.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
// @Transactional(rollbackFor = Exception.class)
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	@Override
	public List<Board> selectBoardList() {
		return boardDao.selectBoardList();
	}

	@Override
	public List<Board> selectBoardList(Map<String, Object> param) {
		return boardDao.selectBoardList(param);
	}

	@Override
	public int selectBoardTotalContents() {
		return boardDao.selectBoardTotalContents();
	}

	// @Transactional 트랜잭션
	// rollbackFor 속성 : 트랜잭션 rollback처리하기 위한 예외 등록
	// Exception -> 모든 예외. 생략하면 기본적으로 RuntimeException만 rollback한다.
	// @Transactional(rollbackFor = Exception.class)
	@Override
	public int insertBoard(BoardExt board) {
		int result = 0; 
		// 1. board 등록
		result = boardDao.insertBoard(board); 
		log.debug("board = {}", board);
		// selectKey 태그로 인해 이 board객체의 no필드에 값이 담겨있게 됨
		// 던져질 때는 no필드가 0이지만, 메소드 끝나고나면 no필드가 채워있게 됨!!
		// 2. attachment 등록
		// attachment가 있을 경우에만 실행
		if(board.getAttachList().size() > 0) {
			for(Attachment attach : board.getAttachList()) {
				attach.setBoardNo(board.getNo()); // boardNo fk 세팅
				// insertBoard안에서 insertAttachment를 호출
				result = insertAttachment(attach);
			}
		}
		return result;
	}

	// @Transactional(rollbackFor = Exception.class)
	@Override
	public int insertAttachment(Attachment attach) {
		return boardDao.insertAttachment(attach);
	}
}
