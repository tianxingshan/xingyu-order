package com.kongque.service.goods.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.kongque.constants.Constants;
import com.kongque.entity.basics.MeasureCategory;
import com.kongque.entity.goods.GoodsDetailSelect;
import com.kongque.entity.order.OrderDetail;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kongque.dao.IDaoService;
import com.kongque.dto.GoodsDto;
import com.kongque.entity.basics.Category;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.goods.Goods;
import com.kongque.entity.goods.GoodsDetail;
import com.kongque.entity.goods.GoodsDetailTanant;
import com.kongque.service.goods.IGoodsService;
import com.kongque.util.FileOSSUtil;
import com.kongque.util.HttpUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Service
public class GoodsServiceImpl implements IGoodsService{
	
	@Resource
	IDaoService dao;
	@Resource
	private FileOSSUtil fileOSSUtil;

	@Override
	public Pagination<Goods> list(GoodsDto dto, PageBean pageBean) {
		Criteria criteria=dao.createCriteria(Goods.class);
		if(!StringUtils.isBlank(dto.getCode())){
			criteria.add(Restrictions.like("code", dto.getCode().trim(), MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(dto.getName())){
			criteria.add(Restrictions.like("name", dto.getName().trim(), MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(dto.getStatus())){
			criteria.add(Restrictions.eq("status", dto.getStatus()));
		}
		if(!StringUtils.isBlank(dto.getQ())){
			criteria.add(Restrictions.or(Restrictions.like("code",dto.getQ().trim(),MatchMode.ANYWHERE),
        			Restrictions.like("name",dto.getQ().trim(),MatchMode.ANYWHERE)));
		}
		if (StringUtils.isNotBlank(dto.getForOrder())){
			criteria.add(Restrictions.eq("forOrder",dto.getForOrder()));
		}
		@SuppressWarnings("unchecked")
		List<Goods> list = criteria.list();
		for (int i = 0; i < list.size(); i++) {
			List<GoodsDetail> goodsDetailList = list.get(i).getGoodsDetailList();
			if(goodsDetailList!=null && goodsDetailList.size()>0){
				for (int j = 0; j < goodsDetailList.size(); j++) {
					List<GoodsDetailTanant> listGoodsDetailTanant = goodsDetailList.get(j).getListGoodsDetailTanant();
					if(listGoodsDetailTanant!=null && listGoodsDetailTanant.size()>0){
						for (int k = 0; k < listGoodsDetailTanant.size(); k++) {
							Tenant tenant = listGoodsDetailTanant.get(k).getTenant();
							if(tenant!=null){
								listGoodsDetailTanant.get(k).setTenantName(tenant.getTenantName()); 
							}
						}
					}
				}
			}
		}
		Pagination<Goods> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@Override
	public Pagination<GoodsDetailSelect> listDetail(GoodsDto dto, PageBean pageBean) {
		Criteria criteria=dao.createCriteria(GoodsDetailSelect.class);
		criteria.createAlias("goodsSelect","goodsSelect");
		if(!StringUtils.isBlank(dto.getCode())){
			criteria.add(Restrictions.like("goodsSelect.code", dto.getCode().trim(), MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(dto.getName())){
			criteria.add(Restrictions.like("goodsSelect.name", dto.getName().trim(), MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(dto.getQ())){
			criteria.add(Restrictions.or(Restrictions.like("goodsSelect.code",dto.getQ().trim(),MatchMode.ANYWHERE),
					Restrictions.like("goodsSelect.name",dto.getQ().trim(),MatchMode.ANYWHERE)));
		}
		Pagination<GoodsDetailSelect> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@Override
	public Result saveOrUpdate(GoodsDto dto) {
		Goods goods=new Goods();
		BeanUtils.copyProperties(dto, goods);
		goods.setCategory(dao.findById(Category.class, dto.getCategoryId()));
		if(StringUtils.isNotBlank(dto.getMeasureCategoryId())){
			goods.setMeasureCategory(dao.findById(MeasureCategory.class,dto.getMeasureCategoryId()));
		}
		if(StringUtils.isBlank(dto.getId())){
			goods.setCreateTime(new Date());
			dao.save(goods);
			saveGoodsDetail(goods,dto);
			return new Result(goods);
		}else{
			goods.setUpdateTime(new Date());
			dao.update(goods);
			updateGoodsDetail(goods,dto);
			return new Result(goods);
		}
	}
	
	private void saveGoodsDetail(Goods goods,GoodsDto dto){
		if(dto.getListGoodsDetail()!=null){
			List<GoodsDetail> goodsDetails = new ArrayList<>();
			for (int i = 0; i < dto.getListGoodsDetail().size(); i++) {
				GoodsDetail goodsDetail=new GoodsDetail();
				goodsDetail.setGoodsId(goods.getId());
				goodsDetail.setColorName(dto.getListGoodsDetail().get(i).getColorName());
				goodsDetail.setCostPrice(dto.getListGoodsDetail().get(i).getCostPrice());
				goodsDetail.setImageKeys(dto.getListGoodsDetail().get(i).getImageKeys());
				goodsDetail.setMaterielCode(dto.getListGoodsDetail().get(i).getMaterielCode());
				if (StringUtils.isBlank(dto.getListGoodsDetail().get(i).getStatus())){
					goodsDetail.setStatus(Constants.ENABLE_FLAG.ENABLE);
				} else {
					goodsDetail.setStatus(dto.getListGoodsDetail().get(i).getStatus());
				}
				dao.save(goodsDetail);
				goodsDetails.add(goodsDetail);
			}
			goods.setGoodsDetailList(goodsDetails);
		}
	}
	
	private void updateGoodsDetail(Goods goods,GoodsDto dto){
		List<GoodsDetail> goodsDetails = new ArrayList<>();
		if(dto.getListGoodsDetail()!=null){
			for (int i = 0; i < dto.getListGoodsDetail().size(); i++) {
				//如果详情id不为空，则进行修改
				GoodsDetail goodsDetail=new GoodsDetail();
				if(StringUtils.isNotBlank(dto.getListGoodsDetail().get(i).getId())){
					goodsDetail = dao.findById(GoodsDetail.class, dto.getListGoodsDetail().get(i).getId());
					goodsDetail.setColorName(dto.getListGoodsDetail().get(i).getColorName());
					goodsDetail.setCostPrice(dto.getListGoodsDetail().get(i).getCostPrice());
					goodsDetail.setImageKeys(dto.getListGoodsDetail().get(i).getImageKeys());
					goodsDetail.setMaterielCode(dto.getListGoodsDetail().get(i).getMaterielCode());
					if (StringUtils.isNotBlank(dto.getListGoodsDetail().get(i).getStatus())){
						goodsDetail.setStatus(dto.getListGoodsDetail().get(i).getStatus());
					}
					dao.update(goodsDetail);

				}else{
					//如果详情id为空，则进行新增

					goodsDetail.setGoodsId(goods.getId());
					goodsDetail.setColorName(dto.getListGoodsDetail().get(i).getColorName());
					goodsDetail.setCostPrice(dto.getListGoodsDetail().get(i).getCostPrice());
					goodsDetail.setImageKeys(dto.getListGoodsDetail().get(i).getImageKeys());
					goodsDetail.setMaterielCode(dto.getListGoodsDetail().get(i).getMaterielCode());
					if (StringUtils.isBlank(dto.getListGoodsDetail().get(i).getStatus())){
						goodsDetail.setStatus(Constants.ENABLE_FLAG.ENABLE);
					} else {
						goodsDetail.setStatus(dto.getListGoodsDetail().get(i).getStatus());
					}
					dao.save(goodsDetail);
				}
				goodsDetails.add(goodsDetail);
			}
			goods.setGoodsDetailList(goodsDetails);
		}
	}
	
	@Override
	public Result updateStatus(String id,String status){
		Goods goods = dao.findById(Goods.class, id);
		goods.setStatus(status);
		dao.update(goods);
		return new Result(goods); 
	}
	
	@Override
	public Result uploadFile(MultipartFile[] files,String[] imageDelKeys){
		if(imageDelKeys!=null){
			delOSSFile(imageDelKeys);
		}
		Set<String> imageKeys = null;
		if(files!=null){
			imageKeys = appendOrderImage(files);
		}
		return new Result(imageKeys.toString());
	}
	
	private Set<String> appendOrderImage(MultipartFile[] file) {
		Set<String> addedImageNames = null;
		if (file == null) {// 如果没有上传任何图片文件
			return addedImageNames;
		}
		addedImageNames = new HashSet<>();// 初始化保存失败的上传文件名称列表
		for (MultipartFile imageFile : file) {// 遍历上传的图片文件
			try {
				String newKey = "xingyu/goods/" + UUID.randomUUID().toString().replace("-", "") + "." + imageFile.getOriginalFilename();
				newKey = fileOSSUtil.uploadPrivateFile(newKey, imageFile.getInputStream());
				String keys = "\"" + newKey + "\"";
				// 把上传文件保存到OSS系统，并把OSS系统保存文件成功后返回的该文件的key添加到JSON数组中
				addedImageNames.add(keys);
			} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
				e.printStackTrace();
			}
		}
		return addedImageNames;// 返回保存失败的上传文件名称列表	
	}

//	private String getFileFolder(Date date) {
//		return DateUtil.formatDate(date, "yyyy") + "/" + DateUtil.formatDate(date, "MM") + "/"
//				+ DateUtil.formatDate(date, "dd") + "/";
//	}
	
	
	
	/**
	 * 删除上传到oss的文件
	 * 
	 * @param delFileList
	 * @return
	 */
	public String[] delOSSFile(String[] delFileList) {
		if (delFileList != null) {
			for (String imgKey : delFileList) {
				String removeImgKey = fileOSSUtil.fromUrlToKey(imgKey);
				// 在oss 删除 文件
				fileOSSUtil.deletePrivateFile(removeImgKey);
			}
		}
		return delFileList;
	}
	
	public void getAttachment(String filePath) {
		OutputStream outputStream = null;
		try {
			if (StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				HttpServletResponse response = HttpUtil.getHttpServletResponse();
				response.reset();
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
				outputStream = response.getOutputStream();
				outputStream.write(fileOSSUtil.getPrivateObject(filePath));
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public Result getAllTenantsByGoodsDetailId(String goodsDetailId) {
		List<Map<String, Object>> lists = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT 0 AS flag,t.c_id  AS tenantId,t.c_tenant_name AS tenantName,0 balancePrice,0 AS tenantPrice");
		sb.append(" FROM t_tenant t");
		sb.append(" WHERE t.c_del = 0 AND t.c_parent_id='00' AND t.c_id NOT IN (SELECT r.c_tenant_id FROM t_goods_detail_tanant_relation r WHERE r.c_goods_detail_id='"+goodsDetailId+"')");
		sb.append(" UNION ALL");
		sb.append(" SELECT 1 AS flag,r.c_tenant_id  AS tenantId,t.c_tenant_name AS tenantName,r.c_balance_price AS balancePrice,r.c_tenant_price AS tenantPrice");
		sb.append(" FROM t_goods_detail_tanant_relation r");
		sb.append(" JOIN t_tenant t ON r.c_tenant_id = t.c_id");
		sb.append(" WHERE r.c_goods_detail_id='"+goodsDetailId+"'");
		List<Object> list = dao.queryBySql(sb.toString());
		for(Object object :list){
			Object[] properties = (Object[])object;
			Map<String, Object> map = new HashMap<>();
			map.put("flag", properties[0]==null?"0":properties[0]);
			map.put("tenantId", properties[1]==null?"":properties[1]);
			map.put("tenantName", properties[2]==null?"":properties[2]);
			map.put("balancePrice", properties[3]==null?"0":properties[3]);
			map.put("tenantPrice", properties[4]==null?"0":properties[4]);
			lists.add(map);
		}
		return new Result(lists);
	}

	@Override
	public Result saveGoodsDetailTenant(List<GoodsDetailTanant> list) {
		if(list.size() > 0 ) {
			if (StringUtils.isBlank(list.get(0).getGoodsDetailId())){
				return new Result("500","请先保存商品明细信息");
			}
			dao.deleteBySql("DELETE FROM t_goods_detail_tanant_relation WHERE c_goods_detail_id='"+list.get(0).getGoodsDetailId()+"'");
			if(StringUtils.isNotBlank(list.get(0).getTenantId())) {
				dao.saveAllEntity(list);
			}
		}
		return new Result(list);
	}

	@Override
	public Result deleteGoodsDetail(String id){
		GoodsDetail entity = dao.findById(GoodsDetail.class,id);
		if (entity ==null ){
			return new Result("500","id不存在!");
		}
		List<OrderDetail> orderDetails= dao.findListByProperty(OrderDetail.class,"goodsDetailId",id);
		if(orderDetails.size()>0){
			return new Result("500","商品明细已被使用,不能删除!");
		}
		List<GoodsDetailTanant> goodsDetailTanants = dao.findListByProperty(GoodsDetailTanant.class,"goodsDetailId",id);
		if(goodsDetailTanants.size()> 0 ){
			return new Result("500","请先删除商品明细对应的商户!");
		}
		dao.delete(entity);
		return new Result();
	}
}
