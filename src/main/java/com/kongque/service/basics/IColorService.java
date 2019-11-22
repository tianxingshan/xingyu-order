package com.kongque.service.basics;

import org.springframework.web.multipart.MultipartFile;

import com.kongque.dto.ColorDto;
import com.kongque.entity.basics.Color;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IColorService {

	Pagination<Color> colorList(ColorDto dto, PageBean pageBean);
	
	Result saveOrUpdate(ColorDto dto,MultipartFile[] files);

	Result delete(String id);

}
