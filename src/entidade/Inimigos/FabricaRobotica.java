package entidade.Inimigos;

import entidade.Inimigo;

public class FabricaRobotica extends Inimigo {
	
	public FabricaRobotica() {
		nomeDoInimigo = new String("Fábrica Robótica");
		vida = maxVida = 350;
		dano = 150;
	}
	
}
