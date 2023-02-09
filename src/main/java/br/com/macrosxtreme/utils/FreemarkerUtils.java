package br.com.macrosxtreme.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public final class FreemarkerUtils {

	private static Configuration cfg = new Configuration();

	private FreemarkerUtils() {
	}

	public static final String parseTemplate(Map<?, ?> map, String templateName) throws TemplateException, IOException {
		// diretÃ³rio onde estÃ£o templates
		cfg.setClassForTemplateLoading(FreemarkerUtils.class, "/templates_ftl/");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");

		// recupera o template
		Template t = cfg.getTemplate(templateName);
		StringWriter writer = new StringWriter();

		// Freemarker
		t.process(map, writer);
		writer.flush();
		writer.close();
		return writer.toString();
	}
}