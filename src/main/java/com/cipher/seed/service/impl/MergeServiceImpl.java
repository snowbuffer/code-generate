package com.cipher.seed.service.impl;

import com.cipher.seed.service.MergeService;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * 合并接口实现类
 * Created by cipher on 2017/5/5.
 */
@Component
public class MergeServiceImpl implements MergeService {

    private static String encoding = "UTF-8";

    private static Logger LOG = LoggerFactory.getLogger(MergeServiceImpl.class);

    @Autowired
    private VelocityEngine engine;

    @Override
    public String mergeTemplateIntoString(String templateName, Map<String, Object> model) {
        return VelocityEngineUtils.mergeTemplateIntoString(engine, templateName, encoding, model);
    }

    @Override
    public void mergeTemplate(String templateName, Map<String, Object> model, Writer writer) {
        VelocityEngineUtils.mergeTemplate(engine, templateName, encoding, model, writer);
    }

    @Override
    public void mergeTemplateToFile(String templateName, Map<String, Object> model, String filePath, String fileName) {
        StringWriter writer = new StringWriter();
        mergeTemplate(templateName, model, writer);
        FileOutputStream out;
        try {
            File outDir = new File(filePath);
            if (!outDir.exists()) {
                boolean mkdirs = outDir.mkdirs();
                LOG.debug("mkdirs: " + mkdirs);
            }
            String outputFile = filePath + File.separator + fileName;
            out = new FileOutputStream(new File(outputFile));
            String outputString = writer.toString();
            out.write(outputString.getBytes());
            out.close();
            writer.close();
        } catch (Exception e) {
            LOG.error("" + e);
            throw new RuntimeException(e);
        }
    }
}
