package com.cipher.seed.util;

import com.cipher.seed.domain.TableInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Component
public class PropertyUtil {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${package.prefix}")
    private String packagePrefix;
    @Value("${tables}")
    private String tableStr;
    @Value("${out_path}")
    private String path;

    // -==================================================

    /**
     * 转换为短表名
     */
    public String transferToShortTableName(String name) {
        String str = transferToPascalName(name);
        ByteArrayInputStream bin = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int t;
        while ((t = bin.read()) != -1) {
            char c = (char) t;
            if ((c >= 'A') && (c <= 'Z')) {
                out.write(t);
            }
        }
        return out.toString().toLowerCase();
    }

    /**
     * 转换为首字母大写驼峰命名
     */
    public String transferToPascalName(String name) {
        String camelName = transferToCamelName(name);
        return camelName.substring(0, 1).toUpperCase()
                + camelName.substring(1, camelName.length());
    }

    /**
     * 转换为首字母小写驼峰命名
     */
    public String transferToCamelName(String name) {
        name = name.toLowerCase();
        String camelName = name + "";
        while (camelName.contains("_")) {
            int index = camelName.indexOf("_");
            if (camelName.length() <= 1) {
                camelName = "";
            } else if (camelName.startsWith("_")) {
                camelName = camelName.substring(1, camelName.length());
            } else if (camelName.endsWith("_")) {
                camelName = camelName.substring(0, camelName.length() - 1);
            } else {
                camelName = camelName.substring(0, index)
                        + camelName.substring(index + 1, index + 2)
                        .toUpperCase() + camelName.substring(index + 2);
            }
        }
        return camelName.substring(0, 1).toLowerCase()
                + camelName.substring(1, camelName.length());
    }

    /**
     * 获取数据库名
     */
    public String getTableSchema() {
        int index1 = datasourceUrl.lastIndexOf("/");
        int index2 = datasourceUrl.indexOf("?");
        return datasourceUrl.substring(index1 + 1, index2);
    }

    /**
     * 获取表名
     */
    public String[] getTables() {
        return tableStr.split(",");
    }

    /**
     * 获取模板变量
     */
    public Map<String, Object> getModel(String type) {
        Map<String, Object> model = Maps.newConcurrentMap();
        String templateName = null;
        String suffix = null;
        switch (type.toLowerCase()) {
            case "domain":
                templateName = "VOTemplate.vm";
                suffix = "VO.java";
                break;
            case "condition":
                templateName = "ConditionTemplate.vm";
                suffix = "Condition.java";
                break;
            case "mapper":
                templateName = "MapperTemplate.vm";
                suffix = "Mapper.xml";
                break;
            case "dao":
                templateName = "DaoTemplate.vm";
                suffix = "Dao.java";
                break;
            default:
                break;
        }
        String path = this.path + File.separator + "code" + File.separator + type;
        model.put("domainPackage", ".domain.vo");
        model.put("conditionPackage", ".domain.condition");
        model.put("daoPackage", ".dao");
        model.put("path", path);
        model.put("templateName", templateName);
        model.put("suffix", suffix);
        model.put("packagePrefix", this.packagePrefix);
        model.put("nowDate", getNow());
        return model;
    }

    /**
     * 获取字段集合
     */
    public List<Map<String, String>> getFieldList(List<TableInfo> tablesInfo) {
        List<Map<String, String>> list = Lists.newArrayList();
        for (TableInfo t : tablesInfo) {
            Map<String, String> map = Maps.newConcurrentMap();
            String camelName = transferToCamelName(t.getColName());
            String pascalName = transferToPascalName(t.getColName());
            String type = getDataType(t.getColType())[0];
            String mapperType = getDataType(t.getColType())[1];
            map.put("colName", t.getColName().toLowerCase());
            map.put("camelName", camelName);
            map.put("pascalName", pascalName);
            map.put("type", type);
            map.put("mapperType", mapperType);
            list.add(map);
        }
        return list;
    }

    // -====================== 私有方法 ========================

    // 获取当前时间
    private String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    // 获取字段类型
    private String[] getDataType(String dataType) {
        String javaType;
        String mapperType;
        switch (dataType.toLowerCase()) {
            case "bigint":
                javaType = "Long";
                mapperType = "BIGINT";
                break;
            case "int":
                javaType = "Integer";
                mapperType = "INTEGER";
                break;
            case "mediumint":
                javaType = "Integer";
                mapperType = "INTEGER";
                break;
            case "varchar":
                javaType = "String";
                mapperType = "VARCHAR";
                break;
            case "char":
                javaType = "String";
                mapperType = "VARCHAR";
                break;
            case "text":
                javaType = "String";
                mapperType = "VARCHAR";
                break;
            case "longtext":
                javaType = "String";
                mapperType = "VARCHAR";
                break;
            case "date":
                javaType = "Timestamp";
                mapperType = "TIMESTAMP";
                break;
            case "time":
                javaType = "Timestamp";
                mapperType = "TIMESTAMP";
                break;
            case "timestamp":
                javaType = "Timestamp";
                mapperType = "TIMESTAMP";
                break;
            case "datetime":
                javaType = "Timestamp";
                mapperType = "TIMESTAMP";
                break;
            case "smallint":
                javaType = "Integer";
                mapperType = "INTEGER";
                break;
            case "tinyint":
                javaType = "Integer";
                mapperType = "INTEGER";
                break;
            case "float":
                javaType = "Double";
                mapperType = "DOUBLE";
                break;
            case "double":
                javaType = "Double";
                mapperType = "DOUBLE";
                break;
            case "decimal":
                javaType = "BigDecimal";
                mapperType = "BIGDECIMAL";
                break;
            case "blob":
                javaType = "byte[]";
                mapperType = "BINARY";
                break;
            default:
                throw new IllegalArgumentException("找不到类型映射关系，type：" + dataType);
        }
        return new String[]{javaType, mapperType};
    }

}