package estadoDoJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import extras.FuncoesExtras;

public class PosCreditos extends EstadoDoJogo {

	private String titulo;

	private static List<String> descricoes = new ArrayList<String>();

	public PosCreditos(GerenciadorDoEstadoDoJogo gej) {
		this.gej = gej;

		titulo = new String("PÓS-CRÉDITOS");
		titulo = titulo.toUpperCase();

		descricoes.add("\n=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n"
				+ "=+=+=+ CRIADORES DO JOGO CITY HUNTER =+=+=+\n"
				+ "=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");				
		descricoes.add(new String("\n|| 	FERNANDO LIMA DINIZ 		 ||"));		
		descricoes.add(new String("\n|| 	LUCAS RODRIGUES MARCONDES 	 ||"));
		descricoes.add(new String("\n|| 	HEITOR PALAZZI OLIVEIRA  	 ||"));		
		descricoes.add(new String("\n|| 	RAFAEL MOREIRA DA SILVA  	 ||"));		
		descricoes.add(new String("\n|| 	VITOR DE SOUZA SILVA 		 ||"));		
		
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
			if (!descricoes.isEmpty()) {
				for (String string : descricoes) {
					System.out.print(string);
					TimeUnit.MILLISECONDS.sleep(1200);
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selecionar() {		
		System.out.println();
		System.exit(0);
	}

}
