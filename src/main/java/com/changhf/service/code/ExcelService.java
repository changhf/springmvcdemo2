package com.changhf.service.code;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alice
 * 导出excel
 * @param <T>
 * @Date:2016年11月18日
 */
public interface ExcelService<T> {

    /**
     * 导出excel
     * @param listContent
     * @param fileName
     * @param exportMap
     * @param response
     * @return
     */
    public Map<String, Object> exportExcelByJxl(List<Map<String, Object>> listContent, String fileName, Map<String, String> exportMap,
                                                Map<String, String> fileTypeMap, HttpServletResponse response);
}
