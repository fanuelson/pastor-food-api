package com.foundation.filtroConsulta;

import java.util.Date;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FiltroConsultaCaixa {

	private Long idCaixa;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	public Date getDataInicialHoraZerada() {
		return this.dataInicial == null ? null : new DateTime(this.dataInicial).withTimeAtStartOfDay().toDate();
	}
	
	public Date getDataFinalHoraFinalDia() {
		return this.dataFinal == null ? null : new DateTime(this.dataFinal).plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
	}
	
}
