package com.kongque.service.taiwan;

import com.kongque.entity.taiwan.MesGarmentDesign;
import com.kongque.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaxt
 * @date 2018/10/24.
 */
public interface TechProcessTWService {

    /**
     * 文件上传
     * @param file
     * @param orderDetailId
     * @param userId
     * @return
     * @throws IOException
     */
    Result uploadGarmentDesign(MultipartFile file, String orderDetailId, String userId) throws IOException;

    /**
     * 删除文件
     * @param mesGarmentDesign
     */
    Result deleteFile(MesGarmentDesign mesGarmentDesign);

    /**
     * 文件下载
     * @param mesGarmentDesign
     */
    void downloadFile(HttpServletResponse response, MesGarmentDesign mesGarmentDesign);

    /**
     * 根据orderDetailId读取上传信息
     * @param orderDetailId
     */
    Result findGarmentDesign(String orderDetailId);

    /**
     * 下载PDF
     * @param response
     * @param mesGarmentDesign
     * @throws IOException
     */
    void downloadPDF(HttpServletResponse response, MesGarmentDesign mesGarmentDesign);
    /**
     * 生成PDF
     * @param request
     * @param userId
     * @param orderDetailId
     * @return
     */
    Result savePDF(HttpServletRequest request, String userId, String orderDetailId) throws Exception;
}
