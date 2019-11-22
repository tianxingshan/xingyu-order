package com.kongque.entity.goods;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_goods_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GoodsDetailSelect implements Serializable{

	private static final long serialVersionUID = -3512886759195397486L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;

	@ManyToOne
	@JoinColumn(name = "c_goods_id")
	private GoodsSelect goodsSelect;
	
	@Column(name = "c_color_name")
	private String colorName;
	
	@Column(name = "c_cost_price")
	private String costPrice;
	
	@Column(name = "c_materiel_code")
	private String materielCode;
	
	@Column(name = "c_image_keys")
	private String imageKeys;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getImageKeys() {
		return imageKeys;
	}

	public void setImageKeys(String imageKeys) {
		this.imageKeys = imageKeys;
	}

	public GoodsSelect getGoodsSelect() {
		return goodsSelect;
	}

	public void setGoodsSelect(GoodsSelect goodsSelect) {
		this.goodsSelect = goodsSelect;
	}
}
