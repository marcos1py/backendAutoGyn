package com.sistemaOficina.backend.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sistemaOficina.backend.entidade.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);
    private final SpringTemplateEngine templateEngine;

    public PdfService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] gerarPdfOrdemServico(OrdemServico os,
                                      Cliente cliente,
                                      Veiculo veiculo,
                                      List<ItensPeca> itensPeca,
                                      List<ItensServico> itensServico) throws Exception {

        // Criar contexto com os dados
        Context ctx = new Context();
        ctx.setVariable("os", os);
        ctx.setVariable("cliente", cliente);
        ctx.setVariable("veiculo", veiculo);
        ctx.setVariable("itensPeca", itensPeca);
        ctx.setVariable("itensServico", itensServico);
        ctx.setVariable("now", LocalDateTime.now());

        // Processar o template HTML
        String html = templateEngine.process("os-pdf", ctx);

        logger.debug("Gerando PDF para a OS número: {}", os.getNumero());

        // Criar o PDF diretamente em memória
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null)
                  .useFastMode()
                  .useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM) // A4
                  .usePdfUaAccessbility(false)
                  .toStream(baos)
                  .run();

            byte[] pdfBytes = baos.toByteArray();
            logger.info("PDF gerado com sucesso para a OS número: {}, tamanho: {} bytes", os.getNumero(), pdfBytes.length);

            return pdfBytes;
        }
    }
}
