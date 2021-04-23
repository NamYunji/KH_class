package com.kh.test.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTemplate {
		
			static String driverClass = "oracle.jdbc.OracleDriver";
			static String url = "jdbc:oracle:thin:@192.168.10.3:1521:xe";
			static String user = "kh";
			static String password = "kh";
			
			static {
				try {
					Class.forName(driverClass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}


			public static Connection getConnection() {
				Connection conn = null;
				try {
					conn = DriverManager.getConnection(url, user, password);
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return conn;
			}
			
			public static void close(Connection conn) {
				try {
					if (conn != null)
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			public static void close(ResultSet rset) {
				try {
					if(rset != null)
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			public static void close(PreparedStatement pstmt) {
				try {
					if(pstmt != null)
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
					
			public static void commit(Connection conn) {
				try {
					if(conn != null)
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			public static void rollback(Connection conn) {
				try {
					if (conn != null)
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
