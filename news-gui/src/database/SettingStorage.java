package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SettingStorage {
	private Connection db;

	public static final String updateIntervalHourKey = "update_interval_hour";
	public static final String deleteIntervalDayKey = "delete_interval_day";

	public Connection getDb() {
		return db;
	}

	public void setDb(Connection db) {
		this.db = db;
	}

	public SettingStorage(Connection db) {
		super();
		this.db = db;
	}
	
	public void updateHour(int h) {
		PreparedStatement ps = null;
		try {
			  String sql = "UPDATE settings SET number_value = ? WHERE name = 'update_interval_hour'" ;
		      ps = db.prepareStatement(sql);
		      ps.setInt(1, h);
		      ps.execute();
		    } catch(SQLException e) {
		      System.out.println(e.toString());
		    }
	}
	
	public void updateDay(int d) {
		PreparedStatement ps = null;
		try {
			  String sql = "UPDATE settings SET number_value = ? WHERE name = 'delete_interval_day'" ;
		      ps = db.prepareStatement(sql);
		      ps.setInt(1, d);
		      ps.execute();
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
	}
	
	public int getSettingValueByKey(String key) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int value=0;;
		try {
			  String sql = "SELECT number_value from settings WHERE name = ? " ;
		      ps = db.prepareStatement(sql);
		      ps.setString(1,key);
		      rs=ps.executeQuery();
		      value= rs.getInt(1);
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return value;
	}
}
