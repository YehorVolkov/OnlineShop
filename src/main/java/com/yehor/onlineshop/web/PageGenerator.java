package com.yehor.onlineshop.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {

    private final Configuration configuration = new Configuration();

    public String getPage(String filename, Map<String, ?> data) {
        Writer stream = new StringWriter();
        try {
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate(filename);
            template.process(data, stream);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public String getPage(String filename) {
        return getPage(filename, null);
    }
}