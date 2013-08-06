package com.tuna.idchk.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tuna.idchk.db.ex.SmoothExceptionsIdChkDb;

public class JdbcTemplate {
	
	public JdbcTemplate(){		
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		} catch (Exception ex) {
			SmoothExceptionsIdChkDb.driverLoadFailed("Failed to load HSQLDB JDBC driver.", ex);
		}
	}

	public Connection createConnection(){
		try {
			Connection c = DriverManager.getConnection("jdbc:hsqldb:file:/etc/idchck", "", "");
			return c;
		} catch (SQLException ex) {
			SmoothExceptionsIdChkDb.connectionFailed(ex);
			return null;
		}
	}
}
