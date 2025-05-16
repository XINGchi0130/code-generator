package com.xc.common.core.generator;

import com.xc.common.constant.CharacterConstants;
import com.xc.common.core.domain.model.Table;
import com.xc.common.utils.FreeMakerUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

public abstract class AbstractFreeMaker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private void execute(String outFileUrl, String templateFileUrl, String templateFileName) throws IOException, TemplateException {
        Table table = ThreadLocalManager.getCurrentTable();
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

    public abstract void editTable(Table table);
}
