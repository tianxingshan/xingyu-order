package com.kongque.service.file;

import java.io.File;
import java.io.InputStream;

import com.kongque.util.PropertiesUtils;

public interface IFileOssService {

	/**
	 * 上传公共OSS 
	 * @param key oss文件唯一标识，自己生成唯一key，包含路径和名字 
	 * 如： 2017/08/唯一标识/1.jpg
	 * @param inputStream
	 * @return url
	 */
	public String uploadPublicReadFileUrl(String key, InputStream inputStream);
	
	/**
	 * 文件删除(公共读)
	 * 
	 * @param key
	 *            文件唯一标识
	 */
	void deletePublicReadFile(String key);

	public static String fromKeyToUrl(String imageKey) {
		PropertiesUtils propertiesUtils = new PropertiesUtils(File.separator + "dao.properties");// 获取配置文件：base.properties
		return propertiesUtils.getString("oss.publicReadFileUrlPre") + imageKey;
	}

	public static String[] fromKeyToUrl(String[] imageKeys) {
		if (imageKeys != null) {
			PropertiesUtils propertiesUtils = new PropertiesUtils(File.separator + "dao.properties");// 获取配置文件：base.properties
			for (int i = 0; i < imageKeys.length; ++i) {
				imageKeys[i] = propertiesUtils.getString("oss.publicReadFileUrlPre") + imageKeys[i];
			}
		}
		return imageKeys;
	}

	public static String fromUrlToKey(String imageUrl) {
		PropertiesUtils propertiesUtils = new PropertiesUtils(File.separator + "dao.properties");// 获取配置文件：base.properties
		return imageUrl.substring(propertiesUtils.getString("oss.publicReadFileUrlPre").length()+1);
	}

	public static String[] fromUrlToKey(String[] imageUrls) {
		if (imageUrls != null) {
			PropertiesUtils propertiesUtils = new PropertiesUtils(File.separator + "dao.properties");// 获取配置文件：base.properties
			for (int i = 0; i < imageUrls.length; ++i) {
				imageUrls[i] = imageUrls[i].substring(propertiesUtils.getString("oss.publicReadFileUrlPre").length());
			}
		}
		return imageUrls;
	}
}
