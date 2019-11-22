package com.kongque.entity.measure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@Entity
@Table(name = "mes_goods_measure_position_size")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GoodsMeasurePositionSize implements Serializable {


	private static final long serialVersionUID = -77320564862532272L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_goods_id")
	private String goodsId;
	
	@Column(name = "c_measure_size_id")
	private String measureSizeId;

	@Column(name = "c_measure_position_id")
	private String measurePositionId;
	
	@Column(name = "c_model_net_size")
	private String modelNetSize;
	
	@Column(name = "c_model_finish_size")
	private String modelFinishSize;
	
	@Column(name = "c_shrinkage")
	private String shrinkage;
	
	@Column(name = "c_tolerance")
	private String tolerance;

	@Column(name = "c_del")
	private String del;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@OrderBy("code ASC")
	@JoinColumn(name = "c_measure_position_id",insertable=false, updatable=false)
	private MeasurePosition measurePosition;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "c_measure_size_id",insertable=false, updatable=false)
	private MeasureSize measureSize;

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

	public String getMeasureSizeId() {
		return measureSizeId;
	}

	public void setMeasureSizeId(String measureSizeId) {
		this.measureSizeId = measureSizeId;
	}

	public String getMeasurePositionId() {
		return measurePositionId;
	}

	public void setMeasurePositionId(String measurePositionId) {
		this.measurePositionId = measurePositionId;
	}

	public String getModelNetSize() {
		return modelNetSize;
	}

	public void setModelNetSize(String modelNetSize) {
		this.modelNetSize = modelNetSize;
	}

	public String getModelFinishSize() {
		return modelFinishSize;
	}

	public void setModelFinishSize(String modelFinishSize) {
		this.modelFinishSize = modelFinishSize;
	}

	public String getShrinkage() {
		return shrinkage;
	}

	public void setShrinkage(String shrinkage) {
		this.shrinkage = shrinkage;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public MeasurePosition getMeasurePosition() {
		return measurePosition;
	}

	public void setMeasurePosition(MeasurePosition measurePosition) {
		this.measurePosition = measurePosition;
	}

	public MeasureSize getMeasureSize() {
		return measureSize;
	}

	public void setMeasureSize(MeasureSize measureSize) {
		this.measureSize = measureSize;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
}
