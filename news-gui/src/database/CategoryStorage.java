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
	public Connection getDb() {
		return db;
	}
	public void setDb(Connection db) {
		this.db = db;
	}
	public ArrayList<Category> getCategory(int page){
		ArrayList<Category> categoryList = new ArrayList<Category>();
		PreparedStatement ps = null; 
	    ResultSet rs = null;
	    int offset = (page-1)*10;
	    String sql = "SELECT * FROM categories LIMIT 10 OFFSET "+offset;
	    try {
		      ps = db.prepareStatement(sql); 
		      rs = ps.executeQuery();
		      while(rs.next()) {
		    	  String name = rs.getString("name");
		          String source = rs.getString("source");
		          String link = rs.getString("link");
		    	  Category c = new Category(name, source, link);
		    	  categoryList.add(c);
		      }
		    } catch(SQLException e) {
		      System.out.println(e.toString());
		    }
	    return categoryList;
	}
	
	public String[] getCategoryNames() {
		String sql = "Select distinct name from categories";
		ArrayList<String> nameslist = new ArrayList<String>();
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
		      ps = db.prepareStatement(sql); 
		      rs = ps.executeQuery();
		      while(rs.next()) {
		    	  String name = rs.getString("name");
		    	  nameslist.add(name);
		      }
		    } catch(SQLException e) {
		      System.out.println(e.toString());
		    }
	    String[] categoryNames=nameslist.toArray(new String[nameslist.size()]);
	    return categoryNames;
	}
	
	public String[] getCategorySources() {
		String sql = "Select distinct source from categories";
		ArrayList<String> sourceslist = new ArrayList<String>();
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
		      ps = db.prepareStatement(sql); 
		      rs = ps.executeQuery();
		      while(rs.next()) {
		    	  String source = rs.getString("source");
		    	  sourceslist.add(source);
		      }
		    } catch(SQLException e) {
		      System.out.println(e.toString());
		    }
	    String[] categorySources=sourceslist.toArray(new String[sourceslist.size()]);
	    return categorySources;
	}
	
	
	public int countCategory() {
		String statement = "SELECT COUNT(*) FROM categories";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count =0;
		try {
		      ps = db.prepareStatement(statement); 
		      rs = ps.executeQuery();
		      count = rs.getInt(1);
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return count;
	}
	
	public int pageCount() {
		int n = countCategory();
		if(n%10==0 && n!=0) return n/10;
		return n/10+1;
	}
	
	public void addtoCategory(String name, String source, String link) {
	    PreparedStatement ps = null;
	    try {
	    	String sql = "INSERT OR IGNORE INTO categories(name, source, link) VALUES(?,?,?)";
	    	ps = db.prepareStatement(sql);
	    	ps.setString(1,name);
	    	ps.setString(2,source);
	    	ps.setString(3,link);
	    	ps.execute();
	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	    }
	}
	
	public void deleteCategorybyId(int id) {
		PreparedStatement ps = null;
	    try {
	    	String sql = "DELETE FROM categories where id = ?";
	    	ps = db.prepareStatement(sql);
	    	ps.setInt(1,id);
	    	ps.execute();
	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	    }
	}
}
