package entidade;

public class Inimigo {
	
	/* Variáveis do Inimigo */
	protected String nomeDoInimigo;
	protected int vida;
	protected int maxVida;
	protected boolean morto;
	protected int dano;
	
	protected Inimigo() { }
	
	public String getNomeDoInimigo() { return nomeDoInimigo; }
	public int getVida() { return vida; }
	public int getMaxVida() { return maxVida; }
	public boolean estaMorto() { return morto; }
	public int getDano() { return dano; }
	
	public void hit(int dano) {
		System.out.printf("\u001B[33m" + "%n>> Vida do %s: %d ==> ", nomeDoInimigo, vida);
		vida -= dano;
		
		if(vida <= 0) {
			vida = 0;
			morto = true;
		}
		
		System.out.printf("%d%n" + "\u001B[0m", vida);
	}
}
