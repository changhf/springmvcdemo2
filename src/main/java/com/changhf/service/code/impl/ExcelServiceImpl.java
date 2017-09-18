package com.changhf.service.code.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.changhf.utils.DateConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhf.common.Constants;
import com.changhf.common.ResponseMessageMap;
import com.changhf.service.code.ExcelService;
import com.changhf.utils.StringUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
@Service("excelService")
public class ExcelServiceImpl implements ExcelService<Map<String, Object>> {
	private final static Logger logger = Logger.getLogger(ExcelServiceImpl.class);
	@Autowired
	private DateConverter dateConverter;
	@Override
	public Map<String, Object> exportExcelByJxl(List<Map<String, Object>> listContent, String fileName,
			Map<String, String> exportMap, Map<String, String> fileTypeMap, HttpServletResponse response) {
		 try {
	            // 定义输出流，以便打开保存对话框______________________begin
	            OutputStream os = response.getOutputStream();// 取得输出流
	            response.reset();// 清空输出流
	            response.setHeader("Content-disposition", "attachment; filename="
	                    + new String((fileName).getBytes("gbk"), "iso8859-1") + ".xls");// 组装附件名称和格式
	            response.setContentType("application/msexcel");// 定义输出类型
	            // 定义输出流，以便打开保存对话框_______________________end

	            /** **********创建工作簿************ */
	            WritableWorkbook workbook = Workbook.createWorkbook(os);

	            //数据量太大的时候，分多个sheet表导出
	            int maxNum = Constants.MAX_EXCEL_CELL;
	            int sheetNum = 0;
	            if (null != listContent && listContent.size() > 0) {
	                if (listContent.size() % maxNum == 0) {
	                    sheetNum = listContent.size() / maxNum;
	                } else {
	                    sheetNum = listContent.size() / maxNum + 1;
	                }
	            } else {
	                sheetNum = 1;
	            }
	            for (int sh = 0; sh < sheetNum; sh++) {
	                List<Map<String, Object>> subList = new ArrayList<>();
	                int fromIndex = 0;
	                int toIndex = 0;
	                String sheetName = null;
	                if (null != listContent && listContent.size() > 0) {
	                    fromIndex = maxNum * sh;
	                    if (sh == sheetNum - 1) {
	                        if (listContent.size() % maxNum == 0) {
	                            toIndex = maxNum * (sh + 1);
	                        } else {
	                            toIndex = maxNum * sh + listContent.size() % maxNum;
	                        }
	                    } else {
	                        toIndex = maxNum * (sh + 1);
	                    }
	                }
	                if (sheetNum == 1) {
	                    subList = listContent;
	                    sheetName = fileName;
	                } else {
	                    subList = listContent.subList(fromIndex, toIndex);
	                    int sheetIndex = sh+1;
	                    sheetName = fileName+sheetIndex;
	                }

	                /** **********创建工作表************ */

	                WritableSheet sheet = workbook.createSheet(fileName, 0);
	                sheet.setName(sheetName);

	                /** **********设置纵横打印（默认为纵打）、打印纸***************** */
	                jxl.SheetSettings sheetset = sheet.getSettings();
	                sheetset.setProtected(false);

	                /** ************设置单元格字体************** */
	                WritableFont normalFont = new WritableFont(WritableFont.createFont("宋体"), 10);
	                WritableFont boldFont = new WritableFont(WritableFont.createFont("宋体"), 10,
	                        WritableFont.BOLD);

	                /** ************以下设置三种单元格样式，灵活备用************ */
	                // 用于标题居中
	                WritableCellFormat wcfCenter = new WritableCellFormat(boldFont);
	                wcfCenter.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
	                wcfCenter.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
	                wcfCenter.setAlignment(Alignment.CENTRE); // 文字水平对齐
	                wcfCenter.setWrap(false); // 文字是否换行

	                // 用于正文居左
	                WritableCellFormat wcfLeft = new WritableCellFormat(normalFont);
	                wcfLeft.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
	                wcfLeft.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
	                wcfLeft.setAlignment(Alignment.LEFT); // 文字水平对齐
	                wcfLeft.setWrap(false); // 文字是否换行

	                /** ***************以下是EXCEL开头大标题，暂时省略********************* */
	                // sheet.mergeCells(0, 0, colWidth, 0);
	                // sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
	                /** ***************以下是EXCEL第一行列标题********************* */
	                Object[] columArr = exportMap.keySet().toArray();
	                Object[] filedNameArr = exportMap.values().toArray();
	                for (int i = 0; i < columArr.length; i++) {
	                    sheet.addCell(new Label(i, 0, columArr[i].toString(), wcfCenter));
	                }
	                /** ***************以下是EXCEL正文数据********************* */
	                int i = 1;
	                if (null != subList && subList.size() > 0) {
	                    for (Map<String, Object> obj : subList) {

	                        int j = 0;
	                        for (Object fieldName : filedNameArr) {
	                            Object value = obj.get(fieldName);
	                            if (value == null) {
	                                value = "";
	                            }
	                            if ("illegalStatus".equals(fieldName)) {
	                                if ("2".equals(value.toString())) {
	                                    value = "异常";
	                                } else if ("1".equals(value.toString())) {
	                                    value = "可疑";
	                                } else {
	                                    value = "正常";
	                                } 
	                                
	                            }
	                            if ("status".equals(fieldName)) {
	                                if ((int) value == 1) {
	                                    value = "可用";
	                                } else {
	                                    value = "不可用";
	                                }
	                            }
	                            if (!StringUtils.isEmpty(value) && null != fileTypeMap && fileTypeMap.size() > 0) {
	                                String fieldType = fileTypeMap.get(fieldName.toString());
	                                if (!StringUtils.isEmpty(fieldType) && Constants.EXCEL_TYPE_DATE.equals(fieldType)) {
										value = dateConverter.format((Date) value);
									}
	                            }
	                            sheet.addCell(new Label(j, i, value.toString(), wcfLeft));
	                            j++;
	                        }
	                        i++;
	                    }
	                }
	            }
	            /** **********将以上缓存中的内容写到EXCEL文件中******** */
	            workbook.write();
	            /** *********关闭文件************* */
	            workbook.close();
	            logger.info("导出excel成功");
	        } catch (Exception e) {
	            logger.error("导出excel失败", e);
	            return ResponseMessageMap.selfDefineMap(-1,"导出excel失败");
	        }
	        return ResponseMessageMap.successMap();
	}

}
