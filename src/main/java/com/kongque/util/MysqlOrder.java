package com.kongque.util;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

/**
 * 重写Criteria的order排序方法
 * @author xiaxt
 * @date 2018/11/8.
 */
public class MysqlOrder extends Order {

    private String propertyName;
    private boolean ascending;
    protected MysqlOrder(String propertyName, boolean ascending) {
        super(propertyName, ascending);
        this.propertyName = propertyName;
        this.ascending = ascending;
    }
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException {
        String column = criteriaQuery.getColumn(criteria,propertyName);
        //在这里对字段进行处理
        return " ( "+column+" +0) ";
    }
    public static MysqlOrder getOrderAsc(String propertyName ) {
        return new MysqlOrder(propertyName, true);
    }
    public static MysqlOrder getOrderDesc(String propertyName ) {
        return new MysqlOrder(propertyName, false);
    }

}
