package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@HeadRowHeight(50)
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class Master implements Serializable {
    @Id
    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("法号")
    private String username;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("图片路径")
    private String head;

    private String status;
}
