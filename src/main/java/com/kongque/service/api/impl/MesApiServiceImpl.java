package com.kongque.service.api.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.MesProcessLibrary;
import com.kongque.entity.process.MesWorkstation;
import com.kongque.entity.process.MesWorkstationProcessRelation;
import com.kongque.entity.productionorder.MesOrderDetailMeasure;
import com.kongque.model.ARTModel;
import com.kongque.service.api.IMesApiService;
import com.kongque.util.Result;
import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Administrator
 */
@Service
public class MesApiServiceImpl implements IMesApiService {

    @Resource
    private IDaoService dao;

    @Override
    public ARTModel getStaffByCardNo(String cardNo) {

        ARTModel model = new ARTModel();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c_code,c_name FROM mes_staff WHERE c_make_card_no='").append(cardNo).append("'");
        Object obj = dao.uniqueBySql(sql.toString());
        if(obj !=null) {
            Object[] properties = (Object[]) obj;
            model.setEmpCode(properties[0]==null ? "" : properties[0].toString());
            model.setEmpName(properties[1]==null ? "" : properties[1].toString());
        }
        return model;
    }

    @Override
    public Result insertProcess(ARTModel model) {
        String mesWorkstationId="",mesProcessLibraryId="";
        MesWorkstationProcessRelation entity = new MesWorkstationProcessRelation();
        List<MesWorkstation> mesWorkstationList = dao.findListByProperty(MesWorkstation.class,"terminalNumber",model.getDevNo());
        if(mesWorkstationList.size()> 0){
            mesWorkstationId = mesWorkstationList.get(0).getId();
        }else {
            return new Result("500","终端设备号没有设置!");
        }
        List<MesProcessLibrary> processList = dao.findListByProperty(MesProcessLibrary.class,"makeCardNo",model.getMaterialCardId());
        if(processList.size()> 0){
            mesProcessLibraryId = processList.get(0).getId();
        }else {
            return new Result("500","工序不存在!");
        }
        Criteria criteria = dao.createCriteria(MesWorkstationProcessRelation.class);
        criteria.add(Restrictions.eq("mesWorkstationId",mesWorkstationId)).add(Restrictions.eq("mesProcessLibraryId",mesProcessLibraryId));
        List<MesWorkstationProcessRelation> entitys =  criteria.list();
        if(entitys.size()> 0){
            entity = entitys.get(0);
            entity.setCreateTime(new Date());
            dao.update(entity);
        }else{
            entity.setMesProcessLibraryId(mesProcessLibraryId);
            entity.setMesWorkstationId(mesWorkstationId);
            entity.setCreateTime(new Date());
            dao.save(entity);
        }
        ARTModel  m = new ARTModel();
        m.setProcessCode(processList.get(0).getCode());
        m.setProcessName(processList.get(0).getName());
        return new Result(m);
    }

    @Override
    public Result deleteProcess(ARTModel model) {
        String mesWorkstationId="";
        List<MesWorkstation> mesWorkstationList = dao.findListByProperty(MesWorkstation.class,"terminalNumber",model.getDevNo());
        if(mesWorkstationList.size()> 0){
            mesWorkstationId = mesWorkstationList.get(0).getId();
        }else {
            return new Result("500","终端设备号没有设置!");
        }

        Criteria criteria = dao.createCriteria(MesWorkstationProcessRelation.class);
        criteria.add(Restrictions.eq("mesWorkstationId",mesWorkstationId));
        List<MesWorkstationProcessRelation> entitys =  criteria.list();
        dao.deleteAllEntity(entitys);
        return new Result();
    }

    @Override
    public Result scan(ARTModel model) {
        String orderDetailId="";
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT tor.c_customer_name,tg.c_name AS style_name,tgd.c_color_name,mods.c_mes_measure_size_name,tod.c_id AS order_detail_id " +
                " FROM mes_make_card mmc" +
                " JOIN t_order_detail tod ON mmc.c_order_detail_id = tod.c_id" +
                " JOIN t_order tor ON tod.c_order_id = tor.c_id" +
                " JOIN t_goods_detail tgd ON tod.c_goods_detail_id = tgd.c_id" +
                " JOIN t_goods tg ON tgd.c_goods_id = tg.c_id " +
                " JOIN mes_order_detail_size mods ON tod.c_id = mods.c_order_detail_id" +
                " WHERE mmc.c_status='1' AND mmc.c_card_code='"+model.getMaterialCardId()+"'");
        List list = dao.queryBySql(sb.toString());
        ListOrderedMap map = new ListOrderedMap();
        if(list.size()> 0){
            Object object = list.get(0);
            Object[] properties = (Object[]) object;
            map.put("customerName",properties[0]);
            map.put("styleName",properties[1]);
            map.put("styleColor",properties[2]);
            map.put("styleSize",properties[3]);
            orderDetailId = properties[4].toString();
        }
        sb.delete(0,sb.length());
        sb.append(" SELECT mpl.c_code,mpl.c_name" +
                " FROM mes_order_detail_sewing_process modsp" +
                " JOIN mes_process_library mpl ON modsp.c_process_id = mpl.c_id" +
                " JOIN mes_workstation_process_relation mwpr ON mpl.c_id = mwpr.c_mes_process_library_id AND mwpr.c_status='0'" +
                " JOIN mes_workstation mw ON mwpr.c_mes_workstation_id = mw.c_id AND mw.c_status='0'" +
                " WHERE modsp.c_order_detail_id='"+orderDetailId+"' AND mw.c_terminal_number='"+model.getDevNo()+"'");

        List resultSet = dao.queryBySql(sb.toString());

        List<Map<String,Object>> mapList = new ArrayList<>();
        for(Object object :resultSet){
            Map<String,Object> objectMap = new HashMap<>();
            Object[] properties = (Object[]) object;
            objectMap.put("processCode",properties[0]);
            objectMap.put("processName",properties[1]);
            mapList.add(objectMap);
        }
        map.put("processInfoList",mapList);

        return new Result(map);
    }
}
/**
 * @program: xingyu-order
 * @description: 用于MES接口调用
 * @author: zhuxl
 * @create: 2019-08-07 09:19
 **/
