package com.kongque.service.taiwan.impl;

import com.kongque.common.BillStatus;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.entity.goods.Goods;
import com.kongque.entity.material.MaterialCategory;
import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.process.MesProcess;
import com.kongque.entity.productionorder.*;
import com.kongque.entity.taiwan.MesGarmentDesign;
import com.kongque.service.file.impl.FileOssServiceImpl;
import com.kongque.service.taiwan.TechProcessTWService;
import com.kongque.util.Converter;
import com.kongque.util.DateUtil;
import com.kongque.util.Result;
import com.kongque.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2018/1/8.
 */
@Service
@Transactional
public class TechProcessTWServiceImpl implements TechProcessTWService{
    private static final String MasterFileUrl="mes/garmentDesgin/";

    @Resource
    private IDaoService dao;

    @Override
    public Result uploadGarmentDesign(MultipartFile file, String orderDetailId, String userId) throws IOException {
        System.out.println(file.getOriginalFilename());
        if(!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).equalsIgnoreCase("dxf")){
            return new Result("400","必须为DXF文件");
        }
        @SuppressWarnings("unchecked")
        List<MesGarmentDesign> mesGarmentDesigns = dao.createCriteria(MesGarmentDesign.class)
                .add(Restrictions.eq("orderDetailId",orderDetailId)).list();
        if(mesGarmentDesigns!=null&&mesGarmentDesigns.size()>1){
            return new Result("400","数量错误");
        }
        OrderDetail orderDetail = dao.findById(OrderDetail.class,orderDetailId);
        if (orderDetail == null){
            return new Result("400","orderDetail:"+orderDetailId+"不存在");
        }
        if(StringUtils.isBlank(orderDetail.getErpNo())){
            return new Result("400","ErpNo为空");
        }
        if(StringUtils.isBlank(orderDetail.getGoodsSn())){
            return new Result("400","GoodsSn为空");
        }
        Order order = dao.findById(Order.class,orderDetail.getOrderId());
        if (order == null){
            return new Result("400","order:"+orderDetail.getOrderId()+"不存在");
        }
        String fileName=orderDetail.getGoodsSn()+".DXF";
        Calendar now = Calendar.getInstance();
        now.setTime(order.getCreateTime());
        String url= MasterFileUrl+now.get(Calendar.YEAR)+"/"+(now.get(Calendar.MONTH) + 1)+"/"+now.get(Calendar.DAY_OF_MONTH)+"/"+order.getOrderCode()+"/"+fileName;

        MesGarmentDesign mesGarmentDesign;
        FileOssServiceImpl fileOssService = new FileOssServiceImpl();
        if(mesGarmentDesigns==null||mesGarmentDesigns.size()==0){
            mesGarmentDesign=new MesGarmentDesign();
            mesGarmentDesign.setOrderDetailId(orderDetailId);
            mesGarmentDesign.setCreateTime(now.getTime());
            mesGarmentDesign.setUpdateTime(now.getTime());
            mesGarmentDesign.setCreateUserId(userId);
            mesGarmentDesign.setUpdateUserId(userId);
            mesGarmentDesign.setGarmentDesignName(url);
            mesGarmentDesign.setFileName(fileName);
            mesGarmentDesign.setStatus(0);
            fileOssService.uploadPrivateFile(url,file.getInputStream());
            dao.save(mesGarmentDesign);
        }else{
            mesGarmentDesign=mesGarmentDesigns.get(0);
            if(mesGarmentDesign.getGarmentDesignName()!=null){
                fileOssService.deletePrivateFile(mesGarmentDesign.getGarmentDesignName());
            }
            mesGarmentDesign.setUpdateTime(now.getTime());
            mesGarmentDesign.setUpdateUserId(userId);
            mesGarmentDesign.setGarmentDesignName(url);
            mesGarmentDesign.setFileName(fileName);
            mesGarmentDesign.setStatus(0);
            fileOssService.uploadPrivateFile(url,file.getInputStream());
            dao.saveOrUpdate(mesGarmentDesign);
        }
        return new Result(mesGarmentDesign);
    }

    @Override
    public Result deleteFile(MesGarmentDesign mesGarmentDesign) {
        Criteria criteria = dao.createCriteria(MesGarmentDesign.class);
        if (StringUtils.isNotBlank(mesGarmentDesign.getOrderDetailId())){
            criteria.add(Restrictions.eq("orderDetailId",mesGarmentDesign.getOrderDetailId()));
        }
        if (StringUtils.isNotBlank(mesGarmentDesign.getFileName())){
            criteria.add(Restrictions.like("fileName",mesGarmentDesign.getFileName()));
        }
        @SuppressWarnings("unchecked")
        List<MesGarmentDesign> mesGarmentDesigns = criteria.list();
        if (mesGarmentDesigns!=null&&mesGarmentDesigns.size()>1){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"获取不止一条文件记录，请联系管理员删除！");
        }
        FileOssServiceImpl fileOssService = new FileOssServiceImpl();
        try {
            fileOssService.deletePrivateFile(mesGarmentDesigns.get(0).getGarmentDesignName());
            mesGarmentDesigns.get(0).setGarmentDesignName(null);
            mesGarmentDesigns.get(0).setFileName(null);
            dao.saveOrUpdate(mesGarmentDesigns.get(0));
        } catch (Exception e){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"删除失败！");
        }
        return new Result();
    }

    @Override
    public void downloadFile(HttpServletResponse response, MesGarmentDesign mesGarmentDesign) {
        try {
            boolean flag = false;
            Criteria criteria = dao.createCriteria(MesGarmentDesign.class);
            if (StringUtils.isNotBlank(mesGarmentDesign.getOrderDetailId())){
                criteria.add(Restrictions.eq("orderDetailId",mesGarmentDesign.getOrderDetailId()));
                flag = true;
            }
            if (StringUtils.isNotBlank(mesGarmentDesign.getFileName())){
                criteria.add(Restrictions.like("fileName",mesGarmentDesign.getFileName(),MatchMode.ANYWHERE));
                flag = true;
            }
            if (!flag){
                return ;
            }
            @SuppressWarnings("unchecked")
            List<MesGarmentDesign> mesGarmentDesigns = criteria.list();
            if(mesGarmentDesigns.size()!=1){
                return ;
            }
            response.setHeader("Content-Disposition", "attachment;filename="+mesGarmentDesigns.get(0).getFileName());
            String key=mesGarmentDesigns.get(0).getGarmentDesignName();
            FileOssServiceImpl fileOssService = new FileOssServiceImpl();
            byte[] bytes=fileOssService.getPrivateObject(key);
            OutputStream out=response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Result findGarmentDesign(String orderDetailId) {
        @SuppressWarnings("unchecked")
        List<MesGarmentDesign> mesGarmentDesigns = dao.createCriteria(MesGarmentDesign.class)
                .add(Restrictions.eq("orderDetailId",orderDetailId))
                .addOrder(org.hibernate.criterion.Order.desc("updateTime"))
                .list();
        if (mesGarmentDesigns!=null&&mesGarmentDesigns.size()>1){
            return new Result(Constants.RESULT_CODE.SYS_ERROR,"该订单明细含有多条文件上传记录，请联系管理员处理！");
        } else if (mesGarmentDesigns==null||mesGarmentDesigns.size()==0){
            return new Result(new MesGarmentDesign());
        }
        return new Result(mesGarmentDesigns.get(0));
    }

    @Override
    public void downloadPDF(HttpServletResponse response, MesGarmentDesign mesGarmentDesign) {
        try {
            boolean flag = false;
            Criteria criteria = dao.createCriteria(MesGarmentDesign.class);
            if (StringUtils.isNotBlank(mesGarmentDesign.getOrderDetailId())){
                criteria.add(Restrictions.eq("orderDetailId",mesGarmentDesign.getOrderDetailId()));
                flag = true;
            }
            if (StringUtils.isNotBlank(mesGarmentDesign.getPdfName())){
                criteria.add(Restrictions.like("pdfName",mesGarmentDesign.getPdfName(), MatchMode.ANYWHERE));
                flag = true;
            }
            if (!flag){
                return ;
            }
            @SuppressWarnings("unchecked")
            List<MesGarmentDesign> mesGarmentDesigns = criteria.list();
            if(mesGarmentDesigns.size()!=1){
                return ;
            }
            response.setHeader("Content-Disposition", "attachment;filename="+mesGarmentDesigns.get(0).getPdfName());
            String key=mesGarmentDesigns.get(0).getPdfUrl();
            FileOssServiceImpl fileOssService = new FileOssServiceImpl();
            byte[] bytes=fileOssService.getPrivateObject(key);
            OutputStream out=response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Result savePDF(HttpServletRequest request, String userId, String orderDetailId) throws Exception{
        try {
            @SuppressWarnings("unchecked")
            List<MesGarmentDesign> mesGarmentDesigns = dao.createCriteria(MesGarmentDesign.class)
                    .add(Restrictions.eq("orderDetailId",orderDetailId)).list();
            if(mesGarmentDesigns!=null&&mesGarmentDesigns.size()>1){
                return new Result("400","数量错误");
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Converter converter=new Converter();
            OrderDetail orderDetail=dao.findById(OrderDetail.class,orderDetailId);
            if (orderDetail == null){
                return new Result("400","orderDetail:"+orderDetailId+"不存在");
            }
            if(BillStatus.OrderDetailStatus.UNASSIGN.getValue().equals(orderDetail.getOrderDetailStatus())
                    ||BillStatus.OrderDetailStatus.ASSIGNED.getValue().equals(orderDetail.getOrderDetailStatus())){
                return new Result("400","技术完成后的订单明细才允许生成PDF！");
            }
            Order order = dao.findById(Order.class,orderDetail.getOrderId());
            if (order == null){
                return new Result("400","order:"+orderDetail.getOrderId()+"不存在");
            }
            URL u = new URL(request.getRequestURL().toString());
            String localhost = u.getProtocol()+"://"+u.getHost()+":"+u.getPort()+request.getContextPath();
            converter.generatePDF_1(baos,new StringReader(this.getPdfHtml(localhost,orderDetail,order)));
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            String fileName=orderDetail.getGoodsSn()+".pdf";
            Calendar now = Calendar.getInstance();
            now.setTime(order.getCreateTime());
            String url= MasterFileUrl+now.get(Calendar.YEAR)+"/"+(now.get(Calendar.MONTH) + 1)+"/"+now.get(Calendar.DAY_OF_MONTH)+"/"+order.getOrderCode()+"/"+fileName;
            MesGarmentDesign mesGarmentDesign;
            FileOssServiceImpl fileOssService = new FileOssServiceImpl();
            if(mesGarmentDesigns==null||mesGarmentDesigns.size()==0){
                mesGarmentDesign=new MesGarmentDesign();
                mesGarmentDesign.setOrderDetailId(orderDetailId);
                mesGarmentDesign.setCreateTime(now.getTime());
                mesGarmentDesign.setUpdateTime(now.getTime());
                mesGarmentDesign.setCreateUserId(userId);
                mesGarmentDesign.setUpdateUserId(userId);
                mesGarmentDesign.setPdfUrl(url);
                mesGarmentDesign.setPdfName(fileName);
                mesGarmentDesign.setPdfUpdateTime(now.getTime());
                mesGarmentDesign.setStatusPdf(0);
                fileOssService.uploadPrivateFile(url,is);
                dao.saveOrUpdate(mesGarmentDesign);
            }else{
                mesGarmentDesign=mesGarmentDesigns.get(0);
                if(mesGarmentDesign.getPdfUrl()!=null){
                    fileOssService.deletePrivateFile(mesGarmentDesign.getPdfUrl());
                }
                mesGarmentDesign.setUpdateTime(now.getTime());
                mesGarmentDesign.setUpdateUserId(userId);
                mesGarmentDesign.setPdfUrl(url);
                mesGarmentDesign.setPdfName(fileName);
                mesGarmentDesign.setStatusPdf(0);
                mesGarmentDesign.setPdfUpdateTime(now.getTime());
                fileOssService.uploadPrivateFile(url,is);
                dao.saveOrUpdate(mesGarmentDesign);
            }
            return new Result(mesGarmentDesign);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    public String getPdfHtml(String local, OrderDetail orderDetail,Order order) throws IOException {
        String result=pdfHtml;
        result=result.replaceAll("localhost_url",local);
        //订单明细分配表
        @SuppressWarnings("unchecked")
        List<MesOrderDetailAssign> mesOrderDetailAssigns = dao.createCriteria(MesOrderDetailAssign.class)
                .add(Restrictions.eq("orderDetailId",orderDetail.getId())).list();
        result=result.replaceAll("pdf-customName",StringUtils.isNull(order.getCustomerName()));
        if (mesOrderDetailAssigns != null && mesOrderDetailAssigns.size()>0 && mesOrderDetailAssigns.get(0)!=null){
            result=result.replaceAll("pdf-assignUser",StringUtils.isNull(mesOrderDetailAssigns.get(0).getTechnicianName()));
            if (mesOrderDetailAssigns.get(0).getTechnicalFinishedTime()!=null){
                result=result.replaceAll("pdf-techFinishTime", StringUtils.isNull(DateUtil.formatDate(mesOrderDetailAssigns.get(0).getTechnicalFinishedTime(),"yyyy-MM-dd")));
            }else{
                result=result.replaceAll("pdf-techFinishTime", "");
            }
            if (orderDetail.getGoodsDetail()!=null){
                //商品表
                Goods goods = dao.findById(Goods.class,orderDetail.getGoodsDetail().getGoodsId());
                result=result.replaceAll("pdf-cStyleCode",goods!=null?StringUtils.isNull(goods.getCode()):"");
                result=result.replaceAll("pdf-cStyleName",goods!=null?StringUtils.isNull(goods.getName()):"");
                result=result.replaceAll("pdf-styleColorName",StringUtils.isNull(orderDetail.getGoodsDetail().getColorName()));
            }
        }
        result=result.replaceAll("pdf-character",StringUtils.isNull(order.getOrderCharacter()));
        result=result.replaceAll("pdf-cOwner",StringUtils.isNull(order.getEmbroidName()));
        result=result.replaceAll("pdf-cCode",StringUtils.isNull(order.getOrderCode()));
        //订单明细尺码
        @SuppressWarnings("unchecked")
        List<MesOrderDetailSize> mesOrderDetailSizes = dao.createCriteria(MesOrderDetailSize.class)
                .add(Restrictions.eq("orderDetailId",orderDetail.getId())).list();
        String pdfSizeMark="";
        if (mesOrderDetailSizes!=null&&mesOrderDetailSizes.size()>0&&mesOrderDetailSizes.get(0)!=null){
            result=result.replaceAll("pdf-cSizeCode",StringUtils.isNull(mesOrderDetailSizes.get(0).getMesMeasureSizeName()));
            pdfSizeMark = mesOrderDetailSizes.get(0).getRemarks();
            result=result.replaceAll("pdf-sizeMark",StringUtils.isNull(pdfSizeMark));
        }
        MesOrderDetailSewingProcess mesOrderDetailSewingProcess = null;
        String station=" <tr  class=\"stationtr\" >\n" +
                "        <td width=\"40\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <b><span style=\"font-size:6.0pt;font-family:&quot;\">pdf-no</span></b>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"150\" colspan=\"3\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-stationName</span></b>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"80\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-pureSize</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"50\" style=\"border:solid windowtext 1.0pt;background:white;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-finishSize</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"80\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-shrinkSize</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"50\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-tolerance</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"80\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\"></span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "pdf-stationRight"+
                "    </tr>\n" +
                "   ";
        //订单明细-量体部位尺寸
        @SuppressWarnings("unchecked")
        List<MesOrderDetailMeasure> mesOrderDetailMeasures = dao.createCriteria(MesOrderDetailMeasure.class)
                .add(Restrictions.eq("orderDetailId",orderDetail.getId())).list();
        StringBuffer stationS=new StringBuffer();
        int index=1;
        if (mesOrderDetailMeasures!=null&&mesOrderDetailMeasures.size()>0){
            for (MesOrderDetailMeasure mesOrderDetailMeasure : mesOrderDetailMeasures) {
                if (mesOrderDetailMeasure != null) {
                    String stationR=station.replaceFirst("pdf-no",String.valueOf(index++));
                    if (mesOrderDetailMeasure.getMeasurePosition()!=null){
                        stationR=stationR.replaceFirst("pdf-stationName",StringUtils.isNull(mesOrderDetailMeasure.getMeasurePosition().getName()));
                    }
                    stationR=stationR.replaceFirst("pdf-pureSize",StringUtils.isNull(mesOrderDetailMeasure.getModelNetSize()));
                    stationR=stationR.replaceFirst("pdf-finishSize",StringUtils.isNull(mesOrderDetailMeasure.getModelFinishSize()));
                    stationR=stationR.replaceFirst("pdf-shrinkSize",StringUtils.isNull(mesOrderDetailMeasure.getShrinkage()));
                    stationR=stationR.replaceFirst("pdf-tolerance",StringUtils.isNull(mesOrderDetailMeasure.getTolerance()));
                    stationS.append(stationR);

                }
            }
        }
        result=result.replaceFirst("pdf-stations",stationS.toString());
        String stationRight=   "        <td width=\"180\" id =\"stationRight\" colspan=\"3\"  style=\"border:solid 1px;\" rowspan=\""+(index-1)+"\">\n" +
                "            <span style=\"font-size:8.0pt;font-family:&quot;\" id=\"sizeMark\">pdf-sizeMark</span>\n" +
                "        </td>\n" +
                "        <td style=\"border:none;\" width=\"0\" height=\"18\">\n" +
                "        </td>\n" ;
        stationRight=stationRight.replaceFirst("pdf-sizeMark",StringUtils.isNull(pdfSizeMark));
        result=result.replaceFirst("pdf-stationRight",StringUtils.isNull(stationRight));
        result=result.replaceAll("pdf-stationRight","");
        String processes="  <tr class=\"processtr\">\n" +
                "        <td width=\"40\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <b><span style=\"font-size:6.0pt;font-family:&quot;\">pdf-processCode</span></b>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"150\" colspan=\"3\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-processName</span></b>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"80\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-machineName</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"50\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-linename</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"80\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-needlename</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"50\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\"></span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td width=\"260\"  colspan=\"4\" style=\"border:solid 1px;\">\n" +
                "            <p class=\"MsoNormal\" align=\"left\">\n" +
                "                <span style=\"font-size:8.0pt;font-family:&quot;\">pdf-qualityAssurance</span>\n" +
                "            </p>\n" +
                "        </td>\n" +
                "        <td style=\"border:none;\" width=\"0\" height=\"18\">\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "  ";
        //订单明细缝制工序
        @SuppressWarnings("unchecked")
        List<MesOrderDetailSewingProcess> mesOrderDetailSewingProcesses = dao.createCriteria(MesOrderDetailSewingProcess.class)
                .add(Restrictions.eq("orderDetailId",orderDetail.getId())).list();
        StringBuffer processS=new StringBuffer();
        for(MesOrderDetailSewingProcess mesOrderDetailSewingProcess1:mesOrderDetailSewingProcesses){
            if (mesOrderDetailSewingProcess1!=null){
                if (mesOrderDetailSewingProcess1.getProcess()!=null){
                    MesProcess mesProcess =  mesOrderDetailSewingProcess1.getProcess();
                    String processR=processes.replaceFirst("pdf-processCode",StringUtils.isNull(mesProcess.getCode()));
                    processR=processR.replaceFirst("pdf-processName",StringUtils.isNull(mesProcess.getName()));
                    if (mesProcess.getMachine()!=null){
                        processR=processR.replaceFirst("pdf-machineName",StringUtils.isNull(mesProcess.getMachine().getName()));
                    }
                    if (mesProcess.getThread()!=null){
                        processR=processR.replaceFirst("pdf-linename",StringUtils.isNull(mesProcess.getThread().getName()));
                    }
                    if (mesProcess.getNeedleRange()!=null){
                        processR=processR.replaceFirst("pdf-needlename",StringUtils.isNull(mesProcess.getNeedleRange().getName()));
                    }
                    processR=processR.replaceFirst("pdf-qualityAssurance",StringUtils.isNull(mesProcess.getQuality()));
                    processS.append(processR);
                }
            }
        }
        result=result.replaceFirst("pdf-process",processS.toString());
        String materials=" <tr id=\"materialAfter\" class=\"materialtr\">\n" +
                "            <td width=\"39\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-no</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"70\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-category</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"80\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-stationCode</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"100\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-stationName</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"110\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-customcode</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"150\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-name</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"70\" >\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-specifications</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td  width=\"85\" height=\"18\">\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-attr</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td  width=\"67\" height=\"18\">\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-quantity</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td  width=\"50\" height=\"18\">\n" +
                "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
                "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">pdf-unit</span></b>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>";
        //订单明细物料
        @SuppressWarnings("unchecked")
        List<MesOrderDetailMaterial> mesOrderDetailMaterials = dao.createCriteria(MesOrderDetailMaterial.class)
                .add(Restrictions.eq("orderDetailId",orderDetail.getId())).list();
        index=1;
        StringBuffer materialS=new StringBuffer();
        if (mesOrderDetailMaterials!=null){
            for(MesOrderDetailMaterial mesOrderDetailMaterial : mesOrderDetailMaterials){
                String materialR=materials.replaceFirst("pdf-no",String.valueOf(index++));
                if (mesOrderDetailMaterial!=null){
                    MaterialCategory materialCategory = getFirstCateGory(mesOrderDetailMaterial.getMaterialCategory());
                    materialR=materialR.replaceFirst("pdf-category",materialCategory==null?"":StringUtils.isNull(materialCategory.getName()));
                    if (mesOrderDetailMaterial.getMaterialPosition()!=null){
                        materialR=materialR.replaceFirst("pdf-stationCode",StringUtils.isNull(mesOrderDetailMaterial.getMaterialPosition().getCode()));
                        materialR=materialR.replaceFirst("pdf-stationName",StringUtils.isNull(mesOrderDetailMaterial.getMaterialPosition().getName()));
                    }
                    materialR=materialR.replaceFirst("pdf-customcode",StringUtils.isNull(mesOrderDetailMaterial.getCode()));
                    materialR=materialR.replaceFirst("pdf-name",StringUtils.isNull(mesOrderDetailMaterial.getName()));
                    materialR=materialR.replaceFirst("pdf-specifications",StringUtils.isNull(mesOrderDetailMaterial.getSpec()));
                    materialR=materialR.replaceFirst("pdf-attr",StringUtils.isNull(mesOrderDetailMaterial.getColor()));
                    materialR=materialR.replaceFirst("pdf-quantity",StringUtils.isNull(mesOrderDetailMaterial.getNum()));
                    materialR=materialR.replaceFirst("pdf-unit",StringUtils.isNull(mesOrderDetailMaterial.getInventoryUnit()));
                    materialS.append(materialR);
                }
            }
        }
        result=result.replaceFirst("pdf-materials",materialS.toString());
        return result;
    }

    public static String pdfHtml="<html>\n" +
            "<head>\n" +
            "        <meta charset=\"utf-8\"/>\n" +
            "        <style>\n" +
            "            p{margin-top:0pt;margin-bottom:1pt;}p.a{text-align:justified;}span.a3{font-size:9.0pt;}span.Char{font-size:9.0pt;}\n" +
            "            td{border:1px}\n" +
            "        </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "\n" +
            "    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"760\" style=\"width:570.2pt;\">\n" +
            "        <tbody>\n" +
            "        <tr style='height:21pt;'>\n" +
            "            <td width=\"140\" rowspan=\"2\" colspan=\"3\" style=\"border:solid 1px;line-height: 20px;height:20px\"  >\n" +
            "                <img src=\"localhost_url/asset/images/logo.png\" width=\"138\" height=\"33.0pt\"/>\n" +
            "            </td>\n" +
            "            <td width=\"600\" rowspan=\"2\" colspan=\"6\" style=\"border:solid 1px;line-height: 20pt;height:20px\"  height=\"20px\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:16.0pt;\">产品工艺书<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\"  height=\"20px\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:10.0pt;color: red\">批准<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\"  height=\"20px\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\" >\n" +
            "                    <b><span style=\"font-size:12.0pt;\">审核<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\"  height=\"20px\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:12.0pt;\">编制<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td style=\"border:none;\" width=\"0\" height=\"20px\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:16.0pt;\"><span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:16.0pt;\"><span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:16.0pt;\"><span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td style=\"border:none;\" width=\"0\" height=\"42\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"40\">\n" +
            "            <td width=\"40\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">单号<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"100\" colspan=\"2\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"cCode\"><span>pdf-cCode</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">品名<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"cStyleName\"><span>pdf-cStyleName</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">款号<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"cStyleCode\"><span>pdf-cStyleCode</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">颜色<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"styleColorName\"><span>pdf-styleColorName</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">预交日期<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"100\" colspan=\"2\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"jhrq\"><span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"40\">\n" +
            "            <td width=\"40\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">顾客性质<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"60\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"character\"><span>pdf-character</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"40\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">尺码<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"cSizeCode\"><span>pdf-cSizeCode</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">顾客<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"customName\"><span>pdf-customName</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">绣字名<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"cOwner\"><span>pdf-cOwner</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">版型师<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"assignUser\"><span>pdf-assignUser</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\">作成日<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"100\"  style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:10.0pt;\" id=\"techFinishTime\"><span>pdf-techFinishTime</span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "\n" +
            "        <tr>\n" +
            "            <td width=\"750\" height=\"199\" colspan=\"12\" rowspan=\"2\" style=\"border:solid 1px;\">\n" +
            "                <!--<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">-->\n" +
            "                <!--</p>-->\n" +
            "                <img src=\"localhost_url/asset/images/AA17001.png\" width=\"600\" height=\"190\" />\n" +
            "            </td>\n" +
            "            <!--<td style=\"border:none;\" width=\"0\" height=\"199\">-->\n" +
            "            <!--</td>-->\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td >\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "\n" +
            "        <tr height=\"30\">\n" +
            "            <td width=\"40\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">序号<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"150\" colspan=\"3\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">部位名称<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">样板净尺寸<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">样板完成尺寸<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">缩量<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">公差 （±）<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">实际测量<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"180\" colspan=\"3\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\"> 特殊备注说明<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "\n" +
            "        </tr>\n" +
            "        pdf-stations\n" +
            "\n" +
            "        <tr id=\"stationBefore\">\n" +
            "            <td width=\"750\" colspan=\"12\" rowspan=\"2\" style=\"border:solid black 1.0pt;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:16.0pt;\">缝制工序<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td style=\"border:none;\" width=\"0\" height=\"21\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td style=\"border:none;\" width=\"0\" height=\"21\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td width=\"40\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">序号<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"150\" colspan=\"3\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">工程名称<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">机种<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">用线<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">针幅<span>/</span>数<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"50\" style=\"border:solid 1px;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">副材<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"260\" colspan=\"4\" style=\"border:solid 1px;\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;\">注意事项<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td style=\"border:none;\" width=\"0\" height=\"18\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        pdf-process\n" +
            "        </tbody>\n" +
            "    </table>\n" +
            "    <table class=\"MsoNormalTable ke-zeroborder trLeftTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"760\" style=\"width:570.2pt;\">\n" +
            "        <tbody>\n" +
            "        <tr >\n" +
            "            <td width=\"760\" colspan=\"10\" rowspan=\"1\" style=\"border:solid black 1.0pt;\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <span style=\"font-size:16.0pt;\">物料清单<span></span></span>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td width=\"39\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">序号<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"70\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">大类<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"80\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">部位编码<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"100\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">使用部位<span></span><span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"110\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">物料编码<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"150\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">物料名称材质<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td  width=\"70\" height=\"18\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">规格<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td width=\"85\" >\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">颜色<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td  width=\"67\" height=\"18\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">需用量<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "            <td  width=\"50\" height=\"18\">\n" +
            "                <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\">\n" +
            "                    <b><span style=\"font-size:8.0pt;font-family:&quot;\">单位<span></span></span></b>\n" +
            "                </p>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        pdf-materials\n" +
            "        </tbody>\n" +
            "\n" +
            "    </table>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    /**
     * 获取最内层的物料分类（即物料大类）
     * @param materialCategory
     * @return
     */
    private static MaterialCategory getFirstCateGory(MaterialCategory materialCategory){
        if (materialCategory!=null) {
            MaterialCategory fatherCategory = materialCategory.getParentCategory();
            if (fatherCategory != null) {
                return getFirstCateGory(fatherCategory);
            }
        }
        return materialCategory;
    }
}
