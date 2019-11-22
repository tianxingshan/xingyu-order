package com.kongque.entity.basics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_size_code")
public class SizeCode implements Serializable{

	private static final long serialVersionUID = 1562929015891924723L;

	@Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
	private String id;
	
	@Column(name = "c_min_number")
	private Double minNumber;
	
	@Column(name = "c_max_number")
	private Double maxNumber;
	
	@Column(name = "c_size_code")
	private String sizeCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Double minNumber) {
		this.minNumber = minNumber;
	}

	public Double getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Double maxNumber) {
		this.maxNumber = maxNumber;
	}

	public String getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	
	
}
