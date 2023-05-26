package estadoDoJogo;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidade.Jogador;
import extras.FuncoesExtras;

public class Login extends EstadoDoJogo {

	private Scanner resposta = new Scanner(System.in);
	private int opcaoDeEscolhaAtual = 0;
	private int acaoAtual = 0;
	
	private String cabecalho;
	private String nomeDoUsuario;	
	private int limiteCaracteres_nomeDousuario;
	
	private Boolean ERRO = false;
	private String MENSAGEMDEERRO = "";
	
	protected static Jogador jogador;
	
	public Login(GerenciadorDoEstadoDoJogo gej) {
		
		this.gej = gej;
		
		this.cabecalho = new String("para jogar, faça seu login primeiro");
		cabecalho = FuncoesExtras.capitalize(cabecalho);
		
		limiteCaracteres_nomeDousuario = 10;
		
	}
	
	private void setAcaoAtual(int acaoAtual) {
		this.acaoAtual = acaoAtual;
	}

	public String getNomeDoUsuario() {
		return nomeDoUsuario;
	}

	@Override
	public void iniciar() {
		// Cabeçalho
		System.out.println();
		FuncoesExtras.desenhaSimbolo("\u001B[36m", "=-", (cabecalho.length() / 2) + 4);
		System.out.printf("%n%38s%n", cabecalho);
		FuncoesExtras.desenhaSimbolo("\u001B[36m", "=-", (cabecalho.length() / 2) + 4);		
	}

	@Override
	public void escrever() {
		if (acaoAtual == 0) {
			// Escrever para o usuário informar o nome
			System.out.printf("\n> Informe seu Nome (limite de %d caracteres): ", limiteCaracteres_nomeDousuario);
		} else if (acaoAtual == 1) {			
			System.out.printf("%n> O Nome escolhido foi (%s).%n> Digite [1](Confirmar) ou [0](Trocar de Nome): ", getNomeDoUsuario());
		}
	}

	@Override
	public void selecionar() {
		try {

			String entrada = resposta.nextLine();

			TimeUnit.MILLISECONDS.sleep(700);		

			switch (acaoAtual) {
			case 0:
				if (entrada.isEmpty()) {
					ERRO = true;
					MENSAGEMDEERRO = "> Nome Inválido!\n";
				} else if (entrada.length() > limiteCaracteres_nomeDousuario) {
					ERRO = true;
					MENSAGEMDEERRO = String.format("> O Nome pode ter no máximo %d caracteres\n", limiteCaracteres_nomeDousuario);
				}
				else {
					ERRO = false;
					nomeDoUsuario = entrada;
					setAcaoAtual(1);
				}
				break;
			case 1:
				String entrada_SemEspaco = entrada.trim();
				opcaoDeEscolhaAtual = Integer.parseInt(entrada_SemEspaco);
				switch (opcaoDeEscolhaAtual) {
				case 1:
					ERRO = false;
					setAcaoAtual(0);
					
					jogador = new Jogador(nomeDoUsuario, null);
					
					gej.setEstado(GerenciadorDoEstadoDoJogo.SELECAOPERSONAGEM);
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
