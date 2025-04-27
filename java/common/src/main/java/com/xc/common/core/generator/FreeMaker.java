package com.xc.common.core.generator;

import com.xc.common.utils.FreeMakerUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;

public abstract class FreeMaker {

    public static final String ENCODING = "utf-8";
    public static final int ENCODING_SIZE = 10240;

    private String outFileUrl;
    private String templateFileUrl;
    private String templateFileName;
    private HashMap<String, Object> dataMap;

    public FreeMaker(String outFileUrl, String templateFileUrl, String templateFileName) {
        this.outFileUrl = outFileUrl;
        this.templateFileUrl = templateFileUrl;
        this.templateFileName = templateFileName;
    }

    private void execute() throws IOException, TemplateException {
        Configuration configuration = FreeMakerUtils.crateConfiguration(this.templateFileUrl);
        File outFile = new File(this.outFileUrl);
        FileOutputStream os = new FileOutputStream(outFile);
        Template template = configuration.getTemplate(this.templateFileName, ENCODING);
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), ENCODING), ENCODING_SIZE);
        json2DataMap();
        template.process(this.dataMap, out);
        out.close();
        os.close();
    }

    private void json2DataMap(){
        editDataMap(this.dataMap);
    }

    public abstract void editDataMap(HashMap<String, Object> dataMap);
}
