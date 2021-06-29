package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> getVertici(String genere, Map<Integer,Actor>idMap){
		String sql = "SELECT DISTINCT a.id, a.first_name, a.last_name, a.gender "
				+ "FROM movies_genres mg, roles r, actors a "
				+ "WHERE mg.genre=? AND  mg.movie_id= r.movie_id AND a.id= r.actor_id ";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				idMap.put(res.getInt("id"), actor);
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getGeneri() {
		String sql = "SELECT DISTINCT genre "+
				"FROM movies_genres "+
				"ORDER BY genre ASC ";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("genre"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(String cate, Map<Integer,Actor>idMap){
		String sql = "SELECT a1.id, a2.id, COUNT(*) AS peso "
				+ "FROM actors a1, actors a2, roles r1, roles r2, movies_genres mg1,movies_genres mg2 "
				+ "WHERE mg1.genre=? AND  r1.movie_id=mg1.movie_id AND mg2.genre=? AND  r2.movie_id=mg2.movie_id AND "
				+ "a1.id=r1.actor_id AND a2.id=r2.actor_id AND a1.id>a2.id AND r1.movie_id= r2.movie_id "
				+ "GROUP BY a1.id, a2.id";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,cate);
			st.setString(2,cate);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Actor a1= idMap.get(res.getInt("a1.id"));
				Actor a2= idMap.get(res.getInt("a2.id"));
				if(a1!=null && a2!= null && !a1.equals(a2))
				result.add(new Adiacenza(a1,a2,res.getInt("peso")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
