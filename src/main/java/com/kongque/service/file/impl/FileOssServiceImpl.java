package com.kongque.service.file.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.kongque.util.PropertiesUtils;
import org.apache.commons.io.IOUtils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.kongque.service.file.IFileOssService;

public class FileOssServiceImpl implements IFileOssService {

	private String accessKeyId;
	private String accessKeySecret;
	private String privateBucketName;
	private String publicReadBucketName;
	private String endpoint;
	private String publicReadFileUrlPre;

	private int maxConnections;
	private int socketTimeout;
	private int maxErrorRetry;

	public FileOssServiceImpl() {
		super();
		PropertiesUtils propertiesUtils = new PropertiesUtils(File.separator + "oss.properties");// 获取配置文件：dao.properties
		this.accessKeyId = propertiesUtils.getString("oss.accessKeyId");
		this.accessKeySecret = propertiesUtils.getString("oss.accessKeySecret");
		this.privateBucketName = propertiesUtils.getString("oss.bucketName.private");
		this.publicReadBucketName = propertiesUtils.getString("oss.bucketName.publicRead");
		this.endpoint = propertiesUtils.getString("oss.endpoint");
		this.publicReadFileUrlPre = propertiesUtils.getString("oss.publicReadFileUrlPre");
		this.maxConnections = propertiesUtils.getInt("oss.maxConnections");
		this.socketTimeout = propertiesUtils.getInt("oss.socketTimeout");
		this.maxErrorRetry = propertiesUtils.getInt("oss.maxErrorRetry");
	}

    public String uploadPublicReadFileUrl(String key,InputStream inputStream) {
        OSSClient ossClient = getOSSClient();
        String bucketName = publicReadBucketName;
        //不能以  / 开头
        if(key.startsWith("/")) {
            key = key.substring(1);
        }
        //创建文件夹
        if(-1 != key.lastIndexOf("/")) {
            String folder = key.substring(0, key.lastIndexOf("/")+1);
            if(!ossClient.doesObjectExist(bucketName, folder)) {
                ossClient.putObject(bucketName, folder, new ByteArrayInputStream(new byte[0]));
            }
        }
        //上传文件
        ossClient.putObject(bucketName, key, inputStream);
        ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        ossClient.shutdown();
        return publicReadFileUrlPre+"/"+key;
    }

	
	private OSSClient getOSSClient() {
		ClientConfiguration conf = new ClientConfiguration();
		conf.setMaxConnections(maxConnections);
		conf.setSocketTimeout(socketTimeout);
		conf.setMaxErrorRetry(maxErrorRetry);

		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
		return ossClient;
	}

	/**
	 * 文件上传到oss(私有)
	 * 
	 * @param key
	 *            文件唯一标识 eg： 2017/08/1.jpg 其中2017/08/ 为文件夹路径，1.jpg为文件名，不能以 / 开头
	 * @param inputStream
	 *            文件流
	 * @return key
	 */
	public String uploadPrivateFile(String key, InputStream inputStream) {
		OSSClient ossClient = getOSSClient();
		String bucketName = privateBucketName;
		// 不能以 / 开头
		if (key.startsWith("/")) {
			key = key.substring(1);
		}
		// 创建文件夹
		if (-1 != key.lastIndexOf("/")) {
			String folder = key.substring(0, key.lastIndexOf("/") + 1);
			if (!ossClient.doesObjectExist(bucketName, folder)) {
				ossClient.putObject(bucketName, folder, new ByteArrayInputStream(new byte[0]));
			}
		}
		// 上传文件
		ossClient.putObject(bucketName, key, inputStream);
		ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Private);
		ossClient.shutdown();
		return key;
	}

	/**
	 * 文件上传到oss(公共读)
	 * 
	 * @param key
	 *            文件唯一标识 eg： 2017/08/1.jpg 其中2017/08/ 为文件夹路径，1.jpg为文件名，不能以 / 开头
	 * @param inputStream
	 *            文件流
	 * @return 文件url
	 */
	public String uploadPublicReadFile(String key, InputStream inputStream) {
		OSSClient ossClient = getOSSClient();
		String bucketName = publicReadBucketName;
		// 不能以 / 开头
		if (key.startsWith("/")) {
			key = key.substring(1);
		}
		// 创建文件夹
		if (-1 != key.lastIndexOf("/")) {
			String folder = key.substring(0, key.lastIndexOf("/") + 1);
			if (!ossClient.doesObjectExist(bucketName, folder)) {
				ossClient.putObject(bucketName, folder, new ByteArrayInputStream(new byte[0]));
			}
		}
		// 上传文件
		ossClient.putObject(bucketName, key, inputStream);
		ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
		ossClient.shutdown();
		return key;
	}

	/**
	 * 文件获取(私有)
	 *
	 * @param key
	 *            文件唯一标识
	 * @return 文件byte数组
	 */
	public byte[] getPrivateObject(String key) throws IOException {
		OSSClient ossClient = getOSSClient();
		String bucketName = privateBucketName;
		OSSObject object = ossClient.getObject(bucketName, key);
		byte[] returnByteArr = IOUtils.toByteArray(object.getObjectContent());
		ossClient.shutdown();
		return returnByteArr;
	}

	/**
	 * 文件获取(公共读)
	 * 
	 * @param key
	 *            文件唯一标识
	 * @return 文件byte数组
	 */
	public byte[] getPublicReadObject(String key) throws IOException {
		OSSClient ossClient = getOSSClient();
		String bucketName = publicReadBucketName;
		OSSObject object = ossClient.getObject(bucketName, key);
		byte[] returnByteArr = IOUtils.toByteArray(object.getObjectContent());
		ossClient.shutdown();
		return returnByteArr;
	}

	/**
	 * 文件删除(私有)
	 * 
	 * @param key
	 *            文件唯一标识
	 */
	public void deletePrivateFile(String key) {
		OSSClient ossClient = getOSSClient();
		String bucketName = privateBucketName;
		ossClient.deleteObject(bucketName, key);
		ossClient.shutdown();
	}

	/**
	 * 文件删除(公共读)
	 * 
	 * @param key
	 *            文件唯一标识
	 */
	public void deletePublicReadFile(String key) {
		OSSClient ossClient = getOSSClient();
		String bucketName = publicReadBucketName;
		ossClient.deleteObject(bucketName, key);
		ossClient.shutdown();
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getPrivateBucketName() {
		return privateBucketName;
	}

	public void setPrivateBucketName(String privateBucketName) {
		this.privateBucketName = privateBucketName;
	}

	public String getPublicReadBucketName() {
		return publicReadBucketName;
	}

	public void setPublicReadBucketName(String publicReadBucketName) {
		this.publicReadBucketName = publicReadBucketName;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getMaxErrorRetry() {
		return maxErrorRetry;
	}

	public void setMaxErrorRetry(int maxErrorRetry) {
		this.maxErrorRetry = maxErrorRetry;
	}

	public String getPublicReadFileUrlPre() {
		return publicReadFileUrlPre;
	}

	public void setPublicReadFileUrlPre(String publicReadFileUrlPre) {
		this.publicReadFileUrlPre = publicReadFileUrlPre;
	}

}
