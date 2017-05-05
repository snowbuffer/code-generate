package com.cipher.seed.service.impl;

import com.cipher.seed.dao.TableInfoDao;
import com.cipher.seed.service.MergeService;
import com.cipher.seed.service.TemplateService;
import com.cipher.seed.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 模板接口实现类
 * Created by cipher on 2017/5/5.
 */
@Component
public class TemplateServiceImpl implements TemplateService {

    private static Logger LOG = LoggerFactory.getLogger(MergeServiceImpl.class);

    @Autowired
    private MergeService mergeService;

    @Autowired
    private TableInfoDao dao;

    @Autowired
    private PropertyUtil property;

    @Override
    public void merge(String type) {
        Map<String, Object> model = property.getModel(type);
        // -========= 基础变量 ============
        String templateName = model.get("templateName").toString();
        String path = model.get("path").toString();
        String suffix = model.get("suffix").toString();
        String tableSchema = property.getTableSchema();
        // -========= 表相关的变量 ============
        for (String table : property.getTables()) {
            String camelName = property.transferToCamelName(table);
            String pascalName = property.transferToPascalName(table);
            model.put("camelName", camelName);
            model.put("pascalName", pascalName);
            model.put("tableName", table);
            model.put("shortTableName", property.transferToShortTableName(table));
            model.put("fieldList", property.getFieldList(dao.selectTableInfo(tableSchema, table)));
            mergeService.mergeTemplateToFile(templateName, model, path, pascalName + suffix);
            LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> merge<" + type + ">success!");
        }
    }

}
