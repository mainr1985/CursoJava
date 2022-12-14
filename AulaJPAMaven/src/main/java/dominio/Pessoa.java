package dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity significa que esta é uma classe de domínio e se transformará em uma tabela no bd
@Entity 
public class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id //quer dizer que o atributo id será chave primária na tabela criada
	@GeneratedValue (strategy=GenerationType.IDENTITY)//pra dizer pro BD que é pra criar as colunas com os nomes desses atributos aqui
	private Integer id;
	private String nome;
	
	//@Column(name="e_mail") -> assim indica pro bd que não é pra criar o campo com nome 'email', e sim 'e_mail'.
	private String email;
	
	public Pessoa() {
	}

	public Pessoa(Integer id, String nome, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email + "]";
	}
	
	
}
