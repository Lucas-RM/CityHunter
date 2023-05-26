package entidade.Personagens;

import java.util.Arrays;

import entidade.Personagem;
import extras.FuncoesExtras;

public class Arqueiro extends Personagem {
	
	public Arqueiro() {

		nomeDoPersonagem = new String("Guerreiro");
		vida = maxVida = 1000;
		
		/* Ataques */
		nome_ataques.addAll(Arrays.asList(new String[] { 
				"Espada", "Golpe Especial", "Ataque em Área", "Poção de Veneno" }));

		dano_ataques.addAll(Arrays.asList(new Integer[] { 20, 1000, 50, 30 }));

		quantidade_ataques.addAll(Arrays.asList(new Integer[] { 50, 100, 20, 30 }));
		
		/* Defesas e Curas */
		nome_defesas_curas.addAll(Arrays.asList(new String[] { 
				"Escudo", "Esquiva", "Poção de Cura" }));

		valor_defesas_curas.addAll(Arrays.asList(new Integer[] { 20, 100, 25 }));

		quantidade_defesas_curas.addAll(Arrays.asList(new Integer[] { 30, 10, 30 }));

	}

	@Override
	public void DefenderOuCurar(String acaoEscolhida, int valorDaDefesaOuCura, int danoDoInimigo) {
		System.out.println();
		FuncoesExtras.desenhaSimbolo("\u001B[35m", "=+", 30);
		
		if (acaoEscolhida.toUpperCase().equals("escudo".toUpperCase())) {
			System.out.printf("\u001B[32m" + "%n>> Boa Defesa! Reduziu em %d%s do dano que o inimigo te causaria!%n" 
					+ "\u001B[0m", valorDaDefesaOuCura, "%");
			
			System.out.printf("\u001B[33m" + "%n>> Vida do %s: %d ==> ", nomeDoPersonagem, vida);
			
			double danoAtaqueInimigoComDefesa = danoDoInimigo - (danoDoInimigo * ((double) valorDaDefesaOuCura / 100));
			hit((int) Math.round(danoAtaqueInimigoComDefesa));
			
			System.out.printf("%d%n" + "\u001B[0m", vida);
			
		} else if (acaoEscolhida.toUpperCase().equals("Esquiva".toUpperCase())) {
			System.out.println("\u001B[32m" + "\n>> Muito Bom! Você se esquivou do ataque do inimigo!" + "\u001B[0m");
			
//			for (int i = 0; i < dano_ataques.size(); i++) {
//				dano_ataques.set(i, dano_ataques.get(i) + (dano_ataques.get(i) * valorDaDefesaOuCura / 100));
//			}
			
		} else if (acaoEscolhida.toUpperCase().equals("Poção de Cura".toUpperCase())) {
			System.out.println("\u001B[32m" + "\n>> Boa! Sempre é bom se curar "
					+ "\n   Mas, você tomou o ataque do inimigo." + "\u001B[0m");
			
			System.out.printf("\u001B[33m" + "%n>> Vida do %s: %d ==> ", nomeDoPersonagem, vida);
			
			vida += vida * valorDaDefesaOuCura / 100;
			hit(danoDoInimigo);
			
			System.out.printf("%d%n" + "\u001B[0m", vida);
			
		}

	}

}
