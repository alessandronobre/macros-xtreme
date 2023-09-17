package br.com.macrosxtreme.mapper;

import br.com.macrosxtreme.dto.EmailDTO;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@Component
public class GerarItensEmailMapper {

    private final TemplateEngine templateEngine;

    public EmailDTO instanciaEmailDtoComConteudoHtml(String destinatario,
                                              String titulo,
                                              Object conteudo,
                                              String nomeTemplate) {
        EmailDTO email = new EmailDTO();
        email.setDestinatario(destinatario);
        email.setTitulo(titulo);
        email.setConteudo(processarTemplateHtml(nomeTemplate, conteudo));
        return email;
    }

    public EmailDTO instanciaEmailDtoComAnexoPdf(String destinatario,
                                                 String titulo,
                                                 String conteudo,
                                                 Object anexo,
                                                 String nomeAnexoComExtensao,
                                                 String nomeTemplateAnexo) {
        EmailDTO email = new EmailDTO();
        email.setDestinatario(destinatario);
        email.setTitulo(titulo);
        email.setConteudo(conteudo);
        email.setNomeAnexo(nomeAnexoComExtensao);
        email.setAnexo(gerarPdf(nomeTemplateAnexo, anexo));
        return email;
    }

    public String processarTemplateHtml(String template, Object itensParaProcessamento) {
        Context context = new Context();
        context.setVariable("itens", itensParaProcessamento);

        String html = templateEngine.process(template, context);
        return html;
    }

    public byte[] gerarPdf(String template, Object itensParaProcessamento) {
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(processarTemplateHtml(template, itensParaProcessamento), pdfStream);
        return pdfStream.toByteArray();
    }
}
