package it.example.dto;

public class UtenteDTO {

	private String username;
	private String nome;
	private String cognome;
	public UtenteDTO() {
	}
	public UtenteDTO(String username, String nome, String cognome) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
	}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public String getCognome() {return cognome;}
	public void setCognome(String cognome) {this.cognome = cognome;}
	
	
	
	
}
