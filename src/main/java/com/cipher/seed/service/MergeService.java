package com.cipher.seed.service;

import java.io.Writer;
import java.util.Map;

/**
 * 合并接口
 * Created by cipher on 2017/5/5.
 */
public interface MergeService {

    String mergeTemplateIntoString(String templateName, Map<String, Object> model);

    void mergeTemplate(String templateName, Map<String, Object> model, Writer writer);

    void mergeTemplateToFile(String templateName, Map<String, Object> model, String filePath, String fileName);

}
