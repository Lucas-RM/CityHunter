package entidade;

import estadoDoJogo.SelecaoPersonagem;

public class Jogador {
	
	/* Variáveis do Jogador */
	private String nomeDoJogador;
	private Personagem personagem;
	
	public Jogador(String nomeDoJogador, Personagem personagem) { 
		this.nomeDoJogador = nomeDoJogador;
		this.personagem = personagem;
	}
	
	public String getNomeDoJogador() { return nomeDoJogador; }
	public Personagem getPersonagem() { return personagem;}
	
	public Jogador resetarJogador() {
		SelecaoPersonagem.personagens.clear();
		SelecaoPersonagem.criarPersonagens();
		return new Jogador(nomeDoJogador, SelecaoPersonagem.personagens.get(SelecaoPersonagem.opcaoDeEscolhaAtual - 1));
	}
}
