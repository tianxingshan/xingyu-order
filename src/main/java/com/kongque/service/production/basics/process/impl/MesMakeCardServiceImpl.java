package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.dto.MesMakeCardDto;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.process.MesMakeCard;
import com.kongque.model.MesMakeCardModel;
import com.kongque.service.production.basics.process.IMesMakeCardService;
import com.kongque.util.CodeUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @program: xingyu-order
 * @description: 制卡
 * @author: zhuxl
 * @create: 2019-07-26 10:19
 **/
@Service
public class MesMakeCardServiceImpl implements IMesMakeCardService {

    @Resource
    private IDaoService dao;


    @Override
    public Result make(MesMakeCard dto) {
        MesMakeCard entity = new MesMakeCard();
        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetailId());
        if(orderDetail==null){
            return new Result("500","订单明细不存在!");
        }else{
            String status = orderDetail.getOrderDetailStatus();
            if(StringUtils.isNotBlank(status)&&status.equals("1")){
            }else {
                return new  Result("500","只有生产中的订单才可以制卡!");
            }
        }
        List<MesMakeCard> list = dao.findListByProperty(MesMakeCard.class,"orderDetailId",dto.getOrderDetailId());
        if(list.size()> 0){
            entity = list.get(0);
            entity.setCreater(dto.getCreater());
            dao.update(entity);
        }else {
            BeanUtils.copyProperties(dto,entity);
            entity.setCreateTime(new Date());
            entity.setCardCode(CodeUtil.createCardNo("3",7));
            entity.setStatus("1");
            dao.save(entity);
        }
        return new Result(entity);
    }


    @Override
    public Result clear(MesMakeCard dto) {
        if(StringUtils.isBlank(dto.getId())){
            return new Result("500","ID不能为空!");
        }
        MesMakeCard entity = dao.findById(MesMakeCard.class,dto.getId());
        if(entity==null){
            return new Result("500","不存在制卡!");
        }
        entity.setStatus("2");
        if(StringUtils.isNotBlank(dto.getCreater())) {
            entity.setCreater(dto.getCreater());
        }
        dao.update(entity);
        return new Result(entity);
    }

    @Override
    public Pagination<MesMakeCardModel> list(MesMakeCardDto dto, PageBean pageBean) {
        Pagination<MesMakeCardModel> pagination = new Pagination<>();
        List<MesMakeCardModel> list = new ArrayList<>();
        StringBuilder sqlSelect = new StringBuilder("SELECT mop.c_plan_number,mop.c_send_time,tod.c_order_detail_status,tor.c_order_code,txc.c_customer_name,tor.c_embroid_name,td.c_dept_name,tod.c_goods_sn,tg.c_code AS goods_code" +
                ",tg.c_name AS goods_name,tgd.c_color_name,mods.c_mes_measure_size_name,moda.c_technician_name,IFNULL(mmc.c_status,'0') AS card_status,mmc.c_card_code ,mmc.c_create_time AS make_card_time,tod.c_id order_detail_id,mmc.c_id make_card_id " );
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(1) ");
        StringBuilder sqlFrom  = new StringBuilder("FROM mes_order_plan mop " +
                " JOIN mes_order_plan_detail mopd ON mop.c_id = mopd.c_order_plan_id AND mopd.c_delete_flag=0" +
                " JOIN t_order_detail tod ON mopd.c_order_detail_id = tod.c_id " +
                " JOIN t_order tor ON tod.c_order_id = tor.c_id AND tor.c_delete_flag=0" +
                " JOIN t_xiuyu_customer txc ON tor.c_customer_id = txc.c_id" +
                " JOIN t_dept td ON tor.c_shop_id = td.c_id" +
                " JOIN t_goods_detail tgd ON tod.c_goods_detail_id = tgd.c_id" +
                " JOIN t_goods tg ON tgd.c_goods_id = tg.c_id" +
                " LEFT JOIN mes_order_detail_size mods ON mopd.c_order_detail_id = mods.c_order_detail_id" +
                " LEFT JOIN mes_order_detail_assign moda ON mopd.c_order_detail_id = moda.c_order_detail_id AND moda.c_delete_flag='0'" +
                " LEFT JOIN mes_make_card mmc ON mopd.c_order_detail_id = mmc.c_order_detail_id" +
                " WHERE mop.c_delete_flag=0 ");
        StringBuilder sqlWhere = new StringBuilder();
        StringBuilder sqlLimit = new StringBuilder();
        /**
         * 卡号
         */
        if(StringUtils.isNotBlank(dto.getCardCode())){
            sqlWhere.append(" AND mmc.c_card_code LIKE '%").append(dto.getCardCode()).append("%'");
        }
        /**
         * 商品唯一码
         */
        if(StringUtils.isNotBlank(dto.getGoodSN())){
            sqlWhere.append(" AND tod.c_goods_sn LIKE '%").append(dto.getGoodSN()).append("%'");
        }
        /**
         * 计划单号
         */
        if(StringUtils.isNotBlank(dto.getOrderPlanCode())){
            sqlWhere.append(" AND mop.c_plan_number LIKE '%").append(dto.getOrderPlanCode()).append("%'");
        }

        /**
         * 计划单状态
         */
        if(StringUtils.isNotBlank(dto.getOrderPlanStatus())){
            sqlWhere.append(" AND mop.c_plan_status='").append(dto.getOrderPlanStatus()).append("'");
        }
        /**
         * 单号
         */
        if(StringUtils.isNotBlank(dto.getOrderCode())){
            sqlWhere.append(" AND tor.c_order_code LIKE '%").append(dto.getOrderCode()).append("%'");
        }
        /**
         * 顾客姓名
         */
        if (StringUtils.isNotBlank(dto.getCustomerName())){
            sqlWhere.append(" AND txc.c_customer_name LIKE '%").append(dto.getCustomerName()).append("%'");
        }
        /**
         * 款式id
         */
        if (StringUtils.isNotBlank(dto.getGoodId())){
            sqlWhere.append(" AND tg.c_id ='").append(dto.getGoodId()).append("'");
        }
        /**
         * 款号
         */
        if (StringUtils.isNotBlank(dto.getGoodCode())){
            sqlWhere.append(" AND tg.c_code LIKE '%").append(dto.getGoodCode()).append("%'");
        }
        /**
         * 款名
         */
        if (StringUtils.isNotBlank(dto.getGoodName())){
            sqlWhere.append(" AND tg.c_id LIKE '%").append(dto.getGoodName()).append("%'");
        }
        /**
         * 版型师
         */
        if(StringUtils.isNotBlank(dto.getTechnicianName())){
            sqlWhere.append(" AND moda.c_technician_name LIKE '%").append(dto.getTechnicianName()).append("%'");
        }
        /**
         * 制卡状态
         */
        if(StringUtils.isNotBlank(dto.getCardStatus())){
            sqlWhere.append(" AND IFNULL(mmc.c_status,'0')='").append(dto.getCardStatus()).append("'");
        }

        if(pageBean.getPage()!=null && pageBean.getRows()!=null){
            sqlLimit.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
        }

        List resultSet = dao.queryBySql(sqlSelect.append(sqlFrom).append(sqlWhere).append(sqlLimit).toString());
        for(Object result : resultSet){
            MesMakeCardModel model = new MesMakeCardModel();//构建返回数据模型
            Object[] properties = (Object[])result;
            model.setOrderPlanCode(properties[0]==null ? "" : properties[0].toString());
            model.setPlanSendTime(properties[1]==null ? "" : properties[1].toString());
            model.setOrderDetailStatus(properties[2]==null ? "" : properties[2].toString());
            model.setOrderCode(properties[3]==null ? "" : properties[3].toString());
            model.setCustomerName(properties[4]==null ? "" : properties[4].toString());
            model.setEmbroidName(properties[5]==null ? "" : properties[5].toString());
            model.setShopName(properties[6]==null ? "" : properties[6].toString());
            model.setGoodSN(properties[7]==null ? "" : properties[7].toString());
            model.setGoodCode(properties[8]==null ? "" : properties[8].toString());
            model.setGoodName(properties[9]==null ? "" : properties[9].toString());
            model.setGoodColorName(properties[10]==null ? "" : properties[10].toString());
            model.setGoodSize(properties[11]==null ? "" : properties[11].toString());
            model.setTechnicianName(properties[12]==null ? "" : properties[12].toString());
            model.setCardStatus(properties[13]==null ? "" : properties[13].toString());
            model.setCardCode(properties[14]==null ? "" : properties[14].toString());
            model.setCreateTime(properties[15]==null ? "" :properties[15].toString());
            model.setOrderDetailId(properties[16]==null ? "" :properties[16].toString());
            model.setId(properties[17]==null ? "" :properties[17].toString());
            list.add(model);
        }

        List<BigInteger> result = dao.queryBySql(sqlCount.append(sqlFrom).append(sqlWhere).toString());
        Long count = result == null || result.isEmpty() ? 0L : result.get(0).longValue();
        pagination.setRows(list);
        pagination.setTotal(count);
        return pagination;
    }
}

