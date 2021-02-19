package prod.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import prod.model.vo.IO;
import prod.model.vo.Stock;

import static common.JDBCTemplate.close;

public class ProdDao {

	private Properties prop = new Properties();
	
	public List<Stock> sltAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from product_stock";
		List<Stock> list = null;
		List<IO> ioList = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list = new ArrayList<>();
			
			while(rset.next()) {
				String productId = rset.getString("product_id");
				String productName = rset.getString("product_name");
				int price = rset.getInt("price");
				String description = rset.getString("description");
				int stock = rset.getInt("stock");				
				Stock stc = new Stock(productId, productName, price, description, stock);
				list.add(stc);
			}
			
		} 		  
		  catch (SQLException e) {
			e.printStackTrace();
		} 
		
		  finally {
				close(rset);
				close(pstmt);  
		}
		
		return list;
	}

	public Stock sltOneId(Connection conn, String productId) {
		Stock s = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from product_stock where product_id = ?";


		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				s = new Stock();
				s.setProductId(rset.getString("product_id"));
				s.setProductName(rset.getString("product_name"));
				s.setPrice(rset.getInt("price"));
				s.setDescription(rset.getString("description"));
				s.setStock(rset.getInt("stock"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return s;
	}

	public List<Stock> sltByName(Connection conn, String productName) {
		
		ArrayList<Stock> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from product_stock where product_name like ?";
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+ productName+ "%");
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Stock>();
			
			while(rset.next()){
				Stock s = new Stock();
				s.setProductId(rset.getString("product_id"));
				s.setProductName(rset.getString("product_name"));
				s.setPrice(rset.getInt("price"));
				s.setDescription(rset.getString("description"));
				s.setStock(rset.getInt("stock"));

				list.add(s);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertMember(Connection conn, Stock s) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into product_stock values(?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,s.getProductId());
			pstmt.setString(2,s.getProductName());
			pstmt.setInt(3, s.getPrice());
			pstmt.setString(4,s.getDescription());			
			pstmt.setInt(5, s.getStock());

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateProdInfo(Connection conn, Stock s) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update product_stock set "
					 + " product_name=?"
					 + ",price=?"
					 + ",description=?"
					 + " where product_id=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, s.getProductName());
			pstmt.setInt(2, s.getPrice());
			pstmt.setString(3, s.getDescription());
			pstmt.setString(4, s.getProductId());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteProd(Connection conn, String productId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from product_stock where product_id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, productId);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<IO> sltIOInfo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from product_io";
		ArrayList<IO> ioList = null;	
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			ioList = new ArrayList<>();

			
			while (rset.next()) {
					IO io = new IO();
					io.setIoNo(rset.getInt("io_no"));
					io.setProductId(rset.getString("product_id"));
					io.setIoDate(rset.getDate("iodate"));
					io.setAmount(rset.getInt("amount"));
					io.setStatus(rset.getString("status"));
					
					ioList.add(io);
				}
		} 
		  catch (SQLException e) {
			e.printStackTrace();
		} 
		
		  finally {

			close(rset);
			close(pstmt);
		}		
		
		return ioList;
	}
	
	
	
	

}


	
	

