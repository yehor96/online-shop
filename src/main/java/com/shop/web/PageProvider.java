package com.shop.web;

import com.shop.helper.ClasspathReader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageProvider {

    private static final Configuration CONFIG = new Configuration(Configuration.VERSION_2_3_31);

    public PageProvider() {
        String templatesPath = ClasspathReader.getFilePath("/templates");
        try {
            CONFIG.setDirectoryForTemplateLoading(new File(templatesPath));
            CONFIG.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Not able to configure PageProvider. " + e);
        }
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CONFIG.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }

    public String getCssPage(String filename) {
        return getPage("css" + filename);
    }

}
