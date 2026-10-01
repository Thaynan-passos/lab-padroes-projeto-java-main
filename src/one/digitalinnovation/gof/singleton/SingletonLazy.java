package one.digitalinnovation.gof.singleton;

public class SingletonLazy {

	private static volatile SingletonLazy instancia;
	
	private SingletonLazy() {
	}
	
	public static SingletonLazy getInstancia() {
		if (instancia == null) {
			instancia = new SingletonLazy();
		}
		return instancia;
	}
}
