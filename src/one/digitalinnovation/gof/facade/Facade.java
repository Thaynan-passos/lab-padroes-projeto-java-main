package one.digitalinnovation.gof.facade;

import subsistema1.crm.CrmService;
import subsistema2.cep.CepApi;

public class Facade {

	private final CrmService crm;
	private final CepApi cepApi;

	public Facade() {
		this(new CrmService(), CepApi.getInstancia());
	}

	public Facade(CrmService crm, CepApi cepApi) {
		this.crm = crm;
		this.cepApi = cepApi;
	}

	public void migrarCliente(String nome, String cep) {
		CepApi.Endereco endereco = cepApi.consultar(cep);
		crm.gravarCliente(nome, cep, endereco.cidade(), endereco.estado());
	}
}