package estadoDoJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import extras.FuncoesExtras;

public class Menu extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);

	private List<String> opcoesDoMenu = new ArrayList<String>();
	private int opcaoDeEscolhaAtual = 0;

	private String nomeDoJogo;
	private String cabecalho;

	public Menu(GerenciadorDoEstadoDoJogo gej) {
		
		this.gej = gej;

		nomeDoJogo = new String("City Hunter");
		cabecalho = String.format("Seja Bem Vindo ao Jogo - %s", nomeDoJogo);

		opcoesDoMenu.add("Iniciar");
		opcoesDoMenu.add("Sobre o Jogo");
		opcoesDoMenu.add("Sair do Jogo");

	}

	@Override
	public void iniciar() {
		// Titulo
		FuncoesExtras.desenhaSimbolo("\u001B[31m", "=-", (cabecalho.length() / 2) + 4);
		System.out.printf("%n%40s%n", cabecalho);
		FuncoesExtras.desenhaSimbolo("\u001B[31m", "=-", (cabecalho.length() / 2) + 4);
	}

	@Override
	public void escrever() {
		// Escrever menu de opções
		System.out.println();
		for (int i = 0; i < opcoesDoMenu.size(); i++) {
			System.out.printf("[%d] %s%n", i + 1, opcoesDoMenu.get(i));
		}
		System.out.print("Escolha: ");
	}

	@Override
	public void selecionar() {
		try {

			String opcoesMenu_resposta = resposta.nextLine();
			String opcoesMenu_respostaSemEspaco = opcoesMenu_resposta.trim();

			TimeUnit.MILLISECONDS.sleep(700);
			opcaoDeEscolhaAtual = Integer.parseInt(opcoesMenu_respostaSemEspaco);
			switch (opcaoDeEscolhaAtual) {
			case 1:
				gej.setEstado(GerenciadorDoEstadoDoJogo.LOGIN);
				break;
			case 2:				
				gej.setEstado(GerenciadorDoEstadoDoJogo.SOBREOJOGO);
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.err.println("> Escolha Inválida!\n");
				TimeUnit.MILLISECONDS.sleep(700);
				gej.setReiniciar(true);
				break;
			}
			
		} catch (NumberFormatException e) {
			System.err.println("> Caracter Inválido!\n");
			gej.setReiniciar(true);
		} catch (Exception e) {}
	}

}
