package mainGame;

public interface UnidadeBloqueada {
	public int getCodigoUnico();
	public int getTime();
	public int getTipo();
	public float getLife();
	public float getDano();
	public float getVel();
	public float getRaioAtaque();
	public double getX();
	public double getY();
	public float getDist2(UnidadeBloqueada un);
	public double getAng(UnidadeBloqueada un);
	public boolean getVivo();
}
