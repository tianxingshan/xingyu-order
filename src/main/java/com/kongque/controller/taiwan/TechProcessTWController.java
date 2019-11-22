package com.kongque.controller.taiwan;

import com.kongque.constants.Constants;
import com.kongque.entity.taiwan.MesGarmentDesign;
import com.kongque.service.taiwan.TechProcessTWService;
import com.kongque.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/1/8.
 */
@Controller
public class TechProcessTWController {
    private static final Logger log = LoggerFactory.getLogger(TechProcessTWController.class);

    @Resource
    private TechProcessTWService techProcessTWService;

    @RequestMapping(value = "/taiwan/upload/garmentDesign",method = RequestMethod.POST)
    public @ResponseBody Result uploadGarmentDesign(@RequestParam("file")MultipartFile file, String orderDetailId, String userId) {
        log.info("TW文件上传开始");
        try{
            return techProcessTWService.uploadGarmentDesign(file,orderDetailId,userId);
        } catch (Exception e){
            log.error("TW文件上传失败",e);
            e.printStackTrace();
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"上传失败！");
        }
    }

    @RequestMapping(value = "/taiwan/save/pdf",method = RequestMethod.GET)
    public @ResponseBody Result savePDF(HttpServletRequest request, String userId, String orderDetailId) {
        log.info("TW生成PDF开始");
        try{
            return techProcessTWService.savePDF(request,userId,orderDetailId);
        } catch (Exception e){
            log.error("TW生成PDF失败",e);
            e.printStackTrace();
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"生成PDF失败！");
        }
    }


    @RequestMapping(value = "/taiwan/delete/file",method = RequestMethod.GET)
    public @ResponseBody Result deleteFile(MesGarmentDesign mesGarmentDesign) {
        log.info("TW文件删除开始");
        return techProcessTWService.deleteFile(mesGarmentDesign);
    }

    @RequestMapping(value = "/taiwan/download/file",method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, MesGarmentDesign mesGarmentDesign) {
        log.info("TW文件下载开始");
        techProcessTWService.downloadFile(response,mesGarmentDesign);
    }
    @RequestMapping(value = "/taiwan/download/pdf",method = RequestMethod.GET)
    public void downloadPDF(HttpServletResponse response, MesGarmentDesign mesGarmentDesign) {
        log.info("TW下载PDF开始");
        techProcessTWService.downloadPDF(response,mesGarmentDesign);
    }

    @RequestMapping(value = "/taiwan/find/garmentDesign",method = RequestMethod.GET)
    public @ResponseBody Result findGarmentDesign(String orderDetailId) {
        log.info("TW读取文件上传信息开始");
        return techProcessTWService.findGarmentDesign(orderDetailId);
    }
}
