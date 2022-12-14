package aplicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Pessoa;

public class Programa {

	public static void main(String[] args) {
			/*
			Objetos já instanciados e salvos na base de dados
			
			Pessoa p1 = new Pessoa(null,"Carlos da Silva", "carlos@gmail.com");
			Pessoa p2 = new Pessoa(null,"Joaquim Torres", "joaquim@gmail.com");
			Pessoa p3 = new Pessoa(null,"Ana Maria", "ana@gmail.com"); //quem vai atribuir os ids é o BD
			
			*/ 
			
			//instanciando o Entity Manager Factory
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("exemplo-jpa"); //o nome da persistence unit dado no arquivo 'persistence.xml' criado.
			
			//instanciando o Entity Manager
			EntityManager em = factory.createEntityManager();			
			//após essas instanciações, o contexto de persistência já está implementado e já existe conexão com o BD
						
			//encontrando um elemento no bd por id
			Pessoa p = em.find(Pessoa.class, 2); //busca o id = 2 no bd criado através da classe Pessoa.
			//System.out.println(p);
			
			//necessário uma transação quando o JPA faz alguma coisa que não é só um select.
			em.getTransaction().begin();
			
			//inserindo os dados no bd
			/*em.persist(p1);
			em.persist(p2);
			em.persist(p3);*/
			
			//deletando um dado do bd: é preciso fazer o select primeiro e abrir a transação, senão não funciona.
			// -> entidade monitorada: uma entidade que acabou de ser inserida ou foi recuperada por um select primeiro. O 'remove' exige uma entidade monitorada pra funcionar. 
			em.remove(p);
			
			//pra confirmar as alterações feitas.
			em.getTransaction().commit();
			System.out.println("Ok.");
			em.close();
			factory.close();
	}
}