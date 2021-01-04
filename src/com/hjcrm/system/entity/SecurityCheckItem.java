package com.hjcrm.system.entity;

import java.io.Serializable;

import com.hjcrm.publics.util.BaseEntity;

public class SecurityCheckItem extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private Long id;
	private String description;
	private String url;
	private String name;
	
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getIdColumnName() {
		
		return "id";
	}

}
