package entidade.Inimigos;

import entidade.Inimigo;

public class SuperRobo extends Inimigo {
	
	public SuperRobo() {
		nomeDoInimigo = new String("Super Robô (Motosserra)");
		vida = maxVida = 300;
		dano = 150;
	}
	
}
