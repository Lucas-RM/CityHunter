package entidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import estadoDoJogo.Batalha;
import extras.FuncoesExtras;

public abstract class Personagem {
	
	/* Variáveis do Personagem */
	protected String nomeDoPersonagem;
	protected int vida;
	protected int maxVida;
	protected boolean morto;
	
	/* Variáveis dos Ataques*/
	protected List<String> nome_ataques = new ArrayList<String>();
	protected List<Integer> dano_ataques = new ArrayList<Integer>();
	protected List<Integer> quantidade_ataques = new ArrayList<Integer>();
	
	/* Variáveis das Defesas */
	protected List<String> nome_defesas_curas = new ArrayList<String>();
	protected List<Integer> valor_defesas_curas = new ArrayList<Integer>();
	protected List<Integer> quantidade_defesas_curas = new ArrayList<Integer>();

	public String getNomeDoPersonagem() { return nomeDoPersonagem; }
	public int getVida() { return vida; }
	public void setVida(int vida) { this.vida = vida; } 
	public int getMaxVida() { return maxVida; }
	public boolean estaMorto() { return morto; }
	
	public List<String> getNomeAtaques() { return nome_ataques; }
	public List<Integer> getDanoAtaques() { return dano_ataques; }
	public List<Integer> getQuantidadeAtaques() { return quantidade_ataques; }
	
	public List<String> getNomeDefesasECuras() { return nome_defesas_curas; }
	public List<Integer> getValorDefesasECuras() { return valor_defesas_curas; }
	public List<Integer> getQuantidadeDefesasECuras() { return quantidade_defesas_curas; }
	
	public void hit(int dano) {
		vida -= dano;
		
		if(vida <= 0) {
			vida = 0;
			morto = true;
		}
	}
	
	public abstract void DefenderOuCurar(String acaoEscolhida, int valorDaDefesaOuCura, int danoDoInimigo);
	
	public void defendeu(int defesa) {
		quantidade_defesas_curas.set(defesa, getQuantidadeDefesasECuras().get(defesa) - 1);
		
		if (quantidade_defesas_curas.get(defesa) == 0) {
			nome_defesas_curas.remove(defesa);
			valor_defesas_curas.remove(defesa);
			quantidade_defesas_curas.remove(defesa);
		}
	}
	
	public void atacou(int ataque) {
		quantidade_ataques.set(ataque, getQuantidadeAtaques().get(ataque) - 1);
		
		if (quantidade_ataques.get(ataque) == 0) {
			nome_ataques.remove(ataque);
			dano_ataques.remove(ataque);
			quantidade_ataques.remove(ataque);
		}
	}
	
	public int escolherAtaque(Scanner entrada) {
		int ataqueEscolhido = 0;
		
		do {
			System.out.printf("\nEscolha um de seus ataques de %s:\n", getNomeDoPersonagem());
			
			for (int i = 0; i < getNomeAtaques().size(); i++) {
				System.out.printf("[%d] %s(%s) - %d dano%n", 
						i + 1, getNomeAtaques().get(i), getQuantidadeAtaques().get(i), getDanoAtaques().get(i));
			}
			System.out.print("Ataque: ");
			
			try {
				String resposta = entrada.nextLine();
				String respostaSemEspaco = resposta.trim();
				ataqueEscolhido = Integer.parseInt(respostaSemEspaco);
				
				if (ataqueEscolhido > 0 && ataqueEscolhido <= getNomeAtaques().size()) return ataqueEscolhido;
				else System.err.println("> Escolha Inválida!\n"); continue;
				
			} catch (NumberFormatException e) {
				System.err.println("> Caracter Inválido!\n");
				continue;
			}
			
		} while(ataqueEscolhido <= 0 || ataqueEscolhido > getNomeAtaques().size());
		
		return -1;
	}
	
	public String escolherDefesa(Scanner entrada) {
		
		String resposta;
		String respostaSemEspaco;
		
		int defesaEscolhida = 0;
		int acaoEscolhida = 0;
		
		removerDefesasECurasIguaisAZero();
		if (getQuantidadeDefesasECuras().isEmpty()) {
			System.out.printf("\u001B[31m" + "%n>>> %s, você utilizou todas as suas defesas e curas\n"
					+ "    Infelimente, você recebeu o ataque%n"
					+ "\u001B[0m", Batalha.jogadorDaBatalha.getNomeDoJogador());
			return null;
		}
		
		do {
			System.out.print("\nEscolha:\n[1] (Defender ou Curar) e Contra-Atacar (Recebe o dano parcialmente)\n"
					+ "[2] Somente Contra-Atacar (Recebe o dano total do inimigo)\n> ");
			
			try {
				resposta = entrada.nextLine();
				respostaSemEspaco = resposta.trim();
				acaoEscolhida = Integer.parseInt(respostaSemEspaco);
				
				if (acaoEscolhida == 2) return "2";
				else if (acaoEscolhida == 1) continue;
				else System.err.println("> Escolha Inválida!\n"); 
				
			} catch (NumberFormatException e) {
				System.err.println("> Caracter Inválido!\n");
			}
			
		} while (acaoEscolhida <= 0 || acaoEscolhida > 2);
		
		
		do {
			
			System.out.printf("\nEscolha uma de suas defesas/curas de %s:\n", getNomeDoPersonagem());
			
			for (int i = 0; i < getNomeDefesasECuras().size(); i++) {
				System.out.printf("[%d] %s(%s) - %d%s%n", 
						i + 1, getNomeDefesasECuras().get(i), 
						getQuantidadeDefesasECuras().get(i), getValorDefesasECuras().get(i), "%");
			}
			System.out.print("Ataque: ");
			
			try {
				resposta = entrada.nextLine();
				respostaSemEspaco = resposta.trim();
				defesaEscolhida = Integer.parseInt(respostaSemEspaco);
				
				if (defesaEscolhida > 0 && defesaEscolhida <= getNomeDefesasECuras().size()) {
					return getNomeDefesasECuras().get(defesaEscolhida - 1);
				} else System.err.println("> Escolha Inválida!\n");
				
			} catch (NumberFormatException e) {
				System.err.println("> Caracter Inválido!\n");
			}
			
		} while(defesaEscolhida <= 0 || defesaEscolhida > getNomeDefesasECuras().size());
		
		return "2";
	}
	
	public void removerAtaquesIguaisAZero() {
		for (int i = 0; i < quantidade_ataques.size(); i++) {
			if (quantidade_ataques.get(i) <= 0) {
				nome_ataques.remove(i);
				dano_ataques.remove(i);
				quantidade_ataques.remove(i);
			}
		}
	}
	
	public void removerDefesasECurasIguaisAZero() {
		for (int i = 0; i < quantidade_defesas_curas.size(); i++) {
			if (quantidade_defesas_curas.get(i) <= 0) {
				nome_defesas_curas.remove(i);
				valor_defesas_curas.remove(i);
				quantidade_defesas_curas.remove(i);
			}
		}
	}
	
	public void escreverDescricaoPersonagem() {
		String descricao_cabecalho = "Descrição do Personagem - ";
		int descricao_cabecalho_tamanho = descricao_cabecalho.length();
		
		//Cabeçalho
		System.out.println();
		FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", ((descricao_cabecalho_tamanho + getNomeDoPersonagem().length()) / 2) + 4);
		System.out.printf("%n%29s%s%n", descricao_cabecalho, getNomeDoPersonagem());
		FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", ((descricao_cabecalho_tamanho + getNomeDoPersonagem().length()) / 2) + 4);		
		System.out.println();
		
		//Descrição do Personagem
		System.out.printf("Personagem: %s%n", getNomeDoPersonagem());
		System.out.printf("Quantidade de Vida: %s%n", getVida());	
		System.out.print("Ataques do Personagem: ");
		
		for (int i = 0; i < getNomeAtaques().size(); i++) {			
			System.out.printf("%n%3s %s (%d de dano)", "->", getNomeAtaques().get(i), getDanoAtaques().get(i));
		}
		
		System.out.print("\nDefesas do Personagem: ");
		for (int i = 0; i < getNomeDefesasECuras().size(); i++) {			
			System.out.printf("%n%3s %s (%d%s)", "->", getNomeDefesasECuras().get(i), getValorDefesasECuras().get(i), "%");
		}
		
		System.out.println();
		FuncoesExtras.desenhaSimbolo("=-", ((descricao_cabecalho_tamanho + getNomeDoPersonagem().length()) / 2) + 4);		
		System.out.println();
	}
}
