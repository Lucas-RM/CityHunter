package estadoDoJogo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidade.Inimigo;
import entidade.Jogador;
import extras.FuncoesExtras;

public abstract class Batalha {

	private static Scanner entrada = new Scanner(System.in);

	public enum EPrimeiroAtaque {
		ATAQUE_DO_JOGADOR, ATAQUE_DO_INIMIGO
	}

	public static Jogador jogadorDaBatalha;
	public static ArrayList<Inimigo> inimigosDaBatalha;
	private static int quantidadeDeInimigosDaBatalha;
	
	private static int inimigosMortos;

	public static void Batalhar(Jogador jogador, ArrayList<Inimigo> inimigos, int quantidadeDeInimigos,
			EPrimeiroAtaque primeiroAtaque) {
		jogadorDaBatalha = jogador;
		inimigosDaBatalha = inimigos;
		quantidadeDeInimigosDaBatalha = quantidadeDeInimigos;

		int ataqueEscolhido = 0;
		int indice_defesaOuCuraEscolhida = 0;
		String defesaOuCuraEscolhida = "";
		boolean primeiroAtaqueIniciado = false;
		boolean jogador_atacar = false;

		/* 0 - Nenhuma ação (Foi atingindo pelo o jogador) 
		 * 1 - Desviou do Ataque
		 * 2 - Desviou do Ataque e atingiu o jogador 
		 * 3 - Desviou e contra-atacou o jogador 
		 * 4 - Contra-ataque (Foi atingido pelo o jogador) 
		 * 5 - Atacar (Não foi atacado) */
		int acaoDoInimigo = 0;
		
		int statusDoJogador = 0;
		int statusDoInimigo = 0;
		
		for (int i = 0; i < quantidadeDeInimigosDaBatalha; i++) {
			try {
				
				jogadorDaBatalha.getPersonagem().removerAtaquesIguaisAZero();
				if (jogadorDaBatalha.getPersonagem().getQuantidadeAtaques().isEmpty()) {

					TimeUnit.MILLISECONDS.sleep(700);
					System.out.printf("\u001B[31m" + "%n>>> %s, você utilizou todos os seus ataques e "
							+ "acabou morrendo pelos inimigos...%n" + "\u001B[0m", jogador.getNomeDoJogador());
					
					jogador.getPersonagem().hit(jogador.getPersonagem().getVida());
					
					break;
				}
				
				TimeUnit.MILLISECONDS.sleep(1200);
				cabecalho(String.format("Batalha: %s X %s (Inimigo %d/%d)",
						jogadorDaBatalha.getPersonagem().getNomeDoPersonagem().toUpperCase(),
						inimigos.get(i).getNomeDoInimigo().toUpperCase(), i + 1, 
						quantidadeDeInimigos));
	
				informacoesDeBatalha(inimigosDaBatalha.get(i));
	
				if (!primeiroAtaqueIniciado) {
					
					if (primeiroAtaque.equals(EPrimeiroAtaque.ATAQUE_DO_INIMIGO)) {
						acaoDoInimigo = 5;
						
						TimeUnit.MILLISECONDS.sleep(700);
						System.out.println();
						FuncoesExtras.desenhaSimbolo("\u001B[30m", "=+", 30);
						System.out.println("\u001B[31m" + "\n>> Cuidado o inimigo está te atacando!" + "\u001B[0m");
						
					} else jogador_atacar = true;
	
					primeiroAtaqueIniciado = true;
				}
	
				if (jogador_atacar) {
					TimeUnit.MILLISECONDS.sleep(1000);
					ataqueEscolhido = jogadorDaBatalha.getPersonagem().escolherAtaque(entrada);				
					acaoDoInimigo = FuncoesExtras.sortear(new Integer[] { 0, 1, 2, 3, 4 }, new Integer[] { 50, 20, 5, 10, 15 });
				}
				
				TimeUnit.MILLISECONDS.sleep(700);
				checarAcao(acaoDoInimigo, inimigosDaBatalha.get(i));
	
				if (acaoDoInimigo == 2)
					jogadorDaBatalha.getPersonagem().hit(inimigos.get(i).getDano());
				
				TimeUnit.MILLISECONDS.sleep(700);
				statusDoJogador = checarjogador();
				
				if (acaoDoInimigo == 0 || acaoDoInimigo == 4 && statusDoJogador == 0) 			
					inimigos.get(i).hit(jogadorDaBatalha.getPersonagem().getDanoAtaques().get(ataqueEscolhido - 1));			
				
				if (acaoDoInimigo != 5)
					jogadorDaBatalha.getPersonagem().atacou(ataqueEscolhido - 1);				
				
				if (statusDoJogador == 1) break;
				
				TimeUnit.MILLISECONDS.sleep(800);
				statusDoInimigo = checarInimigo(inimigosDaBatalha.get(i));
				if (statusDoInimigo == 1) continue;
				else if (statusDoInimigo == 2) break;
	
				if (acaoDoInimigo == 4 && !inimigosDaBatalha.get(i).estaMorto()) {
					TimeUnit.MILLISECONDS.sleep(650);
					System.out.printf("\u001B[33m" + "%n>> Mas... \n>> Cuidado! O %s está te dando um contra-ataque <<"
							 + "\u001B[0m", inimigos.get(i).getNomeDoInimigo());
				}
	
				if (acaoDoInimigo == 3 || acaoDoInimigo == 4 || acaoDoInimigo == 5 && !inimigosDaBatalha.get(i).estaMorto()) {
					TimeUnit.MILLISECONDS.sleep(700);
					
					// Defender ou Curar e contra-atacar ou somente contra-atacar
					defesaOuCuraEscolhida = jogadorDaBatalha.getPersonagem().escolherDefesa(entrada);
					
					if (defesaOuCuraEscolhida != null && !defesaOuCuraEscolhida.equals("2")) {
						// Pega o DefesaOuCura escolhida
						indice_defesaOuCuraEscolhida = 
								jogador.getPersonagem().getNomeDefesasECuras().indexOf(defesaOuCuraEscolhida);
						
						// Pega o valorDaDefesaOuCura escolhida
						int valorDaDefesaOuCura = jogadorDaBatalha.getPersonagem()
								.getValorDefesasECuras().get(indice_defesaOuCuraEscolhida);
											
						jogadorDaBatalha.getPersonagem().DefenderOuCurar(
								defesaOuCuraEscolhida, valorDaDefesaOuCura, inimigos.get(i).getDano());
						
						jogadorDaBatalha.getPersonagem().defendeu(indice_defesaOuCuraEscolhida);
					}
					
					if (jogadorDaBatalha.getPersonagem().getQuantidadeDefesasECuras().isEmpty() 
							|| defesaOuCuraEscolhida.equals("2"))
						jogadorDaBatalha.getPersonagem().hit(inimigos.get(i).getDano());
					
					TimeUnit.MILLISECONDS.sleep(700);
					statusDoJogador = checarjogador(); if (statusDoJogador == 1) break;
					jogador_atacar = true;
				}
	
				if (!inimigos.get(i).estaMorto()) i--;
				
			} catch (Exception e) {}
		}
	}

	private static void cabecalho(String titulo) {
		// Cabecalho
		System.out.println();
		FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (titulo.length() / 2) + 2);
		System.out.printf("%n| %s |%n", titulo);
		FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (titulo.length() / 2) + 2);
	}

	private static void informacoesDeBatalha(Inimigo inimigo) {
		try {		
			
			// Informações do Jogador
			System.out.printf("%nInformações do Jogador - %s (%s):%n", jogadorDaBatalha.getNomeDoJogador(),
					jogadorDaBatalha.getPersonagem().getNomeDoPersonagem());
			System.out.printf("> Vida: %d%n", jogadorDaBatalha.getPersonagem().getVida());
			
			TimeUnit.MILLISECONDS.sleep(700);
			
			// Informações Do Inimigo
			FuncoesExtras.desenhaSimbolo("\u001B[34m", "=-", 25);
			System.out.printf("%nInformações do Inimigo - %s:%n", inimigo.getNomeDoInimigo());
			System.out.printf("> Vida: %d%n", inimigo.getVida());
			
		} catch (Exception e) {}
	}
	
	private static void checarAcao(int acao, Inimigo inimigo) {
		if (acao != 5) {
			System.out.println();
			FuncoesExtras.desenhaSimbolo("\u001B[36m", "=+", 30);
			
			switch (acao) {
			case 0:
				System.out.printf("\u001B[32m" + "%n>> Boa! Você acertou o %s <<%n" + "\u001B[0m", inimigo.getNomeDoInimigo());
				break;
			case 1:
				System.out.printf("%n>> O %s desviou do ataque. Ataque-o Novamente! <<%n" + "\u001B[0m", 
						inimigo.getNomeDoInimigo());
				break;
			case 2:
				System.out.printf("\u001B[31m" + "%n>> Nãão!! O %s desviou do seu ataque e conseguiu te atingir rapidamente. <<"
						+ "\n>> Fique atento da próxima vez! <<%n" + "\u001B[0m", inimigo.getNomeDoInimigo());
				break;
			case 3:
				System.out.printf("\u001B[33m" + "%n>> Cuidado! O %s desviou do seu ataque e está te dando um contra-ataque <<%n" 
						+ "\u001B[0m", inimigo.getNomeDoInimigo());
				break;
			case 4:
				System.out.printf("\u001B[33m" + "%n>> Boa! Você acertou o %s <<%n" + "\u001B[0m", inimigo.getNomeDoInimigo());
				break;
			}
		}
	}
	
	private static int checarjogador() {
		if (jogadorDaBatalha.getPersonagem().estaMorto()) {
			System.out.println("\u001B[31m" + "\n>>> Nãão!! Você Morreu!!! <<<" + "\u001B[0m");
			return 1;
		}
		
		return 0;
	}

	private static int checarInimigo(Inimigo inimigo) {
		/* 0 - Nenhum Morto 
		 * 1 - Morto 
		 * 2 - Todos Mortos */
		
		if (inimigo.estaMorto()) {
			
			inimigosMortos++;
			if (inimigosMortos == quantidadeDeInimigosDaBatalha) {
				System.out.println("\u001B[32m" + "\n>>> Boa! Você matou todos os inimigos dessa batalha!" + "\u001B[0m");
				inimigosMortos = 0;
				removerInimigosDaBatalha();
				return 2;
			}

			System.out.println("\u001B[32m" + "\n>>> Boa você matou o inimigo!" + "\u001B[0m");

			return 1;
		}
		
		return 0;
	}
	
	private static void removerInimigosDaBatalha() {
		if (inimigosDaBatalha.size() == quantidadeDeInimigosDaBatalha) inimigosDaBatalha.clear();
		else {
			for (int i = quantidadeDeInimigosDaBatalha - 1; i >= 0; i--) {
				inimigosDaBatalha.remove(i);
			}
		}
	}

}
