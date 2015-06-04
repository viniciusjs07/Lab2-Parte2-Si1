package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.Objects;

@Entity
public class Instrumento {

	@Id
	private String nome;

	public Instrumento() {

	}

	public String getInstrumento() {
		return nome;
	}

	public void setInstrumento(String nome) {
		this.nome = nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Instrumento)) {
			return false;
		}
		Instrumento novoInstrumento = (Instrumento) obj;

		return getInstrumento().equals(novoInstrumento.getInstrumento());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.nome);
	}

}
