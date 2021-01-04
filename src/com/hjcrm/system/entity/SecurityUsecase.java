package com.hjcrm.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hjcrm.publics.util.BaseEntity;

public class SecurityUsecase extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 2;
	private Long id;
	private String description;
	private String url;
	private String name;
	private String create_by;
	private Timestamp create_time;
	private String update_by;
	private Timestamp update_time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
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
	@Override
	public String getIdColumnName() {
		
		return "id";
	}

	
}
