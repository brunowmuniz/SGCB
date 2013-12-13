package br.com.casabemestilo.model;

import java.util.Date;

public class ParcelaDia {

	private Date dataParcela;
	
	private int totalCheques;
	
	private Float valorTotalParcelas;

	public ParcelaDia(Date dataParcela, int totalCheques,
			Float valorTotalParcelas) {
		super();
		this.dataParcela = dataParcela;
		this.totalCheques = totalCheques;
		this.valorTotalParcelas = valorTotalParcelas;
	}

	public Date getDataParcela() {
		return dataParcela;
	}

	public void setDataParcela(Date dataParcela) {
		this.dataParcela = dataParcela;
	}

	public int getTotalCheques() {
		return totalCheques;
	}

	public void setTotalCheques(int totalCheques) {
		this.totalCheques = totalCheques;
	}

	public Float getValorTotalParcelas() {
		return valorTotalParcelas;
	}

	public void setValorTotalParcelas(Float valorTotalParcelas) {
		this.valorTotalParcelas = valorTotalParcelas;
	}
	
	
	
}
