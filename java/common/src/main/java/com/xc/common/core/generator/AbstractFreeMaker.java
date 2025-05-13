package com.xc.common.core.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xc.common.constant.CharacterConstants;
import com.xc.common.core.annotation.FreeMaker;
import com.xc.common.core.domain.model.Table;
import com.xc.common.utils.FreeMakerUtils;
import com.xc.common.utils.JsonUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFreeMaker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private void execute(String outFileUrl, String templateFileUrl, String templateFileName) throws IOException, TemplateException {
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        HashMap<String, Table> templateTypeTable = ThreadLocalManager.getCurrentTemplateTypeTable();
        String templateType = this.getClass().getAnnotation(FreeMaker.class).templateType();
        HashMap<String, Object> table = objectMapper.convertValue(templateTypeTable.get(templateType), new TypeReference<HashMap<String, Object>>(){});
        Configuration configuration = FreeMakerUtils.crateConfiguration(templateFileUrl);
        File outFile = new File(outFileUrl);
        FileOutputStream os = new FileOutputStream(outFile);
        Template template = configuration.getTemplate(templateFileName, CharacterConstants.UTF8);
        Writer out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outFile.toPath()), CharacterConstants.UTF8));
        editTable(table);
        template.process(table, out);
        out.close();
        os.close();
    }

    public abstract void myExecute(String outFileUrl, String templateFileUrl, String templateFileName);

    public abstract void editTable(Map<String, Object> dataMap);
}
