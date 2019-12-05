package com.baizhi.com;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by HIAPAD on 2019/12/2.
 */
public class ImageConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws IOException {
        // 将value 截取 转换为绝对路径
        // new File(绝对路径)
        return new CellData(FileUtils.readFileToByteArray(new File(value)));
    }
}
