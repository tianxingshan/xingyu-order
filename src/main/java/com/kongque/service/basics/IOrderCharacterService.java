package com.kongque.service.basics;
import com.kongque.dto.OrderCharacterDto;
import com.kongque.entity.basics.OrderCharacter;
import com.kongque.entity.basics.OrderCharacterTenantRelation;
import com.kongque.model.TenantModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IOrderCharacterService {
	public Pagination<OrderCharacter> orderCharacterList(OrderCharacterDto dto, PageBean pageBean);//根据条件分页查询地区
	public Result saveOrUpdate(OrderCharacterDto dto);//添加或修改地区
	public Result updateStatus(String orderCharacterId,String orderCharacterStatus);//删除地区

    Result listByTenant(OrderCharacterDto dto);

    Result saveTenantListbyOrderCharacterId(OrderCharacterDto dto);

    Result orderCharacterTenantRelationUpdateStatus(String id, String status);

    Pagination<OrderCharacterTenantRelation> orderCharacterTenantRelationlList(OrderCharacterDto dto, PageBean pageBean);

    Result orderCharacterTenantRelationlDeleteById(String id);
}
