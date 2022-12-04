package db;

//classe que faz uma exce��o personalizada para usar no programa
public class DbException extends RuntimeException{
	
	private static final long serialVersionUID = 1L; //n�mero da vers�o -> sugest�o do java
		
	public DbException(String msg) {
		super(msg); //manda a mensagem de exce��o recebida pra RuntimeException
	}
}