package com.kongque.service.productionRepair.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kongque.component.impl.JsonMapper;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.RepairSupplementDto;
import com.kongque.entity.basics.Code;
import com.kongque.entity.productionRepair.MesRepairProductionPlan;
import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplement;
import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplementMateriel;
import com.kongque.model.MaterialsModel;
import com.kongque.model.RepairSupplementModel;
import com.kongque.service.productionRepair.IRepairProductionPlanSupplementService;
import com.kongque.util.CodeUtil;
import com.kongque.util.DateUtil;
import com.kongque.util.HttpClientUtils;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class RepairProductionPlanSupplementServiceImpl implements IRepairProductionPlanSupplementService{
	
	@Resource
	private IDaoService dao;
	
	@Override
	public Pagination<MesRepairProductionPlanSupplement> list(RepairSupplementDto dto,PageBean pageBean){
		Criteria criteria=dao.createCriteria(MesRepairProductionPlanSupplement.class);
		if(StringUtils.isNotBlank(dto.getSupplementSn())){
			criteria.add(Restrictions.like("supplementSn", dto.getSupplementSn().trim(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getSupplementStatus())){
			criteria.add(Restrictions.eq("supplementStatus", dto.getSupplementStatus()));
		}
		if(StringUtils.isNotBlank(dto.getRepairPlanCode())){
			criteria.createCriteria("repairPlan","repairPlan").add(Restrictions.eq("repairPlan.planCode", dto.getRepairPlanCode()));
		}
		if(dto.getSupplementTimeStr()!=null && dto.getSupplementTimeEnd()!=null){
			criteria.add(Restrictions.between("supplementTime", dto.getSupplementTimeStr(), dto.getSupplementTimeEnd()));
		}else if(dto.getSupplementTimeStr()!=null && dto.getSupplementTimeEnd()==null){
			criteria.add(Restrictions.ge("supplementTime", dto.getSupplementTimeStr()));
		}else if(dto.getSupplementTimeStr()==null && dto.getSupplementTimeEnd()!=null){
			criteria.add(Restrictions.le("supplementTime", dto.getSupplementTimeEnd()));
		}
		criteria.add(Restrictions.eq("del", "0"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("supplementTime"));
		Pagination<MesRepairProductionPlanSupplement> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	@Override
	public Result saveOrUpdate(RepairSupplementDto dto){
		MesRepairProductionPlanSupplement rpps = new MesRepairProductionPlanSupplement();
		if(StringUtils.isBlank(dto.getId())){
			BeanUtils.copyProperties(dto, rpps);
			rpps.setSupplementSn(CodeUtil.createRepairPlanSupplementCode(getrepairPlanSupplementMaxValue()));
			rpps.setCreatetime(new Date());
			rpps.setSupplementStatus("0");
			rpps.setDel("0"); 
			dao.save(rpps);
			saveMateriel(rpps,dto);
		}else{
			BeanUtils.copyProperties(dto, rpps);
			rpps.setUpdateTime(new Date());
			dao.update(rpps);
			updateMateriel(rpps,dto);
		}
		MesRepairProductionPlan repairPlan = dao.findById(MesRepairProductionPlan.class, rpps.getRepairPlanId());
		rpps.setRepairPlanCode(repairPlan.getPlanCode()); 
		return new Result(rpps);
	}
	
	
	@Override
	public Result getInfoById(String id){
		RepairSupplementModel model = new RepairSupplementModel();
		MesRepairProductionPlanSupplement rpps = dao.findById(MesRepairProductionPlanSupplement.class, id);
		if(rpps!=null){
			model.setRepairPlanSupplement(rpps);
		}
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanSupplementMateriel.class);
		criteria.add(Restrictions.eq("supplementId", id));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanSupplementMateriel> list = criteria.list();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", list.get(i).getMaterialId());
				Result result = JsonMapper.toObject(HttpClientUtils.doGet(Constants.PRODUCTION.KONGQUE_PRODUCTION_INFOMATION,
						map), Result.class);
				// 如果数据正常返回
				if (Constants.RESULT_CODE.SUCCESS.equals(result.getReturnCode())) {
					JSONArray array=JSONArray.fromObject(result.getRows());
					Object o=array.get(0);
			        JSONObject jsonObject2=JSONObject.fromObject(o);
			        MaterialsModel stu2=(MaterialsModel)JSONObject.toBean(jsonObject2, MaterialsModel.class);
					list.get(i).setMaterial(stu2);
				}else{
					return new Result(result.getReturnCode(), result.getReturnMsg());
				}
			}
		}
		model.setMaterielList(list);
		return new Result(model);
	}

	@SuppressWarnings(value = "unchecked")
	@Override
	public Result getInfoByRepairPlanId(String repairPlanId) {
		if (StringUtils.isBlank(repairPlanId)){
			return new Result();
		}
		List<Map<String,Object>> returnList = new ArrayList<>();
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanSupplement.class)
				.add(Restrictions.eq("del", Constants.ENABLE_FLAG.ENABLE))
				.add(Restrictions.eq("repairPlanId",repairPlanId));
		List<MesRepairProductionPlanSupplement> list = criteria.list();
		if (list!=null && list.size()>0){
			for (MesRepairProductionPlanSupplement model : list) {
				Map<String,Object> supplementMap = new HashMap<>();
				supplementMap.put("supplementId",model.getId());
				supplementMap.put("supplementSn",model.getSupplementSn());
				Criteria criteriaMaterial = dao.createCriteria(MesRepairProductionPlanSupplementMateriel.class);
				criteriaMaterial.add(Restrictions.eq("supplementId", model.getId()));
				@SuppressWarnings("unchecked")
				List<MesRepairProductionPlanSupplementMateriel> materiels = criteriaMaterial.list();
				if(materiels!=null){
					Set<String> set = new HashSet<>();
					for (int i = 0; i < materiels.size(); i++) {
						set.add(materiels.get(i).getMaterialId());
					}
					if (set.size()==0) {
						set.add("-1");
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("ids", String.join(",",set));
					Result result = JsonMapper.toObject(HttpClientUtils.doGet(Constants.PRODUCTION.KONGQUE_PRODUCTION_INFOMATION,
							map), Result.class);
					// 如果数据正常返回
					if (Constants.RESULT_CODE.SUCCESS.equals(result.getReturnCode())) {
						JSONArray array=JSONArray.fromObject(result.getRows());
						supplementMap.put("materials",array);
					}else{
						return new Result(result.getReturnCode(), result.getReturnMsg());
					}
				}
				returnList.add(supplementMap);
			}
		}
		return new Result(returnList);
	}

	@Override
	public Result status(String id,String userId,String status){
		MesRepairProductionPlanSupplement rpps = dao.findById(MesRepairProductionPlanSupplement.class, id);
		rpps.setSupplementStatus(status);
		rpps.setConfirmerUserId(userId);
		rpps.setConfirmertime(new Date());
		dao.update(rpps);
		return new Result(rpps);
	}
	
	@Override
	public Result del(String id){
		MesRepairProductionPlanSupplement rpps = dao.findById(MesRepairProductionPlanSupplement.class, id);
		rpps.setDel("1");
		dao.update(rpps);
		return new Result("200","删除成功");
	}
	
	
	private void saveMateriel(MesRepairProductionPlanSupplement rpps,RepairSupplementDto dto){
		if(dto.getMaterielList()!=null && dto.getMaterielList().size()>0){
			for (int i = 0; i < dto.getMaterielList().size(); i++) {
				MesRepairProductionPlanSupplementMateriel rppsm = new MesRepairProductionPlanSupplementMateriel();
				rppsm.setSupplementId(rpps.getId());
				rppsm.setMaterialId(dto.getMaterielList().get(i).getMaterialId());
				rppsm.setQuantity(dto.getMaterielList().get(i).getQuantity());
				dao.save(rppsm);
			}
		}
	}
	private void updateMateriel(MesRepairProductionPlanSupplement rpps,RepairSupplementDto dto){
		Criteria criteria = dao.createCriteria(MesRepairProductionPlanSupplementMateriel.class);
		criteria.add(Restrictions.eq("supplementId", rpps.getId()));
		@SuppressWarnings("unchecked")
		List<MesRepairProductionPlanSupplementMateriel> list = criteria.list();
		if(list!=null){
			for (MesRepairProductionPlanSupplementMateriel mesRepairProductionPlanSupplementMateriel : list) {
				dao.delete(mesRepairProductionPlanSupplementMateriel);
			}
		}
		if(dto.getMaterielList()!=null && dto.getMaterielList().size()>0){
			for (int i = 0; i < dto.getMaterielList().size(); i++) {
				MesRepairProductionPlanSupplementMateriel rppsm = new MesRepairProductionPlanSupplementMateriel();
				rppsm.setSupplementId(rpps.getId());
				rppsm.setMaterialId(dto.getMaterielList().get(i).getMaterialId());
				rppsm.setQuantity(dto.getMaterielList().get(i).getQuantity());
				dao.save(rppsm);
			}
		}
	}
	
	private String getrepairPlanSupplementMaxValue() {
		Date date = new Date();
		Criteria criteria = dao.createCriteria(Code.class);
		criteria.add(Restrictions.between("updateTime", DateUtil.minDate(date), DateUtil.maxDate(date)));
		criteria.add(Restrictions.eq("type", "RPPS"));
		criteria.addOrder(org.hibernate.criterion.Order.desc("maxValue"));
		criteria.setMaxResults(1);
		Code code = (Code) criteria.uniqueResult();
		if (code == null) {
			code = new Code();
			code.setMaxValue(1);
			code.setType("RPPS");
			code.setUpdateTime(date);
			dao.save(code);
		} else {
			code.setMaxValue(code.getMaxValue() + 1);
		}
		return String.format("%0" + 6 + "d", code.getMaxValue());
	}

}
