package com.board.common;

public class BoardVO {
	private String BOARD_TYPE;
	private String TITLE;
	private String CONTENT;
	private String INSERT_DATE;
	private String WRITER;
	private String idx;
	private String juno;
	private String hit_no;
	private String boolfile;
	private int comment_sum;
	private int max;
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getComment_sum() {
		return comment_sum;
	}
	public void setComment_sum(int comment_sum) {
		this.comment_sum = comment_sum;
	}
	public String getIdx() {
		return idx;
	}
	public String getJuno() {
		return juno;
	}
	public String getHit_no() {
		return hit_no;
	}
	public String getBoolfile() {
		return boolfile;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public void setJuno(String juno) {
		this.juno = juno;
	}
	public void setHit_no(String hit_no) {
		this.hit_no = hit_no;
	}
	public void setBoolfile(String boolfile) {
		this.boolfile = boolfile;
	}
	public String getBOARD_TYPE() {
		return BOARD_TYPE;
	}
	public void setBOARD_TYPE(String BOARD_TYPE) {
		this.BOARD_TYPE = BOARD_TYPE;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String TITLE) {
		this.TITLE = TITLE;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String CONTENT) {
		this.CONTENT = CONTENT;
	}
	public String getINSERT_DATE() {
		return INSERT_DATE;
	}
	public void setINSERT_DATE(String iNSERT_DATE) {
		this.INSERT_DATE = iNSERT_DATE;
	}
	public String getWRITER() {
		return WRITER;
	}
	public void setWRITER(String WRITER) {
		this.WRITER = WRITER;
	}
	
	@Override
	public String toString() {
		return " BOARD_TYPE = "+BOARD_TYPE+"\n CONTENT = "+CONTENT+"\n INSERT_DATE = "+INSERT_DATE+"\n WRITER = "+WRITER;
	}
}
