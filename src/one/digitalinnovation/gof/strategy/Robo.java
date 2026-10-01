package one.digitalinnovation.gof.strategy;

import java.util.Objects;

public class Robo {

	private Comportamento comportamento = new ComportamentoNormal();

	public void setComportamento(Comportamento comportamento) {
		this.comportamento = Objects.requireNonNull(comportamento, "Comportamento não pode ser nulo");
	}

	public void mover() {
		comportamento.mover();
	}
}