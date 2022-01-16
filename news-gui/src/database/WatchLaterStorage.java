package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Article;

public class WatchLaterStorage {
	private static Connection db;
	
	static {
		db = DbConnection.connect();
	}
	
	public static void addtoWatchLater(int id) {
		PreparedStatement ps = null; 
		try {
			String statement = "INSERT OR IGNORE INTO article_watch_laters(article_id) VALUES (?)";
			ps = db.prepareStatement(statement); 
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ArrayList<Article> getWatchLater(int page) {
		ArrayList<Article> watchlaterList= new ArrayList<Article>();
		int offset = (page-1)*10;
		String statement = "SELECT articles.* from articles,article_watch_laters WHERE id = article_id order by created_at DESC LIMIT 10 OFFSET "+offset;
		PreparedStatement ps = null; 
	    ResultSet rs = null;
		
		
		try {
		      ps = db.prepareStatement(statement); 
		      rs = ps.executeQuery();
		      while(rs.next()) {
		    	  int id = rs.getInt("id");
		    	  String title = rs.getString("title");
		    	  String description = rs.getString("description");
		    	  String link = rs.getString("link");
		          String published_parsed = rs.getString("published_parsed");
		          String image = rs.getString("image");
		          String category = rs.getString("category");
		          String source = rs.getString("source");
		    	  Article a = new Article(id,title,description,link,published_parsed,image,category,source);
		    	  watchlaterList.add(a);
		      }
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return watchlaterList;
	}
	
	public static int countWatchLater() {
		String statement = "SELECT COUNT(*) FROM article_watch_laters";
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
	public static int pageCount() {
		int n = countWatchLater();
		if(n%10==0 && n!=0) return n/10;
		return n/10+1;
	}
	
//	public void deleteWatchater() {
//		PreparedStatement ps = null; 
//		try {
//			String statement = "DELETE FROM article_histories";
//			ps = db.prepareStatement(statement); 
//			ps.execute();
//		} catch (SQLException e) {
//			System.out.println(e.toString());
//		}
//	}
	public static void deleteWatchLaterbyIds(int[] ids) {
		PreparedStatement ps = null;
		String[] s = new String[ids.length];
		for(int i=0;i<ids.length;i++) {
			s[i]=String.valueOf(ids[i]);
		}
		String str = String.join(",", s);
		try {
			String statement = "DELETE FROM article_watch_laters WHERE article_id in ("+str+")";
			ps = db.prepareStatement(statement); 
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
}
