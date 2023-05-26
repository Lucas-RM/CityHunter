package estadoDoJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import extras.FuncoesExtras;

public class SobreOJogo extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);

	private String titulo;

	private static List<String> sobreOJogo = new ArrayList<String>();

	public SobreOJogo(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;

		titulo = new String("Sobre o Jogo");
		titulo = titulo.toUpperCase();

		sobreOJogo.add("\n> O ano � 2023, voc� � contratado pela Secretaria do Meio Ambiente\n"
				+ "  para realizar a limpeza de Portais Poluidores, que s�o port�es de outro mundo,\n"
				+ "  fendas dimensionais que abrem em ambientes metropolitanos para contribuir com o\n"
				+ "  aquecimento global e a diminui��o da qualidade do ar e bem estar da popula��o local,\n"
				+ "  destas fendas, saem monstros que se alimentam de polui��o e causam\n"				
				+ "  desastres ambientais por onde passam.\n\n"				
				+ "> Sua miss�o � encontrar a origem desta fenda de dois n�cleos que abriu no\n"				
				+ "  cora��o da cidade e derrotar os inimigos no caminho.\n");		
		
		sobreOJogo.add(new String("\n> Ao chegar na cidade de S�o Paulo,\n"
				+ "  j� se pode ver os efeitos das fendas poluidoras no local,\n"
				+ "  o c�u fica cinzento e � dif�cil de se respirar,\n"
				+ "  � necess�rio usar m�scaras para se aproximar do local da fenda...\n"));
		
		sobreOJogo.add(String.format("\n> Localizado 5 quarteir�es de dist�ncia de seu local de trabalho,\n"
				+ "  fica o Qg da Secretaria do Meio Ambiente, filial das cat�strofes interdimensionais que\n"
				+ "  passa a ser sua casa pelo tempo em que voc� passar na cidade.\n"
				+ "  Voc� chega numa quinta-feira, por volta de 10h da noite,\n"
				+ "  vai dormir e ao despertar se dirige imediatamente ao local da fenda...\n\n"
				));
		
	}

	@Override
	public void iniciar() {
		// Titulo
		System.out.println();
		FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (titulo.length() / 2) + 4);
		System.out.printf("%n%16s%n", titulo);
		FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (titulo.length() / 2) + 4);
	}

	@Override
	public void escrever() {
		try {			
			if (!sobreOJogo.isEmpty()) {
				for (String string : sobreOJogo) {
					System.out.print(string);
					TimeUnit.MILLISECONDS.sleep(1000);
				}
			}

			System.out.print("Aperte ENTER para voltar para o MENU... ");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selecionar() {
		resposta.nextLine();
		
		System.out.println();
		gej.setEstado(GerenciadorDoEstadoDoJogo.MENU);
	}

}
