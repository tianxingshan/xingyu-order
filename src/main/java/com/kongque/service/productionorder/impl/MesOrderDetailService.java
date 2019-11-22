package com.kongque.service.productionorder.impl;


import com.kongque.common.BillStatus;
import com.kongque.dao.IDaoService;
import com.kongque.dto.*;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.productionorder.*;
import com.kongque.model.MesOrderDetailModel;
import com.kongque.service.productionorder.IMesOrderDetailService;
import com.kongque.util.DateUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: xingyu-order
 * @description: 生产订单明细服务实现
 * @author: zhuxl
 * @create: 2018-10-10 16:06
 **/
@Service
public class MesOrderDetailService implements IMesOrderDetailService {

    @Resource
    private IDaoService dao;

    /**
     * 订单分配保存
     * @param dtos
     * @return
     */
    @Override
    public Result assign(List<MesOrderDetailAssignDto> dtos) {

        for (MesOrderDetailAssignDto dto:dtos) {
            try {
                //保存校验
                String msg = saveValidation(dto);
                if (StringUtils.isNotBlank(msg)) {
                    return new Result("100", msg);
                }
                if (StringUtils.isBlank(dto.getId())) {
                    MesOrderDetailAssign mesOrderDetailAssign = new MesOrderDetailAssign();
                    BeanUtils.copyProperties(dto, mesOrderDetailAssign);
                    mesOrderDetailAssign.setCreateTime(new Date());
                    mesOrderDetailAssign.setUpdateTime(new Date());
                    mesOrderDetailAssign.setDeleteFlag(0);
                    dao.save(mesOrderDetailAssign);
                } else {

                    MesOrderDetailAssign mesOrderDetailAssign = dao.findById(MesOrderDetailAssign.class, dto.getId());
                    //BeanUtils.copyProperties(dto,mesOrderDetailAssign);
                    mesOrderDetailAssign.setUpdateTime(new Date());
                    mesOrderDetailAssign.setTechnicianId(dto.getTechnicianId());
                    mesOrderDetailAssign.setTechnicianName(dto.getTechnicianName());
                    mesOrderDetailAssign.setUpdateTime(new Date());
                    mesOrderDetailAssign.setUpdateUserId(dto.getUpdateUserId());
                    dao.update(mesOrderDetailAssign);
                }

                OrderDetail orderDetail = dao.findById(OrderDetail.class, dto.getOrderDetailId());
                orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.ASSIGNED.getValue());
                dao.update(orderDetail);

            } catch (Exception ex) {
                String err = ex.getMessage().toString();
                return new Result("100", err);
            }
        }
        return new Result("200", "操作成功！");
    }

    /**
     * 取消订单分配
     * @param id
     * @return
     */
    @Override
    public Result unAssign(String id) {

        try {


            if(StringUtils.isBlank(id)){
                return new Result("100","主键ID不能为空!");
            }
            MesOrderDetailAssign mesOrderDetailAssign = dao.findById(MesOrderDetailAssign.class,id);

            if (mesOrderDetailAssign==null){
                return new Result("100","要删除的订单不存在!");
            }
            mesOrderDetailAssign.setUpdateTime(new Date());
            mesOrderDetailAssign.setDeleteFlag(1);
            OrderDetail orderDetail = dao.findById(OrderDetail.class,mesOrderDetailAssign.getOrderDetailId());
            if (!BillStatus.OrderDetailStatus.ASSIGNED.getValue().equals(orderDetail.getOrderDetailStatus())){
                return new Result("100","此状态不能取消分配");
            }
            orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.UNASSIGN.getValue());
            dao.update(mesOrderDetailAssign);
            dao.update(orderDetail);

        } catch (Exception ex){
            return new Result("100", ex.getMessage().toString());
        }

        return new Result("200", "操作成功！");
    }



    @Override
    public Pagination<MesOrderDetailModel> getAllOrderDetailList(MesOrderDetailSearchDto dto , PageBean pageBean){

        Pagination<MesOrderDetailModel> pagination = new Pagination<>();

        List<MesOrderDetailModel> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlCount = new StringBuilder();
        StringBuilder sqlRows = new StringBuilder();
        sqlCount.append(" select count(*) ");
        sqlRows.append(" select moda.c_id id, " +
                "tod.c_goods_sn goodsSn," +
                "tg.c_code goodsCode," +
                "tg.c_name goods_name," +
                "tgd.c_color_name goodsColorName," +
                "tod.c_num num," +
                "t.c_order_code orderCode," +
                "t.c_create_time orderCreateTime," +
                "ifnull(tod.c_order_detail_status,'0') orderDetailStatus," +
                "t.c_customer_code customerCode," +
                "t.c_customer_name customerName," +
                "t.c_shop_name shopName," +
                "moda.c_technician_id technicianId," +
                "moda.c_technician_name technicianName," +
                "tu.c_user_name assignCreateName," +
                "moda.c_create_time assignCreatTime," +
                "tod.c_id orderDetailId " +
                ",tc.c_id category_id," +
                "tc.c_name categoryName," +
                "tg.c_id goodsId," +
                "tod.c_goods_detail_id goodsDetailId," +
                "t.c_order_character," +
                "t.c_reset," +
                "t.c_id," +
                "mods.c_mes_measure_size_name ," +
                "moda.c_technical_finished_time," +
                "t.c_embroid_name," +
                "mods.c_remarks assignRemarks," +
                "mods.c_mes_measure_size_id," +
                "t.c_xingyu_chek_time," +
                "ifnull(tod.c_closed_status,'0') c_closed_status ");
        sql.append(" from t_order_detail tod ");
        sql.append(" join t_order t on tod.c_order_id = t.c_id");
        sql.append(" JOIN t_goods_detail tgd ON tod.c_goods_detail_id = tgd.c_id ");
        sql.append(" join t_goods tg on tgd.c_goods_id = tg.c_id ");
        sql.append(" left join mes_order_detail_assign moda on tod.c_id = moda.c_order_detail_id and moda.c_delete_flag=0");
        sql.append(" left join t_user tu on moda.c_create_user_id = tu.c_id");
        sql.append(" left join t_category tc on tg.c_category_id = tc.c_id ");
        sql.append(" left join mes_order_detail_size mods on tod.c_id = mods.c_order_detail_id ");
        sql.append(" where ifnull(t.c_delete_flag,'0')='0' and ifnull(t.c_status_before_produce,'0')='2' " +
                "and ifnull(t.c_status_bussiness,'') in ('3','6','7','8') "
        );

        if (StringUtils.isNotBlank(dto.getEmbroidName())){
            sql.append(" and t.c_embroid_name like '%").append(dto.getEmbroidName()).append("%' ");
        }

        //订单创建日期
        if(StringUtils.isNotBlank(dto.getOrderCreateTimeBegin()) && StringUtils.isNotBlank(dto.getOrderCreateTimeEnd())){
            sql.append(" and t.c_create_time between '").append(dto.getOrderCreateTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getOrderCreateTimeEnd()).append(" 23:59:59'");
        }

        //订单单号
        if(StringUtils.isNotBlank(dto.getOrderCode())){
            sql.append(" and t.c_order_code like '%").append(dto.getOrderCode()).append("%'");
        }
        //门店id
        if(StringUtils.isNotBlank(dto.getShopId())){
            sql.append(" and t.c_shop_id = '").append(dto.getShopId()).append("'");
        }

        //订单审核日期
        if(StringUtils.isNotBlank(dto.getOrderAuditTimeBegin()) && StringUtils.isNotBlank(dto.getOrderAuditTimeEnd())){
            sql.append(" and t.c_xingyu_chek_time between '").append(dto.getOrderAuditTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getOrderAuditTimeEnd()).append(" 23:59:59'");
        }

        //顾客id
        if(StringUtils.isNotBlank(dto.getCustomerId())){
            sql.append(" and t.c_customer_id = '").append(dto.getCustomerId()).append("'");
        }
        if(StringUtils.isNotBlank(dto.getCustomerQ())){
            sql.append(" and (t.c_customer_name like '%"+dto.getCustomerQ()+"%' or t.c_customer_code like '%"+dto.getCustomerQ()+"%')");
        }

        //商品id
        if(StringUtils.isNotBlank(dto.getGoodsDetailId())){
            sql.append(" and tod.c_goods_detail_id = '").append(dto.getGoodsDetailId()).append("'");
        }

        //分类ID
        if(StringUtils.isNotBlank(dto.getCategoryId())){
            sql.append(" and tg.c_category_id = '").append(dto.getCategoryId()).append("'");
        }

        //订单性质
        if(StringUtils.isNotBlank(dto.getCharacteres()) && !"全部".equals(dto.getCharacteres())){
            sql.append(" and t.c_order_character = '").append(dto.getCharacteres()).append("'");
        }

        //唯一码
        if(StringUtils.isNotBlank(dto.getGoodsSN())){
            sql.append(" and tod.c_goods_sn='").append(dto.getGoodsSN()).append("'");
        }
        //订单明细状态
        if (StringUtils.isNotBlank(dto.getOrderDetailStatus()) && !"全部".equals(dto.getOrderDetailStatus())){
            //如果等于6 就是没分配的订单
            if(BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(dto.getOrderDetailStatus())){
                sql.append(" and  ifnull(tod.c_closed_status,'0') in ('','0','1','4') ");
//                        .append(" and not exists (select 1 from mes_order_detail_assign oa where tod.c_id =oa.c_order_detail_id and oa.c_delete_flag='0' )");
            }
            sql.append(" and ifnull(tod.c_order_detail_status,'").append(dto.getOrderDetailStatus()).append("')='")
                    .append(dto.getOrderDetailStatus()).append("'");
        }
        else {
            sql.append(" and (ifnull(tod.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')='")
                    .append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("' and  ifnull(tod.c_closed_status,'0') in ('','0','1','4')  " +
                    " or  ifnull(tod.c_order_detail_status,'").append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("')!='"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"'" +
                    " )");
        }
        //尺码 20181026新增
        if(StringUtils.isNotBlank(dto.getMesCode())){
            sql.append(" and mods.c_mes_measure_size_name = '").append(dto.getMesCode()).append("'");
        }
        //版型师
        if(StringUtils.isNotBlank(dto.getTechnicianId())){
            sql.append(" and moda.c_technician_id = '").append(dto.getTechnicianId()).append("'");
        }

        //订单技术完成日期
        if(StringUtils.isNotBlank(dto.getOrderTechnicalFinishedTimeBegin()) && StringUtils.isNotBlank(dto.getOrderTechnicalFinishedTimeEnd())){
            sql.append(" and moda.c_technical_finished_time between '").append(dto.getOrderTechnicalFinishedTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getOrderTechnicalFinishedTimeEnd()).append(" 23:59:59'");
        }
        //订单分配日期
        if(StringUtils.isNotBlank(dto.getAssignTimeBegin()) && StringUtils.isNotBlank(dto.getAssignTimeEnd())){
            sql.append(" and moda.c_create_time between '").append(dto.getAssignTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getAssignTimeEnd()).append(" 23:59:59'");
        }
        sqlCount.append(sql);
        sql.append(" order by t.c_create_time , moda.c_create_time ");

        if(pageBean.getPage()!=null && pageBean.getRows()!=null){
            sql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
        }

        List resultSet = dao.queryBySql(sqlRows.append(sql).toString());
        List<BigInteger> count = dao.queryBySql(sqlCount.toString());

        for(Object result : resultSet){
            MesOrderDetailModel model = new MesOrderDetailModel();//构建返回数据模型
            Object[] properties = (Object[])result;
            model.setId(properties[0]==null ? "" : properties[0].toString());
            model.setGoodsSN(properties[1]==null ? "" : properties[1].toString());
            model.setGoodsCode(properties[2]==null ? "" : properties[2].toString());
            model.setGoodsName(properties[3]==null ? "" : properties[3].toString());
            model.setGoodsColor(properties[4]==null ? "" : properties[4].toString());
            model.setNum(properties[5]==null ? "0" : properties[5].toString());
            model.setOrderCode(properties[6]==null ? "" : properties[6].toString());
            model.setOrderCreateTime(properties[7]==null ? "" : DateUtil.formatDate((Date)properties[7],"yyyy-MM-dd HH:mm:ss"));
            model.setOrderDetailStatus(properties[8]==null || properties[8].equals("") ? "未分配" : BillStatus.orderDetailStatus(properties[8].toString()));
            model.setCustomerCode(properties[9]==null ? "" : properties[9].toString());
            model.setCustomerName(properties[10]==null ? "" : properties[10].toString());
            model.setShopName(properties[11]==null ? "" : properties[11].toString());
            model.setTechnicianId(properties[12]==null ? "" : properties[12].toString());
            model.setTechnicianName(properties[13]==null ? "" : properties[13].toString());
            model.setAssignCreateName(properties[14]==null ? "" : properties[14].toString());
            model.setAssignCreateTime(properties[15]==null ? "" : DateUtil.formatDate((Date)properties[15],"yyyy-MM-dd HH:mm:ss"));
            model.setOrderDetailId(properties[16]==null ? "" : properties[16].toString());
            model.setCategoryId(properties[17]==null ? "" : properties[17].toString());
            model.setGetCategoryName(properties[18]==null ? "" : properties[18].toString());
            model.setGoodsId(properties[19]==null ? "" : properties[19].toString());
            model.setGoodsDetailId(properties[20]==null ? "" : properties[20].toString());
            //20181022添加订单类型与订单性质
            model.setCharacteres(properties[21]==null ? "" : properties[21].toString());    //订单性质
            model.setReset(properties[22]==null ? "" : properties[22].toString().equals("0")?"普通订单":"重置订单");  //订单类型 0：普通订单，1：重置订单
            //20181022添加订单ID
            model.setOrderId(properties[23]==null ? "" : properties[23].toString());    //订单id
            model.setMesSizeName(properties[24]==null ? "" : properties[24].toString()); //分配尺码
            model.setTechnicalFinishedTime(properties[25]==null ? "" : DateUtil.formatDate((Date)properties[25],"yyyy-MM-dd HH:mm:ss"));//技术完成日期
            model.setEmbroidName(properties[26]==null ? "" : properties[26].toString()); //绣字名
            model.setAssignRemarks(properties[27]==null ? "" : properties[27].toString());//分配备注
            model.setMesSizeId(properties[28]==null ? "" : properties[28].toString());//分配尺码id
            model.setXingyuChekTime(properties[29]==null ? "" : properties[29].toString());//星域审核日期
            model.setClosedStatus(properties[30]==null ? "" : properties[30].toString());//结案状态
            list.add(model);
        }
        pagination.setRows(list);
        pagination.setTotal(count == null || count.isEmpty() ? 0L : count.get(0).longValue());
        return pagination;
    }

    @Override
    public Long getCountAllOrderDetail(MesOrderDetailSearchDto dto) {

        StringBuilder sql = new StringBuilder();
        sql.append(" select count(*) ");
        sql.append(" from t_order_detail tod ");
        sql.append(" join t_order t on tod.c_order_id = t.c_id");
        sql.append(" join t_goods_detail tgd ON tod.c_goods_detail_id = tgd.c_id ");
        sql.append(" join t_goods tg on tgd.c_goods_id = tg.c_id ");
        sql.append(" left join mes_order_detail_assign moda on tod.c_id = moda.c_order_detail_id and moda.c_delete_flag=0");
        sql.append(" left join t_user tu on moda.c_create_user_id = tu.c_id");
        sql.append(" left join t_category tc on tg.c_category_id = tc.c_id ");
        sql.append(" left join mes_order_detail_size mods on tod.c_id = mods.c_order_detail_id ");
        sql.append(" where  ifnull(t.c_status_before_produce,'0')='2' " +
                "and ifnull(t.c_status_bussiness,'') in ('3','6','7','8') " );
//                "and ifnull(tod.c_closed_status,'0') in ('','0','1','4') ");
        //1 未分配 2 已分配
//        if("1".equals(dto.getSearchType())){
//            sql.append(" and ifnull(tod.c_order_detail_status,'')='' ");
//        }else if ("2".equals(dto.getSearchType())) {
//            sql.append(" and ifnull(tod.c_order_detail_status,'') !='' ");
//        }


        //订单创建日期
        if(StringUtils.isNotBlank(dto.getOrderCreateTimeBegin()) && StringUtils.isNotBlank(dto.getOrderCreateTimeEnd())){
            sql.append(" and t.c_create_time between '").append(dto.getOrderCreateTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getOrderCreateTimeEnd()).append(" 23:59:59'");
        }

        //订单单号
        if(StringUtils.isNotBlank(dto.getOrderCode())){
            sql.append(" and t.c_order_code like '%").append(dto.getOrderCode()).append("%'");
        }
        //门店id
        if(StringUtils.isNotBlank(dto.getShopId())){
            sql.append(" and t.c_shop_id = '").append(dto.getShopId()).append("'");
        }

        //订单审核日期
        if(StringUtils.isNotBlank(dto.getOrderAuditTimeBegin()) && StringUtils.isNotBlank(dto.getOrderAuditTimeEnd())){
            sql.append(" and t.c_xingyu_chek_time between '").append(dto.getOrderAuditTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getOrderAuditTimeEnd()).append(" 23:59:59'");
        }

        //顾客id
        if(StringUtils.isNotBlank(dto.getCustomerId())){
            sql.append(" and t.c_customer_id = '").append(dto.getCustomerId()).append("'");
        }

        //商品id
        if(StringUtils.isNotBlank(dto.getGoodsDetailId())){
            sql.append(" and tod.c_goods_detail_id = '").append(dto.getGoodsDetailId()).append("'");
        }

        //分类ID
        if(StringUtils.isNotBlank(dto.getCategoryId())){
            sql.append(" and tg.c_category_id = '").append(dto.getCategoryId()).append("'");
        }

        //唯一码
        if(StringUtils.isNotBlank(dto.getGoodsSN())){
            sql.append(" and tod.c_goods_sn='").append(dto.getGoodsSN()).append("'");
        }

        //订单性质
        if(StringUtils.isNotBlank(dto.getCharacteres())&& !"全部".equals(dto.getCharacteres())){
            sql.append(" and t.c_order_character = '").append(dto.getCharacteres()).append("'");
        }

        //订单明细状态
        if (StringUtils.isNotBlank(dto.getOrderDetailStatus()) && !"全部".equals(dto.getOrderDetailStatus())){
            //如果等于6 就是没分配的订单
            if(BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(dto.getOrderDetailStatus())){
                sql.append(" and ifnull(tod.c_order_detail_status,'").append(dto.getOrderDetailStatus()).append("')='")
                        .append(dto.getOrderDetailStatus()).append("'")
                        .append(" and  ifnull(tod.c_closed_status,'0') in ('','0','1','4') ")
                        .append(" and not exists (select 1 from mes_order_detail_assign oa where tod.c_id =oa.c_order_detail_id )");
            }else {
                sql.append(" and tod.c_order_detail_status='").append(dto.getOrderDetailStatus()).append("'")
                       /* .append(" and moda.c_order_detail_id is not null ")
                        .append(" and tu.c_id is not null ")
                        .append(" and tc.c_id is not null ")*/;
            }
        }
        else {
            sql.append(" and (ifnull(tod.c_order_detail_status,'").append(dto.getOrderDetailStatus()).append("')='")
                    .append(BillStatus.OrderDetailStatus.UNASSIGN.getValue()).append("' and  ifnull(tod.c_closed_status,'0') in ('','0','1','4')  " +
                    " or  ifnull(tod.c_order_detail_status,'").append(dto.getOrderDetailStatus()).append("')!='"+BillStatus.OrderDetailStatus.UNASSIGN.getValue()+"'" +
                    " )");
        }
        //尺码 20181026新增
        if(StringUtils.isNotBlank(dto.getMesCode())){
            sql.append(" and mods.c_mes_measure_size_name = '").append(dto.getMesCode()).append("'");
        }
        //版型师
        if(StringUtils.isNotBlank(dto.getTechnicianId())){
            sql.append(" and moda.c_technician_id = '").append(dto.getTechnicianId()).append("'");
        }

        //订单技术完成日期
        if(StringUtils.isNotBlank(dto.getOrderTechnicalFinishedTimeBegin()) && StringUtils.isNotBlank(dto.getOrderTechnicalFinishedTimeEnd())){
            sql.append(" and moda.c_technical_finished_time between '").append(dto.getOrderTechnicalFinishedTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getOrderTechnicalFinishedTimeEnd()).append(" 23:59:59'");
        }
        //订单分配日期
        if(StringUtils.isNotBlank(dto.getAssignTimeBegin()) && StringUtils.isNotBlank(dto.getAssignTimeEnd())){
            sql.append(" and moda.c_create_time between '").append(dto.getAssignTimeBegin()).append(" 00:00:00'").append(" and '").append(dto.getAssignTimeEnd()).append(" 23:59:59'");
        }
        sql.append(" order by t.c_create_time , moda.c_create_time ");

        List<BigInteger> result = dao.queryBySql(sql.toString());
        return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
    }

    /**
     * 已分配订单明细
     * @param dto
     * @param pageBean
     * @return
     */
    @Override
    public List<MesOrderDetailModel> getAssignAllOrderDetailList(MesOrderDetailSearchDto dto, PageBean pageBean) {
        dto.setSearchType("2");
        return null;//this.getAllOrderDetailList(dto,pageBean);
    }

    /**
     * 已分配订单条数
     * @param dto
     * @return
     */
    @Override
    public Long getAssignCountAllOrderDetail(MesOrderDetailSearchDto dto) {
        dto.setSearchType("2");
        return this.getCountAllOrderDetail(dto);
    }

    /**
     * 未分配订单明细
     * @param dto
     * @param pageBean
     * @return
     */
    @Override
    public List<MesOrderDetailModel> getNotAssignAllOrderDetailList(MesOrderDetailSearchDto dto, PageBean pageBean) {
        dto.setSearchType("1");
        return null;// this.getAllOrderDetailList(dto,pageBean);
    }

    /**
     * 未分配订单明细条数
     * @param dto
     * @return
     */
    @Override
    public Long getNotAssignCountAllOrderDetail(MesOrderDetailSearchDto dto) {
        dto.setSearchType("1");
        return this.getCountAllOrderDetail(dto);
    }

    /**
     * 分配尺码
     * @param dto
     * @return
     */
    @Override
    public Result assignMesOrderDetailSize(MesOrderDetailSizeDto dto) {

        Date date = new Date();
        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetailId());

        if("2".equals(orderDetail.getClosedStatus())||"3".equals(orderDetail.getClosedStatus())){
            return new Result("500","此订单明细已经结案！");
        }

        MesOrderDetailSize entity = new MesOrderDetailSize();
        List<MesOrderDetailMaterial> materialList = dao.findListByProperty(MesOrderDetailMaterial.class,"orderDetailId",dto.getOrderDetailId());
        List<MesOrderDetailMeasure> mesOrderDetailMeasureList = dao.findListByProperty(MesOrderDetailMeasure.class,"orderDetailId",dto.getOrderDetailId());

        List<MesOrderDetailSize> list = dao.findListByProperty(MesOrderDetailSize.class,"orderDetailId",dto.getOrderDetailId());



        if (list.size()>0){
            entity = list.get(0);

//            if ((materialList.size() > 0 || mesOrderDetailMeasureList.size()> 0) && !dto.getMesMeasureSizeId().equals(entity.getMesMeasureSizeId())){
//                return new Result("500","已经配置量体尺寸或物料,不能更新尺码！");
//            }
            if(materialList.size() > 0 && !dto.getMesMeasureSizeId().equals(list.get(0).getMesMeasureSizeId())){
            dao.deleteAllEntity(materialList);
            }
            if(mesOrderDetailMeasureList.size()> 0 && !dto.getMesMeasureSizeId().equals(list.get(0).getMesMeasureSizeId())) {
                dao.deleteAllEntity(mesOrderDetailMeasureList);
            }
            entity.setMesMeasureSizeId(dto.getMesMeasureSizeId());
            entity.setMesMeasureSizeName(dto.getMesMeasureSizeName());
            entity.setRemarks(dto.getRemarks());
            entity.setUpdateUserId(dto.getUpdateUserId());
            entity.setUpdateTime(date);
            dao.update(entity);
        }else {
            BeanUtils.copyProperties(dto,entity);
            entity.setCreateTime(date );
            entity.setUpdateTime(date);
            dao.save(entity);
        }

//        if(StringUtils.isBlank(dto.getId())){
//            BeanUtils.copyProperties(dto,entity);
//            entity.setCreateTime(date );
//            entity.setUpdateTime(date);
//            if (list.size()>0){
//                entity.setId(list.get(0).getId());
//                dao.update(entity);
//            }else {
//                dao.save(entity);
//            }
//        }else {
//            BeanUtils.copyProperties(dto,entity);
//            entity.setUpdateTime(date);
//            if(list.size()>0 && !list.get(0).getId().equals(dto.getId())){
//                entity.setId(list.get(0).getId());
//            }
//            dao.update(entity);
//        }
        return new Result(entity);
    }

    /**
     * 通过订单明细id查询分配尺码
     * @param orderDetailId
     * @return
     */
    @Override
    public Result getSizeByOrderDetailId(String orderDetailId) {
        List<MesOrderDetailSize> list = dao.findListByProperty(MesOrderDetailSize.class,"orderDetailId",orderDetailId);
        if (list.size()>0){
            return new Result(list.get(0));
        }
        return new Result();
    }

    /**
     * 分配量体尺寸
     * @param dto
     * @return
     */
    @Override
    public Result assignMeasure(MesOrderDetailMeasureDto dto){

        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetailId());

        if("2".equals(orderDetail.getClosedStatus())||"3".equals(orderDetail.getClosedStatus())){
            return new Result("500","此订单明细已经结案！");
        }

        List<MesOrderDetailSize> mesOrderDetailSizeList = dao.findListByProperty(MesOrderDetailSize.class,"orderDetailId",dto.getOrderDetailId());
        if (mesOrderDetailSizeList.size()<1){
            return new Result("500","请先保存尺码");
        }

        for (PositionDto obj : dto.getPositionList()) {
            MesOrderDetailMeasure entity = new MesOrderDetailMeasure();
            if (StringUtils.isBlank(obj.getId())){
                entity.setOrderDetailId(dto.getOrderDetailId());
                entity.setCreateTime(new Date());
                entity.setMeasurePositionId(obj.getMeasurePositionId());
                entity.setModelNetSize(obj.getModelNetSize());
                entity.setModelFinishSize(obj.getModelFinishSize());
                entity.setShrinkage(obj.getShrinkage());
                entity.setTolerance(obj.getTolerance());
                dao.save(entity);
            }else{

                entity = dao.findById(MesOrderDetailMeasure.class,obj.getId());
                if(entity !=null){
                    entity.setOrderDetailId(dto.getOrderDetailId());
                    entity.setMeasurePositionId(obj.getMeasurePositionId());
                    entity.setModelNetSize(obj.getModelNetSize());
                    entity.setModelFinishSize(obj.getModelFinishSize());
                    entity.setShrinkage(obj.getShrinkage());
                    entity.setTolerance(obj.getTolerance());
                    dao.update(entity);
                }
            }
        }

        Criteria criteria = dao.createCriteria(MesOrderDetailSewingProcess.class);
        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
        @SuppressWarnings("unchecked")
        List<MesOrderDetailSewingProcess> list = criteria.list();
        return new Result(list) ;

//        Criteria criteria = dao.createCriteria(MesOrderDetailMeasure.class);
//        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
//        @SuppressWarnings("unchecked")
//        List<MesOrderDetailMeasure> list = criteria.list();
//        if(list.size()==0 || list == null){
//            //新增
//            if(dto.getPositionList()!=null && dto.getPositionList().size()>0){
//                for (int i = 0; i < dto.getPositionList().size(); i++) {
//                    MesOrderDetailMeasure mesOrderDetailMeasure = new MesOrderDetailMeasure();
//                    mesOrderDetailMeasure.setOrderDetailId(dto.getOrderDetailId());
//                    mesOrderDetailMeasure.setCreateTime(new Date());
//                    mesOrderDetailMeasure.setMeasurePositionId(dto.getPositionList().get(i).getMeasurePositionId());
//                    mesOrderDetailMeasure.setModelNetSize(dto.getPositionList().get(i).getModelNetSize());
//                    mesOrderDetailMeasure.setModelFinishSize(dto.getPositionList().get(i).getModelFinishSize());
//                    mesOrderDetailMeasure.setShrinkage(dto.getPositionList().get(i).getShrinkage());
//                    mesOrderDetailMeasure.setTolerance(dto.getPositionList().get(i).getTolerance());
//                    dao.save(mesOrderDetailMeasure);
//                }
//            }
//        }else{
//            //修改
//            if(dto.getPositionList()!=null && dto.getPositionList().size()>0){
//                for (int j = 0; j < list.size(); j++) {
//                    dao.delete(list.get(j));
//                }
//                for (int i = 0; i < dto.getPositionList().size(); i++) {
//                    MesOrderDetailMeasure mesOrderDetailMeasure = new MesOrderDetailMeasure();
//                    mesOrderDetailMeasure.setOrderDetailId(dto.getOrderDetailId());
//                    mesOrderDetailMeasure.setCreateTime(new Date());
//                    mesOrderDetailMeasure.setMeasurePositionId(dto.getPositionList().get(i).getMeasurePositionId());
//                    mesOrderDetailMeasure.setModelNetSize(dto.getPositionList().get(i).getModelNetSize());
//                    mesOrderDetailMeasure.setModelFinishSize(dto.getPositionList().get(i).getModelFinishSize());
//                    mesOrderDetailMeasure.setShrinkage(dto.getPositionList().get(i).getShrinkage());
//                    mesOrderDetailMeasure.setTolerance(dto.getPositionList().get(i).getTolerance());
//                    dao.save(mesOrderDetailMeasure);
//                }
//            }
//        }
//        return new Result(list);
    }

    /**
     * 删除部位尺寸
     * @param id
     * @return
     */
    @Override
    public Result unAssignMeasure(String id) {
        if(StringUtils.isBlank(id)){
            return new Result("100","主键id不能为空");
        }
        MesOrderDetailMeasure mesOrderDetailMeasure = dao.findById(MesOrderDetailMeasure.class,id);
        if (mesOrderDetailMeasure==null){
            return new Result("100","主键id不存在");
        }
        dao.delete(mesOrderDetailMeasure);
        return new Result("200","删除成功！");
    }

    /**
     * 查询分配量体通过订单明细id
     * @param orderDetailId
     * @return
     */
    @Override
    public Result getAssignMeasureByOrderDetailId(String orderDetailId) {
        List<MesOrderDetailMeasure> list = dao.findListByProperty(MesOrderDetailMeasure.class,"orderDetailId",orderDetailId);
        return new Result(list);
    }

    /**
     * 分配工序
     * @param dto
     * @return
     */
    @Override
    public Result assignSewingProcess(MesOrderDetailSewingProcessDto dto) {

        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetailId());

        if("2".equals(orderDetail.getClosedStatus())||"3".equals(orderDetail.getClosedStatus())){
            return new Result("500","此订单明细已经结案！");
        }

        Criteria criteria = dao.createCriteria(MesOrderDetailSewingProcess.class);
        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
        @SuppressWarnings("unchecked")
        List<MesOrderDetailSewingProcess> list = criteria.list();
        dao.deleteAllEntity(list);

        MesOrderDetailSize mesOrderDetailSize = dao.findUniqueByProperty(MesOrderDetailSize.class,"orderDetailId",dto.getOrderDetailId());
        if (mesOrderDetailSize ==null){
            mesOrderDetailSize = new MesOrderDetailSize();
            mesOrderDetailSize.setMesGoodsOptionalTechnologyId(dto.getMesGoodsOptionalTechnologyId());
            dao.save(mesOrderDetailSize);
        }else {
            mesOrderDetailSize.setMesGoodsOptionalTechnologyId(dto.getMesGoodsOptionalTechnologyId());
            dao.update(mesOrderDetailSize);
        }
        for (SewDto sewDto : dto.getSewProcessList()) {
            MesOrderDetailSewingProcess mesOrderDetailSewingProcess = new MesOrderDetailSewingProcess();
                mesOrderDetailSewingProcess.setOrderDetailId(dto.getOrderDetailId());
                mesOrderDetailSewingProcess.setProcessPositionId(sewDto.getProcessPositionId());
                mesOrderDetailSewingProcess.setProcessId(sewDto.getProcessId());
                mesOrderDetailSewingProcess.setSort(sewDto.getSort());
                dao.save(mesOrderDetailSewingProcess);
        }



//        Criteria criteria = dao.createCriteria(MesOrderDetailSewingProcess.class);
//        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
//        @SuppressWarnings("unchecked")
//        List<MesOrderDetailSewingProcess> list = criteria.list();

//        if(list==null || list.size()==0){
//            if(dto.getSewProcessList()!=null && dto.getSewProcessList().size()>0){
//                for (int i = 0; i < dto.getSewProcessList().size(); i++) {
//                    MesOrderDetailSewingProcess mesOrderDetailSewingProcess = new MesOrderDetailSewingProcess();
//                    mesOrderDetailSewingProcess.setOrderDetailId(dto.getOrderDetailId());
//                    mesOrderDetailSewingProcess.setProcessPositionId(dto.getSewProcessList().get(i).getProcessPositionId());
//                    mesOrderDetailSewingProcess.setProcessId(dto.getSewProcessList().get(i).getProcessId());
//                    mesOrderDetailSewingProcess.setSort(dto.getSewProcessList().get(i).getSort());
//                    dao.save(mesOrderDetailSewingProcess);
//                }
//            }
//        }else{
//            dao.deleteAllEntity(list);
//            if(dto.getSewProcessList()!=null && dto.getSewProcessList().size()>0){
//                for (int i = 0; i < dto.getSewProcessList().size(); i++) {
//                    MesOrderDetailSewingProcess mesOrderDetailSewingProcess = new MesOrderDetailSewingProcess();
//                    mesOrderDetailSewingProcess.setOrderDetailId(dto.getOrderDetailId());
//                    mesOrderDetailSewingProcess.setProcessPositionId(dto.getSewProcessList().get(i).getProcessPositionId());
//                    mesOrderDetailSewingProcess.setProcessId(dto.getSewProcessList().get(i).getProcessId());
//                    mesOrderDetailSewingProcess.setSort(dto.getSewProcessList().get(i).getSort());
//                    dao.save(mesOrderDetailSewingProcess);
//                }
//            }
//        }
        criteria = dao.createCriteria(MesOrderDetailSewingProcess.class);
        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
        list = criteria.list();
        return new Result(list) ;
    }

    /**
     * 取消分配工序
     * @param id
     * @return
     */
    @Override
    public Result unAssignSewingProcess(String id) {
        MesOrderDetailSewingProcess mesOrderDetailSewingProcess = dao.findById(MesOrderDetailSewingProcess.class, id);
        if (mesOrderDetailSewingProcess==null){
            return new Result("100","主键不存在");
        }
        dao.delete(mesOrderDetailSewingProcess);
        return new Result("200","删除成功！");
    }

    /**
     * 获取分配工序通过订单明细id
     * @param orderDetailId
     * @return
     */
    @Override
    public Result getAssignSewingProcessByOrderDetailId(String orderDetailId) {
        List<MesOrderDetailSewingProcess> list = dao.findListByProperty(MesOrderDetailSewingProcess.class,"orderDetailId",orderDetailId);
        return new Result(list);
    }


    /**
     * 分配物料
     * @param dto
     * @return
     */
    @Override
    public Result assignMaterial(MesOrderDetailMaterialDto dto){

        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetailId());

        if("2".equals(orderDetail.getClosedStatus())||"3".equals(orderDetail.getClosedStatus())){
            return new Result("500","此订单明细已经结案！");
        }

        List<MesOrderDetailSize> mesOrderDetailSizeList = dao.findListByProperty(MesOrderDetailSize.class,"orderDetailId",dto.getOrderDetailId());
        if (mesOrderDetailSizeList.size()<1){
            return new Result("500","请先保存尺码");
        }

        for (MesOrderDetailMaterial obj : dto.getPositionList()) {
            MesOrderDetailMaterial entity = new MesOrderDetailMaterial();
            if (StringUtils.isBlank(obj.getId())){
                entity.setOrderDetailId(dto.getOrderDetailId());
                entity.setMaterialPositionId(obj.getMaterialPositionId());
                entity.setMaterialId(obj.getMaterialId());
                entity.setCode(obj.getCode());
                entity.setName(obj.getName());
                entity.setInventoryUnit(obj.getInventoryUnit());
                entity.setMaterialCategoryId(obj.getMaterialCategoryId());
                entity.setSpec(obj.getSpec());
                entity.setQuality(obj.getQuality());
                entity.setNum(obj.getNum());
                entity.setColor(obj.getColor());
                entity.setColorCode(obj.getColorCode());
                entity.setMaterialName(obj.getMaterialName());
                dao.save(entity);
            }else{

                entity = dao.findById(MesOrderDetailMaterial.class,obj.getId());
                if(entity !=null){
                    entity.setOrderDetailId(dto.getOrderDetailId());
                    entity.setMaterialPositionId(obj.getMaterialPositionId());
                    entity.setMaterialId(obj.getMaterialId());
                    entity.setCode(obj.getCode());
                    entity.setName(obj.getName());
                    entity.setInventoryUnit(obj.getInventoryUnit());
                    entity.setMaterialCategoryId(obj.getMaterialCategoryId());
                    entity.setSpec(obj.getSpec());
                    entity.setQuality(obj.getQuality());
                    entity.setNum(obj.getNum());
                    entity.setColor(obj.getColor());
                    entity.setColorCode(obj.getColorCode());
                    entity.setMaterialName(obj.getMaterialName());
                    dao.update(entity);
                }
            }
        }


        Criteria criteria = dao.createCriteria(MesOrderDetailMaterial.class);
        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
        @SuppressWarnings("unchecked")
        List<MesOrderDetailMaterial> list = criteria.list();
        return new Result(list) ;

//        Criteria criteria=dao.createCriteria(MesOrderDetailMaterial.class);
//        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()));
//        @SuppressWarnings("unchecked")
//        List<MesOrderDetailMaterial> list = criteria.list();
//        if(list!=null && list.size()>0){
//            for (int i = 0; i < dto.getPositionList().size(); i++) {
//                MesOrderDetailMaterial mesOrderDetailMaterial = new MesOrderDetailMaterial();
//                mesOrderDetailMaterial.setOrderDetailId(dto.getOrderDetailId());
//                mesOrderDetailMaterial.setMaterialPositionId(dto.getOrderDetailId());
//                mesOrderDetailMaterial.setCode(dto.getPositionList().get(i).getCode());
//                mesOrderDetailMaterial.setName(dto.getPositionList().get(i).getName());
//                mesOrderDetailMaterial.setInventoryUnit(dto.getPositionList().get(i).getInventoryUnit());
//                mesOrderDetailMaterial.setMaterialCategoryId(dto.getPositionList().get(i).getMaterialCategoryId());
//                mesOrderDetailMaterial.setSpec(dto.getPositionList().get(i).getSpec());
//                mesOrderDetailMaterial.setQuality(dto.getPositionList().get(i).getQuality());
//                mesOrderDetailMaterial.setNum(dto.getPositionList().get(i).getNum());
//                mesOrderDetailMaterial.setColor(dto.getPositionList().get(i).getColor());
//                mesOrderDetailMaterial.setColorCode(dto.getPositionList().get(i).getColorCode());
//                dao.save(mesOrderDetailMaterial);
//            }
//        }else{
//            for (int k = 0; k < list.size(); k++) {
//                dao.delete(list.get(k));
//            }
//            for (int i = 0; i < dto.getPositionList().size(); i++) {
//                MesOrderDetailMaterial mesOrderDetailMaterial = new MesOrderDetailMaterial();
//                mesOrderDetailMaterial.setOrderDetailId(dto.getOrderDetailId());
//                mesOrderDetailMaterial.setMaterialPositionId(dto.getOrderDetailId());
//                mesOrderDetailMaterial.setCode(dto.getPositionList().get(i).getCode());
//                mesOrderDetailMaterial.setName(dto.getPositionList().get(i).getName());
//                mesOrderDetailMaterial.setInventoryUnit(dto.getPositionList().get(i).getInventoryUnit());
//                mesOrderDetailMaterial.setMaterialCategoryId(dto.getPositionList().get(i).getMaterialCategoryId());
//                mesOrderDetailMaterial.setSpec(dto.getPositionList().get(i).getSpec());
//                mesOrderDetailMaterial.setQuality(dto.getPositionList().get(i).getQuality());
//                mesOrderDetailMaterial.setNum(dto.getPositionList().get(i).getNum());
//                mesOrderDetailMaterial.setColor(dto.getPositionList().get(i).getColor());
//                mesOrderDetailMaterial.setColorCode(dto.getPositionList().get(i).getColorCode());
//                dao.save(mesOrderDetailMaterial);
//            }
//        }
//        return new Result(list);
    }

    /**
     * 取消分配物料
     * @param id
     * @return
     */
    @Override
    public Result unAssignMaterial(String id){
        if (StringUtils.isBlank(id)){
            return new Result("100","主键不能为空");
        }
        MesOrderDetailMaterial mesOrderDetailMaterial = dao.findById(MesOrderDetailMaterial.class, id);
        if (mesOrderDetailMaterial==null){
            return new Result("100","主键id不存在");
        }
        dao.delete(mesOrderDetailMaterial);
        return new Result("200","删除成功");
    }

    /**
     * 获取分配工序通过订单明细id
     * @param orderDetailId
     * @return
     */
    @Override
    public Result getAssignMaterialByOrderDetailId(String orderDetailId) {

        Criteria criteria = dao.createCriteria(MesOrderDetailMaterial.class);
        criteria.add(Restrictions.eq("orderDetailId", orderDetailId));
        criteria.createAlias("materialPosition","materialPosition");
        criteria.addOrder(Order.asc("materialPosition.code"));
        @SuppressWarnings("unchecked")
        List<MesOrderDetailMaterial> list = criteria.list();
        return new Result(list) ;
    }

    /**
     * 技术完成
     * @param id
     * @param userId
     * @return
     */
    @Override
    public Result technicalFinished(String id,String userId) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(userId)){
            return new Result("500","id或版型师不能为空！");
        }
        MesOrderDetailAssign mesOrderDetailAssign = dao.findById(MesOrderDetailAssign.class,id);
        mesOrderDetailAssign.setTechnicalFinishedTime(new Date());
        mesOrderDetailAssign.setTechnicalFinishedUserId(userId);
        OrderDetail orderDetail = dao.findById(OrderDetail.class,mesOrderDetailAssign.getOrderDetailId());
        if("2".equals(orderDetail.getClosedStatus())||"3".equals(orderDetail.getClosedStatus())){
            return new Result("500","此订单明细已经结案！");
        }
        if (!orderDetail.getOrderDetailStatus().equals(BillStatus.OrderDetailStatus.ASSIGNED.getValue())){
            return new Result("500","只有已分配状态才能技术完成！");
        }
        orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue());
        dao.update(mesOrderDetailAssign);
        dao.update(orderDetail);
        return new Result(mesOrderDetailAssign);
    }

    /**
     * 取消技术完成
     * @param id
     * @return
     */
    @Override
    public Result unTechnicalFinished(String id) {

        if (StringUtils.isBlank(id)){
            return new Result("500","Id不能为空");
        }
        MesOrderDetailAssign mesOrderDetailAssign = dao.findById(MesOrderDetailAssign.class,id);
        mesOrderDetailAssign.setTechnicalFinishedTime(null);
        mesOrderDetailAssign.setTechnicalFinishedUserId(null);
        OrderDetail orderDetail = dao.findById(OrderDetail.class,mesOrderDetailAssign.getOrderDetailId());
        if (!orderDetail.getOrderDetailStatus().equals(BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue())){
            return new Result("500","只有技术完成状态才能取消！");
        }
        orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.ASSIGNED.getValue());
        dao.update(mesOrderDetailAssign);
        dao.update(orderDetail);
        return new Result(mesOrderDetailAssign);
    }

    @Override
    public Result saveMesOrderDetailPrintLog(List<MesOrderDetailPrintLogDto> dtos) {

        String goodsDetailIdTemp ="",sizeIdTemp="";
        if (dtos.size() > 0){
            Criteria criteria = dao.createCriteria(MesOrderDetailPrintLog.class);
            criteria.add(Restrictions.eq("createUserId",dtos.get(0).getCreateUserId()));
            List<MesOrderDetailPrintLog> list = criteria.list();
            if (list.size()>0){
               OrderDetail orderDetail = dao.findById(OrderDetail.class,list.get(0).getOrderDetailId());
               //默认取第一条明细的商品ID
                goodsDetailIdTemp = orderDetail.getGoodsDetailId();
               List<MesOrderDetailSize> mesOrderDetailSizeList =  dao.findListByProperty(MesOrderDetailSize.class,"orderDetailId",list.get(0).getOrderDetailId());
                //默认取第一条明细的尺码ID
               if (mesOrderDetailSizeList.size()>0){
                   sizeIdTemp = mesOrderDetailSizeList.get(0).getMesMeasureSizeId();
               } else {
                   sizeIdTemp="";
               }
            }
        }
        for (MesOrderDetailPrintLogDto detailPrintLogDto :dtos){
            String orderDetailId = detailPrintLogDto.getOrderDetailId();
            String createUserId = detailPrintLogDto.getCreateUserId();
            OrderDetail orderDetail = dao.findById(OrderDetail.class,orderDetailId);
            List<MesOrderDetailSize> mesOrderDetailSizeList = dao.findListByProperty(MesOrderDetailSize.class,"orderDetailId",orderDetailId);
            String sizeId="",goodsDetailId="";
            goodsDetailId = orderDetail.getGoodsDetailId();
            if (mesOrderDetailSizeList.size()>0){
                sizeId = mesOrderDetailSizeList.get(0).getMesMeasureSizeId();
            } else {
                sizeId="";
            }
            if (StringUtils.isNotBlank(goodsDetailIdTemp)){
                if(goodsDetailIdTemp.equals(goodsDetailId) && sizeIdTemp.equals(sizeId)){
                    Criteria criteria = dao.createCriteria(MesOrderDetailPrintLog.class);
                    criteria.add(Restrictions.eq("orderDetailId",orderDetailId));
                    criteria.add(Restrictions.eq("createUserId",createUserId));
                    List<MesOrderDetailPrintLog> list = criteria.list();
//                   List<MesOrderDetailPrintLog> list = dao.findListByProperty(MesOrderDetailPrintLog.class,"orderDetailId",orderDetailId);
                   if (null==list || list.size() <= 0){
                       MesOrderDetailPrintLog mesOrderDetailPrintLog = new MesOrderDetailPrintLog();
                       BeanUtils.copyProperties(detailPrintLogDto, mesOrderDetailPrintLog);
                       dao.save(mesOrderDetailPrintLog);
                   }
                }else {
                    return new Result("500","必须是同一个商品");
                }
            } else {
                Criteria criteria = dao.createCriteria(MesOrderDetailPrintLog.class);
                criteria.add(Restrictions.eq("orderDetailId",orderDetailId));
                criteria.add(Restrictions.eq("createUserId",createUserId));
                List<MesOrderDetailPrintLog> list = criteria.list();
//                List<MesOrderDetailPrintLog> list = dao.findListByProperty(MesOrderDetailPrintLog.class,"orderDetailId",orderDetailId);
                if (list.size() <= 0){
                    MesOrderDetailPrintLog mesOrderDetailPrintLog = new MesOrderDetailPrintLog();
                    BeanUtils.copyProperties(detailPrintLogDto, mesOrderDetailPrintLog);
                    dao.save(mesOrderDetailPrintLog);
                }
                goodsDetailIdTemp = orderDetail.getGoodsDetailId();
                sizeIdTemp = sizeId;
            }
        }
        return new Result();
    }

    @Override
    public Result getMesOrderDetailPrintLog(MesOrderDetailPrintLogDto dto) {
        List<MesOrderDetailModel> list = new ArrayList<>();
        String sql=" select modpl.c_id,tor.c_order_code,tg.c_code goods_code,tg.c_name goods_name,tgd.c_color_name goods_color_name,tor.c_embroid_name,tor.c_customer_name," +
                "  tor.c_shop_name,tod.c_order_detail_status,tor.c_order_character,mods.c_mes_measure_size_name,moda.c_technician_name,moda.c_technical_finished_time," +
                " modpl.c_order_detail_id " +
                " from mes_order_detail_print_log modpl " +
                " left join t_order_detail tod on modpl.c_order_detail_id = tod.c_id " +
                " LEFT JOIN t_goods_detail tgd ON tod.c_goods_detail_id = tgd.c_id" +
                " LEFT JOIN t_goods tg ON tgd.c_goods_id = tg.c_id " +
                " left join t_order tor on tod.c_order_id = tor.c_id " +
                " left join mes_order_detail_size mods on modpl.c_order_detail_id = mods.c_order_detail_id" +
                " left join mes_order_detail_assign moda on modpl.c_order_detail_id = moda.c_order_detail_id AND moda.c_delete_flag=0 " +
                " where modpl.c_create_user_id='"+dto.getCreateUserId()+"'";
        List resultSet = dao.queryBySql(sql);
        for(Object result : resultSet){
            MesOrderDetailModel model = new MesOrderDetailModel();//构建返回数据模型
            Object[] properties = (Object[])result;
            model.setId(properties[0]==null ? "" : properties[0].toString());
            model.setOrderCode(properties[1]==null ? "" : properties[1].toString());
            model.setGoodsCode(properties[2]==null ? "" : properties[2].toString());
            model.setGoodsName(properties[3]==null ? "" : properties[3].toString());
            model.setGoodsColor(properties[4]==null ? "" : properties[4].toString());
            model.setEmbroidName(properties[5]==null ? "" : properties[5].toString()); //绣字名
            model.setCustomerName(properties[6]==null ? "" : properties[6].toString());
            model.setShopName(properties[7]==null ? "" : properties[7].toString());
            model.setOrderDetailStatus(properties[8]==null || properties[8].equals("") ? "未分配" : BillStatus.orderDetailStatus(properties[8].toString()));
            model.setCharacteres(properties[9]==null ? "" : properties[9].toString());    //订单性质
            model.setMesSizeName(properties[10]==null ? "" : properties[10].toString()); //分配尺码
            model.setTechnicianName(properties[11]==null ? "" : properties[11].toString());
            model.setTechnicalFinishedTime(properties[12]==null ? "" : DateUtil.formatDate((Date)properties[12],"yyyy-MM-dd HH:mm:ss"));//技术完成日期
            model.setOrderDetailId(properties[13]==null ? "" : properties[13].toString());
            list.add(model);
        }
        return new Result(list);
    }

    @Override
    public Result delAllMesOrderDetailPrintLogByCreateUserId(String createUserId) {
        List<MesOrderDetailPrintLog> list = dao.findListByProperty(MesOrderDetailPrintLog.class,"createUserId",createUserId);
        dao.deleteAllEntity(list);
        return new Result();
    }

    @Override
    public Result delMesOrderDetailPrintLogById(String id) {
        dao.delete(dao.findById(MesOrderDetailPrintLog.class,id));
        return new Result();
    }


    /**
     * 保存校验
     * @param dto
     * @return
     */
    private String saveValidation(MesOrderDetailAssignDto dto){

        Criteria criteria = dao.createCriteria(MesOrderDetailAssign.class);
        criteria.add(Restrictions.eq("orderDetailId", dto.getOrderDetailId()))
                .add(Restrictions.eq("deleteFlag",0));
        long l = dao.findTotalWithCriteria(criteria);
        if(l > 0){
            return "已经存在，不能重复分配！";
        }
        if (StringUtils.isBlank(dto.getId())){



        }
        else {
            MesOrderDetailAssign mesOrderDetailAssign = dao.findById(MesOrderDetailAssign.class,dto.getId());
            if (mesOrderDetailAssign==null){
                return "该订单还没有分配！";
            }
        }

        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetailId());
        if (orderDetail==null){
            return "订单明细不存在！";
        }else {
            if (StringUtils.isNotBlank(orderDetail.getOrderDetailStatus())&&!BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(orderDetail.getOrderDetailStatus())) {
                return "此订单明细状态不能分配";
            }
            if("2".equals(orderDetail.getClosedStatus())||"3".equals(orderDetail.getClosedStatus())){
                return "此订单明细已经结案";
            }
        }
        return null;
    }

    @Override
	public List<MesOrderDetailMaterial> getListByOrderDateilIds(String[] orderDetailIds) {
		List<String> list = new ArrayList<>();
		for (String string : orderDetailIds) {
			string = "'"+string+"'";
			list.add(string);
		}
		String[] newOrderDetailIds = new String[list.size()];
		String[] ids = list.toArray(newOrderDetailIds);
	
		List<MesOrderDetailMaterial> modelList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("c_id,");
		sql.append("c_num,");
		sql.append("c_spec,");
		sql.append("c_name,");
		sql.append("c_code,");
		sql.append("c_color,");
		sql.append("c_quality,");
		sql.append("c_color_code,");
		sql.append("c_material_id,");
		sql.append("c_material_name,");
		sql.append("c_inventory_unit,");
		sql.append("c_order_detail_id,");
		sql.append("c_material_position_id,");
		sql.append("c_material_category_id ");
		sql.append("FROM ");
		sql.append("mes_order_detail_material ");
		sql.append("WHERE ");
		sql.append("c_order_detail_id ");
		sql.append("IN ");
		sql.append("(");
		sql.append(String.join(",", ids));
		sql.append(") ");
		
		List queryBySql = dao.queryBySql(sql.toString());
		for (Object object : queryBySql) {//这里强转不了,构建返回模型
			MesOrderDetailMaterial model = new MesOrderDetailMaterial();
			Object[] properties = (Object[])object;
			model.setId(properties[0] ==null ? "" : properties[0].toString());
			model.setNum(properties[1] == null? "" : properties[1].toString());
			model.setSpec(properties[2] == null ? "" : properties[2].toString());
			model.setName(properties[3] ==null ? "" : properties[3].toString());
			model.setCode(properties[4] == null ? "": properties[4].toString());
			model.setColor(properties[5] == null ? "" : properties[5].toString());
			model.setQuality(properties[6] == null ? "" : properties[6].toString());
			model.setColorCode(properties[7] == null ? "" : properties[7].toString());
			model.setMaterialId(properties[8] == null ? "" : properties[8].toString());
			model.setMaterialName(properties[9] == null ? "" : properties[9].toString());
			model.setInventoryUnit(properties[10] == null ? "" : properties[10].toString());
			model.setOrderDetailId(properties[11] == null ? "" : properties[11].toString());
			model.setMaterialPositionId(properties[12] == null ? "" : properties[12].toString());
			model.setMaterialCategoryId(properties[13] == null ? "" : properties[13].toString());
			
			modelList.add(model);
		}
		
		return modelList;
	}

}

