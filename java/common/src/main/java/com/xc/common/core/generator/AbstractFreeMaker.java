package com.xc.common.core.generator;

import com.xc.common.utils.FreeMakerUtils;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String ENCODING = "utf-8";
    public static final int ENCODING_SIZE = 10240;

    protected void execute(String outFileUrl, String templateFileUrl, String templateFileName) throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        Configuration configuration = FreeMakerUtils.crateConfiguration(templateFileUrl);
        File outFile = new File(outFileUrl);
        FileOutputStream os = new FileOutputStream(outFile);
        Template template = configuration.getTemplate(templateFileName, ENCODING);
        Writer out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outFile.toPath()), ENCODING), ENCODING_SIZE);
        json2DataMap(dataMap);
        template.process(dataMap, out);
        out.close();
        os.close();
    }

    private void json2DataMap(Map<String, Object> dataMap){
        Map<String, Object> resources = ThreadLocalManager.getResources().get();
        Object json = resources.get("json");
        editDataMap(dataMap);
    }

    public abstract void editDataMap(Map<String, Object> dataMap);
}
