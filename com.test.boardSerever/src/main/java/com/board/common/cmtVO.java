package com.board.common;

public class cmtVO {
	private int idx;
	private int parent;
	private int depth;
	private String content;
	private String hidden;
	private String del;
	private int seq;
	public int getIdx() {
		return idx;
	}
	public int getParent() {
		return parent;
	}
	public int getDepth() {
		return depth;
	}
	public String getContent() {
		return content;
	}
	public String getHidden() {
		return hidden;
	}
	public String getDel() {
		return del;
	}
	public int getSeq() {
		return seq;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}
