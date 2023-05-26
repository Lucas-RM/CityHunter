package estadoDoJogo;

import java.util.Scanner;

import extras.FuncoesExtras;

public class FimDeJogo extends EstadoDoJogo {
	
	private Scanner resposta = new Scanner(System.in);

	private String titulo;
	
	public FimDeJogo(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;
		
		this.titulo = new String("fim de jogo");
		titulo = FuncoesExtras.capitalize(titulo);
	}

	@Override
	public void iniciar() {
		// Titulo
		System.out.println();
		FuncoesExtras.desenhaSimbolo("\u001B[31m", "=-", (titulo.length() / 2) + 4);
		System.out.printf("\u001B[31m" + "%n%14s%n" + "\u001B[0m", titulo);
		FuncoesExtras.desenhaSimbolo("\u001B[31m", "=-", (titulo.length() / 2) + 4);
	}

	@Override
	public void escrever() {
		System.out.print("\u001B[36m" + "\n>>> Aperte ENTER para reiniciar...\n" + "\u001B[0m");
	}

	@Override
	public void selecionar() {
		String reiniciar = resposta.nextLine();
		
		if (reiniciar.equals("")) {
			EscolhaDosPortais.resetarPortais();
			gej.setEstado(GerenciadorDoEstadoDoJogo.MENU);
		}
		else System.err.println("> Caracter Inválido!");
	}

}
