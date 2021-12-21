package com.wz.test.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wz.excel.annotation.Export;
import com.wz.excel.enums.TemplateTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName: wz-component-test
 * @package: com.wz.test.controller
 * @className: ExcelController
 * @description:
 * @author: zhi
 * @date: 2021/12/20
 * @version: 1.0
 */
@RestController
@RequestMapping("/export")
public class ExcelController {

    @GetMapping("/default")
    @Export
    public List<Data> defaultExport() {
        return List.of(new Data("张三", 18), new Data("李四", 20));
    }

    @GetMapping("/default_mulit")
    @Export(sheet = {
            @Export.Sheet(sheetName = "data1"),
            @Export.Sheet(sheetName = "data2"),
    })
    public List<List<Object>> defaultExportMulit() {
        return List.of(
                List.of(new Data("张三", 18), new Data("李四", 20)),
                List.of(new Data2("sheet2", 12, 11))
        );
    }

    @GetMapping("/tmp1")
    @Export(templateType = TemplateTypeEnum.CLASSPATH, template = "sheet1.xlsx")
    public List<Data> temp1() {
        return List.of(new Data("张三", 18), new Data("李四", 20));
    }

    @GetMapping("/tmp2")
    @Export(fileName = "tmp2", templateType = TemplateTypeEnum.CLASSPATH, template = "sheet2.xlsx")
    public List<List<Object>> temp2() {
        return List.of(
                List.of(new Data("张三", 18), new Data("李四", 20)),
                List.of(new Data2("sheet2", 12, 11))
        );
    }

    @GetMapping("/tmp3")
    @Export(fileName = "tmp3", templateType = TemplateTypeEnum.LOCAL, template = "/Users/wangzhi/work/test-wz-component/test-wz-excel/src/main/resources/sheet2.xlsx")
    public List<List<Object>> temp3() {
        return List.of(
                List.of(new Data("张三", 18), new Data("李四", 20)),
                List.of(new Data2("sheet2", 12, 11))
        );
    }

    @GetMapping("/tmp4")
    @Export(fileName = "tmp4", templateType = TemplateTypeEnum.URL, template = "https://gitee.com/wz-dazhi/pic/raw/master/test/sheet2.xlsx")
    public List<List<Object>> temp4() {
        return List.of(
                List.of(new Data("张三", 18), new Data("李四", 20)),
                List.of(new Data2("sheet2", 12, 11))
        );
    }

    @lombok.Data
    @AllArgsConstructor
    static class Data implements Serializable {
        @ExcelProperty("姓名")
        private String name;
        @ExcelProperty("年龄")
        private int age;
    }

    @lombok.Data
    @AllArgsConstructor
    static class Data2 implements Serializable {
        @ExcelProperty("姓名")
        private String name;
        @ExcelProperty("年龄")
        private int age;
        @ExcelProperty("年龄2")
        private int age11;
    }

}
