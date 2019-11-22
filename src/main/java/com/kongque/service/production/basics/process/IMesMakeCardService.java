package com.kongque.service.production.basics.process;

import com.kongque.dto.MesMakeCardDto;
import com.kongque.entity.process.MesMakeCard;
import com.kongque.model.MesMakeCardModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

/**
 * @author Administrator
 * @description 制卡
 */
public interface IMesMakeCardService {
    Result make(MesMakeCard dto);
    Result clear(MesMakeCard dto);
    Pagination<MesMakeCardModel> list(MesMakeCardDto dto, PageBean pageBean);
}
