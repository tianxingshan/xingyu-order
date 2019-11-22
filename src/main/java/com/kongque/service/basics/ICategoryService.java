package com.kongque.service.basics;

import com.kongque.dto.CategoryDto;
import com.kongque.entity.basics.Category;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface ICategoryService {


	Result saveOrUpdate(CategoryDto dto);

	Result delete(String id);

	Pagination<Category> list(CategoryDto dto, PageBean pageBean);

}
