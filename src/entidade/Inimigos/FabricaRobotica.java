package entidade.Inimigos;

import entidade.Inimigo;

public class FabricaRobotica extends Inimigo {
	
	public FabricaRobotica() {
		nomeDoInimigo = new String("F�brica Rob�tica");
		vida = maxVida = 350;
		dano = 150;
	}
	
}
