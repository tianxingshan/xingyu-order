package com.kongque.entity.process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mes_goods_optional_technology")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GoodsOptionalTechnology implements Serializable {

	private static final long serialVersionUID = 2729462007611042110L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_goods_id")
	private String goodsId;

	@Column(name = "c_optional_technology_id")
	private String optionalTechnologyId;
	
	@ManyToOne
	@JoinColumn(name = "c_optional_technology_id", insertable = false, updatable = false)
	private OptionalTechnology  optionalTechnology;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getOptionalTechnologyId() {
		return optionalTechnologyId;
	}

	public void setOptionalTechnologyId(String optionalTechnologyId) {
		this.optionalTechnologyId = optionalTechnologyId;
	}

	public OptionalTechnology getOptionalTechnology() {
		return optionalTechnology;
	}

	public void setOptionalTechnology(OptionalTechnology optionalTechnology) {
		this.optionalTechnology = optionalTechnology;
	}
	

}
