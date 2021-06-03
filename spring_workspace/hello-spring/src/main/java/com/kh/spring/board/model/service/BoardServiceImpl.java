package com.kh.spring.board.model.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;

@Service
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

	@Override
	public int insertBoard(BoardExt board) {
		int result = 0; 
		// 1. board 등록
		result = boardDao.insertBoard(board); 
		// 2. attachment 등록
		// attachment가 있을 경우에만 실행
		if(board.getAttachList().size() > 0) {
			for(Attachment attach : board.getAttachList()) {
				attach.setBoardNo(result);
				// insertBoard안에서 insertAttachment를 호출
				result = insertAttachment(attach);
			}
		}
		return result;
	}

	@Override
	public int insertAttachment(Attachment attach) {
		return boardDao.insertAttachment(attach);
	}
}
