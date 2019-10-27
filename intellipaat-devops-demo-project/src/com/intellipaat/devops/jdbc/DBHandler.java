/**
 * 
 */
package com.intellipaat.devops.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.intellipaat.devops.domain.User;

/**
 * @author Hari Somagatta
 *
 */
public class DBHandler {

	public String saveUser(User user) throws Exception{

		System.out.println("saveUser:");
		Connection conn = null;
		PreparedStatement  stmt = null;
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		try{

			conn = 	DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hari", "root", "admin");
			if (conn != null) {
				System.out.println("Connected to the database!");
				System.out.println("USER:"+user.toString());
				
				String maxUidSql = "select max(UID) from USER";
				stmt = conn.prepareStatement(maxUidSql);
				ResultSet rs = stmt.executeQuery();
				int uid = 1;
				if(rs != null && rs.last()){
					int rowCount = rs.getRow();
					uid = rs.getInt(1);
					uid =uid+1;
				}
				
				String sql = "INSERT INTO USER (UID, FIRSTNAME,LASTNAME,ADDRESS1,ADDRESS2,CITY,STATE,ZIP,USERID,PASSWORD)" +
		                   " VALUES (?, ?, ?, ?,?, ?, ?, ?,?, ?)";
				
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, uid);
				stmt.setString(2,user.getFirstname());
				stmt.setString(3, user.getLastname());
				stmt.setString(4, user.getAddress1());
				stmt.setString(5, user.getAddress2());
				stmt.setString(6, user.getCity());
				stmt.setString(7, user.getState());
				stmt.setString(8, user.getZip());
				stmt.setString(9, user.getUserid());
				stmt.setString(10, user.getPassword());
				System.out.println("Prepared Statement after bind variables set:\n\t" + stmt.toString());
				stmt.executeUpdate();
			} else {
				System.out.println("Failed to make connection!");
				return "fail";
			}
			return "success";

		}finally{
			if(stmt !=null){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn !=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
