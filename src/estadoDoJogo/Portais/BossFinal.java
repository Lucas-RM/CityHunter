package estadoDoJogo.Portais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidade.Inimigo;
import entidade.Jogador;
import entidade.Inimigos.MostroDaPoluicao;
import estadoDoJogo.Batalha;
import estadoDoJogo.Batalha.EPrimeiroAtaque;
import estadoDoJogo.EstadoDoJogo;
import estadoDoJogo.GerenciadorDoEstadoDoJogo;
import estadoDoJogo.SelecaoPersonagem;
import extras.FuncoesExtras;

public class BossFinal extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);
	private int historiaAtual = 0;

	private String titulo;
	
	private int vidas;

	private List<String> historias = new ArrayList<String>();
	private List<String> dadosFormatados_frasesDasHistorias = new ArrayList<String>();
	private List<String> opcoesDeEscolha = new ArrayList<String>();
	
	private Jogador jogador;
	private ArrayList<Inimigo> inimigo;

	public BossFinal(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;

		titulo = new String("Boss Final");
		titulo = titulo.toUpperCase();
		
		criarBossFinal();
		
		vidas = 4;		

		historias.add("\n> Após finalizado os dois portais você se vira, se preparando para ir fazer o relatório\n"
				+ "  para a secretaria do meio ambiente interdimensional, mas no momento em que você está a 10 passos\n"
				+ "  de distância da antiga entrada da dungeon um enorme Monstro da Poluição,\n"
				+ "  provavelmente de algum gás poluente aparece atrás de você..."); //0
		historias.add("\n> %s: \"Eles não vão me deixar ter essa fácil, não é mesmo?\""); //1
		historias.add("\n> O grande ser poluído começa a espalhar seus gases pela cidade,\n"
				+ "  você deve derrotá-lo o mais rápido possível para evitar casualidades.\n\n"); //2
		historias.add("\n> Você tosse um pouco, essa missão não foi fácil."); //3
		historias.add("\n> Você se vira e entra no carro elétrico que estava à sua espera e volta para o Qg,\n"
				+ "  ficando no aguardo da próxima missão...\n\n"); //4

		opcoesDeEscolha.addAll(Arrays.asList(new String[] {
				"", "", //1 
				"> Aperte ENTER para Batalhar", //2
				"", "FIM..." //4
		}));
	}

	private void sethistoriaAtual(int historiaAtual) {
		this.historiaAtual = historiaAtual;
	}

	private void criarBossFinal() {
		inimigo = new ArrayList<Inimigo>();
		inimigo.add(new MostroDaPoluicao());
	}
	
	private void atualizar() {		
		if (jogador.getPersonagem().estaMorto()) {
			vidas--;
			
			historiaAtual = 0;
			SelecaoPersonagem.jogador = jogador.resetarJogador();
			criarBossFinal();
			
			if (vidas == 0) {
				vidas = 3;
				gej.setEstado(GerenciadorDoEstadoDoJogo.FIMDEJOGO);
			}
			else gej.setEstado(GerenciadorDoEstadoDoJogo.BOSSFINAL);
			
		}
		else {
			if (inimigo.size() == 0 || inimigo.isEmpty()) {
				if (historiaAtual == historias.size() - 1) {
					JogoCompletado();
					gej.setEstado(GerenciadorDoEstadoDoJogo.POSCREDITOS);
				}
				
			} else {				
				sethistoriaAtual(historiaAtual + 1);
				gej.setReiniciar(true);
			}
			
		}
	}
	
	private void JogoCompletado() {
		try {
			String msg = "Parábens!!! Você derrotou o Boss Final!!!";
			
			System.out.println();
			FuncoesExtras.desenhaSimbolo("\u001B[34m", "<->", (msg.length() / 3) + 3);
			System.out.printf("\u001B[32m" + "%n%45s%n" + "\u001B[0m", msg);
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
		System.out.printf("%n%14s%n", titulo);
		FuncoesExtras.desenhaSimbolo("=-", (titulo.length() / 2) + 4);
		
		System.out.printf("%nVidas Restantes - Fase Final (%s): %d%n", titulo, vidas);
		
		// Instanciando Jogador		
		jogador = SelecaoPersonagem.jogador;
		
		dadosFormatados_frasesDasHistorias.addAll(Arrays.asList(new String[] {
			"", jogador.getPersonagem().getNomeDoPersonagem(), //1
			"", "", "" //4			
		}));
	}

	@Override
	public void escrever() {
		if (historias.get(historiaAtual).contains("%s")) {
			historias.set(historiaAtual, historias.get(historiaAtual)
					.replaceAll("%s", dadosFormatados_frasesDasHistorias.get(historiaAtual)));
		}
		System.out.print(historias.get(historiaAtual));									

		if (opcoesDeEscolha.get(historiaAtual).length() > 0) 
			System.out.printf("%s", opcoesDeEscolha.get(historiaAtual));
	}

	@Override
	public void selecionar() {
		resposta.nextLine();
		
		if (opcoesDeEscolha.get(historiaAtual).length() == 0) sethistoriaAtual(historiaAtual + 1);
		else {				
			switch (historiaAtual) {
			case 2:
				Batalha.Batalhar(jogador, inimigo, 1, EPrimeiroAtaque.ATAQUE_DO_JOGADOR);
				break;
			}
			
			atualizar();
			if (opcoesDeEscolha.get(historiaAtual).length() != 0) sethistoriaAtual(historiaAtual + 1);
		}
	}

}
