package estadoDoJogo.Portais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidade.Inimigo;
import entidade.Jogador;
import entidade.Inimigos.FabricaRobotica;
import entidade.Inimigos.Robo;
import entidade.Inimigos.SuperRobo;
import estadoDoJogo.Batalha;
import estadoDoJogo.Batalha.EPrimeiroAtaque;
import estadoDoJogo.EstadoDoJogo;
import estadoDoJogo.GerenciadorDoEstadoDoJogo;
import estadoDoJogo.SelecaoPersonagem;
import extras.FuncoesExtras;

public class Portal02 extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);
	private int opcaoDeEscolhaAtual = 0;
	private int historiaAtual = 0;

	private String titulo;
	
	private int vidas;
	private final int maxVidas;
	private int inimigosRestantes;

	private List<String> historias = new ArrayList<String>();
	private List<String> dadosFormatados_frasesDasHistorias = new ArrayList<String>();
	private List<String> opcoesDeEscolha = new ArrayList<String>();
	private int opcoesDeEscolha_quantidade;
	
	private Jogador jogador;
	private ArrayList<Inimigo> inimigos;

	public Portal02(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;

		titulo = new String("portal 02");
		titulo = titulo.toUpperCase();
		
		criarInimigos();
		
		vidas = maxVidas = 3;
		inimigosRestantes = inimigos.size();
		
		opcoesDeEscolha_quantidade = 0;

		historias.add("\n> %s: \"Lá vamos nós\""); //0
		historias.add("\n> Ao entrar no portal localizado ao lado direito você se depara com uma zona morta,\n"
				+ "  ao que parece, uma antiga floresta, ao seu redor se encontra milhares de arvores mortas,\n"
				+ "  alguns podres, outras cortadas, neste mundo o céu não é totalmente poluído,\n"
				+ "  mas ainda assim você sente uma enorme dificuldade para respirar."); //1
		historias.add("\n> Andando um pouco você se depara com destroços de robôs, que no lugar das mãos,\n"
				+ "  possuíam machados, provavelmente, utilizados para cortar as árvores desta terra que hoje "
				+ "está morta..."); //2
		historias.add("\n> No instante que você se vira para continuar seu caminho, os robôs que aparentemente\n"
				+ "  estavam desligados quase enterrados nesta terra arenosa se levantam\n"
				+ "  e começam a te atacar...\n\n"); //3
		historias.add("\n> Olhando ao redor, você vê que os robôs deixam um traço, provavelmente a origem "
				+ "do problema deste mundo..."); //4
		historias.add("\n> Você segue estes rastros e no horizonte vê uma fábrica,\n"
				+ "  ao lado, é possível notar centenas de milhares de troncos de madeira deitados,\n"
				+ "  provavelmente prontos para entrega, você aperta seu passo..."); //5
		historias.add("\n> Na medida em que você se aproxima, ouve certos barulhos estranhos...\n"
				+ "  Um motosserra a distância. Você para pra ouvir e aos poucos,\n"
				+ "  alguns robôs com grandes motosserras na mão te cercam,\n"
				+ "  desta vez você precisa ter mais agilidade.\n\n"); //6
		historias.add("\n> Se livrando desta última distração antes de chegar na fábrica você nota que\n"
				+ "  a mesma é um pouco estranha, a rastros no chão, como se todo este complexo\n"
				+ "  se... se movesse?"); //7
		historias.add("\n> Deixando as dúvidas de lado, você entra no prédio na busca por respostas de como\n"
				+ "  desligar os robôs e como parar está fábrica de trazer uma invasão para a cidade de São Paulo,\n"
				+ "  mas no momento em que você iria colocar o pen drive com um vírus no computador principal,\n"
				+ "  Tentáculos Robóticos, Serras e Machados saem da parede..."); //8
		historias.add("\n> A fábrica é um robô! Você irá ter trabalho nesta batalha.\n\n"); //9
		historias.add("\n> Você ainda machucado, coloca o vírus na interface principal da fábrica,\n"
				+ "  ouve os robôs que faziam a ronda caírem no chão.\n"
				+ "  Uma luz branca te envolve e você volta para o começo.\n\n"); //10

		opcoesDeEscolha.addAll(Arrays.asList(new String[] {
				"", "", "", //2
				"Aperte ENTER para Batalhar", //3
				"", "", //5
				"Aperte ENTER para Batalhar", //6
				"", "", "Aperte ENTER para Batalhar", //9
				"continua..." //10
		}));
	}

	private void sethistoriaAtual(int historiaAtual) {
		this.historiaAtual = historiaAtual;
	}

	private void criarInimigos() {
		inimigos = new ArrayList<Inimigo>();
		
		inimigos.add(new Robo());
		inimigos.add(new Robo());
		inimigos.add(new Robo());
		inimigos.add(new SuperRobo());
		inimigos.add(new SuperRobo());
		inimigos.add(new FabricaRobotica());
	}
	
	private void atualizar() {
		inimigosRestantes = inimigos.size();
		
		if (jogador.getPersonagem().estaMorto()) {
			vidas--;
			
			opcaoDeEscolhaAtual = 0;
			historiaAtual = 0;
			SelecaoPersonagem.jogador = jogador.resetarJogador();
			criarInimigos();
			inimigosRestantes = inimigos.size();
			
			if (vidas == 0) {
				vidas = maxVidas;
				gej.setEstado(GerenciadorDoEstadoDoJogo.FIMDEJOGO);
			}
			else gej.setEstado(GerenciadorDoEstadoDoJogo.PORTAL02);
			
		}
		else {
			if (inimigos.size() == 0 || inimigos.isEmpty()) {
				if (historiaAtual == historias.size() - 1) {
					msgPortalCompletado();
					gej.setEstado(GerenciadorDoEstadoDoJogo.ESCOLHADOSPORTAIS);
				}
				
			} else {				
				sethistoriaAtual(historiaAtual + 1);
				gej.setReiniciar(true);
			}
			
		}
	}
	
	private void msgPortalCompletado() {
		try {
			String msg = "Parábens!!! Você derrotou todos os inimigos desse Portal";
			
			System.out.println();
			FuncoesExtras.desenhaSimbolo("\u001B[34m", "<->", (msg.length() / 3) + 3);
			System.out.printf("\u001B[32m" + "%n%60s%n" + "\u001B[0m", msg);
			FuncoesExtras.desenhaSimbolo("\u001B[34m", "<->", (msg.length() / 3) + 3);
			System.out.println();
		
			TimeUnit.MILLISECONDS.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void iniciar() {
		// Titulo
		System.out.println();
		FuncoesExtras.desenhaSimbolo("=-", (titulo.length() / 2) + 4);
		System.out.printf("%n%12s%n", titulo);
		FuncoesExtras.desenhaSimbolo("=-", (titulo.length() / 2) + 4);
		
		System.out.println("\nInimigos Restantes: " + inimigosRestantes);
		System.out.printf("Vidas Restantes do Jogo (%s): %d%n", titulo, vidas);
		
		// Instanciando Jogador		
		jogador = SelecaoPersonagem.jogador;
		
		dadosFormatados_frasesDasHistorias.addAll(Arrays.asList(new String[] {
			jogador.getPersonagem().getNomeDoPersonagem(), //0
			"", "", "", "", "", "", "", "", "", "" //15
		}));
	}

	@Override
	public void escrever() {
		if (historias.get(historiaAtual).contains("%s")) {
			historias.set(historiaAtual, historias.get(historiaAtual)
					.replaceAll("%s", dadosFormatados_frasesDasHistorias.get(historiaAtual)));
		}
		System.out.print(historias.get(historiaAtual));									
	
		if (opcoesDeEscolha.get(historiaAtual).contains(";") || opcoesDeEscolha.get(historiaAtual).length() > 0) {
			String[] opcoes = opcoesDeEscolha.get(historiaAtual).split(";");
			opcoesDeEscolha_quantidade = opcoes.length;
			for (int i = 0; i < opcoes.length; i++) {
				System.out.printf("[%d] %s%n", i + 1, opcoes[i]);
			}
			System.out.print(">> ");
		} else opcoesDeEscolha_quantidade = 0;
	}

	@Override
	public void selecionar() {			
		try {
			String opcoesMenu_resposta = resposta.nextLine();
			
			if (opcoesDeEscolha.get(historiaAtual).length() == 0) sethistoriaAtual(historiaAtual + 1);
			else {
				String opcoesMenu_respostaSemEspaco = opcoesMenu_resposta.trim();
				
				if (opcoesDeEscolha.get(historiaAtual).contains(";")) {
					TimeUnit.MILLISECONDS.sleep(700);
					opcaoDeEscolhaAtual = Integer.parseInt(opcoesMenu_respostaSemEspaco);				
				} 
				
				if (opcoesDeEscolha_quantidade > 0) {					
					if (opcaoDeEscolhaAtual <= 0 && opcaoDeEscolhaAtual > opcoesDeEscolha_quantidade) {
						System.err.println("> Escolha Inválida!\n");
						gej.setReiniciar(true);
					}					
				}

				switch (historiaAtual) {
				case 3:
					Batalha.Batalhar(jogador, inimigos, 3, EPrimeiroAtaque.ATAQUE_DO_INIMIGO);
					break;
				case 6:					
					Batalha.Batalhar(jogador, inimigos, 2, EPrimeiroAtaque.ATAQUE_DO_JOGADOR);					
					break;
				case 9:
					Batalha.Batalhar(jogador, inimigos, 1, EPrimeiroAtaque.ATAQUE_DO_JOGADOR);
					break;
				}
				
				atualizar();
				if (opcoesDeEscolha.get(historiaAtual).length() != 0) sethistoriaAtual(historiaAtual + 1);
			}
		} catch (NumberFormatException e) {
			System.err.println("> Caracter Inválido!\n");
			gej.setReiniciar(true);
		} catch (Exception e) {}
			
	}

}
