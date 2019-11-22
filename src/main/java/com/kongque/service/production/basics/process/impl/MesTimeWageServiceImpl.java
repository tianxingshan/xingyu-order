package com.kongque.service.production.basics.process.impl;

import com.kongque.dao.IDaoService;
import com.kongque.entity.process.MesTimeWage;
import com.kongque.service.production.basics.process.IMesTimeWageService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class MesTimeWageServiceImpl implements IMesTimeWageService {

    @Resource
    private IDaoService dao;
    @Override
    public Result save(MesTimeWage dto)  {
        if(dto.getStartTime()==null){
            return new Result("500","起始日期不能为空!");
        }

        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        Criteria criteria = dao.createCriteria(MesTimeWage.class);
        try {
            criteria.add(Restrictions.eq("endTime",sDateFormat.parse("2049-12-31 23:59:59")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<MesTimeWage> list = criteria.list();
        if(list.size()== 0){
            dto.setCreateTime(new Date());
            try {
                dto.setEndTime(sDateFormat.parse("2049-12-31 23:59:59"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dao.save(dto);
        }else {
            MesTimeWage entity = list.get(0);
            int compareTo = entity.getStartTime().compareTo(dto.getStartTime());
            if(compareTo >= 0){
                return new Result("500","起始日期不能小于当期设置的日期!");
            }
            SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dto.getStartTime());
            calendar.add(Calendar.DATE, -1);
            try {
                entity.setEndTime(sDateFormat.parse(sj.format(calendar.getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dao.update(entity);
            try {
                dto.setEndTime(sDateFormat.parse("2049-12-31 23:59:59"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dto.setCreateTime(new Date());
            dao.save(dto);
        }

        return new Result(dto);
    }

    @Override
    public Pagination<MesTimeWage> list(MesTimeWage dto, PageBean pageBean)  {
        Pagination<MesTimeWage> pagination = new Pagination<>();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        Criteria criteria = dao.createCriteria(MesTimeWage.class);

        if(dto.getStartTime()!=null){
            criteria.add(Restrictions.eq("startTime",dto.getStartTime()));
        }
        if(dto.getEndTime()!=null){
            SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            try {
                criteria.add(Restrictions.eq("endTime",sDateFormat.parse(sj.format(dto.getEndTime()))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(dto.getPrice()!=null){
            criteria.add(Restrictions.eq("price",dto.getPrice()));
        }

        pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
        pagination.setTotal(dao.findTotalWithCriteria(criteria));
        return pagination;
    }
}
/**
 * @program: xingyu-order
 * @description: 计时工资
 * @author: zhuxl
 * @create: 2019-08-27 09:53
 **/
