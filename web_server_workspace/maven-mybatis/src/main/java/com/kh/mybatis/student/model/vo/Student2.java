package com.kh.mybatis.student.model.vo;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private int no;
	private String name;
	private String tel;
	private Date regDate; 
	// 그동안은 java.sql.Date를 사용했었음
	// Mybatis에서는 날짜형에서 java.util.Date가 기본값, util.Date는 시분초정보도 주고받을 수 있음
	
	public Student() {
		super();
	}
	public Student(int no, String name, String tel, Date regDate) {
		super();
		this.no = no;
		this.name = name;
		this.tel = tel;
		this.regDate = regDate;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Student [no=" + no + ", name=" + name + ", tel=" + tel + ", regDate=" + regDate + "]";
	}
}
