package com.xc.common.utils;


import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

public class FreeMakerUtils {

    public static Configuration crateConfiguration(String templatesUrl) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        configuration.setDirectoryForTemplateLoading(new File(templatesUrl));
        configuration.setDefaultEncoding("utf-8");

        return configuration;
    }

}
