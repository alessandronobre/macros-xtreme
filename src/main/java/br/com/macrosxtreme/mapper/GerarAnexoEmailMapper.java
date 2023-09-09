package br.com.macrosxtreme.mapper;

import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@Component
public class GerarAnexoEmailMapper {

    private final TemplateEngine templateEngine;

    public byte[] gerarAnexoPdf(String template, Object anexo) {
        Context context = new Context();
        context.setVariable("anexo", anexo);

        String html = templateEngine.process(template, context);

        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, pdfStream);
        return pdfStream.toByteArray();
    }
}
