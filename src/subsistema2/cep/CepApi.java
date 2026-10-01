package subsistema2.cep;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CepApi {

	public record Endereco(String cidade, String estado) {
	}

	private static final CepApi instancia = new CepApi();

	private final HttpClient client = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(5))
			.build();

	private CepApi() {
		super();
	}

	public static CepApi getInstancia() {
		return instancia;
	}

	public String recuperarCidade(String cep) {
		return consultar(cep).cidade();
	}

	public String recuperarEstado(String cep) {
		return consultar(cep).estado();
	}

	
	public Endereco consultar(String cep) {
		String digitos = cep == null ? "" : cep.replaceAll("\\D", "");
		if (digitos.length() != 8) {
			throw new IllegalArgumentException("CEP inválido: " + cep);
		}

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://viacep.com.br/ws/" + digitos + "/json/"))
				.timeout(Duration.ofSeconds(5))
				.GET()
				.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				throw new IllegalStateException("ViaCEP respondeu HTTP " + response.statusCode());
			}
			return interpretar(response.body(), cep);
		} catch (IOException e) {
			throw new IllegalStateException("Falha ao consultar o ViaCEP", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Consulta ao ViaCEP interrompida", e);
		}
	}

	static Endereco interpretar(String json, String cep) {
		if (json.contains("\"erro\"")) {
			throw new IllegalArgumentException("CEP não encontrado: " + cep);
		}
		return new Endereco(extrair(json, "localidade"), extrair(json, "uf"));
	}

	private static String extrair(String json, String campo) {
		Matcher m = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
		if (!m.find()) {
			throw new IllegalStateException("Campo ausente na resposta: " + campo);
		}
		return m.group(1);
	}
}