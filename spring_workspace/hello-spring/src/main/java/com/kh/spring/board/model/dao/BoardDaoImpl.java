package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private SqlSessionTemplate session;

	@Override
	public List<Board> selectBoardList() {
		return session.selectList("board.selectBoardList");
	}

	@Override
	public List<Board> selectBoardList(Map<String, Object> param) {
		// param에 담겨있는 offset, limit가져오기
		int offset = (int)param.get("offset");
		int limit = (int)param.get("limit");
		
		// RowBounds객체 생성, offset, limit을 인자로 전달
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// String statement, Object parameeter, RowBounds rowBounds
		return session.selectList("board.selectBoardList", null, rowBounds);
	}

	@Override
	public int selectBoardTotalContents() {
		return session.selectOne("board.selectBoardTotalContents");
	}

	@Override
	public int insertBoard(BoardExt board) {
		return session.insert("board.insertBoard", board);
	}

	@Override
	public int insertAttachment(Attachment attach) {
		return session.insert("board.insertAttachment", attach);
	}
	
}
