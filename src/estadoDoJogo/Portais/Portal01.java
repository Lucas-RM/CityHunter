package estadoDoJogo.Portais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidade.Inimigo;
import entidade.Jogador;
import entidade.Inimigos.CachorroPoluido;
import entidade.Inimigos.GatoRadioativo;
import entidade.Inimigos.RatoPoluido;
import estadoDoJogo.Batalha;
import estadoDoJogo.Batalha.EPrimeiroAtaque;
import estadoDoJogo.EstadoDoJogo;
import estadoDoJogo.GerenciadorDoEstadoDoJogo;
import estadoDoJogo.SelecaoPersonagem;
import extras.FuncoesExtras;

public class Portal01 extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);
	private int opcaoDeEscolhaAtual = 0;
	private int historiaAtual = 0;

	private String titulo;
	
	private int vidas;
	private int maxVidas;
	private int inimigosRestantes;

	private List<String> historias = new ArrayList<String>();
	private List<String> dadosFormatados_frasesDasHistorias = new ArrayList<String>();
	private List<String> opcoesDeEscolha = new ArrayList<String>();
	private int opcoesDeEscolha_quantidade;
	
	private Jogador jogador;
	private ArrayList<Inimigo> inimigos;

	public Portal01(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;

		titulo = new String("portal 01");
		titulo = titulo.toUpperCase();
		
		criarInimigos();
		
		vidas = maxVidas = 3;
		inimigosRestantes = inimigos.size();
		
		opcoesDeEscolha_quantidade = 0;

		historias.add("\n> %s: \"L� vamos n�s\""); //0
		historias.add("\n> Logo de cara, voc� percebe que o mundo no qual voc� foi levado se trata de uma\n"
				+ "  metr�pole do futuro, despeda�ada, n�o se tem um pr�dio intacto e ao olhar para o c�u,\n"
				+ "  n�o � poss�vel ver nada, al�m de uma grande nebulosa cinza."); //1
		historias.add("\n> No horizonte, voc� v� a causa do problema do primeiro portal, um rio,\n"
				+ "  que cruza a cidade localizado numa hidroel�trica podre, que est� contaminando a �gua,\n"
				+ "  deixando-a radioativa, consequentemente matando tudo que est� no solo e sob ele perto do rio..."); //2
		historias.add("\n> Voc� corre em dire��o a hidroel�trica, mas se depara com Cachorros Polu�dos...\n"
				+ "  voc� pestaneja, mas precisa lutar...\n\n"); //3
		historias.add("\n> Voc� continua caminhado, na beira do rio, seguindo sua destina��o,\n"
				+ "  voc� se depara com uma cidade cuja sua cultura rodeava o uso do rio no dia a dia...\n"
				+ "  Com�rcios, farm�cias, o que pareciam ser pr�dios, todos apodrecidos devido a doen�a causada\n"
				+ "  pelo rio apodrecido..."); //4
		historias.add("\n> %s: \"Eu n�o posso deixar a cidade desse jeito\"."); //5
		historias.add("\n> Voc� volta a andar. Ap�s uma hora caminhado chega num pr�dio,\n"
				+ "  pouco abaixo da parte central da hidroel�trica."); //6
		historias.add("\n> Voc� entra para explorar e se depara com um bando de Ratos Polu�dos...\n"
				+ "  Eles eram enormes, do tamanho de um Golden Retriever, provavelmente, a radia��o e a\n"
				+ "  polui��o causados por beber a �gua do rio causaram isso..."); //7
		historias.add("\n> %s: \"Preciso tomar cuidado nesta batalha...\"\n\n"); //8
		historias.add("\n> Voc� segue andando para a �rea central d� hidroel�trica, finalmente, voc� v� um tanque que\n"
				+ "  desagua um l�quido verde fosforescente que caiu na �gua que estava sendo tratada\n"
				+ "  e usada para gera��o de energia do rio."); //9
		historias.add("\n> Voc� precisa reter o tanque de continuar despejando o l�quido radioativo\n"
				+ "  e acionar os bot�es da hidroel�trica para que seja realizado o tratamento da �gua,\n"
				+ "  assim podendo resolver a causa deste portal..."); //10
		historias.add("\n> Neste meio tempo, um animal feroz entra na sala, um Gato Radioativo, ele come�a a atacar,\n"
				+ "  voc� precisa elimina-lo para que consiga dar andamento da despolui��o do portal...\n\n"); //11
		historias.add("\n> Agora que derrotado o gato polu�do, voc� consegue realizar a \n"
				+ "  extra��o do material radioativo da �gua e colocar a hidroel�trica novamente para funcionar."); //12
		historias.add("\n> %s: \"Nossa! O que � isso!? Tudo est� ficando branco...\""); //13
		historias.add("> %s: Ahh N�o!!! Estou voltando para o inicio da dungeon.\""); //14
		historias.add("> %s: Espera um pouco. Mas... e os inimigos, ser� que matei todos???\"\n\n"); //15

		opcoesDeEscolha.addAll(Arrays.asList(new String[] {
				"", "", "", //2
				"Aperte ENTER para Batalhar", //3
				"", "", "", "", //7
				"Aperte ENTER para Batalhar", //8
				"", "", "Aperte ENTER para Batalhar", //11
				"", "", "", "continua..." //15
		}));
	}

	private void sethistoriaAtual(int historiaAtual) {
		this.historiaAtual = historiaAtual;
	}

	private void criarInimigos() {
		inimigos = new ArrayList<Inimigo>();
		
		inimigos.add(new CachorroPoluido());
		inimigos.add(new CachorroPoluido());
		inimigos.add(new CachorroPoluido());
		inimigos.add(new CachorroPoluido());
		inimigos.add(new RatoPoluido());
		inimigos.add(new RatoPoluido());
		inimigos.add(new RatoPoluido());
		inimigos.add(new GatoRadioativo());
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
			else gej.setEstado(GerenciadorDoEstadoDoJogo.PORTAL01);
			
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
			"", "", "", "", //4
			jogador.getPersonagem().getNomeDoPersonagem(), //5
			"", "", //7
			jogador.getPersonagem().getNomeDoPersonagem(), //8
			"", "", "", "", //12
			jogador.getPersonagem().getNomeDoPersonagem(), //13
			jogador.getPersonagem().getNomeDoPersonagem(), //14
			jogador.getPersonagem().getNomeDoPersonagem(), //15
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
					Batalha.Batalhar(jogador, inimigos, 4, EPrimeiroAtaque.ATAQUE_DO_JOGADOR);
					break;
				case 8:					
					Batalha.Batalhar(jogador, inimigos, 3, EPrimeiroAtaque.ATAQUE_DO_JOGADOR);					
					break;
				case 11:
					Batalha.Batalhar(jogador, inimigos, 1, EPrimeiroAtaque.ATAQUE_DO_INIMIGO);
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
