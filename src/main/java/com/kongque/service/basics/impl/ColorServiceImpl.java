package com.kongque.service.basics.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kongque.dao.IDaoService;
import com.kongque.dto.ColorDto;
import com.kongque.entity.basics.Color;
import com.kongque.service.basics.IColorService;
import com.kongque.util.DateUtil;
import com.kongque.util.FileOSSUtil;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;
import net.sf.json.JSONArray;

@Service
public class ColorServiceImpl implements IColorService{
	
	@Resource
	IDaoService dao;
	@Resource
	private FileOSSUtil fileOSSUtil;

	@Override
	public Pagination<Color> colorList(ColorDto dto, PageBean pageBean) {
		Criteria criteria = dao.createCriteria(Color.class);
		if(!StringUtils.isBlank(dto.getCode())){
			criteria.add(Restrictions.like("code", dto.getCode(), MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(dto.getName())){
			criteria.add(Restrictions.like("name", dto.getName(), MatchMode.ANYWHERE));
		}
		if(pageBean.getPage() == null) {
			pageBean.setPage(0);
		}
		if(pageBean.getRows() == null) {
			pageBean.setRows(999);
		}
		Pagination<Color> pagination=new Pagination<>();
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	
	@Override
	public Result saveOrUpdate(ColorDto dto,MultipartFile[] files) {
		if(StringUtils.isBlank(dto.getId())){
			Criteria criteria=dao.createCriteria(Color.class);
			List<Color> list=criteria.list();
			for(Color s:list){
				if(s.getCode().equals(dto.getCode())){
					return new Result("500","颜色编码不能重复");
				}
			}
			Set<String> imageKeys = null;
			Color color=new Color();
			color.setCode(dto.getCode());
			color.setName(dto.getName());
			if (files!= null) {
				imageKeys = appendOrderImage(files);
				color.setOssKey(imageKeys.toString());
				color.setFileName(dto.getFileName());
			}
			dao.save(color);
			return new Result(color);
			}
		else{
			Color color=dao.findById(Color.class, dto.getId());
			if(!color.getCode().equals(dto.getCode())){
				Criteria criteria=dao.createCriteria(Color.class);
				List<Color> list=criteria.list();
				for(Color s:list){
					if(s.getCode().equals(dto.getCode())){
						return new Result("500","颜色编码不能重复");
					}
				}
			}
			color.setCode(dto.getCode());
			color.setName(dto.getName());
			String[] delFileList=dto.getImageDelKeys();
			// 删除商品图片文件信息并同步到数据库中
			if (delFileList != null && delFileList.length > 0) {
				delOSSFile(color,delFileList);
			}
			Set<String> imageKeys = null;
			if (files!= null&&files.length>0) {
				imageKeys = appendOrderImage(files);
				if(imageKeys!=null){
//					JSONArray imgKeys = color.getOssKey() == null || color.getOssKey().isEmpty()
//							? new JSONArray() : JSONArray.fromObject(color.getOssKey());// 初始化JSON数组对象
//					for (String key : imageKeys) {
//						imgKeys.add(key);
//					}
					color.setOssKey(imageKeys.toString());
				}
			}   
			color.setFileName(dto.getFileName());
			dao.update(color);
			return new Result(color);
		}
	}

	@Override
	public Result delete(String id) {
		Color color=dao.findById(Color.class,id);
		dao.delete(color);
		return new Result(color);
	}
	private Set<String> appendOrderImage(MultipartFile[] file) {
		Set<String> addedImageNames = null;
		if (file == null) {// 如果没有上传任何图片文件
			return addedImageNames;
		}
		addedImageNames = new HashSet<>();// 初始化保存失败的上传文件名称列表
		for (MultipartFile imageFile : file) {// 遍历上传的图片文件
			try {
				String newKey = getFileFolder(new Date()) + imageFile.getOriginalFilename();
				newKey = fileOSSUtil.uploadPublicReadFile(newKey, imageFile.getInputStream());
				String keys = "\"" + newKey + "\"";
				// 把上传文件保存到OSS系统，并把OSS系统保存文件成功后返回的该文件的key添加到JSON数组中
				addedImageNames.add(keys);
			} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
				e.printStackTrace();
			}
		}
		return addedImageNames;// 返回保存失败的上传文件名称列表	
	}

	private String getFileFolder(Date date) {

		return DateUtil.formatDate(date, "yyyy") + "/" + DateUtil.formatDate(date, "MM") + "/"
				+ DateUtil.formatDate(date, "dd") + "/";
	}
	/**
	 * 上传试穿图片
	 * 
	 * @param file
	 * @return
	 */
	private Set<String> appendOrderImage(Color color,MultipartFile[] file) {
		Set<String> addedImageNames = null;
		if (file == null) {// 如果没有上传任何图片文件
			return addedImageNames;
		}
		JSONArray imgKeys = color.getOssKey()== null || color.getOssKey().isEmpty() ? new JSONArray()
				: JSONArray.fromObject(color.getOssKey());// 初始化JSON数组对象
		addedImageNames = new HashSet<>();// 初始化保存失败的上传文件名称列表
		for (MultipartFile imageFile : file) {// 遍历上传的图片文件
			try {
				String newKey = getFileFolder(new Date()) + imageFile.getOriginalFilename();
				imgKeys.add(fileOSSUtil.uploadPublicReadFile(newKey, imageFile.getInputStream()));
				// 把上传文件保存到OSS系统，并把OSS系统保存文件成功后返回的该文件的key添加到JSON数组中
				addedImageNames.add(newKey);
			} catch (IOException e) {// 当读取上传图片文件输入流抛出异常时
				e.printStackTrace();
			}
		}
		if (!addedImageNames.isEmpty()) {// 如果OSS成功保存的文件不为空
			color.setOssKey(imgKeys.toString());// 把保存在OSS系统中各文件对应的key所形成的JSON字符串保存到商品对应的属性中
			dao.update(color);// 更新数据库中的商品信息
			dao.flush();// 释放缓冲区
		}
		return addedImageNames;// 返回保存失败的上传文件名称列表
	}
	/**
	 * 删除上传到oss的文件
	 * 
	 * @param dto
	 * @return
	 */
	public String[] delOSSFile(Color color, String[] delFileList) {
		if (delFileList != null) {
			JSONArray imgKeys = color.getOssKey() == null || color.getOssKey() .isEmpty() ? new JSONArray()
					: JSONArray.fromObject(color.getOssKey() );// 初始化JSON数组对象
			for (String imgKey : delFileList) {
//				imgKey = imgKey.substring(2,imgKey.length()-2);
				String removeImgKey = fileOSSUtil.fromUrlToKey(imgKey);
				imgKeys.remove(imgKey);
				// 在oss 删除 文件
				fileOSSUtil.deletePublicReadFile(removeImgKey);
			}
			color.setOssKey(imgKeys.toString());
			dao.update(color);
			dao.flush();// 释放缓冲区
		}
		return delFileList;
	}
}
