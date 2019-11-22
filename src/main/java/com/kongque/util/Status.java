package com.kongque.util;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author Administrator
 */
public  class Status {

    /**
     * 转换微调单状态
     * @param code
     * @return
     */
    public static String getRepairStatusName(Object code){
        String name="";
        //微调单状态（0：未送出；1：已送出；2：星域审核通过；3：星域驳回；4：计划维护；5：生产中；6：生产完成；7：已发货；8：已收货；9:待收货；10:待发货）

        if (code !=null)
        {
            switch (code.toString()) {
                case "0":
                    name="未送出";
                    break;

                case "1":
                    name="已送出";
                    break;

                case "2":
                    name="星域审核通过";
                    break;

                case "3":
                    name="星域驳回";
                    break;
                case "4":
                    name="计划维护";
                    break;
                case "5":
                    name="生产中";
                    break;
                case "6":
                    name="生产完成";
                    break;
                case "7":
                    name="已发货";
                    break;
                case "8":
                    name="已收货";
                case "9":
                    name="待收货";
                case "10":
                    name="待发货";
                    break;
                default:
                    name=code.toString();
            }
        }else {
            name = "";
        }
        return name;
    }

    public static String getPublishersName(Object code){
        String name="";
        if (code !=null)
        {
            switch (code.toString()) {
                case "1":
                    name="青岛星域";
                    break;
                case "2":
                    name="台湾秀妮儿";
                    break;
                default:
                    name=code.toString();
            }
        }else {
            name = "";
        }
        return name;
    }

    public static String getBalanceStatusName(Object code){
        String name="";
        if (code !=null)
        {
            switch (code.toString()) {
                case "1":
                    name="待确认";
                    break;
                case "2":
                    name="已确认";
                    break;
                case "3":
                    name="已结算";
                    break;
                default:
                    name=code.toString();
            }
        }else {
            name = "";
        }
        return name;
    }
}
/**
 * @program: xingyu-order
 * @description: 状态
 * @author: zhuxl
 * @create: 2019-05-23 16:14
 **/
