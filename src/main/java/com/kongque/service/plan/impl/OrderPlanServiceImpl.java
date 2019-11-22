package com.kongque.service.plan.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.kongque.entity.goods.Goods;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kongque.common.BillStatus;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.OrderPlanDetailDto;
import com.kongque.dto.OrderPlanDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.plan.OrderPlan;
import com.kongque.entity.plan.OrderPlanDetail;
import com.kongque.entity.user.User;
import com.kongque.service.plan.OrderPlanService;
import com.kongque.util.CodeUtil;
import com.kongque.util.DateUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

/**
 * @author xiaxt
 * @date 2018/10/16.
 */
@Service
@Transactional
public class OrderPlanServiceImpl implements OrderPlanService {

    @Resource
    private IDaoService dao;

    @Override
    public Result saveOrUpdateOrderPlan(OrderPlanDto dto) {
        Calendar calendar= Calendar.getInstance();
        Date date = calendar.getTime();
        //id不为空更新，否则新增
        if(StringUtils.isNotBlank(dto.getId())){
            OrderPlan orderPlan = dao.findById(OrderPlan.class,dto.getId());
            if (orderPlan==null || Constants.ENABLE_FLAG.DELETE.equals(orderPlan.getDeleteFlag().toString())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"您操作的计划单不存在或已被删除！");
            }
            //只有草稿和已驳回状态才允许编辑
            if (!Constants.PLAN_STATUS.草稿.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))
                    &&!Constants.PLAN_STATUS.待审核.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"计划单状态有误，只允许保存为草稿或待审核状态！");
            }
            if (!Constants.PLAN_STATUS.草稿.getValByCode().equals(orderPlan.getPlanStatus())
                    &&!Constants.PLAN_STATUS.已驳回.getValByCode().equals(orderPlan.getPlanStatus())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单的状态不允许编辑！");
            }
            orderPlan.setPlanStatus(Integer.valueOf(dto.getPlanStatus()));
            if (StringUtils.isNotBlank(dto.getProdFactory())){
                orderPlan.setProdFactory(dto.getProdFactory());
            }
            if (dto.getDeliveryTime()!=null){
                orderPlan.setDeliveryTime(dto.getDeliveryTime());
            }
            if (dto.getProdTime()!=null){
                orderPlan.setProdTime(dto.getProdTime());
            }

            //新增的数据插入数据库
            if (dto.getOrderPlanDetails()!=null&&dto.getOrderPlanDetails().size()>0) {
                for (OrderPlanDetailDto orderPlanDetail : dto.getOrderPlanDetails()) {
                    OrderDetail orderDetail = dao.findById(OrderDetail.class, orderPlanDetail.getOrderDetail());
                    if (orderDetail != null && BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue().equals(orderDetail.getOrderDetailStatus())) {
                        //判断订单是否结案
                        if(isClosed(orderDetail.getClosedStatus())){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                            return new Result(Constants.RESULT_CODE.SYS_ERROR, "已有订单明细申请结案！");
                        }
                        orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PLANMAINTENANCE.getValue());
                        dao.saveOrUpdate(orderDetail);
                        orderPlan.setTotalCount(orderPlan.getTotalCount()+orderDetail.getNum());
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                        return new Result(Constants.RESULT_CODE.SYS_ERROR, "保存失败，订单明细不存在或已被占用！");
                    }
                    //新增计划单明细
                    OrderPlanDetail detail = new OrderPlanDetail();
                    detail.setOrderPlanId(orderPlan.getId());
                    detail.setOrderId(orderPlanDetail.getOrder());
                    detail.setOrderDetailId(orderPlanDetail.getOrderDetail());
                    detail.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.ENABLE));
                    dao.save(detail);
                }
            }
            orderPlan.setPublishers(dto.getPublishers());
            orderPlan.setRemark(dto.getRemark());
            orderPlan.setUpdateUserId(dto.getUser());
            orderPlan.setUpdateTime(date);
            dao.saveOrUpdate(orderPlan);
            @SuppressWarnings("unchecked")
            List<OrderPlanDetail> list = dao.createCriteria(OrderPlanDetail.class)
                    .add(Restrictions.eq("orderPlanId",dto.getId()))
                    .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                    .list();
            Map<String,Object> resMap = new HashMap<>();
            resMap.put("orderPlan",orderPlan);
            resMap.put("orderPlanDetails",list);
            return new Result(resMap);
        } else {
            OrderPlan orderPlan = new OrderPlan();
            orderPlan.setPlanStatus(Integer.valueOf(dto.getPlanStatus()));
            orderPlan.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.ENABLE));
            orderPlan.setProdFactory(dto.getProdFactory());
            orderPlan.setDeliveryTime(dto.getDeliveryTime());
            orderPlan.setProdTime(dto.getProdTime());
            orderPlan.setPublishers(dto.getPublishers());
            int count = 0;
            if (dto.getOrderPlanDetails()!=null&&dto.getOrderPlanDetails().size()>0){
                for (OrderPlanDetailDto detailDto : dto.getOrderPlanDetails()) {
                    count += detailDto.getOrderCount()==null?0:detailDto.getOrderCount();
                }
            }
            orderPlan.setTotalCount(count);
            orderPlan.setPlanNumber(CodeUtil.createOrderPlanCode(getCodeMaxValue()));
            orderPlan.setCreateUserId(dto.getUser());
            orderPlan.setCreateTime(date);
            orderPlan.setUpdateUserId(dto.getUser());
            orderPlan.setUpdateTime(date);
            orderPlan.setRemark(dto.getRemark());
            dao.save(orderPlan);
            for (OrderPlanDetailDto detailDto : dto.getOrderPlanDetails()) {
                OrderDetail orderDetail = dao.findById(OrderDetail.class,detailDto.getOrderDetail());
                //如果该订单明细是技术完成状态，更新订单明细为计划维护
                if (orderDetail!=null&&BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue().equals(orderDetail.getOrderDetailStatus())){
                    //判断订单是否结案
                    if(isClosed(orderDetail.getClosedStatus())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                        return new Result(Constants.RESULT_CODE.SYS_ERROR, "已有订单明细申请结案！");
                    }
                    orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PLANMAINTENANCE.getValue());
                    dao.saveOrUpdate(orderDetail);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                    return new Result(Constants.RESULT_CODE.SYS_ERROR,"保存失败，订单明细不存在或已被占用！");
                }
                //新增计划单明细
                OrderPlanDetail detail = new OrderPlanDetail();
                detail.setOrderPlanId(orderPlan.getId());
                detail.setOrderId(detailDto.getOrder());
                detail.setOrderDetailId(detailDto.getOrderDetail());
                detail.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.ENABLE));
                dao.save(detail);
            }
            @SuppressWarnings("unchecked")
            List<OrderPlanDetail> orderPlanDetails = dao.createCriteria(OrderPlanDetail.class)
                    .add(Restrictions.eq("orderPlanId",orderPlan.getId()))
                    .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                    .list();
            Map<String,Object> resMap = new HashMap<>();
            resMap.put("orderPlan",orderPlan);
            resMap.put("orderPlanDetails",orderPlanDetails);
            return new Result(resMap);
        }
    }

    @Override
    public Result delOrderPlan(OrderPlanDto dto) {
        Criteria criteria = dao.createCriteria(OrderPlan.class);
        criteria.add(Restrictions.eq("id",dto.getId()))
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)));
        OrderPlan orderPlan = (OrderPlan)criteria.uniqueResult();
        if (orderPlan==null||Constants.ENABLE_FLAG.DELETE.equals(orderPlan.getDeleteFlag().toString())){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单不存在或已被删除！");
        }
        if (!Constants.PLAN_STATUS.草稿.getValByCode().equals(orderPlan.getPlanStatus())
                &&!Constants.PLAN_STATUS.已驳回.getValByCode().equals(orderPlan.getPlanStatus())){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"只有草稿或者被驳回的计划单才能够删除！");
        }
        User user = new User();
        user.setId(dto.getUser());
        Calendar calendar= Calendar.getInstance();
        Date date = calendar.getTime();
        orderPlan.setUpdateTime(date);
        orderPlan.setUpdateUser(user);
        orderPlan.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.DELETE));
        dao.saveOrUpdate(orderPlan);
        List list = dao.createCriteria(OrderPlanDetail.class)
                .add(Restrictions.eq("orderPlanId",dto.getId()))
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                .list();
        if (list!=null&&list.size()>0){
            for (Object o : list) {
                OrderPlanDetail orderPlanDetail = (OrderPlanDetail)o;
                orderPlanDetail.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.DELETE));
                dao.saveOrUpdate(orderPlanDetail);
                OrderDetail orderDetail = orderPlanDetail.getOrderDetail();
                if (!BillStatus.OrderDetailStatus.PLANMAINTENANCE.getValue().equals(orderDetail.getOrderDetailStatus())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                    return new Result(Constants.RESULT_CODE.SYS_ERROR,"删除失败，订单明细不是计划维护状态！");
                }
                orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue());
                dao.saveOrUpdate(orderDetail);
            }
        }
        return new Result();
    }

    @Override
    public Result delOrderPlanDetail(String id) {
        OrderPlanDetail orderPlanDetail = dao.findById(OrderPlanDetail.class,id);
        if (orderPlanDetail!=null){
            OrderDetail orderDetail = orderPlanDetail.getOrderDetail();
            if(orderDetail==null||!BillStatus.OrderDetailStatus.PLANMAINTENANCE.getValue().equals(orderDetail.getOrderDetailStatus())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"操作失败，只有计划维护状态才允许删除！");
            }
            OrderPlan orderPlan = dao.findById(OrderPlan.class,orderPlanDetail.getOrderPlanId());
            if (orderPlan!=null) {
                int count = (orderPlan.getTotalCount() == null ? 0 : orderPlan.getTotalCount()) - orderDetail.getNum();
                if (count < 0) {
                    count = 0;
                }
                orderPlan.setTotalCount(count);
                dao.saveOrUpdate(orderPlan);
            }
            orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue());
            dao.saveOrUpdate(orderDetail);
        }
        orderPlanDetail.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.DELETE));
        dao.saveOrUpdate(orderPlanDetail);
        return new Result(orderPlanDetail);
    }

    @Override
    public Result delOrderPlanDetail(OrderPlanDetailDto dto) {
        Criteria criteria = dao.createCriteria(OrderPlanDetail.class);
        criteria.add(Restrictions.eq("id",dto.getId()))
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)));
        OrderPlanDetail orderPlanDetail = (OrderPlanDetail)criteria.uniqueResult();
        if (orderPlanDetail==null||Constants.ENABLE_FLAG.DELETE.equals(orderPlanDetail.getDeleteFlag().toString())){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单明细不存在或已被删除！");
        }
        orderPlanDetail.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.DELETE));
        dao.saveOrUpdate(orderPlanDetail);
        return new Result();
    }

    @Override
    public Pagination<OrderPlan> findOrderPlanByPage(OrderPlanDto dto, PageBean pageBean, Integer[] planStatus) {
        boolean weather = false;
        Criteria criteria = dao.createCriteria(OrderPlanDetail.class)
                .createAlias("order","order")
                .createAlias("orderDetail","orderDetail")
                .createAlias("orderDetail.goodsDetail","goodsDetail");
        criteria.add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)));
        criteria.add(Restrictions.eqProperty("orderDetail.id","orderDetailId"));
        if (StringUtils.isNotBlank(dto.getOrderNumber())){
            criteria.add(Restrictions.like("order.orderCode",dto.getOrderNumber(), MatchMode.ANYWHERE));
            weather = true;
        }
        if (StringUtils.isNotBlank(dto.getGoods())){
            criteria.add(Restrictions.eq("goodsDetail.id",dto.getGoods()));
            weather = true;
        }
        if (StringUtils.isNotBlank(dto.getCustomerQ())){
            criteria.add(Restrictions.or(Restrictions.like("order.customerCode",dto.getCustomerQ(),MatchMode.ANYWHERE),
            		Restrictions.like("order.customerName", dto.getCustomerQ(),MatchMode.ANYWHERE)));
            weather = true;
        }
        if (StringUtils.isNotBlank(dto.getSingleNo())){
            criteria.add(Restrictions.like("orderDetail.goodsSn",dto.getSingleNo(),MatchMode.ANYWHERE));
            weather = true;
        }
        if (StringUtils.isNotBlank(dto.getOrderDetailStatus())){
            criteria.add(Restrictions.eq("orderDetail.orderDetailStatus",dto.getOrderDetailStatus()));
            weather = true;
        }
        Set<String> set = new HashSet<>();
        if (weather){
            List list = criteria.list();
            for (Object o : list) {
                OrderPlanDetail orderPlanDetail = (OrderPlanDetail)o;
                if (orderPlanDetail!=null&&StringUtils.isNotBlank(orderPlanDetail.getOrderPlanId())){
                    set.add(orderPlanDetail.getOrderPlanId());
                }
            }
            //有查询条件过滤出的结果集为零，添加限制，防止接下来的分页查询无条件限制
            if (set.size()==0){
                set.add("-1");
            }
        }
        Criteria criteriaPlan = dao.createCriteria(OrderPlan.class)
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)));
        if (StringUtils.isNotBlank(dto.getPlanNumber())){
            criteriaPlan.add(Restrictions.like("planNumber",dto.getPlanNumber(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(dto.getPlanStatus())){
            criteriaPlan.add(Restrictions.eq("planStatus",Integer.valueOf(dto.getPlanStatus())));
        }
        if (dto.getPlanStatusArray()!=null&&dto.getPlanStatusArray().length>0){
            Integer[] array = (Integer[])ConvertUtils.convert(dto.getPlanStatusArray(),Integer.class);
            criteriaPlan.add(Restrictions.in("planStatus",array));
        }
        if (StringUtils.isNotBlank(dto.getPublishers())){
            criteriaPlan.add(Restrictions.eq("publishers",dto.getPublishers()));
        }
        if (StringUtils.isNotBlank(dto.getProdFactory())){
            criteriaPlan.add(Restrictions.eq("prodFactory",dto.getProdFactory()));
        }
        if (dto.getProdTimeBegin()!=null){
            criteriaPlan.add(Restrictions.ge("prodTime",dto.getProdTimeBegin()));
        }
        if (dto.getProdTimeEnd()!=null){
            criteriaPlan.add(Restrictions.le("prodTime",dto.getProdTimeEnd()));
        }
        if (dto.getDeliveryTimeBegin()!=null){
            criteriaPlan.add(Restrictions.ge("deliveryTime",dto.getDeliveryTimeBegin()));
        }
        if (dto.getDeliveryTimeEnd()!=null){
            criteriaPlan.add(Restrictions.le("deliveryTime",dto.getDeliveryTimeEnd()));
        }
        if (planStatus!=null&&planStatus.length>0){
            criteriaPlan.add(Restrictions.in("planStatus",planStatus));
        }
        if (set.size()>0){
            criteriaPlan.add(Restrictions.in("id",set));
        }
        criteriaPlan.addOrder(org.hibernate.criterion.Order.desc("updateTime"));    //根据最后更新时间倒序排列
        Pagination<OrderPlan> pagination=new Pagination<>();
        pagination.setRows(dao.findListWithPagebeanCriteria(criteriaPlan, pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteriaPlan));
        return pagination;
    }

    @Override
    public Pagination<OrderPlanDetail> findOrderPlanDetailByPage(OrderPlanDetailDto dto, PageBean pageBean) {
        Criteria criteria = dao.createCriteria(OrderPlanDetail.class)
                .add(Restrictions.eq("orderPlanId",dto.getOrderPlan()))
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)));
        Pagination<OrderPlanDetail> pagination = new Pagination<>();
        pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    @Override
    public Pagination<Map<String, Object>> findOrderPlanAndDetailByPage(OrderPlanDto dto, PageBean pageBean, Integer[] planStatus) {
        Pagination<Map<String, Object>> pagination=new Pagination<>();
        List<Map<String,Object>> list = new ArrayList<>();
//        Pagination<OrderPlan> planPagination =  findOrderPlanByPage(dto, pageBean, planStatus);
//        if (planPagination.getRows()!=null&&planPagination.getRows().size()>0){
            if (pageBean.getRows()==null||pageBean.getPage()==null){
                pageBean.setPage(1);
                pageBean.setRows(10);
            }
            StringBuilder planSql = new StringBuilder("select DISTINCT mop.c_id,mop.c_update_time ");
            StringBuilder countSql = new StringBuilder("select count(1) from ( ");
            StringBuilder sql = new StringBuilder("select " +
                    "mop.c_id as planId,\n" +
                    "mop.c_plan_number,\n" +
                    "mop.c_plan_status,\n" +
                    "mop.c_prod_factory_id,\n" +
                    "mop.c_prod_time,\n" +
                    "mop.c_delivery_time,\n" +
                    "mop.c_total_count,\n" +
                    "cu.c_actual_name as cuc_actual_name,\n" +
                    "mop.c_create_time,\n" +
                    "au.c_actual_name as auc_actual_name,\n" +
                    "mop.c_audit_time,\n" +
                    "su.c_actual_name as suc_actual_name,\n" +
                    "mop.c_send_time,\n" +
                    "mop.c_remark,\n" +
                    "mop.status_tw,\n" +
                    "mop.tw_post_time,\n" +
                    "mop.publishers,\n" +
                    "mopd.c_id,\n" +
                    "mopd.c_prod_finish_time,\n" +
                    "tod.c_id as odId,\n" +
                    "tod.c_goods_detail_id,\n" +
                    "tod.c_goods_sn,\n" +
                    "goods.c_code goods_code,\n" +
                    "goods.c_name goods_name,\n" +
                    "goodsDetail.c_color_name goods_color_name,\n" +
                    "tod.c_order_detail_status,\n" +
                    "tor.c_id as orId,\n" +
                    "tor.c_order_code,\n" +
                    "tor.c_create_time as torc_create_time,\n" +
                    "tor.c_customer_code,\n" +
                    "tor.c_customer_name,\n" +
                    "tor.c_order_character,\n" +
                    "tor.c_reset,\n" +
                    "tor.c_shop_name,\n" +
                    "moda.c_technician_name,\n" +
                    "mods.c_mes_measure_size_name ");
            String from = " from mes_order_plan_detail mopd\n" +
                    "inner join t_order_detail tod on tod.c_id=mopd.c_order_detail_id and mopd.c_delete_flag=0\n" +
                    "inner join t_order tor on tod.c_order_id=tor.c_id \n" +
                    "join t_goods_detail goodsDetail ON tod.c_goods_detail_id = goodsDetail.c_id " +
                    "join t_goods goods on goodsDetail.c_goods_id = goods.c_id " +
                    "inner join mes_order_plan mop on mopd.c_order_plan_id=mop.c_id and mop.c_delete_flag=0 \n" +
                    "left join mes_order_detail_assign moda on moda.c_order_detail_id=tod.c_id and moda.c_delete_flag=0 \n" +
                    "left join mes_order_detail_size mods on mods.c_order_detail_id=tod.c_id\n" +
                    "left join t_user cu on mop.c_create_user_id=cu.c_id\n" +
                    "left join t_user au on mop.c_audit_user_id=au.c_id\n" +
                    "left join t_user su on mop.c_send_user_id=su.c_id " +
                    "where mopd.c_delete_flag=0 ";
            sql.append(from);
            planSql.append(from);
            if (StringUtils.isNotBlank(dto.getProdFactory())){
                sql.append(" and mop.c_prod_factory_id = '").append(dto.getProdFactory()).append("' ");
                planSql.append(" and mop.c_prod_factory_id = '").append(dto.getProdFactory()).append("' ");
            }
            if (StringUtils.isNotBlank(dto.getStylist())){
                sql.append(" and moda.c_technician_id = '").append(dto.getStylist()).append("' ");
                planSql.append(" and moda.c_technician_id = '").append(dto.getStylist()).append("' ");
            }
            if (StringUtils.isNotBlank(dto.getSize())){
                sql.append(" and mods.c_mes_measure_size_name like '%").append(dto.getSize()).append("%' ");
                planSql.append(" and mods.c_mes_measure_size_name like '%").append(dto.getSize()).append("%' ");
            }
            if (StringUtils.isNotBlank(dto.getPlanNumber())){
                sql.append(" and mop.c_plan_number like '%").append(dto.getPlanNumber()).append("%' ");
                planSql.append(" and mop.c_plan_number like '%").append(dto.getPlanNumber()).append("%' ");
            }
            if (StringUtils.isNotBlank(dto.getOrderNumber())){
                sql.append(" and tor.c_order_code like '%").append(dto.getOrderNumber()).append("%' ");
                planSql.append(" and tor.c_order_code like '%").append(dto.getOrderNumber()).append("%' ");
            }
            if (StringUtils.isNotBlank(dto.getGoods())){
                sql.append(" and tod.c_goods_detail_id = '").append(dto.getGoods()).append("' ");
                planSql.append(" and tod.c_goods_detail_id = '").append(dto.getGoods()).append("' ");
            }
            if (StringUtils.isNotBlank(dto.getVip())){
                sql.append(" and tor.c_customer_id = '").append(dto.getVip()).append("' ");
                sql.append(" and tor.c_customer_id = '").append(dto.getVip()).append("' ");
            }
            if (StringUtils.isNotBlank(dto.getSingleNo())){
                sql.append(" and tod.c_goods_sn like '%").append(dto.getSingleNo()).append("%' ");
                planSql.append(" and tod.c_goods_sn like '%").append(dto.getSingleNo()).append("%' ");
            }
            if (StringUtils.isNotBlank(dto.getOrderDetailStatus())){
                sql.append(" and tod.c_order_detail_status = '").append(dto.getOrderDetailStatus()).append("' ");
                planSql.append(" and tod.c_order_detail_status = '").append(dto.getOrderDetailStatus()).append("' ");
            }
            if (StringUtils.isNotBlank(dto.getPlanStatus())){
                sql.append(" and mop.c_plan_status = ").append(dto.getPlanStatus()).append(" ");
                planSql.append(" and mop.c_plan_status = ").append(dto.getPlanStatus()).append(" ");
            }
            if (dto.getProdTimeBegin()!=null){
                sql.append(" and mop.c_prod_time > ").append(DateUtil.minDate(dto.getProdTimeBegin())).append(" ");
                planSql.append(" and mop.c_prod_time > ").append(DateUtil.minDate(dto.getProdTimeBegin())).append(" ");
            }
            if (dto.getProdTimeEnd()!=null){
                sql.append(" and mop.c_prod_time <= ").append(DateUtil.maxDate(dto.getProdTimeEnd())).append(" ");
                planSql.append(" and mop.c_prod_time <= ").append(DateUtil.maxDate(dto.getProdTimeEnd())).append(" ");
            }
            if (dto.getDeliveryTimeBegin()!=null){
                sql.append(" and mop.c_delivery_time > ").append(DateUtil.minDate(dto.getDeliveryTimeBegin())).append(" ");
                planSql.append(" and mop.c_delivery_time > ").append(DateUtil.minDate(dto.getDeliveryTimeBegin())).append(" ");
            }
            if (dto.getDeliveryTimeEnd()!=null){
                sql.append(" and mop.c_prod_time <= ").append(DateUtil.maxDate(dto.getDeliveryTimeEnd())).append(" ");
                planSql.append(" and mop.c_prod_time <= ").append(DateUtil.maxDate(dto.getDeliveryTimeEnd())).append(" ");
            }
            countSql.append(planSql).append(" ) countTable ");
            planSql.append(" order by mop.c_update_time desc ");
            planSql.append( " limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
            List<BigInteger> result = dao.queryBySql(countSql.toString());
            long count = (result == null || result.isEmpty()) ? 0L : result.get(0).longValue();
            pagination.setTotal(count);
            if (count>0){
                Map<String,OrderPlan> mapPlan = new LinkedHashMap<>();
                Map<String,List<OrderPlanDetail>> mapPlanDetail = new LinkedHashMap<>();
                List<Object> planIds = dao.queryBySql(planSql.toString());
                List<String> planIdsStr = new ArrayList<>();
                for (Object o : planIds){
                    Object[] obj = (Object[])o;
                    planIdsStr.add(obj==null||obj.length==0?"":obj[0].toString());
                }
                sql.append(" and mop.c_id in ('").append(String.join("','",planIdsStr)).append("')");
                sql.append(" order by mop.c_update_time desc ");
                List<Object> res = dao.queryBySql(sql.toString());
                for(Object o : res){
                    Object[] properties = (Object[])o;
                    //没有计划单 新增计划单
                    if (properties!=null&&properties[0]!=null){
                        if(mapPlan.get(properties[0].toString())==null) {
                            OrderPlan orderPlan = new OrderPlan();
                            orderPlan.setId(properties[0].toString());
                            orderPlan.setPlanNumber(properties[1] == null ? "" : properties[1].toString());
                            orderPlan.setPlanStatus(properties[2] == null ? null : (int) properties[2]);
                            orderPlan.setProdFactory(properties[3] == null ? "" : properties[3].toString());
                            orderPlan.setProdTime(properties[4] == null ? null : DateUtil.objToDate(properties[4]));
                            orderPlan.setDeliveryTime(properties[5] == null ? null : DateUtil.objToDate(properties[5].toString()));
                            orderPlan.setTotalCount(properties[6] == null ? null : (int) properties[6]);
                            User cu = new User();
                            cu.setActualName(properties[7] == null ? "" : properties[7].toString());
                            cu.setUserName(properties[7] == null ? "" : properties[7].toString());
                            orderPlan.setCreateUser(cu);
                            orderPlan.setCreateTime(properties[8] == null ? null : DateUtil.objToDate(properties[8]));
                            User au = new User();
                            au.setActualName(properties[9] == null ? "" : properties[9].toString());
                            au.setUserName(properties[9] == null ? "" : properties[9].toString());
                            orderPlan.setAuditUser(au);
                            orderPlan.setAuditTime(properties[10] == null ? null : DateUtil.objToDate(properties[10]));
                            User su = new User();
                            su.setActualName(properties[11] == null ? "" : properties[11].toString());
                            su.setUserName(properties[11] == null ? "" : properties[11].toString());
                            orderPlan.setSendUser(su);
                            orderPlan.setSendTime(properties[12] == null ? null : DateUtil.objToDate(properties[12]));

                            orderPlan.setRemark(properties[13] == null ? "" : properties[13].toString());
                            orderPlan.setStatusTW(properties[14] == null ? null : (int) properties[14]);
                            orderPlan.setPostTimeTW(properties[15] == null ? null : DateUtil.objToDate(properties[15]));
                            orderPlan.setPublishers(properties[16] == null ? "" : properties[16].toString());
                            mapPlan.put(properties[0].toString(), orderPlan);
                        }
                        List<OrderPlanDetail> orderPlanDetails = null;
                        if (mapPlanDetail.get(properties[0].toString())==null) {
                            orderPlanDetails = new ArrayList<>();
                        } else {
                            orderPlanDetails = mapPlanDetail.get(properties[0].toString());
                        }
                        OrderPlanDetail orderPlanDetail = new OrderPlanDetail();
                        orderPlanDetail.setId(properties[17] == null ? "" : properties[17].toString());
                        orderPlanDetail.setProdFinishTime(properties[18] == null ? null : DateUtil.objToDate(properties[18]));
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setId(properties[19] == null ? "" : properties[19].toString());
                        orderDetail.setGoodsDetailId(properties[20] == null ? "" : properties[20].toString());
                        orderDetail.setGoodsSn(properties[21] == null ? "" : properties[21].toString());
                        orderDetail.setGoodsCode(properties[22] == null ? "" : properties[22].toString());
                        orderDetail.setGoodsName(properties[23] == null ? "" : properties[23].toString());
                        orderDetail.setGoodsColorName(properties[24] == null ? "" : properties[24].toString());
                        orderDetail.setOrderDetailStatus(properties[25] == null ? "" : properties[25].toString());
                        orderPlanDetail.setOrderDetail(orderDetail);
                        Order order = new Order();
                        order.setId(properties[26] == null ? "" : properties[26].toString());
                        order.setOrderCode(properties[27] == null ? "" : properties[27].toString());
                        order.setCreateTime(properties[28] == null ? null : DateUtil.objToDate(properties[28]));
                        order.setCustomerCode(properties[29] == null ? "" : properties[29].toString());
                        order.setCustomerName(properties[30] == null ? "" : properties[30].toString());
                        order.setOrderCharacter(properties[31] == null ? "" : properties[31].toString());
                        order.setReset(properties[32] == null ? "" : properties[32].toString());
                        order.setShopName(properties[33] == null ? "" : properties[33].toString());
                        orderPlanDetail.setOrder(order);
                        orderPlanDetail.setStylist(properties[34] == null ? "" : properties[34].toString());
                        orderPlanDetail.setSize(properties[35] == null ? "" : properties[35].toString());
                        orderPlanDetails.add(orderPlanDetail);
                        orderPlanDetail.setOrderId(order.getId());
                        orderPlanDetail.setOrderDetailId(orderDetail.getId());
                        orderPlanDetail.setOrderPlanId(properties[0].toString());
                        mapPlanDetail.put(properties[0].toString(),orderPlanDetails);
                    }
                }
                for (Object planId : planIdsStr) {
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("orderPlan",mapPlan.get(planId));
                    map.put("orderPlanDetails",mapPlanDetail.get(planId));
                    list.add(map);
                }
            }
//        }
//        pagination.setTotal(planPagination.getTotal());
        pagination.setRows(list);
        return pagination;
    }

    @Override
    public Result findOrderPlan(String id) {
        OrderPlan orderPlan = dao.findById(OrderPlan.class,id);
        if (orderPlan==null||Constants.ENABLE_FLAG.DELETE.equals(orderPlan.getDeleteFlag().toString())){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单不存在或已被删除！");
        }
        return new Result(orderPlan);
    }

    @Override
    public Result findOrderPlanDetails(String id) {
        Criteria criteria = dao.createCriteria(OrderPlanDetail.class)
                .createAlias("orderDetail","orderDetail")
                .add(Restrictions.eqProperty("orderDetail.id","orderDetailId"))
                .add(Restrictions.eq("orderPlanId",id))
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)));
        List<OrderPlanDetail> list = criteria.list();
        for (OrderPlanDetail orderPlanDetail:list) {
           OrderDetail orderDetail = orderPlanDetail.getOrderDetail();
           if(orderDetail!=null){
               String goodsId = orderDetail.getGoodsDetail().getGoodsId();
               Goods goods = dao.findById(Goods.class,goodsId);
               orderDetail.setGoodsName(goods.getName());
               orderDetail.setGoodsCode(goods.getCode());
               orderDetail.setGoodsColorName(orderDetail.getGoodsDetail().getColorName());
           }
        }
        return new Result(list);
    }

    @Override
    public Result auditOrSendOrderPlan(OrderPlanDto dto) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        OrderPlan orderPlan = dao.findById(OrderPlan.class, dto.getId());
        if (orderPlan == null || Constants.ENABLE_FLAG.DELETE.equals(orderPlan.getDeleteFlag().toString())) {
            return new Result(Constants.RESULT_CODE.SYS_ERROR, "您操作的计划单不存在或已被删除！");
        }
        if (Constants.PLAN_STATUS.已审核.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))
                || Constants.PLAN_STATUS.已驳回.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))) {
            if (!Constants.PLAN_STATUS.待审核.getValByCode().equals(orderPlan.getPlanStatus())) {
                return new Result(Constants.RESULT_CODE.SYS_ERROR, "该计划单的状态不允许审核操作！");
            }
            orderPlan.setAuditTime(date);
            orderPlan.setAuditUserId(dto.getUser());
        } else if(Constants.PLAN_STATUS.已下达.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))){
            if (!Constants.PLAN_STATUS.已审核.getValByCode().equals(orderPlan.getPlanStatus())) {
                return new Result(Constants.RESULT_CODE.SYS_ERROR, "该计划单的状态不允许下达操作！");
            }
            orderPlan.setSendTime(date);
            orderPlan.setSendUserId(dto.getUser());
        }
        List list = dao.createCriteria(OrderPlanDetail.class)
                .add(Restrictions.eq("orderPlanId",orderPlan.getId()))
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                .list();
        for (Object o : list) {
            OrderPlanDetail orderPlanDetail = (OrderPlanDetail)o;
            if (orderPlanDetail!=null&&orderPlanDetail.getOrderDetail()!=null){
                OrderDetail orderDetail = orderPlanDetail.getOrderDetail();
                //判断订单是否结案
                if(isClosed(orderDetail.getClosedStatus())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                    return new Result(Constants.RESULT_CODE.SYS_ERROR, "已有订单明细申请结案！");
                }
                //计划单下达同步更新订单明细状态到生产中
                if(Constants.PLAN_STATUS.已下达.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))){
                    orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PRODUCING.getValue());
                    dao.saveOrUpdate(orderPlanDetail);
                }
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"未找到该计划单下的订单明细信息！");
            }
        }
        orderPlan.setPlanStatus(Integer.valueOf(dto.getPlanStatus()));
        orderPlan.setRemark(dto.getRemark());
        orderPlan.setUpdateTime(date);
        orderPlan.setUpdateUserId(dto.getUser());
        dao.saveOrUpdate(orderPlan);
        return new Result(orderPlan);
    }

    @Override
    public Result rejectOrFinishOrderPlan(OrderPlanDto dto) {
        OrderPlan orderPlan = dao.findById(OrderPlan.class,dto.getId());
        if (orderPlan==null||Constants.ENABLE_FLAG.DELETE.equals(orderPlan.getDeleteFlag().toString())){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单不存在或已被删除！");
        }
        if (Constants.PLAN_STATUS.已驳回.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))){
            //只有已下达状态的计划单才允许驳回
            if (!Constants.PLAN_STATUS.已下达.getValByCode().equals(orderPlan.getPlanStatus())&&!Constants.PLAN_STATUS.生产完成.getValByCode().equals(orderPlan.getPlanStatus())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"只有已下达和生产完成状态才允许驳回！");
            }
            //计划单驳回，校验该计划单下的订单明细是有已生产完成的，若有生产完成的明细不允许驳回
            List list = dao.createCriteria(OrderPlanDetail.class)
                    .createAlias("orderDetail","orderDetail")
                    .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                    .add(Restrictions.eq("orderPlanId",dto.getId())).list();
            for (Object o : list) {
                OrderPlanDetail orderPlanDetail = (OrderPlanDetail)o;
                if (orderPlanDetail!=null&&orderPlanDetail.getOrderDetail()!=null){
                    OrderDetail orderDetail = orderPlanDetail.getOrderDetail();
                    /*if ("5".equals(orderDetail.getOrderDetailStatus())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                        return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单下有已生产完成的订单明细，不允许驳回");
                    }*/
                    if (!BillStatus.OrderDetailStatus.PRODUCING.getValue().equals(orderDetail.getOrderDetailStatus())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                        return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单中存在非生产中状态，不允许驳回操作！");
                    }
                    orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PLANMAINTENANCE.getValue()); //驳回到计划维护状态
                    dao.saveOrUpdate(orderDetail);
                }
            }
        } else if (Constants.PLAN_STATUS.生产完成.getValByCode().equals(Integer.valueOf(dto.getPlanStatus()))){
            Set<String> orderIds = new HashSet<>();
            if (!Constants.PLAN_STATUS.已下达.getValByCode().equals(orderPlan.getPlanStatus())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单非已下达状态，不允许生产完成操作！");
            }
            //计划单驳回，校验该计划单下的订单明细是有已生产完成的，若有生产完成的明细不允许驳回
            List list = dao.createCriteria(OrderPlanDetail.class)
                    .createAlias("orderDetail","orderDetail")
                    .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                    .add(Restrictions.eq("orderPlanId",dto.getId())).list();
            for (Object o : list) {
                OrderPlanDetail orderPlanDetail = (OrderPlanDetail)o;
                if (orderPlanDetail!=null&&orderPlanDetail.getOrderDetail()!=null){
                    OrderDetail orderDetail = orderPlanDetail.getOrderDetail();
                    orderIds.add(orderDetail.getOrderId());
                    if (!BillStatus.OrderDetailStatus.PRODUCING.getValue().equals(orderDetail.getOrderDetailStatus())
                            &&!BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue().equals(orderDetail.getOrderDetailStatus())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                        return new Result(Constants.RESULT_CODE.SYS_ERROR,"操作失败，计划单有非生产中和生产完成状态的订单明细！");
                    } else if (BillStatus.OrderDetailStatus.PRODUCING.getValue().equals(orderDetail.getOrderDetailStatus())){
                        orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue()); //更新订单明细到生产完成状态
                        dao.saveOrUpdate(orderDetail);
                    }
                }
                orderPlanDetail.setProdFinishTime(Calendar.getInstance().getTime());
                dao.saveOrUpdate(orderPlanDetail);
            }
            //判断订单下的明细是否都已经生产完成，是则更新订单状态到已完成
            for (String orderId : orderIds) {
                Order order = dao.findById(Order.class,orderId);
                //审核通过状态下的订单才可以更新到生产完成状态
                if (order!=null&&"3".equals(order.getStatusBussiness())){
                    List orderDetailList = dao.createCriteria(OrderDetail.class)
                            .add(Restrictions.eq("orderId",orderId))
                            .add(Restrictions.ne("orderDetailStatus",BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue()))
                            .list();
                    if (orderDetailList==null||orderDetailList.size()==0){
                        order.setStatusBussiness("6");  //设置订单状态为生产完成
                        dao.saveOrUpdate(order);
                    }
                } /*else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                    return new Result(Constants.RESULT_CODE.SYS_ERROR,"订单ID："+orderId+"不存在或该订单状态不是审核通过状态，无法全部完成操作！");
                }*/
            }
        } else {
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"传递状态错误！");
        }
        orderPlan.setPlanStatus(Integer.valueOf(dto.getPlanStatus()));
        if (StringUtils.isNotBlank(dto.getRemark())){
            orderPlan.setRemark(dto.getRemark());
        }
        orderPlan.setUpdateUserId(dto.getUser());
        orderPlan.setUpdateTime(Calendar.getInstance().getTime());
        dao.saveOrUpdate(orderPlan);
        return new Result();
    }

    @Override
    public Result rejectOrFinishOrderDetail(OrderPlanDetailDto dto) {
        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetail());
        if (orderDetail==null){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该订单明细不存在！");
        }
        OrderPlanDetail orderPlanDetail = dao.findById(OrderPlanDetail.class,dto.getId());
        OrderPlan orderPlan = dao.findById(OrderPlan.class,dto.getOrderPlan());
        if ("4".equals(dto.getOperationStatus())){
            if (!BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue().equals(orderDetail.getOrderDetailStatus())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"该订单明细非生产完成状态，无法撤销完成！");
            }
            //如果计划单是生产完成状态，同时撤销计划单
            if (Constants.PLAN_STATUS.生产完成.getValByCode().equals(orderPlan.getPlanStatus())){
                orderPlan.setPlanStatus(Constants.PLAN_STATUS.已下达.getValByCode());
                dao.saveOrUpdate(orderPlan);
            }
            //如果订单是生产完成状态，同时撤销订单状态到审核通过
            if (orderPlanDetail.getOrder()!=null&&"6".equals(orderPlanDetail.getOrder().getStatusBussiness())){
                orderPlanDetail.getOrder().setStatusBussiness("3");
                dao.saveOrUpdate(orderPlanDetail.getOrder());
            }
            orderPlanDetail.setProdFinishTime(null);
            dao.saveOrUpdate(orderPlanDetail);
            orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PRODUCING.getValue());
        } else if("5".equals(dto.getOperationStatus())){
            if (!BillStatus.OrderDetailStatus.PRODUCING.getValue().equals(orderDetail.getOrderDetailStatus())){
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"该订单明细非生产中状态，不允许生产完成操作");
            }
            //判断订单下的明细是否都已生产完成
            Order order = dao.findById(Order.class,orderPlanDetail.getOrderId());
            if (order!=null&&"3".equals(order.getStatusBussiness())){
                List orderDetailList = dao.createCriteria(OrderDetail.class)
                        .add(Restrictions.eq("orderId",orderPlanDetail.getOrderId()))
                        .add(Restrictions.ne("orderDetailStatus",BillStatus.OrderDetailStatus.SHIPPED.getValue()))
                        .add(Restrictions.ne("orderDetailStatus",BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue()))
                        .list();
                if (orderDetailList==null||orderDetailList.size()==0){
                    order.setStatusBussiness("6");  //设置订单状态为生产完成
                    dao.saveOrUpdate(order);
                }
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
                return new Result(Constants.RESULT_CODE.SYS_ERROR,"操作失败，订单不存在或不是审核通过状态！");
            }
            //判断除了当前订单外该计划单下是否还有未生产完成的订单
            Criteria criteria = dao.createCriteria(OrderPlanDetail.class)
                    .createAlias("orderDetail","orderDetail")
                    .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                    .add(Restrictions.eq("orderPlanId",dto.getOrderPlan()))
                    .add(Restrictions.ne("orderDetailId",orderDetail.getId()))
                    .add(Restrictions.ne("orderDetail.orderDetailStatus",BillStatus.OrderDetailStatus.SHIPPED.getValue()))
                    .add(Restrictions.ne("orderDetail.orderDetailStatus",BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue()));
            List list = criteria.list();
            if (list==null||list.size()==0){
                orderPlan.setPlanStatus(Constants.PLAN_STATUS.生产完成.getValByCode());
                dao.saveOrUpdate(orderPlan);
            }
            //更新生产完成时间
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            orderPlanDetail.setProdFinishTime(date);
            dao.saveOrUpdate(orderPlanDetail);
            orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue());
        } else {
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"传递状态有误，操作失败！");
        }
        dao.saveOrUpdate(orderDetail);
        return new Result();
    }

    @Override
    public Result deleteOrderPlanDetail(OrderPlanDetailDto dto) {
        OrderDetail orderDetail = dao.findById(OrderDetail.class,dto.getOrderDetail());
        if (orderDetail==null){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该订单明细不存在！");
        }
        //判断计划单明细是否是生产中的状态，只有生产中的才允许删除
        if (!BillStatus.OrderDetailStatus.PRODUCING.getValue().equals(orderDetail.getOrderDetailStatus())){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"只有生产中的订单才允许删除！");
        }
        OrderPlanDetail orderPlanDetail = dao.findById(OrderPlanDetail.class,dto.getId());
        if (orderPlanDetail==null||Integer.valueOf(Constants.ENABLE_FLAG.DELETE).equals(orderPlanDetail.getDeleteFlag())) {
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单明细不存在或已被删除！");
        }
        OrderPlan orderPlan = dao.findById(OrderPlan.class,dto.getOrderPlan());
        if (orderPlan==null||Integer.valueOf(Constants.ENABLE_FLAG.DELETE).equals(orderPlan.getDeleteFlag())) {
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该计划单不存在或已被删除！");
        }
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        //删除操作，逻辑删除计划单明细数据
        orderPlanDetail.setDeleteFlag(Integer.valueOf(Constants.ENABLE_FLAG.DELETE));
        dao.saveOrUpdate(orderPlanDetail);
        //还原订单明细状态到技术完成状态
        orderDetail.setOrderDetailStatus(BillStatus.OrderDetailStatus.TECHNICALFINISHED.getValue());
        dao.saveOrUpdate(orderDetail);
        //删除后判断订单是否生产完成，生产完成更新订单状态到星域审核通过
        Order order = dao.findById(Order.class,dto.getOrder());
        if (order!=null&&"6".equals(order.getStatusBussiness())){
            order.setStatusBussiness("3");  //设置订单状态为星域审核通过
            order.setUpdateUserId(dto.getUserId());
            order.setUpdateTime(date);
            dao.saveOrUpdate(order);
        }
        //删除后判断该计划单下的计划单明细是否都已生产完成,若完成更新计划单状态
        Criteria criteria = dao.createCriteria(OrderPlanDetail.class)
                .createAlias("orderDetail","orderDetail")
                .add(Restrictions.eq("deleteFlag",Integer.valueOf(Constants.ENABLE_FLAG.ENABLE)))
                .add(Restrictions.eq("orderPlanId",dto.getOrderPlan()))
                .add(Restrictions.ne("orderDetailId",orderDetail.getId()))
                .add(Restrictions.ne("orderDetail.orderDetailStatus",BillStatus.OrderDetailStatus.SHIPPED.getValue()))
                .add(Restrictions.ne("orderDetail.orderDetailStatus",BillStatus.OrderDetailStatus.PRODUCEFINISH.getValue()));
        List list = criteria.list();
        if (list==null||list.size()==0){
            orderPlan.setPlanStatus(Constants.PLAN_STATUS.生产完成.getValByCode());
        }
        //更新计划单数量
        int planCount = orderPlan.getTotalCount()==null?0:orderPlan.getTotalCount();
        orderPlan.setTotalCount(planCount-orderDetail.getNum()<0?0:planCount-orderDetail.getNum());
        orderPlan.setUpdateUserId(dto.getUserId());
        orderPlan.setUpdateTime(date);
        dao.saveOrUpdate(orderPlan);
        return new Result();
    }

    private String getCodeMaxValue() {
        Date date = new Date();
        Criteria criteria = dao.createCriteria(Code.class);
        criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
        criteria.add(Restrictions.eq("type", "PP"));
        criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
        criteria.setMaxResults(1);
        Code code = (Code) criteria.uniqueResult();
        if (code == null) {
            code = new Code();
            code.setMaxValue(1);
            code.setType("PP");
            code.setUpdateTime(date);
            dao.save(code);
        } else {
            code.setMaxValue(code.getMaxValue() + 1);
        }
        return String.format("%0" + 6 + "d", code.getMaxValue());
    }

    /**
     * 判断是否结案
     * @param status
     * @return
     */
    private boolean isClosed(String status){
        if("2".equals(status)||"3".equals(status)){
            return true;
        }
        return false;
    }
}
