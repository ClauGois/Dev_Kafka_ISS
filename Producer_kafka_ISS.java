package ISS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer_kafka_ISS {
	public static String GET(String address) throws IOException {
		URL url = new URL(address); // Variável para armazenar a URL da API de acesso aos dados
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Abre a conexão via HTTP
		connection.setRequestProperty("accept", "application/json"); // Obtém algumas propriedades da conexão
		InputStream responseStream = connection.getInputStream(); // Obtém o Stream de dados
		BufferedReader in = new BufferedReader(new InputStreamReader(responseStream)); // Processa o Stream
		String line = in.readLine(); // Leitura de cada linha do Stream
		in.close(); // Fecha o Stream
		return line; // Retorna o resultado
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String url = "http://api.open-notify.org/iss-now.json";  // url da api a ser consumida
		Properties props = new Properties(); // Configurando as propriendades de conexão para o kafka
		props.put("bootstrap.servers", "192.168.0.104:9092");
		props.put("linger.ms", 1);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 
		Producer<String, String> producer = new KafkaProducer<>(props); // Cria classe de producer do kafka 
		
		String topico = "localizacao_ISS";
		
		for (int i = 0; i < 10; i++) {
			producer.send(new ProducerRecord<String, String>(topico, GET(url))); // Enviando topico e a msg para o kafka
			Thread.sleep(30000); // Pausa de 10 segundos para enviar proxima mensagem
		 }
		 producer.close(); // Fecha o producer
	}

}
