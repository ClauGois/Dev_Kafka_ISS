package ISS;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
public class ConsumoApi {
	public static String GET(String address) throws IOException {

		// Variável para armazenar a URL da API de acesso aos dados
		URL url = new URL(address);

		// Abre a conexão via HTTP
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// Obtém algumas propriedades da conexão
		connection.setRequestProperty("accept", "application/json");

		// Obtém o Stream de dados
		InputStream responseStream = connection.getInputStream();

		// Processa o Stream
		BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));

		// Leitura de cada linha do Stream
		String line = in.readLine();

		// Fecha o Stream
		in.close();

		// Retorna o resultado
		return line;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String url = "http://api.open-notify.org/iss-now.json";	
		for (int i = 0; i < 50; i++) {
			String response = GET(url);
			System.out.println(response);
			Thread.sleep(10000);
		}
	}

}
