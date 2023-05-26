package entidade.Inimigos;

import entidade.Inimigo;

public class MostroDaPoluicao extends Inimigo {
	
	public MostroDaPoluicao() {
		nomeDoInimigo = new String("Mostro Da Poluicao (BOSS)");
		vida = maxVida = 400;
		dano = 200;
	}
	
}
