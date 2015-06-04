package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Estilos {

	@Id
	@GeneratedValue
	private Long id;

	@Transient
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false)
	private String nome;

	public Estilos() {
	}

	public Estilos(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

}
