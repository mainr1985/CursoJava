package db;

//classe que faz uma exceção personalizada para usar no programa
public class DbException extends RuntimeException{
	
	private static final long serialVersionUID = 1L; //número da versão -> sugestão do java
		
	public DbException(String msg) {
		super(msg); //manda a mensagem de exceção recebida pra RuntimeException
	}
}