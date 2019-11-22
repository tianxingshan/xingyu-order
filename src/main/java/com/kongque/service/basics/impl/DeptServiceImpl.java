package com.kongque.service.basics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.DeptDto;
import com.kongque.entity.basics.Company;
import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.basics.VDept;
import com.kongque.entity.basics.XiuyuShop;
import com.kongque.entity.basics.XiuyuShopCompanyRelation;
import com.kongque.entity.basics.XiuyuShopDirectorRelation;
import com.kongque.service.basics.IDeptService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;

@Service
public class DeptServiceImpl implements IDeptService {

	@Resource
	IDaoService dao;

	@Override
	public Pagination<Dept> DeptList(DeptDto dto, PageBean pageBean) {
		Criteria criteria = dao.createCriteria(Dept.class);
		if (!StringUtils.isBlank(dto.getDeptName())) {
			criteria.add(Restrictions.like("deptName", dto.getDeptName(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(dto.getDeptCode())) {
			criteria.add(Restrictions.like("deptCode", dto.getDeptCode(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(dto.getDeptType())) {
			criteria.add(Restrictions.eq("deptType", dto.getDeptType()));
		}
		if (!StringUtils.isBlank(dto.getCompanyId())) {
			criteria.createCriteria("companyList", "ll").createAlias("company", "o")
					.add(Restrictions.eq("o.id", dto.getCompanyId()));
		}
		if (!StringUtils.isBlank(dto.getTenantId())) {
			criteria.add(Restrictions.eq("deptTenantId", dao.findById(Tenant.class, dto.getTenantId())));
		}
		// criteria.createCriteria("deptTenantId", "o").add(Restrictions.eq("o.sysId",
		// "xingyu-order"));
		@SuppressWarnings("unchecked")
		List<Dept> list = criteria.list();
		for (int i = 0; i < list.size(); i++) {
			XiuyuShop shop = dao.findById(XiuyuShop.class, list.get(i).getId());
			list.get(i).setShop(shop);
		}
		if (pageBean.getPage() == null) {
			pageBean.setPage(0);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(999);
		}
		Pagination<Dept> pagination = new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@Override
	public Result saveOrUpdate(DeptDto dto) {
		if (StringUtils.isBlank(dto.getId())) {
			Criteria criteria = dao.createCriteria(Dept.class);
			criteria.add(Restrictions.eq("deptTenantId.id", dto.getDeptTenantId()));
			@SuppressWarnings("unchecked")
			List<Dept> list = criteria.list();
			for (Dept d : list) {
				if (d.getDeptCode().equals(dto.getDeptCode())) {
					return new Result("500", "该商户下部门编码已存在！");
				}
				if (d.getDeptName().equals(dto.getDeptName())) {
					return new Result("500", "该商户下部门名称已存在！");
				}
			}
			Dept dept = new Dept();
			dept.setDeptCode(dto.getDeptCode());
			dept.setDeptName(dto.getDeptName());
			dept.setDeptType(dto.getDeptType());
			dept.setDeptTenantId(dao.findById(Tenant.class, dto.getDeptTenantId()));
			dept.setDeptParentId(dto.getDeptParentId());
			dept.setDeptPhone(dto.getDeptPhone());
			dao.save(dept);
			if ("1".equals(dto.getDeptType())) {
				XiuyuShop xiuyuShop = new XiuyuShop();
				xiuyuShop.setId(dept.getId());
				xiuyuShop.setCreateTime(new Date());
				xiuyuShop.setOperatorId(dto.getOperatorId());
				xiuyuShop.setOperatorName(dto.getOperatorName());
				dao.save(xiuyuShop);
				if (StringUtils.isNotBlank(dto.getDeptParentId())) {
					XiuyuShopCompanyRelation x = new XiuyuShopCompanyRelation();
					x.setCompanyId(dto.getDeptParentId());
					x.setShopId(xiuyuShop.getId());
					dao.save(x);
				}

				// 自动授用户权限
				StringBuilder sb = new StringBuilder();
				sb.append("select a.c_id ");
				sb.append(" from t_user a");
				sb.append(" join t_user_dept_relation tudr ON a.c_id = tudr.c_user_id");
				sb.append(" where a.c_default_authority_flag=1  and tudr.c_dept_id in (");
				sb.append(" select c_parent_id");
				sb.append(" from t_dept td");
				sb.append(" where td.c_id='").append(dept.getId()).append("'");
				sb.append(" union ");
				sb.append(" select tt.c_id");
				sb.append(" from t_tenant tt");
				sb.append(" where tt.c_id='").append(dto.getDeptTenantId()).append("'");
				sb.append(" union ");
				sb.append(" select t.c_parent_id");
				sb.append(" from t_tenant t");
				sb.append(" where t.c_id='").append(dto.getDeptTenantId()).append("')");
				List<String> stringList = dao.queryBySql(sb.toString());
				for (String userId : stringList) {
					XiuyuShopDirectorRelation xiuyuShopDirectorRelation = new XiuyuShopDirectorRelation();
					xiuyuShopDirectorRelation.setUserId(userId);
					xiuyuShopDirectorRelation.setDeptId(dept.getId());
					dao.save(xiuyuShopDirectorRelation);
				}

			} else if ("2".equals(dto.getDeptType())) {
				Company company = new Company();
				company.setId(dept.getId());
				company.setUnitName(dto.getDeptName());
				company.setPhoneNo(dto.getDeptPhone());
				dao.save(company);
			}
			return new Result(dept);
		} else {
			Criteria criteriaDept = dao.createCriteria(Dept.class);
			criteriaDept.add(Restrictions.eq("deptTenantId.id", dto.getDeptTenantId()));
			criteriaDept.add(Restrictions.ne("id", dto.getId()));
			@SuppressWarnings("unchecked")
			List<Dept> depts = criteriaDept.list();
			for (Dept d : depts) {
				if (d.getDeptCode().equals(dto.getDeptCode())) {
					return new Result("500", "该商户下部门编码已存在！");
				}
				if (d.getDeptName().equals(dto.getDeptName())) {
					return new Result("500", "该商户下部门名称已存在！");
				}
			}
			Dept dept = dao.findById(Dept.class, dto.getId());
			if (!dept.getDeptName().equals(dto.getDeptName())) {
				Criteria criteria = dao.createCriteria(Dept.class);
				criteria.add(Restrictions.eq("deptTenantId.id", dto.getDeptTenantId()));
				@SuppressWarnings("unchecked")
				List<Dept> list = criteria.list();
				for (Dept d : list) {
					if (d.getDeptName().equals(dto.getDeptName())) {
						return new Result("500", "该商户下部门名称已存在！");
					}
				}
			}
			dept.setDeptCode(dto.getDeptCode());
			dept.setDeptName(dto.getDeptName());
			dept.setDeptType(dto.getDeptType());
			dept.setDeptTenantId(dao.findById(Tenant.class, dto.getDeptTenantId()));
			dept.setDeptParentId(dto.getDeptParentId());
			dept.setDeptPhone(dto.getDeptPhone());
			dao.update(dept);
			if ("2".equals(dto.getDeptType())) {
				Company company = dao.findById(Company.class, dept.getId());
				company.setUnitName(dto.getDeptName());
				dao.update(company);
			}
			return new Result(dept);
		}
	}

	@Override
	public Result delete(String id) {
		Dept dept = dao.findById(Dept.class, id);
		dao.delete(dept);
		return new Result(200);
	}

	@Override
	public Result parent(String tenantId) {
		return new Result(getDeptsByTenantId(tenantId));

	}

	@Override
	public Result getTenantDeps(DeptDto dto) {
		Criteria criteria = dao.createCriteria(VDept.class);
		List<VDept> list = criteria.list();

		Map<String, VDept> deptMap = list.stream().collect(Collectors.toMap(d -> ((VDept) d).getId(), r -> (VDept) r));

		// 用户门店关系
		List<String[]> rs = dao
				.queryBySql("select c_shop_id, count(c_shop_id) from t_xiuyu_shop_director_relation where c_user_id='"
						+ dto.getUserId() + "' GROUP BY c_shop_id");

		Map<String, Integer> map = rs.stream().collect(Collectors.toMap(r -> r[0], r -> Integer.parseInt(r[1])));
		//
		setChildList(deptMap, map);
		
		List<VDept> rsList=new ArrayList<>();
		
		if(StringUtils.isNotBlank(dto.getId()))
			rsList.add(deptMap.get(dto.getId()));
			
		sortChildList(rsList);
		
		return new Result(rsList);
	}

	@Override
	public Result saveShopListByUserId(DeptDto dto) {
		dao.deleteBySql("delete from t_xiuyu_shop_director_relation where c_user_id='" + dto.getUserId() + "'");
		for (int i = 0; i < dto.getShopIds().length; i++) {
			Tenant tenant = dao.findById(Tenant.class, dto.getShopIds()[i]);
			if (tenant == null) {
				XiuyuShopDirectorRelation s = new XiuyuShopDirectorRelation();
				s.setDeptId(dto.getShopIds()[i]);
				s.setUserId(dto.getUserId());
				dao.save(s);
			}
		}
		return new Result("200", "操作成功！");
	}

	private List<Dept> getDeptsByTenantId(String tenantId) {
		Criteria criteria = dao.createCriteria(Dept.class);
		if (StringUtils.isNotBlank(tenantId)) {
			criteria.add(Restrictions.eq("deptTenantId", dao.findById(Tenant.class, tenantId)));
		}
		@SuppressWarnings("unchecked")
		List<Dept> list = criteria.list();
		Map<String, List<Dept>> map = new HashMap<String, List<Dept>>();
		for (Dept c : list) {
			String key = StringUtils.isBlank(c.getDeptParentId()) ? "null" : c.getDeptParentId();
			if (null == map.get(key)) {
				map.put(key, new ArrayList<Dept>());
			}
			map.get(key).add(c);
		}
		for (Dept c : list) {
			c.setDeptList(map.get(c.getId()));
		}
		List<Dept> returnList = map.get("null");
		return returnList;
	}

	/**
	 * 循环设置子列表
	 * 
	 * @param list
	 * @param userId
	 * @return
	 */
	private void setChildList(Map<String, VDept> deptMap, Map<String, Integer> map) {
		for (Entry<String, VDept> e : deptMap.entrySet()) {
			VDept vDept = e.getValue();
			if ("1".equals(vDept.getType())) {
				if (null != (map.get(vDept.getId())))
					vDept.setFlag(map.get(vDept.getId()));
			}
			if (StringUtils.isNotBlank(vDept.getParentId())) {
				VDept v = deptMap.get(vDept.getParentId());
				if (v != null) {
					List<VDept> t = deptMap.get(vDept.getParentId()).getChildren();
					if (t == null)
						t = new ArrayList<>();
					t.add(vDept);
					deptMap.get(vDept.getParentId()).setChildren(t);
				}
			}
		}

	}
	
	
	private void sortChildList( List<VDept> list) {
		
		if(list!=null&&list.size()>0) {
			list.sort((a,b)->a.getId().compareTo(b.getId()));
			
			list.forEach(v->{
				
				if(v.getChildren()!=null&&v.getChildren().size()>0) {
					sortChildList(v.getChildren());
				}
				
			});
			
		}
	}
	

}
