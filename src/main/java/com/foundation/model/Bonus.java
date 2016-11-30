package com.foundation.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Bonus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "quantidade_bonus_corrente")
	private BigDecimal quantidadeBonusCorrente;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	public Bonus() { }
	
	public Bonus(Cliente cliente, Produto produto) {
		this.cliente = cliente;
		this.produto = produto;
	}
	
	public void diminuirBonus(BigDecimal quantidadeDiminuir) {
		this.quantidadeBonusCorrente = this.quantidadeBonusCorrente.subtract(quantidadeDiminuir);
	}
	
	public void incrementarBonus(BigDecimal quantidadeIncrementar) {
		this.quantidadeBonusCorrente = this.quantidadeBonusCorrente.add(quantidadeIncrementar);
	}
	
}
