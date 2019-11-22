package com.kongque.common;

import com.kongque.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: xingyu-order
 * @description: 单据状态类
 * @author: zhuxl
 * @create: 2018-10-12 15:11
 **/
public class BillStatus {

    public static String orderDetailStatus(String code){

      /*  String name= "";
        if("0".equals(code)){
            name = "未分配";
        }else if("1".equals(code)){
            name = "已分配";
        }else if("2".equals(code)){
            name = "技术完成";
        }else if("3".equals(code)){
            name = "计划维护";
        }else if("4".equals(code)){
            name = "生产中";
        }else if("5".equals(code)){
            name = "生产完成";
        }else if("6".equals(code)){
            name ="已发货";
        }*/
        return OrderDetailStatus.getMap().get(code);
    }

    /**
     * 订单状态
     */
    public static enum OrderDetailStatus{
//        UNASSIGN("0","未分配"),ASSIGNED("1","已分配"),TECHNICALFINISHED("2","技术完成"),PLANMAINTENANCE("3","计划维护"),PRODUCING("4","生产中"),PRODUCEFINISH("5","生产完成"),SHIPPED("6","已发货");
        UNASSIGN("6","未分配"),ASSIGNED("0","已分配"),TECHNICALFINISHED("7","技术完成"),PLANMAINTENANCE("8","计划维护"),PRODUCING("1","生产中"),PRODUCEFINISH("2","生产完成"),SHIPPED("3","已发货"),SHIPPING("9","待发货");
        private OrderDetailStatus(String value,String name){
            this.value = value;
            this.name = name;
        }
        private final String value;
        private final String name;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        private static Map<String,String> map;
        public static Map<String,String> getMap(){
            if (map==null){
                map = new HashMap<>();
                for (OrderDetailStatus orderDetailStatus : OrderDetailStatus.values()) {
                    map.put(orderDetailStatus.getValue(),orderDetailStatus.getName());
                }
            }
            return map;
        }
    }
}

