package com.kongque.service.api;

import com.kongque.model.ARTModel;
import com.kongque.util.Result;

/**
 * @author Administrator
 */
public interface IMesApiService {
    ARTModel getStaffByCardNo(String cardNo);

    Result insertProcess(ARTModel model);

    Result deleteProcess(ARTModel model);

    Result scan(ARTModel model);
}
