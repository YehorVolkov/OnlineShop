package com.yehor.onlineshop.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {

    public static Map<String, Object> allData = new HashMap<>(); // TODO bad solution?

    public static String getPage(String filename, Map<String, Object> additionalData) {
        Writer stream = new StringWriter();
        try {
            Configuration configuration = new Configuration();
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate(filename);
            if (additionalData != null && !additionalData.isEmpty()) {
                allData.putAll(additionalData);
            }
            template.process(allData, stream);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public static String getPage(String filename) {
        return getPage(filename, null);
    }
}