package com.kongque.entity.process;

import com.kongque.entity.goods.Goods;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "mes_style_technology_library_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesStyleTechnologyLibraryRelation implements Serializable {
    private static final long serialVersionUID = 2729462007611042110L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
    private String id;

    /**
     * 款式id
     */
    @Column(name = "c_goods_id")
    private String goodsId;

    @JoinColumn(name = "c_goods_id",insertable = false,updatable = false)
    @ManyToOne
    private Goods goods;

    /**
     * 工艺库
     */
    @Column(name = "c_mes_technology_library_id")
    private String mesTechnologyLibraryId;
    @JoinColumn(name = "c_mes_technology_library_id",insertable = false,updatable = false)
    @ManyToOne
    private MesTechnologyLibrary mesTechnologyLibrary;
    /**
     * 备注
     */
    @Column(name = "c_remarks")
    private String remarks;
    /**
     * 排序
     */
    @Column(name = "c_sort")
    private String sort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getMesTechnologyLibraryId() {
        return mesTechnologyLibraryId;
    }

    public void setMesTechnologyLibraryId(String mesTechnologyLibraryId) {
        this.mesTechnologyLibraryId = mesTechnologyLibraryId;
    }

    public MesTechnologyLibrary getMesTechnologyLibrary() {
        return mesTechnologyLibrary;
    }

    public void setMesTechnologyLibrary(MesTechnologyLibrary mesTechnologyLibrary) {
        this.mesTechnologyLibrary = mesTechnologyLibrary;
    }
}
/**
 * @program: xingyu-order
 * @description: 款式工艺库关系
 * @author: zhuxl
 * @create: 2019-07-15 16:27
 **/
