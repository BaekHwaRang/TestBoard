package com.test.boardServer;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.board.common.BoardDAO;
import com.board.common.BoardVO;
import com.board.common.cmtVO;


@Controller
@Repository
public class BoardController {
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/bbs/insert", produces = "application/x-www-form-urlencoded; charset=UTF-8")
	public void insertBoard(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String id = request.getParameter("id");
		String mode = request.getParameter("mode");
		String QUERY = "";
		System.out.println("type : "+type);
		System.out.println("mode : "+mode );
		if("추가하기".equals(mode)) {
			QUERY = "INSERT INTO TEST_BOARD VALUES(DEFAULT, ";
			QUERY += "'"+type+"'"+", '"+ title +"', '"+content+"', UNIX_TIMESTAMP(), DEFAULT, DEFAULT, '"+id+"', DEFAULT, DEFAULT, DEFAULT)";
		}
		else if("수정하기".equals(mode)) {
			String idx = request.getParameter("idx");
			QUERY = "UPDATE TEST_BOARD SET BOARD_TYPE = ?, TITLE = ?, CONTENT = ? WHERE IDX = "+idx +")";
		}
		BoardVO vo = new BoardVO();
		
		vo.setBOARD_TYPE(type);
		vo.setCONTENT(content);
		vo.setTITLE(title);;
		vo.setBOARD_TYPE(type);
		
		BoardDAO boardDAO = new BoardDAO();
		boardDAO.insertBoard(vo,QUERY);
	}
	@RequestMapping(method= RequestMethod.POST, value="/api/bbs/view",produces = "application/x-www-form-urlencoded; charset=utf8")
	public ModelAndView getBoardList(HttpServletRequest request, HttpServletResponse response){
		String page = request.getParameter("page");
		page = Integer.toString(((Integer.parseInt(page)*10) - 10)); 
		System.out.println(page);
		String Query = "SELECT IDX,BOARD_TYPE,TITLE,CONTENT,COMMENT_SUM,WRITER,HIT_NO, JUNO, FILE_IDX , FROM_UNIXTIME(INSERT_DATE) AS INSERT_DATE FROM TEST_BOARD order by IDX desc limit "+page+",10 ";
		
		return Join(response, Query, "getBoardList");
	}
	@RequestMapping(method = RequestMethod.POST , value = "/api/bbs/detail" ,produces = "application/x-www-form-urlencoded; charset=utf8")
	public ModelAndView board_detail(HttpServletRequest request, HttpServletResponse response) {
		String idx = request.getParameter("idx");
		
		String Query = "SELECT IDX,BOARD_TYPE,TITLE,CONTENT,COMMENT_SUM,WRITER,HIT_NO, JUNO, FILE_IDX , FROM_UNIXTIME(INSERT_DATE) AS INSERT_DATE FROM TEST_BOARD WHERE IDX = "+idx;
		String hitNumber = "UPDATE TEST_BOARD SET HIT_NO = HIT_NO + 1 WHERE IDX = "+idx;
		
		BoardDAO dao = new BoardDAO();
		dao.update(hitNumber);
		return Join(response, Query, "board");
	}
	@RequestMapping(method = RequestMethod.POST, value = "/api/bbs/juno",produces = "application/x-www-form-urlencoded; charset=utf8")
	public void junoUpdate(HttpServletRequest request, HttpServletResponse response) {
		String idx = request.getParameter("idx");
		
	}
	@RequestMapping(method = RequestMethod.POST,value = "/api/bbs/cmtview", produces = "application/x-www-form-urlencoded; charset=utf8")
	public ModelAndView cmtView(HttpServletRequest request, HttpServletResponse response) {
		String idx = request.getParameter("idx");
		JSONArray jsonArray = new JSONArray();
		BoardDAO boardDAO = new BoardDAO();
		
		try {
	        response.addHeader("Access-Control-Max-Age", "3600");
	        response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("utf8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonArray = boardDAO.detail(idx);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("home");
			modelAndView.addObject("obj",jsonArray);
			
			return modelAndView;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
// 함수	
	public ModelAndView Join(HttpServletResponse response ,String SELECT_QUERY, String type) {
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		ArrayList<BoardVO> arraylist = new ArrayList<BoardVO>();
		BoardDAO boardDAO = new BoardDAO();
		arraylist = boardDAO.getBoardList(SELECT_QUERY,type);
		
		try {
	        response.addHeader("Access-Control-Max-Age", "3600");
	        response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("utf8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		for(BoardVO array : arraylist) {
			JSONObject jsonTemp = new JSONObject();
			jsonTemp.put("type", array.getBOARD_TYPE());
			jsonTemp.put("title", array.getTITLE());
			jsonTemp.put("content", array.getCONTENT());
			jsonTemp.put("time", array.getINSERT_DATE());
			jsonTemp.put("writer", array.getWRITER());
			jsonTemp.put("idx", array.getIdx());
			
			if(array.getBoolfile() != null)
			jsonTemp.put("file", array.getBoolfile());
			
			jsonTemp.put("juno", array.getJuno());
			jsonTemp.put("hit_no", array.getHit_no());
			jsonTemp.put("comment_sum", array.getComment_sum());
			
			if(!type.equals("getBoardList")) {
				//System.out.println("select");
				//jsonObject.put("select",jsonTemp);
				modelAndView.addObject("obj",jsonTemp);
			}
			else {
				jsonArray.add(jsonTemp);
				jsonObject.put("select",jsonArray);	
			}
		}
		
		
		
		if(type.equals("getBoardList")) {
			SELECT_QUERY = "SELECT COUNT(*) AS COUNT FROM TEST_BOARD";
			int ROWS = boardDAO.RowCount(SELECT_QUERY);
			jsonObject.put("max",ROWS);
			modelAndView.addObject("obj",jsonObject);
		}
		//System.out.println(modelAndView);
		return modelAndView;
	}
}
