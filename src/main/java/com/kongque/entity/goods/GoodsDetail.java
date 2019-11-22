package com.kongque.entity.goods;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_goods_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GoodsDetail implements Serializable{

	private static final long serialVersionUID = 3957768288672556015L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_goods_id")
	private String goodsId;
	
	@Column(name = "c_color_name")
	private String colorName;
	
	@Column(name = "c_cost_price")
	private String costPrice;
	
	@Column(name = "c_materiel_code")
	private String materielCode;
	
	@Column(name = "c_image_keys")
	private String imageKeys;

	@Column(name = "c_status")
	private String status;

	@OneToMany(mappedBy = "goodsDetailId",cascade = CascadeType.PERSIST)
	private List<GoodsDetailTanant> listGoodsDetailTanant;
	
	@Transient
	private String detail;
	
	@Transient
	private String categoryName;


	@Formula("(select goods.c_measure_category_id from t_goods goods where goods.c_id = c_goods_id )")
	private String measureCategoryId;

	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<GoodsDetailTanant> getListGoodsDetailTanant() {
		return listGoodsDetailTanant;
	}

	public void setListGoodsDetailTanant(List<GoodsDetailTanant> listGoodsDetailTanant) {
		this.listGoodsDetailTanant = listGoodsDetailTanant;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMeasureCategoryId() {
		return measureCategoryId;
	}

	public void setMeasureCategoryId(String measureCategoryId) {
		this.measureCategoryId = measureCategoryId;
	}
}
