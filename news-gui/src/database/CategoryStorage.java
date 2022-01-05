package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Category;

public class CategoryStorage {
	private Connection db;
	
	
	public CategoryStorage(Connection db) {
		super();
		this.db = db;
	}
	public ArrayList<Category> getCategory(){
		ArrayList<Category> categorylist = new ArrayList<Category>();

		PreparedStatement ps = null; 
	    ResultSet rs = null;
	    try {
			  String sql = "SELECT * FROM categories";
		      ps = db.prepareStatement(sql); 
		      rs = ps.executeQuery();
		      while(rs.next()) {
		    	  String name = rs.getString("name");
		          String source = rs.getString("source");
		          String link = rs.getString("link");
		    	  Category c = new Category(name, source, link);
		    	  categorylist.add(c);
		      }
		    } catch(SQLException e) {
		      System.out.println(e.toString());
		    }
	    return categorylist;
	}
	
	public void insert(String name, String source, String link) {
	    PreparedStatement ps = null;
	    try {
	    	String sql = "INSERT INTO categories(name, source, link) VALUES(?,?,?)";
	    	ps = db.prepareStatement(sql);
	    	ps.setString(1,name);
	    	ps.setString(2,source);
	    	ps.setString(3,link);
	    	ps.execute();
	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	    }
	}
	
	public void delete(int id) {
		PreparedStatement ps = null;
	    try {
	    	String sql = "DELETE FROM categories where id =?";
	    	ps = db.prepareStatement(sql);
	    	ps.setInt(1,id);
	    	ps.execute();
	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	    }
	}
}
