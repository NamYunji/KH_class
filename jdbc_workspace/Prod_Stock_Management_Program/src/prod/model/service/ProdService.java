package prod.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import prod.model.dao.ProdDao;
import prod.model.vo.IO;
import prod.model.vo.Stock;

public class ProdService {

	private ProdDao prodDao = new ProdDao();
	

	public List<Stock> sltAll() {
		Connection conn = getConnection();
		List<Stock> list = prodDao.sltAll(conn);
		close(conn);
		return list;
	}

	public Stock sltOneId(String productId) {
		Connection conn = getConnection();
		Stock s = prodDao.sltOneId(conn, productId);
		close(conn);
		return s;
		
	}

	public List<Stock> sltByName(String productName) {
		Connection conn = getConnection();
		List<Stock> list = prodDao.sltByName(conn, productName);
		close(conn);
		return list;
	}

	public int insertProd(Stock stock) {
		Connection conn = getConnection();
		int result = prodDao.insertMember(conn, stock);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;

	}

	public int updateProdInfo(Stock s) {
		Connection conn = getConnection();
		int result = prodDao.updateProdInfo(conn, s);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int deleteProd(String productId) {
		Connection conn = getConnection();
		int result = prodDao.deleteProd(conn, productId);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public List<IO> sltIOInfo() {
		Connection conn = getConnection();
		List<IO> ioList = prodDao.sltIOInfo(conn);
		close(conn);
		return ioList;
	}

}
