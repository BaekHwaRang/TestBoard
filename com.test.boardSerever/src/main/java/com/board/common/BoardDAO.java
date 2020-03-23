package com.board.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.Connection;

public class BoardDAO {
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	public void insertBoard(BoardVO vo, String query) {
		try {
			System.out.println(query);
			conn = JDBCUtility.getConnection();
			stmt = conn.prepareStatement(query);
			/*
			 * stmt.setString(1, vo.getBOARD_TYPE()); stmt.setString(2, vo.getTITLE());
			 * stmt.setString(3, vo.getCONTENT()); //stmt.setString(4, vo.getINSERT_DATE());
			 * stmt.setString(5, vo.getWRITER());
			 */

			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtility.close(stmt, conn);
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public ArrayList getBoardList(String query, String type) {

		ArrayList<BoardVO> arraylist = new ArrayList<BoardVO>();
		ArrayList<cmtVO> cmtlist = new ArrayList<cmtVO>();

		try {
			conn = JDBCUtility.getConnection();
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				BoardVO list = new BoardVO();
				list.setBOARD_TYPE(rs.getString("BOARD_TYPE"));
				list.setTITLE(rs.getString("TITLE"));
				list.setCONTENT(rs.getString("CONTENT"));
				list.setINSERT_DATE(rs.getString("INSERT_DATE"));
				list.setWRITER(rs.getString("WRITER"));
				list.setIdx(rs.getString("IDX"));
				list.setBoolfile(rs.getString("FILE_IDX"));
				list.setJuno(rs.getString("JUNO"));
				list.setHit_no(rs.getString("HIT_NO"));
				list.setComment_sum(rs.getInt("COMMENT_SUM"));
				arraylist.add(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// JDBCUtility.close(rs, stmt, conn);
		}
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arraylist;
	}

	public JSONArray detail(String idx) throws SQLException {
		JSONArray jsonArray = new JSONArray();
		String MAX_COUNT = "SELECT MAX(DEPTH) AS MAX FROM COMMENTS WHERE POST_IDX = "+ idx;
		conn = JDBCUtility.getConnection();
		stmt = conn.prepareStatement(MAX_COUNT);
		
		rs = stmt.executeQuery();
		int length = rs.getInt("MAX");
		int leng = length;
		List<cmtVO> list = new ArrayList<cmtVO>();
		cmtVO vo = new cmtVO();
		while(length == 0) {
			if(length != leng) {
				for(cmtVO v : list) {
					if(rs.getInt("IDX") == vo.getSeq()) {
						
					}
					
				}
			}
			
			String Query = "SELECT IDX, PARENT, DEPTH, CONTENT, HIDDEN, DEL, SEQ, POST_IDX FROM COMMENTS WHERE "
					+"POST_IDX = "+idx+" AND DEPTH = "+(length) +" order by IDX , SEQ";
			JSONObject jsonObject = new JSONObject();
			JSONArray Object_Array = new JSONArray();
			jsonObject.put("idx", rs.getInt("IDX"));
			jsonObject.put("parent", rs.getInt("PARENT"));
			jsonObject.put("depth", rs.getInt("DEPTH"));
			jsonObject.put("content", rs.getString("CONTENT"));
			jsonObject.put("hidden", rs.getString("HIDDEN"));
			jsonObject.put("del", rs.getString("DEL"));
			jsonObject.put("seq", rs.getInt("SEQ"));
			jsonObject.put("post_idx", rs.getInt("POST_IDX"));
			cmtVO vo = new cmtVO();
			vo.setIdx(rs.getInt("IDX"));
			vo.setParent(rs.getInt("PARENT"));
			vo.setDepth(rs.getInt("DEPTH"));
			vo.setContent(rs.getString("CONTENT"));
			vo.setHidden(rs.getString("HIDDEN"));
			vo.setDel(rs.getString("DEL"));
			vo.setSeq(rs.getInt("SEQ"));
			list.add(vo);
			
			length--;
		}
		while (rs.next()) {
			
			System.out.println("while문 실행");
			JSONObject jsonObject = new JSONObject();
			JSONArray Object_Array = new JSONArray();
			jsonObject.put("idx", rs.getInt("IDX"));
			jsonObject.put("parent", rs.getInt("PARENT"));
			jsonObject.put("depth", rs.getInt("DEPTH"));
			jsonObject.put("content", rs.getString("CONTENT"));
			jsonObject.put("hidden", rs.getString("HIDDEN"));
			jsonObject.put("del", rs.getString("DEL"));
			jsonObject.put("seq", rs.getInt("SEQ"));
			jsonObject.put("post_idx", rs.getInt("POST_IDX"));

			while(true) {
				String Query = "SELECT IDX, PARENT, DEPTH, CONTENT, HIDDEN, DEL, SEQ, POST_IDX FROM COMMENTS WHERE "
						+"POST_IDX = "+idx+" AND DEPTH = "+depth+1+" AND SEQ = ";
			
				depth++;
			}
			
		}System.out.println(jsonArray);return jsonArray;

	}

	public int RowCount(String cSelect) {
		conn = JDBCUtility.getConnection();
		try {
			stmt = conn.prepareStatement(cSelect);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int count = Integer.parseInt(rs.getString("COUNT"));
				return count;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtility.close(rs, stmt, conn);
		}
		return 0;
	}

	public void update(String udtQuery) {
		conn = JDBCUtility.getConnection();
		try {
			stmt = conn.prepareStatement(udtQuery);
			stmt.executeUpdate();
			System.out.println("조회수 up");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtility.close(stmt, conn);
		}
	}
}
