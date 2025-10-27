package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class Kredyt {
	
	private String kwota;
	private String lata;
	private String proc;
	private Double rata;

	@Inject
	FacesContext ctx;

	public String getKwota() {
		return kwota;
	}

	public void setKwota(String kwota) {
		this.kwota = kwota;
	}

	public String getLata() {
		return lata;
	}

	public void setLata(String lata) {
		this.lata = lata;
	}
	
	public String getProc() {
		return proc;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}

	public Double getRata() {
		return rata;
	}

	public void setRata(Double rata) {
		this.rata = rata;
	}

	public boolean doTheMath() {
		try {
			double k = Double.parseDouble(this.kwota);
			double l = Double.parseDouble(this.lata);
			double p = Double.parseDouble(this.proc);
			
			if (k <= 0 || l <= 0) {
			    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
			        "Kwota kredytu i liczba lat muszą być większe od 0", null));
			    return false;
			}
			if (p < 0) {
			    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
			        "Oprocentowanie nie może być ujemne", null));
			    return false;
			}

			double ile_mies = l * 12;
			double mies_oproc = (p/100)/12;
			
			if(mies_oproc == 0) {
				rata = k/ile_mies;
			}
			else
			{
				rata = k * (mies_oproc / (1- Math.pow(1 + mies_oproc, -ile_mies)));
				
			}
			

			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
			return false;
		}
	}

	
	public String calc() {
		if (doTheMath()) {
			return "showresult";
		}
		return null;
	}

	public String info() {
		return "info";
	}
}
