package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable{
	/*
	implements serializable -> necessário em java pra que os objetos possam ser transformados em sequência de bytes.
	Se quiser que o objeto seja gravado em arquivo, trafegado em rede, etc.*/
	
	private static final long serialVersionUID = 1L; //campo long default exigido pela interface Serializable
	private Integer id;
	private String name;
	
	public Department() {		
	}
	
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

	//hashcode e equals para que os elementos possam ser comparados pelo seu conteúdo e não pela referência de ponteiro.	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}
}