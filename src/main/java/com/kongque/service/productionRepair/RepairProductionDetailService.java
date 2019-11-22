package com.kongque.service.productionRepair;

import com.kongque.dto.RepairProductionDetailDto;
import com.kongque.entity.repair.OrderRepairCopySearch;
import com.kongque.model.RepairProductionDetailModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaxt
 * @date 2019/1/9.
 */
public interface RepairProductionDetailService {

    /**
     * 条件查询微调生产明细(支持分页或不分页)
     * @param dto
     * @param pageBeans
     * @return
     */
    Pagination<RepairProductionDetailModel> findRepairProductionDetailByPage(RepairProductionDetailDto dto, PageBean... pageBeans);

    /**
     * 条件导出微调生产明细
     * @param dto
     * @return
     */
    void exportRepairProductionDetail(RepairProductionDetailDto dto, HttpServletRequest request, HttpServletResponse response);

    Pagination<OrderRepairCopySearch> remoteOrderRepair(String q, PageBean pageBean);
}
