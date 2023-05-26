package estadoDoJogo;

public abstract class EstadoDoJogo {

	protected GerenciadorDoEstadoDoJogo gej;

	public abstract void iniciar();

	public abstract void escrever();

	public abstract void selecionar();

}
