package entidade.Inimigos;

import entidade.Inimigo;

public class Robo extends Inimigo {
	
	public Robo() {
		nomeDoInimigo = new String("Robô");
		vida = maxVida = 200;
		dano = 100;
	}
	
}
