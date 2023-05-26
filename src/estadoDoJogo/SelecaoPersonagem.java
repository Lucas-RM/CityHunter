package estadoDoJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidade.Jogador;
import entidade.Personagem;
import entidade.Personagens.Guerreiro;
import extras.FuncoesExtras;

public class SelecaoPersonagem extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);

	private List<String> opcoesDoMenu = new ArrayList<String>();
	public static int opcaoDeEscolhaAtual = 0;
	private int acaoAtual = 0;

	private String cabecalho;

	public static Jogador jogador;
	public static ArrayList<Personagem> personagens = new ArrayList<Personagem>();

	private Boolean ERRO = false;
	private String MENSAGEMDEERRO = "";

	public SelecaoPersonagem(GerenciadorDoEstadoDoJogo gej) {

		this.gej = gej;

		cabecalho = new String("Selecionar Personagem");
		cabecalho = FuncoesExtras.capitalize(cabecalho);
		
		criarPersonagens();

		for (int i = 0; i < personagens.size(); i++) {
			opcoesDoMenu.add(personagens.get(i).getNomeDoPersonagem());
		}

	}

	private void setAcaoAtual(int acaoAtual) {
		this.acaoAtual = acaoAtual;
	}

	public static void criarPersonagens() {
		personagens.add(new Guerreiro());
		personagens.add(new Guerreiro());
		personagens.add(new Guerreiro());
	}

	@Override
	public void iniciar() {
		if (acaoAtual == 0) {
			// Cabeçalho
			System.out.println();
			FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (cabecalho.length() / 2) + 4);
			System.out.printf("%n%24s%n", cabecalho);
			FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (cabecalho.length() / 2) + 4);
		}
		
		jogador = Login.jogador;
	}

	@Override
	public void escrever() {
		if (acaoAtual == 0) {
			// Escrever menu de opções
			System.out.println();
			for (int i = 0; i < opcoesDoMenu.size(); i++) {
				System.out.printf("[%d] %s%n", i + 1, opcoesDoMenu.get(i));
			}
			System.out.print("Escolha: ");
		} else if (acaoAtual == 1) {
			personagens.get(opcaoDeEscolhaAtual - 1).escreverDescricaoPersonagem();
			System.out.printf("Confirma a selecao do personagem (%s) [1](Sim) [0](Não): ",
					personagens.get(opcaoDeEscolhaAtual - 1).getNomeDoPersonagem());
		}
	}

	@Override
	public void selecionar() {
		try {

			String opcoesMenu_resposta = resposta.nextLine();
			String opcoesMenu_respostaSemEspaco = opcoesMenu_resposta.trim();

			TimeUnit.MILLISECONDS.sleep(700);
			opcaoDeEscolhaAtual = Integer.parseInt(opcoesMenu_respostaSemEspaco);

			switch (acaoAtual) {
			case 0:
				if (opcaoDeEscolhaAtual > 0 && opcaoDeEscolhaAtual <= personagens.size()) {
					ERRO = false;
					setAcaoAtual(1);
				} else {
					ERRO = true;
					MENSAGEMDEERRO = "> Escolha Inválida!\n";
				}
				break;
			case 1:
				switch (opcaoDeEscolhaAtual) {
				case 1:
					ERRO = false;
					setAcaoAtual(0);
										
					jogador = new Jogador(jogador.getNomeDoJogador(), personagens.get(opcaoDeEscolhaAtual - 1));
							
					gej.setEstado(GerenciadorDoEstadoDoJogo.ESCOLHADOSPORTAIS);
					break;
				case 0:
					ERRO = false;
					setAcaoAtual(0);
					gej.setReiniciar(true);
					break;
				default:
					ERRO = true;
					MENSAGEMDEERRO = "> Escolha Inválida!\n";
					break;
				}
			}

		} catch (NumberFormatException e) {
			ERRO = true;
			MENSAGEMDEERRO = "> Caracter Inválido!\n";
		} catch (Exception e) {
		}

		if (ERRO && !MENSAGEMDEERRO.isEmpty()) {
			System.err.printf("%s", MENSAGEMDEERRO);
			try {
				TimeUnit.MILLISECONDS.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gej.setReiniciar(true);
		}
	}

}
