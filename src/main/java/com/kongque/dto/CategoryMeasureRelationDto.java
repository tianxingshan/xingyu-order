package com.kongque.dto;

import java.util.List;

/**
 * @author Administrator
 */
public class CategoryMeasureRelationDto {
    private String id;
    private String categoryId;
    private String bodyMeasureInfoId;
    private List<String> bodyMeasureInfoIds;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBodyMeasureInfoId() {
        return bodyMeasureInfoId;
    }

    public void setBodyMeasureInfoId(String bodyMeasureInfoId) {
        this.bodyMeasureInfoId = bodyMeasureInfoId;
    }

    public List<String> getBodyMeasureInfoIds() {
        return bodyMeasureInfoIds;
    }

    public void setBodyMeasureInfoIds(List<String> bodyMeasureInfoIds) {
        this.bodyMeasureInfoIds = bodyMeasureInfoIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
/**
 * @program: xingyu-order
 * @description: 品类量体关系
 * @author: zhuxl
 * @create: 2019-07-04 16:47
 **/
