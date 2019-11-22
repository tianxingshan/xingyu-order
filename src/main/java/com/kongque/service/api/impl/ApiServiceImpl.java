package com.kongque.service.api.impl;

import com.kongque.controller.api.ApiController;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderRepairDto;
import com.kongque.dto.YunOrderDetailDto;
import com.kongque.dto.YunOrderDto;
import com.kongque.entity.basics.Category;
import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.basics.XiuyuCustomer;
import com.kongque.entity.goods.Goods;
import com.kongque.entity.goods.GoodsDetail;
import com.kongque.entity.order.*;
import com.kongque.entity.repair.OrderRepair;
import com.kongque.entity.repair.OrderRepairAttachment;
import com.kongque.entity.user.User;
import com.kongque.service.api.IApiService;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @program: xingyu-order
 * @description: 外部接口
 * @author: zhuxl
 * @create: 2018-11-15 10:35
 **/
@Service
@Transactional
public class ApiServiceImpl implements IApiService{
    @Resource
    private IDaoService dao;

    private final static Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);

    @Override
    public Result getOrderPlanMaterialsByOrderPlanNo(String orderPlanNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select  modm.c_material_id,sum(modm.c_num) c_num");
        sb.append(" from mes_order_detail_material modm ");
        sb.append(" join mes_order_plan_detail mopd on modm.c_order_detail_id = mopd.c_order_detail_id");
        sb.append(" join mes_order_plan mop on mopd.c_order_plan_id = mop.c_id");
        sb.append(" where modm.c_material_id is not null and mop.c_plan_number='").append(orderPlanNo).append("'");
        sb.append(" group by modm.c_material_id");
        List resultSet =dao.queryBySql(sb.toString());
        List<Map<String, Object>> lists = new ArrayList<>();
        for (Object object : resultSet) {
            Object[] properties = (Object[])object;
            Map<String, Object> map = new HashMap<>();
            map.put("materialId", properties[0]==null?"":properties[0]);
            map.put("materialNum", properties[1]==null?"":properties[1]);
            lists.add(map);
        }
        return new Result(lists);
    }

    @Override
    public Result getRepairPlanMaterialsByRepairPlanNo(String repairPlanNo, String supplementNo) {
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(repairPlanNo)) {
            sb.append("select c_plan_code,'' c_supplement_sn,c_materiel_id,sum(c_num) num ");
            sb.append(" from (");
            sb.append(" select mrpp.c_plan_code, mrppm.c_materiel_id,mrppm.c_num ");
            sb.append(" from mes_repair_production_plan mrpp");
            sb.append(" join mes_repair_production_plan_detail mrppd on mrpp.c_id = mrppd.c_repair_plan_id");
            sb.append(" join mes_repair_production_plan_materiel mrppm on mrppd.c_order_repair_id = mrppm.c_order_repair_id");
            sb.append(" where  mrpp.c_plan_code='").append(repairPlanNo).append("'");
            sb.append(" union all ");
            sb.append(" select mrpp.c_plan_code,mrppsm.c_material_id,mrppsm.c_quantity ");
            sb.append(" from mes_repair_production_plan mrpp ");
            sb.append(" join mes_repair_production_plan_supplement mrpps on mrpp.c_id = mrpps.c_repairPlan_id");
            sb.append(" join mes_repair_production_plan_supplement_materiel mrppsm on mrpps.c_id = mrppsm.c_supplement_id");
            sb.append(" where mrpp.c_plan_code='").append(repairPlanNo).append("'");
            sb.append(") tb");
            sb.append(" group by c_plan_code,c_materiel_id");

        }
        else{
            sb.append(" select mrpp.c_plan_code,mrpps.c_supplement_sn,mrppsm.c_material_id,sum(mrppsm.c_quantity) num");
            sb.append(" from mes_repair_production_plan mrpp");
            sb.append(" join mes_repair_production_plan_supplement mrpps on mrpp.c_id = mrpps.c_repairPlan_id");
            sb.append(" join mes_repair_production_plan_supplement_materiel mrppsm on mrpps.c_id = mrppsm.c_supplement_id");
            sb.append(" where mrpps.c_supplement_sn='").append(supplementNo).append("'");
            sb.append(" group by mrpp.c_plan_code,mrpps.c_supplement_sn,mrppsm.c_material_id");
        }

        List resultSet =dao.queryBySql(sb.toString());
        List<Map<String, Object>> lists = new ArrayList<>();
        for (Object object : resultSet) {
            Object[] properties = (Object[])object;
            Map<String, Object> map = new HashMap<>();
            map.put("repairPlanNo", properties[0]==null?"":properties[0]);
            map.put("supplementSn", properties[1]==null?"":properties[1]);
            map.put("materialId", properties[2]==null?"":properties[2]);
            map.put("materialNum", properties[3]==null?"":properties[3]);
            lists.add(map);
        }
        return new Result(lists);
    }

    public double getRepairPlanMaterialQuantityByRepairPlanNoAndMaterialId(String repairPlanNo, String supplementNo,String materialId){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(repairPlanNo)) {
            sb.append("select sum(c_num) num ");
            sb.append(" from (");
            sb.append(" select  mrppm.c_num ");
            sb.append(" from mes_repair_production_plan mrpp");
            sb.append(" join mes_repair_production_plan_detail mrppd on mrpp.c_id = mrppd.c_repair_plan_id");
            sb.append(" join mes_repair_production_plan_materiel mrppm on mrppd.c_order_repair_id = mrppm.c_order_repair_id");
            sb.append(" where  mrpp.c_plan_code='").append(repairPlanNo).append("'");
            sb.append(" and mrppm.c_materiel_id='").append(materialId).append("'");
            sb.append(" union all ");
            sb.append(" select mrppsm.c_quantity ");
            sb.append(" from mes_repair_production_plan mrpp ");
            sb.append(" join mes_repair_production_plan_supplement mrpps on mrpp.c_id = mrpps.c_repairPlan_id");
            sb.append(" join mes_repair_production_plan_supplement_materiel mrppsm on mrpps.c_id = mrppsm.c_supplement_id");
            sb.append(" where mrpp.c_plan_code='").append(repairPlanNo).append("'");
            sb.append(" and mrppsm.c_material_id='").append(materialId).append("'");
            sb.append(") tb");

        }
        else{
            sb.append(" select sum(mrppsm.c_quantity) num");
            sb.append(" from mes_repair_production_plan mrpp");
            sb.append(" join mes_repair_production_plan_supplement mrpps on mrpp.c_id = mrpps.c_repairPlan_id");
            sb.append(" join mes_repair_production_plan_supplement_materiel mrppsm on mrpps.c_id = mrppsm.c_supplement_id");
            sb.append(" where mrpps.c_supplement_sn='").append(supplementNo).append("'");
            sb.append(" and mrppsm.c_material_id='").append(materialId).append("'");
        }
        Object obj = dao.uniqueBySql(sb.toString());
        if(obj !=null){
            return Double.parseDouble(obj.toString());
        }
        return 0;
    }

    public double getOrderPlanMaterialQuantityByOrderPlanNoAndMaterialId(String orderPlanNo,String materialId){
        StringBuffer sb = new StringBuffer();
        sb.append("select sum(modm.c_num) c_num");
        sb.append(" from mes_order_detail_material modm ");
        sb.append(" join mes_order_plan_detail mopd on modm.c_order_detail_id = mopd.c_order_detail_id");
        sb.append(" join mes_order_plan mop on mopd.c_order_plan_id = mop.c_id");
        sb.append(" where  mop.c_plan_number='").append(orderPlanNo).append("'");
        sb.append(" and modm.c_material_id='").append(materialId).append("'");
        Object obj = dao.uniqueBySql(sb.toString());
        if(obj !=null){
            return Double.parseDouble(obj.toString());
        }
        return 0;
    }

    /**
     * 保存云平台订单
     * @param dto
     * @return
     */
    @Override
    public Result saveYunOrder(YunOrderDto dto){

        Result result = new Result();
        String sysId="yun-order",unit="件";
        Tenant tenant ;
        Dept dept;
        XiuyuCustomer customer;
        OrderDetail orderDetail;
        Category category;
        Goods goods;
        GoodsDetail goodsDetail = new GoodsDetail();
        BodyMeasure bodyMeasure;
        BodyLanguage bodyLanguage;
        Date date = new Date();
        if(StringUtils.isBlank(dto.getTenantName())){
            return new Result("500","商户名称不能为空");
        }
        if(StringUtils.isBlank(dto.getShopCode())||StringUtils.isBlank(dto.getShopName())){
            return new Result("500","店铺编码或名称不能为空");
        }
        if(StringUtils.isBlank(dto.getCustomerCode())||StringUtils.isBlank(dto.getCustomerName())){
            return new Result("500","会员编码或名称不能为空");
        }
        if(dto.getOrderDetail()==null || dto.getOrderDetail().size()==0){
            return new Result("500","订单明细不能为空");
        }
        if(StringUtils.isBlank(dto.getOrderCode())){
            return new Result("500","订单编号不能为空");
        }

        for (YunOrderDetailDto yunOrderDetailDto : dto.getOrderDetail()) {
            goods = dao.findUniqueByProperty(Goods.class, "code", yunOrderDetailDto.getGoodsCode());
            if(goods==null){
                return new Result("500","款号:"+yunOrderDetailDto.getGoodsCode()+" 不存在!");
            }
            Criteria criteria1 = dao.createCriteria(GoodsDetail.class);
            criteria1.add(Restrictions.eq("goodsId",goods.getId())).add(Restrictions.eq("colorName",yunOrderDetailDto.getGoodsColorName()));
            goodsDetail = (GoodsDetail)criteria1.uniqueResult();
            if(goodsDetail ==null){
                return new Result("500","款号:"+yunOrderDetailDto.getGoodsCode()+" 颜色:"+ yunOrderDetailDto.getGoodsColorName() +" 不存在!");
            }
        }



        try {
            Order order = dao.findUniqueByProperty(Order.class, "orderCode", dto.getOrderCode());
            if (order == null) {
                //商户
                tenant = dao.findUniqueByProperty(Tenant.class, "tenantName", dto.getTenantName());
                if (tenant == null) {
                    tenant = new Tenant();
                    tenant.setTenantName(dto.getTenantName());
                    tenant.setTenantDel("0");
                    tenant.setTenantStatus("0");
                    tenant.setSysId(sysId);
                    dao.save(tenant);
                }
                //店铺
                dept = dao.findUniqueByProperty(Dept.class, "deptCode", dto.getShopCode());
                if (dept == null) {
                    dept = new Dept();
                    dept.setDeptCode(dto.getShopCode());
                    dept.setDeptName(dto.getShopName());
                    dept.setDeptParentId("");
                    dept.setDeptType("1");
                    dept.setDeptTenantId(tenant);
                    dao.save(dept);
                }
                //会员
                customer = dao.findUniqueByProperty(XiuyuCustomer.class, "customerCode", dto.getCustomerCode());
                if (customer == null) {
                    customer = new XiuyuCustomer();
                    customer.setCustomerCode(dto.getCustomerCode());
                    customer.setCustomerName(dto.getCustomerName());
                    customer.setBirthday(dto.getCustomerBirthday());
                    customer.setHeight(dto.getCustomerHeight());
                    customer.setWeight(dto.getCustomerWeight());
                    customer.setProfessional(dto.getCustomerProfessional());
                    customer.setPhone(dto.getCustomerPhone());
                    customer.setCreateUser(dto.getCreateUser());
                    customer.setCreateTime(date);
                    customer.setDeleteFlag("0");
                    customer.setUpdateTime(date);
                    customer.setUpdateUser(dto.getCreateUser());
                    customer.setShopId(dept.getId());
                    dao.save(customer);
                }

                User user = dao.findUniqueByProperty(User.class,"actualName",dto.getCreateUser());

                if(user==null){
                    user = new User();
                    user.setActualName(dto.getCreateUser());
                    user.setUserCode(dto.getCreateUser());
                    user.setUserName(dto.getCreateUser());
                    user.setCreateTime(date);
                    user.setDefaultAuthorityFlag(0);
                    user.setStatus("0");
                    dao.save(user);
                }
                order = new Order();
                BeanUtils.copyProperties(dto, order);
                order.setDeleteFlag("0");
                order.setCustomerId(customer.getId());
                order.setReset("0");
                order.setShopId(dept.getId());
                order.setStatusBussiness("2");
                order.setOutFlag("1");
                order.setSysId(sysId);
                order.setTenantId(tenant.getId());
                order.setStatusBeforeProduce("0");
                order.setStatusBeforeSend("0");
                order.setCreateTime(date);
                order.setUpdateTime(date);
                order.setSubmitTime(date);
                order.setXiuyuChekTime(date);
                order.setCreateUserId(user.getId());
                order.setUpdateUserId(user.getId());
                dao.save(order);
                //量体信息
                bodyMeasure = new BodyMeasure();
                bodyMeasure.setOrderId(order.getId());
                bodyMeasure.setCustomerId(customer.getId());
                bodyMeasure.setOrderMeasure(dto.getOrderBodyMeasure());
                dao.save(bodyMeasure);
                //身体语言
                bodyLanguage = new BodyLanguage();
                bodyLanguage.setOrderId(order.getId());
                bodyLanguage.setCustomerId(customer.getId());
                bodyLanguage.setOrderLanguage(dto.getOrderBodyLanguage());
                dao.save(bodyLanguage);
                //订单附件
                for (OrderAttachment orderAttachment:dto.getOrderAttachment()) {
                     orderAttachment.setOrderId(order.getId());
                     dao.save(orderAttachment);
                }


                for (YunOrderDetailDto yunOrderDetailDto : dto.getOrderDetail()) {
//                    category = dao.findUniqueByProperty(Category.class, "name", yunOrderDetailDto.getGoodsCategoryName());
//                    if (category == null) {
//                        category = new Category();
//                        category.setName(yunOrderDetailDto.getGoodsCategoryName());
//                        category.setDel("0");
//                        dao.save(category);
//                        category.setCode(category.getId());
//                        dao.update(category);
//                    }
                    goods = dao.findUniqueByProperty(Goods.class, "code", yunOrderDetailDto.getGoodsCode());
//                    if (goods == null) {

//                        goods = new Goods();
//                        goods.setCategory(category);
//                        goods.setCode(yunOrderDetailDto.getGoodsCode());
//                        goods.setName(yunOrderDetailDto.getGoodsName());
//                        goods.setForOrder("1");
//                        goods.setStatus("0");
//                        goods.setCreateTime(date);
//                        goods.setCreateUser(dto.getCreateUser());
//                        dao.save(goods);
//                    }
                    Criteria criteria = dao.createCriteria(GoodsDetail.class);
                    criteria.add(Restrictions.eq("goodsId",goods.getId())).add(Restrictions.eq("colorName",yunOrderDetailDto.getGoodsColorName()));
                    goodsDetail = (GoodsDetail)criteria.uniqueResult();
//                    if(goodsDetail==null) {
//                        goodsDetail = new GoodsDetail();
//                        goodsDetail.setGoodsId(goods.getId());
//                        goodsDetail.setColorName(yunOrderDetailDto.getGoodsColorName());
//                        dao.save(goodsDetail);
//                    }

                    orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(yunOrderDetailDto, orderDetail);
                    orderDetail.setNum(1);
                    orderDetail.setOrderId(order.getId());
                    orderDetail.setUnit(unit);
                    orderDetail.setGoodsDetailId(goodsDetail.getId());
                    orderDetail.setOrderDetailStatus("6");
                    //orderDetail.setLiningName(yunOrderDetailDto.getLiningName());
                    dao.save(orderDetail);

                }
            }
        }catch (Exception ex){
            log.error("云平台订单异常",ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
            return new Result("500",ex.getMessage().toString());
        }
        return  result;
    }

    @Override
    public Result saveYunRepairOrder(OrderRepairDto dto){

        Result result = new Result();
        String sysId="yun-order",unit="件";
        Tenant tenant ;
        Dept dept;
        XiuyuCustomer customer;
        OrderDetail orderDetail;
        Goods goods;
        GoodsDetail goodsDetail = new GoodsDetail();
        BodyMeasure bodyMeasure;
        BodyLanguage bodyLanguage;
        Date date = new Date();
        if(StringUtils.isBlank(dto.getTenantName())){
            return new Result("500","商户名称不能为空");
        }
        if(StringUtils.isBlank(dto.getShopId())||StringUtils.isBlank(dto.getShopName())){
            return new Result("500","店铺编码或名称不能为空");
        }
        if(StringUtils.isBlank(dto.getCustomerCode())||StringUtils.isBlank(dto.getCustomerName())){
            return new Result("500","会员编码或名称不能为空");
        }
        if(StringUtils.isBlank(dto.getOrderRepairCode())){
            return new Result("500","微调单编号不能为空");
        }

        goods = dao.findUniqueByProperty(Goods.class, "code", dto.getGoodsCode());
        if(goods==null){
            return new Result("500","款号:"+dto.getGoodsCode()+" 不存在!");
        }
        Criteria criteria1 = dao.createCriteria(GoodsDetail.class);
        criteria1.add(Restrictions.eq("goodsId",goods.getId())).add(Restrictions.eq("colorName",dto.getGoodsColor()));
        goodsDetail = (GoodsDetail)criteria1.uniqueResult();
        if(goodsDetail ==null){
            return new Result("500","款号:"+dto.getGoodsCode()+" 颜色:"+ dto.getGoodsColor() +" 不存在!");
        }




        try {
            OrderRepair order = dao.findUniqueByProperty(OrderRepair.class, "orderRepairCode", dto.getOrderRepairCode());
            if (order == null) {
                //商户
                tenant = dao.findUniqueByProperty(Tenant.class, "tenantName", dto.getTenantName());
                if (tenant == null) {
                    tenant = new Tenant();
                    tenant.setTenantName(dto.getTenantName());
                    tenant.setTenantDel("0");
                    tenant.setTenantStatus("0");
                    tenant.setSysId(sysId);
                    dao.save(tenant);
                }
                //店铺
                dept = dao.findUniqueByProperty(Dept.class, "deptCode", dto.getShopId());
                if (dept == null) {
                    dept = new Dept();
                    dept.setDeptCode(dto.getShopId());
                    dept.setDeptName(dto.getShopName());
                    dept.setDeptParentId("");
                    dept.setDeptType("1");
                    dept.setDeptTenantId(tenant);
                    dao.save(dept);
                }
                //会员
                customer = dao.findUniqueByProperty(XiuyuCustomer.class, "customerCode", dto.getCustomerCode());
                if (customer == null) {
                    customer = new XiuyuCustomer();
                    customer.setCustomerCode(dto.getCustomerCode());
                    customer.setCustomerName(dto.getCustomerName());
                    customer.setBirthday(dto.getCustomerBirthday());
                    customer.setHeight(dto.getCustomerHeight());
                    customer.setWeight(dto.getCustomerWeight());
                    customer.setProfessional(dto.getCustomerProfessional());
                    customer.setPhone(dto.getCustomerPhone());
                    customer.setCreateUser("001");
                    customer.setCreateTime(date);
                    customer.setDeleteFlag("0");
                    customer.setUpdateTime(date);
                    customer.setUpdateUser("001");
                    customer.setShopId(dept.getId());
                    dao.save(customer);
                }

                order = new OrderRepair();
                BeanUtils.copyProperties(dto, order);
                order.setCustomerId(customer.getId());
                order.setShopId(dept.getId());
                order.setOrderRepairStatus("1");
                order.setDel("0");
                order.setOutFlag("1");
                order.setSysId(sysId);
                order.setTenantId(tenant.getId());
                order.setGoodsDetailId(goodsDetail.getId());
                order.setCreateTime(date);
                order.setUpdateTime(date);
                order.setCheckTime(date);
                dao.save(order);

                //订单附件
                for (OrderRepairAttachment orderAttachment:dto.getAttachments()) {
                    orderAttachment.setOrderRepairId(order.getId());
                    dao.save(orderAttachment);
                }

            }
        }catch (Exception ex){
            log.error("云平台售后订单异常",ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
            return new Result("500",ex.getMessage().toString());
        }
        return  result;
    }


}

