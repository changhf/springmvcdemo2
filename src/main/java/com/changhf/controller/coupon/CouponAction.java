package com.changhf.controller.coupon;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhf.common.Constants;
import com.changhf.common.ResponseMessageMap;
import com.changhf.controller.base.WebActionSupport;
import com.changhf.plugin.login.LoginRequired;
import com.changhf.service.code.ExcelService;
import com.changhf.service.coupon.CouponService;
import com.changhf.utils.ParamUtil;
import com.google.common.collect.Maps;
@Controller
@RequestMapping("coupon")
public class CouponAction extends WebActionSupport{
	@Autowired
    protected CouponService couponService;
	@Autowired
    protected ExcelService<Map<String,Object>> excelService;
	/**
     *导出优惠码
     */
    @RequestMapping(value = "exportExcel")
    @LoginRequired(needLogin=false)
    @ResponseBody
    public void exportExcel() {
        Integer groupId = ParamUtil.getIntParameter(request, "groupId", null);
        Assert.notNull(groupId, "优惠码组id不能为空");
        List<Map<String, Object>> couponCodeList = couponService.getCouponCodeByGroupId(groupId);
        String fileName = "优惠码Excel表";
        Map<String, String> exportMap = new LinkedHashMap<>();
        exportMap.put("优惠码ID", "id");
        exportMap.put("优惠码组ID", "groupId");
        exportMap.put("绑定用户ID", "userId");
        exportMap.put("优惠码", "couponCode");
        exportMap.put("优惠码类型", "couponType");
        exportMap.put("开始时间", "startTime");
        exportMap.put("结束时间", "endTime");
        exportMap.put("是否被使用", "isUsed");
        exportMap.put("产品类别", "productType");
        exportMap.put("最低商品价格", "price");
        exportMap.put("满减价格", "subPrice");
        exportMap.put("发放数量", "number");
        exportMap.put("是否启用", "status");
        exportMap.put("操作人", "operator");
        exportMap.put("修改时间", "modifyTime");

        Map<String, String> fileTypeMap = Maps.newHashMap();
        fileTypeMap.put("startTime", Constants.EXCEL_TYPE_DATE);
        fileTypeMap.put("endTime", Constants.EXCEL_TYPE_DATE);
        fileTypeMap.put("modifyTime", Constants.EXCEL_TYPE_DATE);

        if (null != couponCodeList && couponCodeList.size() > 0) {
            excelService.exportExcelByJxl(couponCodeList, fileName, exportMap, fileTypeMap, response);
        } else {
            returnFastJSON(ResponseMessageMap.selfDefineMap(-1,"没有匹配的数据"));
        }
    }
}
