package br.com.casabemestilo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParcelaDia {

	private Date dataParcela;
	
	private int totalCheques = 0;
	
	private Float valorTotalParcelas = new Float("0");
	
	private List<Parcela> parcelas = new ArrayList<Parcela>();

	public ParcelaDia(Date dataParcela, int totalCheques,
			Float valorTotalParcelas) {
		super();
		this.dataParcela = dataParcela;
		this.totalCheques = totalCheques;
		this.valorTotalParcelas = valorTotalParcelas;
	}
	
	public ParcelaDia(){}

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

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	
}
