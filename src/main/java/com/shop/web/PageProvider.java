package com.shop.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

@UtilityClass
public class PageProvider {

    private static final String ROOT = "src/main/resources";
    private static Configuration config;

    //TODO remove static init -> use constructor
    static {
        initConfig();
    }

    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = config.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public static String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }

    public static String getCssPage(String filename) {
        return getPage("css" + filename);
    }

    private static void initConfig() {
        config = new Configuration(Configuration.VERSION_2_3_31);
        try {
            config.setDirectoryForTemplateLoading(new File(ROOT));
            config.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Not able to configure PageProvider. " + e);
        }
    }

}
