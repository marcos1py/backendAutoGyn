package com.sistemaOficina.backend.event;

import com.sistemaOficina.backend.dto.ItensServicoDTO;
import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.service.FuncionarioService;
import com.sistemaOficina.backend.service.ServicoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OrdemServicoEventListener {

    private final FuncionarioService funcionarioService;
    private final ServicoService servicoService;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public OrdemServicoEventListener(FuncionarioService funcionarioService,
                                     ServicoService servicoService,
                                     JavaMailSender mailSender) {
        this.funcionarioService = funcionarioService;
        this.servicoService = servicoService;
        this.mailSender = mailSender;
    }

    @EventListener
    public void handleOrdemServicoCreated(OrdemServicoCreatedEvent event) {
        OrdemServico os = event.getOrdemServico();
        for (ItensServicoDTO dto : event.getItensServico()) {
            Funcionario f = funcionarioService.buscarPorId(dto.getFuncionarioId().intValue());
            Servico s = servicoService.buscarPorId(dto.getServicoId().intValue());
            if (f != null && s != null && f.getEmail() != null && !f.getEmail().isBlank()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(f.getEmail());
                message.setSubject("Novo serviço atribuído: " + s.getNome());
                message.setText("Olá " + f.getNome() + ",\n\n" +
                                "Você foi atribuído ao serviço \"" + s.getNome() + "\" " +
                                "na ordem de serviço número " + os.getNumero() + ".\n\n" +
                                "Atenciosamente,\nEquipe Oficina");
                mailSender.send(message);
            }
        }
    }
}
