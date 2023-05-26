package entidade.Inimigos;

import entidade.Inimigo;

public class GatoRadioativo extends Inimigo {
	
	public GatoRadioativo() {
		nomeDoInimigo = new String("Gato Poluído");
		vida = maxVida = 200;
		dano = 100;
	}
	
}
