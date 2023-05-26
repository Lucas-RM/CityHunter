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

		historias.add("\n> %s: \"L� vamos n�s\""); //0
		historias.add("\n> Ao entrar no portal localizado ao lado direito voc� se depara com uma zona morta,\n"
				+ "  ao que parece, uma antiga floresta, ao seu redor se encontra milhares de arvores mortas,\n"
				+ "  alguns podres, outras cortadas, neste mundo o c�u n�o � totalmente polu�do,\n"
				+ "  mas ainda assim voc� sente uma enorme dificuldade para respirar."); //1
		historias.add("\n> Andando um pouco voc� se depara com destro�os de rob�s, que no lugar das m�os,\n"
				+ "  possu�am machados, provavelmente, utilizados para cortar as �rvores desta terra que hoje "
				+ "est� morta..."); //2
		historias.add("\n> No instante que voc� se vira para continuar seu caminho, os rob�s que aparentemente\n"
				+ "  estavam desligados quase enterrados nesta terra arenosa se levantam\n"
				+ "  e come�am a te atacar...\n\n"); //3
		historias.add("\n> Olhando ao redor, voc� v� que os rob�s deixam um tra�o, provavelmente a origem "
				+ "do problema deste mundo..."); //4
		historias.add("\n> Voc� segue estes rastros e no horizonte v� uma f�brica,\n"
				+ "  ao lado, � poss�vel notar centenas de milhares de troncos de madeira deitados,\n"
				+ "  provavelmente prontos para entrega, voc� aperta seu passo..."); //5
		historias.add("\n> Na medida em que voc� se aproxima, ouve certos barulhos estranhos...\n"
				+ "  Um motosserra a dist�ncia. Voc� para pra ouvir e aos poucos,\n"
				+ "  alguns rob�s com grandes motosserras na m�o te cercam,\n"
				+ "  desta vez voc� precisa ter mais agilidade.\n\n"); //6
		historias.add("\n> Se livrando desta �ltima distra��o antes de chegar na f�brica voc� nota que\n"
				+ "  a mesma � um pouco estranha, a rastros no ch�o, como se todo este complexo\n"
				+ "  se... se movesse?"); //7
		historias.add("\n> Deixando as d�vidas de lado, voc� entra no pr�dio na busca por respostas de como\n"
				+ "  desligar os rob�s e como parar est� f�brica de trazer uma invas�o para a cidade de S�o Paulo,\n"
				+ "  mas no momento em que voc� iria colocar o pen drive com um v�rus no computador principal,\n"
				+ "  Tent�culos Rob�ticos, Serras e Machados saem da parede..."); //8
		historias.add("\n> A f�brica � um rob�! Voc� ir� ter trabalho nesta batalha.\n\n"); //9
		historias.add("\n> Voc� ainda machucado, coloca o v�rus na interface principal da f�brica,\n"
				+ "  ouve os rob�s que faziam a ronda ca�rem no ch�o.\n"
				+ "  Uma luz branca te envolve e voc� volta para o come�o.\n\n"); //10

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
			String msg = "Par�bens!!! Voc� derrotou todos os inimigos desse Portal";
			
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
						System.err.println("> Escolha Inv�lida!\n");
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
			System.err.println("> Caracter Inv�lido!\n");
			gej.setReiniciar(true);
		} catch (Exception e) {}
			
	}

}
