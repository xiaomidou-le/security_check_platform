package com.hjcrm.system.entity;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public class SecurityScanResult extends SecurityCheckItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3003762393184234554L;
	private Long result;
	
	private Timestamp create_time;
	private String create_by;
	private Timestamp update_time;
	

	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}
}
