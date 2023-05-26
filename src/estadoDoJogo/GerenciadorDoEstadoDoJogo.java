package estadoDoJogo;

import java.util.ArrayList;

import estadoDoJogo.Portais.BossFinal;
import estadoDoJogo.Portais.Portal01;
import estadoDoJogo.Portais.Portal02;

public class GerenciadorDoEstadoDoJogo {

	private ArrayList<EstadoDoJogo> estadosDoJogo;
	private int estadoAtual;
	
	private boolean reiniciar;

	public static final int MENU = 0;
	public static final int SOBREOJOGO = 1;
	public static final int LOGIN = 2;
	public static final int SELECAOPERSONAGEM = 3;
	public static final int FIMDEJOGO = 4;
	public static final int ESCOLHADOSPORTAIS = 5;
	public static final int PORTAL01 = 6;
	public static final int PORTAL02 = 7;
	public static final int BOSSFINAL = 8;
	public static final int POSCREDITOS = 9;

	public GerenciadorDoEstadoDoJogo() {

		estadosDoJogo = new ArrayList<EstadoDoJogo>();

		estadoAtual = MENU;
		estadosDoJogo.add(new Menu(this));
		estadosDoJogo.add(new SobreOJogo(this));
		estadosDoJogo.add(new Login(this));
		estadosDoJogo.add(new SelecaoPersonagem(this));
		estadosDoJogo.add(new FimDeJogo(this));
		estadosDoJogo.add(new EscolhaDosPortais(this));
		estadosDoJogo.add(new Portal01(this));
		estadosDoJogo.add(new Portal02(this));
		estadosDoJogo.add(new BossFinal(this));
		estadosDoJogo.add(new PosCreditos(this));
		
		reiniciar = true;
		
		iniciar();

	}

	public void iniciar() {		
		estadosDoJogo.get(estadoAtual).iniciar();
		while (reiniciar) {
			estadosDoJogo.get(estadoAtual).escrever();
			estadosDoJogo.get(estadoAtual).selecionar();
		}		
			
		if (!reiniciar)
			reiniciar = true;
			iniciar();
	}

	public void setEstado(int estado) {

		estadoAtual = estado;
		estadosDoJogo.get(estado).iniciar();

	}
	
	public void setReiniciar(boolean reiniciar) {
		this.reiniciar = !reiniciar;		
	}
}
