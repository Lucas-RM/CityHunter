package estadoDoJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import extras.FuncoesExtras;

public class EscolhaDosPortais extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);
	private int opcaoDeEscolhaAtual = 0;

	private String titulo;

	private static List<String> historiaInicial = new ArrayList<String>();
	private static List<String> historiaInicial_fixo = new ArrayList<String>();
	private String escolherOpcao_frase;
	
	private static List<String> portais = new ArrayList<String>();
	private static List<Integer> indice_portais = new ArrayList<Integer>();
	private static List<String> portais_fixo = new ArrayList<String>();
	private static List<Integer> indice_portais_fixo = new ArrayList<Integer>();
	
	private static boolean concluido;

	public EscolhaDosPortais(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;

		titulo = new String("escolha dos portais");
		titulo = titulo.toUpperCase();

		historiaInicial.add("\n> O ano é 2023, você é contratado pela Secretaria do Meio Ambiente\n"
				+ "  para realizar a limpeza de Portais Poluidores, que são portões de outro mundo,\n"
				+ "  fendas dimensionais que abrem em ambientes metropolitanos para contribuir com o\n"
				+ "  aquecimento global e a diminuição da qualidade do ar e bem estar da população local,\n"
				+ "  destas fendas, saem monstros que se alimentam de poluição e causam\n"				
				+ "  desastres ambientais por onde passam.\n\n"				
				+ "> Sua missão é encontrar a origem desta fenda de dois núcleos que abriu no\n"				
				+ "  coração da cidade e derrotar os inimigos no caminho.\n");		
		
		historiaInicial.add(new String("\n> Ao chegar na cidade de São Paulo,\n"
				+ "  já se pode ver os efeitos das fendas poluidoras no local,\n"
				+ "  o céu fica cinzento e é difícil de se respirar,\n"
				+ "  é necessário usar máscaras para se aproximar do local da fenda...\n"));
		
		historiaInicial.add(String.format("\n> Localizado 5 quarteirões de distância de seu local de trabalho,\n"
				+ "  fica o Qg da Secretaria do Meio Ambiente, filial das catástrofes interdimensionais que\n"
				+ "  passa a ser sua casa pelo tempo em que você passar na cidade.\n"
				+ "  Você chega numa quinta-feira, por volta de 10h da noite,\n"
				+ "  vai dormir e ao despertar se dirige imediatamente ao local da fenda...\n"
				));
		
		historiaInicial_fixo.addAll(historiaInicial);

		escolherOpcao_frase = new String("\n> Para chegar no Boss Final, você precisa primeiro limpar todos os inimigos que\n"
				+ "  estão dentro dos Portais Poluidores. Para isso, escolha um portal para ir:\n");
		
		portais_fixo.add("Portal 1");
		portais_fixo.add("Portal 2");
		portais.addAll(portais_fixo);
		
		indice_portais_fixo.add((int)GerenciadorDoEstadoDoJogo.PORTAL01);
		indice_portais_fixo.add((int)GerenciadorDoEstadoDoJogo.PORTAL02);
		indice_portais.addAll(indice_portais_fixo);
		
		concluido = false;
	}
	
	protected static void resetarPortais() {
		historiaInicial.clear();
		historiaInicial.addAll(historiaInicial_fixo);
		
		portais.clear();
		portais.addAll(portais_fixo);
		
		indice_portais.clear();
		indice_portais.addAll(indice_portais_fixo);
		
		concluido = false;
	}

	@Override
	public void iniciar() {
		if (concluido) gej.setEstado(GerenciadorDoEstadoDoJogo.BOSSFINAL);
		else {
			// Titulo
			System.out.println();
			FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (titulo.length() / 2) + 4);
			System.out.printf("%n%22s%n", titulo);
			FuncoesExtras.desenhaSimbolo("\u001B[33m", "=-", (titulo.length() / 2) + 4);
		}
	}

	@Override
	public void escrever() {
		try {					
			if (!historiaInicial.isEmpty()) {
				for (String string : historiaInicial) {
					System.out.print(string);
					TimeUnit.MILLISECONDS.sleep(1);
				}
			}
			
			System.out.println(escolherOpcao_frase);
	
			// Escrever menu de opções dos portais
			for (int i = 0; i < portais.size(); i++) {
				System.out.printf("[%d] %s%n", i + 1, portais.get(i));
			}
			System.out.print("Escolha: ");			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selecionar() {
		try {
			String opcoesMenu_resposta = resposta.nextLine();
			String opcoesMenu_respostaSemEspaco = opcoesMenu_resposta.trim();
			opcaoDeEscolhaAtual = Integer.parseInt(opcoesMenu_respostaSemEspaco);
			
			if (opcaoDeEscolhaAtual < 1 || opcaoDeEscolhaAtual > portais.size()) {
				System.err.println("> Escolha Inválida!\n");
				gej.setReiniciar(true);
			} else {
				TimeUnit.MILLISECONDS.sleep(700);
				int portalEscolhido = indice_portais.get(opcaoDeEscolhaAtual - 1);				
				gej.setEstado(portalEscolhido);

				portais.remove(indice_portais.indexOf(portalEscolhido));
				indice_portais.remove(indice_portais.indexOf(portalEscolhido));
				
				if (portais.isEmpty()) concluido = true;
				else concluido = false;
				
				historiaInicial.clear();
			}
			
		} catch (NumberFormatException e) {
			System.err.println("> Caracter Inválido!\n");
			gej.setReiniciar(true);
		} catch (Exception e) {}

	}

}
