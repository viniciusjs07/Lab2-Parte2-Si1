package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.common.base.Objects;

// Entidade que representa um Livro
// Referenciar a uma tabela
@Entity(name = "Anuncio")
public class Anuncio implements Serializable,Comparable<Anuncio> {

	// Todo Id tem que ter o GeneratedValue a não ser que ele seja setado
	// Usar Id sempre Long
	@Id
	@GeneratedValue
	private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
	private List<Anunciante> autores;

	@Column
	private String nome,cidade,bairro,email,face,obj;
	//@OneToMany
	//@ElementCollection
	//private List<Instrumento> instrumentos;
	@ElementCollection
	private List<String> instrumentos;
	@ElementCollection
	private List<String> estilosMusicais;
	
	@Column(name = "create_on")
	@Temporal(value = TemporalType.DATE)
	private Date createdOn;
	
	@Transient
	private static SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	
	@Transient
	private static final long serialVersionUID = 1L;

	// Construtor vazio para o Hibernate criar os objetos
	public Anuncio() {
		this.autores = new ArrayList<Anunciante>();
	}
	public Anuncio(String nome,String cidade,String bairro,List<String> instrumentos,List<String> estilosMusicais,String email,String face,String obj) {
		this();
		this.nome = nome;
		this.cidade = cidade;
		this.bairro = bairro;
		this.instrumentos = new ArrayList<String>();
		this.email = email;
		this.face = face;
		this.obj = obj;
		this.estilosMusicais = new ArrayList<String>();
		setCreatedOn(new Date());
	}
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public List<String> getInstrumentos() {
		return instrumentos;
	}
	
	public void setInstrumentos(List<String> instrumentos) {
		this.instrumentos = instrumentos;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFace() {
		return face;
	}
	
	public void setFace(String face) {
		this.face = face;
	}
	
	public String getObj() {
		return obj;
	}
	
	public void setObj(String obj) {
		this.obj = obj;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public List<String> getEstilosMusicais() {
		return estilosMusicais;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setEstilosMusicais(List<String> estilosMusicais) {
		this.estilosMusicais = estilosMusicais;
	}
    public String getNome() {
		return nome;
	}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Anuncio)) {
			return false;
		}
		Anuncio outroLivro = (Anuncio) obj;
		return Objects.equal(outroLivro.getNome(), this.getNome());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getNome());
	}

    public List<Anunciante> getAutores() {
        return Collections.unmodifiableList(autores);
    }

    public void addAnunciante(Anunciante anunciante) {
        autores.add(anunciante);
    }
    
    public String getDateFormat() {
		return formatoData.format(createdOn);
		
	}
	@Override
	public int compareTo(Anuncio other) {
		
		return this.createdOn.compareTo(other.getCreatedOn()) * (-1);
	}

}
