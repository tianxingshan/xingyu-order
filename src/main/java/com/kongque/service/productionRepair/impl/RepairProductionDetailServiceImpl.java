package com.kongque.service.productionRepair.impl;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.RepairProductionDetailDto;
import com.kongque.entity.repair.OrderRepairCopySearch;
import com.kongque.model.RepairProductionDetailModel;
import com.kongque.service.productionRepair.RepairProductionDetailService;
import com.kongque.util.*;
import com.kongque.util.StringUtils;
import org.apache.commons.lang3.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaxt
 * @date 2019/1/9.
 */
@Service
public class RepairProductionDetailServiceImpl implements RepairProductionDetailService {
    private static final Logger log = LoggerFactory.getLogger(RepairProductionDetailServiceImpl.class);

    @Resource
    private IDaoService dao;

    @Override
    public Pagination<RepairProductionDetailModel> findRepairProductionDetailByPage(RepairProductionDetailDto dto, PageBean... pageBeans) {
        Pagination<RepairProductionDetailModel> pagination = new Pagination<RepairProductionDetailModel>();
        PageBean pageBean = initPageBean(pageBeans);
        StringBuilder listSql = new StringBuilder("SELECT\n" +
                "\tmrpp.c_id,\n" +
                "\tmrpp.c_plan_code,\n" +
                "\ttor.c_id AS repair_id,\n" +
                "\ttor.c_order_repair_code,\n" +
                "\ttor.c_customer_id,\n" +
                "\ttor.c_customer_code,\n" +
                "\ttor.c_customer_name,\n" +
                "\ttg.c_code AS goods_code,\n" +
                "\ttg.c_name AS goods_name,\n" +
                "\ttgd.c_color_name,\n" +
                "\ttc.c_code,\n" +
                "\ttc.c_name,\n" +
                "\ttor.c_shop_id,\n" +
                "\ttor.c_shop_name,\n" +
                "\ttor.c_order_repair_status,\n" +
                "\ttor.c_order_character,\n" +
                "\tmop.c_prod_factory_id,\n" +
                "\ttor.c_check_time,\n" +
                "\tdelivery.c_delivery_time,\n" +
                "\tdelivery.c_express_number AS delivery_express_number,\n" +
                "\tmrpp.c_create_time,\n" +
                "\tmrpp.c_confirm_time,\n" +
                "\tmrpp.c_release_time,\n" +
                "\tmrpp.c_plan_date,\n" +
                "\tmrpp.c_finish_date,\n" +
                "\tsend.c_send_time,\n" +
                "\tsend.c_express_number AS send_express_number,\n" +
                "\tmoda.c_technician_name,\n" +
                "\tTIMESTAMPDIFF( DAY, ordersend.c_send_time, delivery.c_delivery_time ) AS duration,\n" +
                "\tordersend.c_send_time AS order_send_time,\n" +
                "\ttor.c_order_code,\n" +
                "\ttod.c_goods_sn,\n" +
                "\ttor.c_ec_order_code, "+
                "\ttod.c_order_id as orderId,\n" +
                "\ttor.c_order_detail_id as orderDetailId,\n" +
                "\tmrppd.c_repair_feedback as repairFeedback,\n" +
                "\tmrppd.c_repair_opinion as repairOpinion,\n" +
                "\tbz.c_balance_status,tor.c_finish_date as production_finish_date,tor.c_frequency,mrppd.c_repair_feedback "
        );
        StringBuilder baseSql = new StringBuilder("FROM t_order_repair tor\n" +
                "\tLEFT JOIN mes_repair_production_plan_detail mrppd ON tor.c_id = mrppd.c_order_repair_id\n" +
                "\tLEFT JOIN mes_repair_production_plan mrpp ON mrppd.c_repair_plan_id = mrpp.c_id and mrpp.c_del='0'\n" +
                "\tLEFT JOIN t_goods_detail tgd ON tor.c_goods_detail_id = tgd.c_id\n" +
                "\tLEFT JOIN t_goods tg ON tgd.c_goods_id = tg.c_id\n" +
                "\tLEFT JOIN t_category tc ON tc.c_id = tg.c_category_id and tc.c_del='0'\n" +
                "\tLEFT JOIN t_order_detail tod ON tor.c_order_detail_id = tod.c_id\n" +
                "\tLEFT JOIN mes_order_detail_assign moda ON tod.c_id = moda.c_order_detail_id and moda.c_delete_flag=0\n" +
                "\tLEFT JOIN mes_order_plan_detail mopd ON tod.c_id = mopd.c_order_detail_id and mopd.c_delete_flag=0\n" +
                "\tLEFT JOIN mes_order_plan mop ON mopd.c_order_plan_id = mop.c_id and mop.c_delete_flag=0\n" +
                "\tLEFT JOIN (\n" +
                "SELECT\n" +
                "\ttltemp.c_order_repair_id,\n" +
                "\tany_value ( tltemp.c_express_number ) AS c_express_number,\n" +
                "\tany_value ( tltemp.c_delivery_time ) AS c_delivery_time \n" +
                "FROM\n" +
                "\t(\n" +
                "SELECT\n" +
                "\ttlo.c_order_repair_id,\n" +
                "\ttlt.c_express_number,\n" +
                "\ttlt.c_delivery_time \n" +
                "FROM\n" +
                "\tt_logistic tlt\n" +
                "\tINNER JOIN t_logistic_order tlo ON tlt.c_id = tlo.c_logistic_id \n" +
                "WHERE\n" +
                "\ttlt.c_logistic_type = 1 \n" +
                "\tAND tlt.c_delete_flag = 0 \n" +
                "\tAND tlo.c_delete_flag = 0 \n" +
                "ORDER BY\n" +
                "\ttlt.c_delivery_time DESC,\n" +
                "\ttlt.c_id \n" +
                "\tLIMIT 10000000000 \n" +
                "\t) AS tltemp \n" +
                "GROUP BY\n" +
                "\ttltemp.c_order_repair_id \n" +
                "\t) delivery ON delivery.c_order_repair_id = tor.c_id\n" +
                "\tLEFT JOIN (\n" +
                "SELECT\n" +
                "\ttltemp1.c_order_repair_id,\n" +
                "\tany_value ( tltemp1.c_express_number ) AS c_express_number,\n" +
                "\tany_value ( tltemp1.c_send_time ) AS c_send_time \n" +
                "FROM\n" +
                "\t(\n" +
                "SELECT\n" +
                "\ttlo1.c_order_repair_id,\n" +
                "\ttlt1.c_express_number,\n" +
                "\ttlt1.c_send_time \n" +
                "FROM\n" +
                "\tt_logistic tlt1\n" +
                "\tINNER JOIN t_logistic_order tlo1 ON tlt1.c_id = tlo1.c_logistic_id \n" +
                "WHERE\n" +
                "\ttlt1.c_logistic_type = 0 \n" +
                "\tAND tlt1.c_delete_flag = 0 \n" +
                "\tAND tlo1.c_delete_flag = 0 \n" +
                "ORDER BY\n" +
                "\ttlt1.c_send_time DESC,\n" +
                "\ttlt1.c_id \n" +
                "\tLIMIT 10000000000 \n" +
                "\t) AS tltemp1 \n" +
                "GROUP BY\n" +
                "\ttltemp1.c_order_repair_id \n" +
                "\t) send ON send.c_order_repair_id = tor.c_id\n" +
                "\tLEFT JOIN (\n" +
                "SELECT\n" +
                "\ttlo2.c_order_detail_id,\n" +
                "\tmax( tlt2.c_send_time ) AS c_send_time \n" +
                "FROM\n" +
                "\tt_logistic tlt2\n" +
                "\tINNER JOIN t_logistic_order tlo2 ON tlt2.c_id = tlo2.c_logistic_id \n" +
                "WHERE\n" +
                "\ttlt2.c_logistic_type = 0 \n" +
                "\tAND tlt2.c_delete_flag = 0 \n" +
                "\tAND tlo2.c_delete_flag = 0 \n" +
                "GROUP BY\n" +
                "\ttlo2.c_order_detail_id \n" +
                "\t) ordersend ON ordersend.c_order_detail_id = tod.c_id\n" +
                " LEFT JOIN ( SELECT r.c_repair_id, b.c_balance_status FROM t_balance_repair_relation r LEFT JOIN t_balance b ON r.c_balance_id = b.c_id) bz on tor.c_id = bz.c_repair_id \n" +
                "where tor.c_del='0' ");
        if (StringUtils.isNotBlank(dto.getRepairPlanCode())){
            baseSql.append(" and mrpp.c_plan_code like '%").append(dto.getRepairPlanCode().trim()).append("%' ");
        }
        if (StringUtils.isNotBlank(dto.getOrderRepairCode())){
            baseSql.append(" and tor.c_order_repair_code like '%").append(dto.getOrderRepairCode().trim()).append("%' ");
        }
        if (StringUtils.isNotBlank(dto.getOrderRepairId())){
            baseSql.append(" and tor.c_id='").append(dto.getOrderRepairId().trim()).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getCustomerCodeOrName())){
            baseSql.append(" and (tor.c_customer_code like '%").append(dto.getCustomerCodeOrName().trim())
                    .append("%' or tor.c_customer_name like '%").append(dto.getCustomerCodeOrName().trim()).append("%')");
        }

        if (null!=dto.getProductionFinishDateStart()){
            baseSql.append(" and tor.c_finish_date >= '").append(DateUtil.fastDateFormat(dto.getProductionFinishDateStart(),DateUtil.DEFAULT_MIN_PATTERN)).append("' ");
        }


        if (null!=dto.getProductionFinishDateEnd()){
            baseSql.append(" and tor.c_finish_date <= '").append(DateUtil.fastDateFormat(dto.getProductionFinishDateEnd(),DateUtil.DEFAULT_MAX_PATTERN)).append("' ");
        }

        if (StringUtils.isNotBlank(dto.getFrequency())){
            baseSql.append(" and tor.c_frequency=").append(dto.getFrequency());
        }

        if (null!=dto.getDeliveryTimeStart()){
            baseSql.append(" and delivery.c_delivery_time >= '").append(DateUtil.fastDateFormat(dto.getDeliveryTimeStart(),DateUtil.DEFAULT_MIN_PATTERN)).append("' ");
        }


        if (null!=dto.getDeliveryTimeEnd()){
            baseSql.append(" and delivery.c_delivery_time <= '").append(DateUtil.fastDateFormat(dto.getDeliveryTimeEnd(),DateUtil.DEFAULT_MAX_PATTERN)).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getGoodsSN())){
            baseSql.append(" and tod.c_goods_sn='").append(dto.getGoodsSN().trim()).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getGoodsCodeOrName())){
            baseSql.append(" and (tg.c_code like '%").append(dto.getGoodsCodeOrName().trim())
                    .append("%' or tg.c_name like '%").append(dto.getGoodsCodeOrName().trim()).append("%')");
        }
        if (StringUtils.isNotBlank(dto.getGoodsCategoryCodeOrName())){
            baseSql.append(" and (tc.c_code like '%").append(dto.getGoodsCategoryCodeOrName().trim())
                    .append("%' or tc.c_name like '%").append(dto.getGoodsCategoryCodeOrName().trim()).append("%')");
        }
        if (null!=dto.getRepairPlanCreateDateStart()){
            baseSql.append(" and mrpp.c_create_time >= '").append(DateUtil.fastDateFormat(dto.getRepairPlanCreateDateStart(),DateUtil.DEFAULT_MIN_PATTERN)).append("' ");
        }
        if (null!=dto.getRepairPlanCreateDateEnd()){
            baseSql.append(" and mrpp.c_create_time <= '").append(DateUtil.fastDateFormat(dto.getRepairPlanCreateDateEnd(),DateUtil.DEFAULT_MAX_PATTERN)).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getOwner())){
            baseSql.append(" and tor.c_owner='").append(dto.getOwner().trim()).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getOrderCode())){
            baseSql.append(" and tor.c_order_code like '%").append(dto.getOrderCode().trim()).append("%' ");
        }
        if (StringUtils.isNotBlank(dto.getOrderProductFactory())&&!"全部".equals(dto.getOrderProductFactory().trim())){
            baseSql.append(" and mop.c_prod_factory_id='").append(dto.getOrderProductFactory().trim()).append("' ");
        }
        if (null!=dto.getRepairPlanReleaseTimeStart()){
            baseSql.append(" and mrpp.c_release_time >= '").append(DateUtil.fastDateFormat(dto.getRepairPlanReleaseTimeStart(),DateUtil.DEFAULT_MIN_PATTERN)).append("' ");
        }
        if (null!=dto.getRepairPlanReleaseTimeEnd()){
            baseSql.append(" and mrpp.c_release_time <= '").append(DateUtil.fastDateFormat(dto.getRepairPlanReleaseTimeEnd(),DateUtil.DEFAULT_MAX_PATTERN)).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getShopId())){
            baseSql.append(" and tor.c_shop_id='").append(dto.getShopId().trim()).append("' ");
        }
        if (null!=dto.getShopIds() && dto.getShopIds().length>0){
            baseSql.append(" and tor.c_shop_id in (''");
            for (String shopId : dto.getShopIds()) {
                baseSql.append(",'").append(shopId.trim()).append("'");
            }
            baseSql.append(")");
        }
        if (StringUtils.isNotBlank(dto.getShopName())){
            baseSql.append(" and tor.c_shop_name like '%").append(dto.getShopName().trim()).append("%' ");
        }
        if (StringUtils.isNotBlank(dto.getCharacter()) && !"全部".equals(dto.getCharacter().trim())){
            baseSql.append(" and tor.c_order_character like '%").append(dto.getCharacter().trim()).append("%' ");
        }
        if (StringUtils.isNotBlank(dto.getOrderRepairStatus())){
            baseSql.append(" and tor.c_order_repair_status='").append(dto.getOrderRepairStatus().trim()).append("' ");
        }
        if (null!=dto.getOrderRepairStatusArr() && dto.getOrderRepairStatusArr().length>0){
            baseSql.append(" and tor.c_order_repair_status in ('").append(String.join("','",dto.getOrderRepairStatusArr())).append("') ");
        }
        if (null!=dto.getPlanDateStart()){
            baseSql.append(" and mrpp.c_plan_date >= '").append(DateUtil.fastDateFormat(dto.getPlanDateStart(),DateUtil.DEFAULT_MIN_PATTERN)).append("' ");
        }
        if (null!=dto.getPlanDateEnd()){
            baseSql.append(" and mrpp.c_plan_date <= '").append(DateUtil.fastDateFormat(dto.getPlanDateEnd(),DateUtil.DEFAULT_MAX_PATTERN)).append("' ");
        }
        if (StringUtils.isNotBlank(dto.getDurationStart())){
            baseSql.append(" and TIMESTAMPDIFF( DAY, ordersend.c_send_time, delivery.c_delivery_time ) >= ").append(Integer.valueOf(dto.getDurationStart()));
        }
        if (StringUtils.isNotBlank(dto.getDurationEnd())){
            baseSql.append(" and TIMESTAMPDIFF( DAY, ordersend.c_send_time, delivery.c_delivery_time ) <= ").append(Integer.valueOf(dto.getDurationEnd()));
        }
        if (null!=dto.getFinishDateStart()){
            baseSql.append(" and mrpp.c_finish_date >= '").append(DateUtil.fastDateFormat(dto.getFinishDateStart(),DateUtil.DEFAULT_MIN_PATTERN)).append("' ");
        }
        if (null!=dto.getFinishDateEnd()){
            baseSql.append(" and mrpp.c_finish_date <= '").append(DateUtil.fastDateFormat(dto.getFinishDateEnd(),DateUtil.DEFAULT_MAX_PATTERN)).append("' ");
        }
        if(null!=dto.getSendDateStart()) {
//        	String fastDateFormat = DateUtil.fastDateFormat(dto.getSendDateStart(),DateUtil.DEFAULT_MIN_PATTERN);
        	String formatDate = DateUtil.formatDate(dto.getSendDateStart(), "yyyy-MM-dd HH:mm:ss");
        	baseSql.append(" and send.c_send_time >= '").append(formatDate).append("' ");
        }
        if(null!=dto.getSendDateEnd()) {
        	String formatDate = DateUtil.formatDate(dto.getSendDateEnd(), "yyyy-MM-dd HH:mm:ss");
        	baseSql.append(" and send.c_send_time <= '").append(formatDate).append("' ");
        }
        if(StringUtils.isNotBlank(dto.getBalanceStatus())) {
        	if(!dto.getBalanceStatus().equals("0")) {//统计非未结算
        		baseSql.append(" and bz.c_balance_status = ").append(dto.getBalanceStatus().toString().trim());
        	}else {
        		baseSql.append(" and bz.c_balance_status is NULL "); //统计未结算的
        	}
        }

        if(org.apache.commons.lang3.StringUtils.isNotBlank(dto.getTechnicianIds())){
            String[] arr = dto.getTechnicianIds().split(",");
            long count = arr.length;
            String technicianids="";
            for(int i = 0;i < count;i++){
               technicianids +="'"+arr[i]+"'";
               if(i < count - 1){
                   technicianids +=",";
               }
            }
            baseSql.append(" AND moda.c_technician_id in (").append(technicianids).append(")");
        }
        
        //判断是否有分页
        if (isPaging(pageBeans)){
            StringBuilder totalSql = new StringBuilder("select count(1) ").append(baseSql);
            List<Object> count = dao.queryBySql(totalSql.toString());
            if (null!=count && count.size()>0){
                pagination.setTotal(Long.parseLong(count.get(0).toString()));
            } else {
                pagination.setTotal(0);
            }
            baseSql.append(" limit ").append((pageBean.getPage()-1)*pageBean.getRows()).append(",").append(pageBean.getRows());
        }
        List<RepairProductionDetailModel> rows = new ArrayList<>();
        List<Object> list = dao.queryBySql(listSql.append(baseSql).toString());
        for (Object obj : list) {
            Object[] o = (Object[]) obj;
            RepairProductionDetailModel model = new RepairProductionDetailModel();
            model.setRepairPlanId(o[0]==null?"":o[0].toString());
            model.setRepairPlanCode(o[1]==null?"":o[1].toString());
            model.setOrderRepairId(o[2]==null?"":o[2].toString());
            model.setOrderRepairCode(o[3]==null?"":o[3].toString());
            model.setCustomerId(o[4]==null?"":o[4].toString());
            model.setCustomerCode(o[5]==null?"":o[5].toString());
            model.setCustomerName(o[6]==null?"":o[6].toString());
            model.setGoodsCode(o[7]==null?"":o[7].toString());
            model.setGoodsName(o[8]==null?"":o[8].toString());
            model.setGoodsColorName(o[9]==null?"":o[9].toString());
            model.setGoodsCategoryCode(o[10]==null?"":o[10].toString());
            model.setGoodsCategoryName(o[11]==null?"":o[11].toString());
            model.setShopId(o[12]==null?"":o[12].toString());
            model.setShopName(o[13]==null?"":o[13].toString());
            model.setOrderRepairStatus(o[14]==null?"":o[14].toString());
            model.setCharacter(o[15]==null?"":o[15].toString());
            model.setOrderProductFactory(o[16]==null?"":o[16].toString());
            model.setCheckTime(o[17]==null?"": DateUtil.fastDateFormat(o[17]));
            model.setDeliveryTime(o[18]==null?"": DateUtil.fastDateFormat(o[18]));
            model.setDeliveryExpressNumber(o[19]==null?"":o[19].toString());
            model.setRepairPlanCreateDate(o[20]==null?"": DateUtil.fastDateFormat(o[20]));
            model.setRepairConfirmTime(o[21]==null?"": DateUtil.fastDateFormat(o[21]));
            model.setRepairPlanReleaseTime(o[22]==null?"": DateUtil.fastDateFormat(o[22]));
            model.setProdTime(o[23]==null?"": DateUtil.fastDateFormat(o[23]));
            model.setFinishDate(o[24]==null?"": DateUtil.fastDateFormat(o[24]));
            model.setSendTime(o[25]==null?"": DateUtil.fastDateFormat(o[25]));
            model.setSendExpressNumber(o[26]==null?"":o[26].toString());
            model.setTechnicianName(o[27]==null?"":o[27].toString());
            model.setDuration(o[28]==null?"":o[28].toString());
            model.setOrderSendTime(o[29]==null?"": DateUtil.fastDateFormat(o[29]));
            model.setOrderCode(o[30]==null?"":o[30].toString());
            model.setGoodsSN(o[31]==null?"":o[31].toString());
            model.setEcOrderCode(o[32]==null?"":o[32].toString());
            model.setOrderId(o[33]==null?"":o[33].toString());
            model.setOrderDetailId(o[34]==null?"":o[34].toString());
            model.setRepairFeedback(o[35]==null?"":o[35].toString());
            model.setRepairOpinion(o[36]==null?"":o[36].toString());
            model.setBalanceStatus(o[37]==null?"":o[37].toString());
            model.setProductionFinishDate(o[38]==null?"":o[38].toString());
            model.setFrequency(o[39]==null?"":o[39].toString());
            model.setRepairFeedback(o[40]==null?"":o[40].toString());
            rows.add(model);
        }
        pagination.setRows(rows);
        return pagination;
    }

    @Override
    public void exportRepairProductionDetail(RepairProductionDetailDto dto, HttpServletRequest request, HttpServletResponse response) {
        List<RepairProductionDetailModel> list = findRepairProductionDetailByPage(dto).getRows();
        String excelFileName = "微调生产明细";
        OutputStream out = null;
        try {
            final String userAgent = request.getHeader("USER-AGENT");
            if (userAgent.toLowerCase().contains("msie")) {// IE浏览器
                excelFileName = URLEncoder.encode(excelFileName, "UTF8");
            } else if (userAgent.toLowerCase().contains("mozilla") || userAgent.toLowerCase().contains("chrom")) {// google浏览器,火狐浏览器
                excelFileName = new String(excelFileName.getBytes(), "ISO8859-1");
            } else {
                excelFileName = URLEncoder.encode(excelFileName, "UTF8");// 其他浏览器
            }
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.addHeader("content-Disposition", "attachment;filename=" + excelFileName + ".xls");
            response.flushBuffer();
            out = response.getOutputStream();
            String[] headers = new String[] {
                    "计划单号", "微调单号","会员姓名","款号", "款名","颜色", "类别", "店铺","微调单状态", "订单性质",
                    "原生产工厂" ,"星域审核日期" ,"收货日期","收货单号","计划创建日期","计划审核日期","计划下达日期","准备投产日期", "准备交货日期","生产完成日期", "发货日期",
                    "发货单号", "版师","时长（天）","原订单发货日期","孔雀订单号", "产品唯一码","EC订单号","微调次数","实调内容"};
            ExportExcelUtil.exportExcel(headers, list, out,"yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            log.error("导出失败！",e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                log.error("流关闭失败！",e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public Pagination<OrderRepairCopySearch> remoteOrderRepair(String q, PageBean pageBean) {
        Pagination<OrderRepairCopySearch> pagination = new Pagination<>();
        Criteria criteria = dao.createCriteria(OrderRepairCopySearch.class)
                .add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE));
        if (StringUtils.isNotBlank(q)) {
            criteria.add(Restrictions.like("orderRepairCode", q, MatchMode.ANYWHERE));
        }
        List<OrderRepairCopySearch> list = dao.findListWithPagebeanCriteria(criteria,pageBean);
        pagination.setRows(list);
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }

    private PageBean initPageBean(PageBean... pageBeans){
        if (!isPaging(pageBeans)){
            PageBean pageBean = new PageBean();
            pageBean.setPage(1);
            pageBean.setRows(Integer.MAX_VALUE);
            return pageBean;
        } else {
            if (null==pageBeans[0].getPage() || null==pageBeans[0].getRows()){
                pageBeans[0].setPage(1);
                pageBeans[0].setRows(10);
            }
            return pageBeans[0];
        }
    }

    private boolean isPaging(PageBean... pageBeans){
        return null!=pageBeans && pageBeans.length>0;
    }
}
