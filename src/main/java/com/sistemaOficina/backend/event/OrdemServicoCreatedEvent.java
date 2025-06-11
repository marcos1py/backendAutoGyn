package com.sistemaOficina.backend.event;

import org.springframework.context.ApplicationEvent;
import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.dto.ItensServicoDTO;
import java.util.List;

public class OrdemServicoCreatedEvent extends ApplicationEvent {
    private final OrdemServico ordemServico;
    private final List<ItensServicoDTO> itensServico;

    public OrdemServicoCreatedEvent(Object source, OrdemServico ordemServico, List<ItensServicoDTO> itensServico) {
        super(source);
        this.ordemServico = ordemServico;
        this.itensServico = itensServico;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public List<ItensServicoDTO> getItensServico() {
        return itensServico;
    }
}
