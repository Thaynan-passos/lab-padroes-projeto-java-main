package one.digitalinnovation.gof.strategy;

public enum TipoComportamento implements Comportamento {
	NORMAL {
		public void mover() {
			System.out.println("Movendo-se normalmente...");
		}
	},
	DEFENSIVO {
		public void mover() {
			System.out.println("Movendo-se defensivamente...");
		}
	},
	AGRESSIVO {
		public void mover() {
			System.out.println("Movendo-se agressivamente...");
		}
	}
}