package com.kongque.constants;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kongque.util.PropertiesUtils;

public class Constants {
	
	public static  PropertiesUtils propertiesUtils=new PropertiesUtils(File.separator+"dao.properties");
	
	public static String ACCOUNT_URL;
	
	public static String PRODUCTION_URL;

	public static String YUN_URL;
	
	static{
		ACCOUNT_URL = propertiesUtils.getString("kongque.account.url");
		PRODUCTION_URL = propertiesUtils.getString("kongque.production.url");
		YUN_URL = propertiesUtils.getString("kongque.yun.url");
	}
	/**
	 * 秀域主管角色id
	 */
	public final static String XIUYU_ROLE_ID = "4";
	/**
	 * 店员角色id
	 */
	public static final String DIANYUAN_ROLE_ID = "2";
	/**
	 * 星域主管角色id
	 */
	public static final String XINGYU_ROLE_ID = "3";
	/**
	 * 秀域分公司财务
	 */
	public static final String FENGONGSICAIWU_ROLE_ID = "9";
	/**
	 * 加盟店角色id
	 */
	public static final String JIAMENG_ROLE_ID = "10";


	
	/**
	 * 错误类型
	 */
	public static class ERROR_TYPE {
		// redis错误
		public static final String REDIS_ERROR = "REDIS_ERROR";

	}

	/**
	 * 接口执行结果状态码
	 */
	public static class RESULT_CODE {
		// 成功
		public static final String SUCCESS = "200";
		// 程序出现bug、异常
		public static final String SYS_ERROR = "500";
		// 未授权
		public static final String UN_AUTHORIZED = "301";
		// 无此用户
		public static final String USER_NOT_EXIST = "1000";
		// 密码错误
		public static final String PWD_ERROR = "1001";

	}
	
	public static class SYSCONSTANTS{
		/**
		 * token有效时间 单位s
		 */
		public static int TOKEN_TIMEOUT=propertiesUtils.getInt("defaultExpire");
	}
	
	public static class ACCOUNT {
		//账号系统接口 账号添加
		public static final String KONGQUE_ACCOUNT_ADD = ACCOUNT_URL + "/kongque-account/account/add";
	}

	public static class YUN {
		//云平台订单驳回
		public static final String KONGQUE_YUN_ORDER_REJECT = YUN_URL + "/factory/order/reject";

		public static final String KONGQUE_YUN_ORDER_LOGISTICS=YUN_URL+"/factory/order/logistics";
	}


	public static class PRODUCTION {
		//获取仓库系统物料编码
		public static final String KONGQUE_PRODUCTION_LIST = PRODUCTION_URL + "/wms/httpClient/find/material/codeOrNames";
	
		//根据物料编码获取物料信息
		public static final String KONGQUE_PRODUCTION_INFOMATION = PRODUCTION_URL + "/wms/httpClient/find/materials";
		
		//根据年,月,物料id数组,获取物料明细
		public static final String KONGQUE_PRODUCTION_MATERIA_DETAIL = PRODUCTION_URL + "/wms/billCostAccountingController/find/accountDetailByMaterial";
	}
	

	/**
	 * 有效标识
	 */
	public static class ENABLE_FLAG {
		public static final String DELETE = "1";	//已删除/禁用
		public static final String ENABLE = "0";	//生效中/启用

	}

	/**
	 * 文件上传状态
	 */
	public static class FILE_STATUS {
		public static final String UPLOADED = "已上传";	//已上传
		public static final String NOT_UPLOADED = "未上传";	//未上传

	}
	//计划单状态枚举
	public enum PLAN_STATUS {
        草稿(0),
        待审核(1),
        已审核(2),
        已驳回(3),
        已下达(4),
        生产完成(9);
        private Integer code;//状态值
		private static final Map<Integer, String> map = new LinkedHashMap<>();
		static {
			for (PLAN_STATUS status : PLAN_STATUS.values()) {
				map.put(status.code, status.name());
			}
		}
		private PLAN_STATUS(Integer code){
            this.code = code;
        }
        /**
         * 获取枚举的value
         */
        public Integer getValByCode() {
            return code;
        }
		/**
		 * 获取枚举的map
		 */
		public static Map<Integer, String> getMap() {
			return map;
		}
        /**
         * 根据value获取code
         */
        public static String getCodeByVal(Integer val) {
            return map.get(val);
        }
	}
	public final static String RMB = "人民币";
}
