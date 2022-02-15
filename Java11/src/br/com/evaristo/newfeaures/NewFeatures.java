package br.com.evaristo.newfeaures;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 
 */

/**
 * @author evari
 *
 */
public class NewFeatures {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	static String ab = "";
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		
		//listOfExample();
		
		//httpClientExample();
		
		//varExample();
		
		//longTextExample();
		
		//switchExample();
		
		//collectionToArray();
		
		//stringExamples();
		
		//optional();

		//filesReadFilesWrite();
		
		httpClientAPI();
	}

	private static void httpClientAPI() throws IOException, InterruptedException {
		String uri = "https://postman-echo.com/get?uname=4121&pwd=12131";
		
		HttpRequest req= HttpRequest
				.newBuilder()
				.uri(URI.create(uri))
				.version(Version.HTTP_2)
				.build();
		
		HttpClient  client = HttpClient
									 .newBuilder()
									 .build();
		
		HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
		System.out.println(resp.statusCode());
		System.out.println(resp.body());	
	}

	private static void filesReadFilesWrite() throws IOException {
		String uri = "./text.txt";
		
		String content = Files.readString(Path.of(uri));
		
		content.lines().collect(Collectors.toList()).forEach(System.out::println);
		
		String newContent = content.repeat(200);
		
		Files.writeString(Path.of(uri), newContent, StandardOpenOption.WRITE);
		
	}

	private static void optional() {
		Optional o = Optional.of("Evaristo");
		
		//Old
		o.ifPresent((s) -> System.out.println(s));
		
		//new
		System.out.println(o.isEmpty());
	}

	private static void stringExamples() {
		
		//isBlank
		String a = "Welcome";
		String b = "          ";
		System.out.println(a.isBlank());
		System.out.println(b.isBlank());
		
		//lines()
		String line =  """
						1 - fdfadfdfafdfadfadfd
						2 - adfadfadfdfafdfdafd
						3 - adfdfdffdfdffdfffff			
				
						""";		
		line.lines().collect(Collectors.toList()).forEach((System.out::println));
		
		//strip() unicode
		
		String strip = " abobora ";
		System.out.println(strip.trim());
		System.out.println(strip.strip());
		System.out.println(strip.stripTrailing());
		System.out.println(strip.stripLeading());

		//repeat
		
		String repeat = "Welcome ";
		System.out.println(repeat.repeat(10));		
	}

	private static void collectionToArray() {
		
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		
		System.out.println(list);
		
		String[] array = new String[10];
		String[] finalArray = list.toArray(array);
		Arrays.stream(finalArray).forEach(System.out::println);		
		
		
		System.out.println("Java 11");
		
		String[] java11Array = list.toArray(String[]::new);
		Arrays.stream(java11Array).forEach(System.out::println);
		
	}
	private static void switchExample() {
		//Old School"
		int valor = 9;
		switch(valor) {
		case 10 : System.out.println("maça"); break;
		case 11 : System.out.println("maça"); break;
		default : System.out.println("finally");			
		}
		
		//New School
		String fruit = "";
		switch(fruit) {
		case "maça" -> System.out.println("errou");
		case "pera" -> System.out.println("errou");
		case "uva" -> System.out.println("errou");
		default -> System.out.println("Fim");
		}
		
	}
	private static void longTextExample() {
		var texto = """
			     It is a example of a long text
			     In this situation you don't have to concern about line breaks
			
			""";
	}
	
	private static void varExample() {
		var map = new HashMap<Integer, String>();
		map.put(1, "Evaristo");
		map.put(2, "Camila");
		map.forEach((key, value) -> System.out.println(key + " - "+ value));
	}

	private static void listOfExample() {
		//It is a immutable list
		List<String> names = List.of("evaristo", "camila", "maria", "joao");
		names.forEach(System.out::println);
	}

	private static void httpClientExample() throws URISyntaxException, IOException, InterruptedException {
		System.out.println("Starting integration");
		
	final HttpClient httpClient = HttpClient
									.newBuilder().
									followRedirects(HttpClient.Redirect.ALWAYS).
									build();
	
	String address ="http://www.google.com.br" ;
	final HttpRequest httpRequest = HttpRequest
										.newBuilder(new URI(address))
										.GET()
										.timeout(Duration.of(10,ChronoUnit.MINUTES))
										.build();
	
	HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
	
	System.out.println("Sincrono \n" + response.body());
	
	System.out.println("Assincrono \n" + response.body());

	final CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
	
	future.whenComplete((responseSuccess, throwable) -> {
		System.out.println(responseSuccess.statusCode());
		System.out.println(responseSuccess.headers());
		System.out.println(responseSuccess.version());	
		System.out.println(responseSuccess.body());	
	});
	System.out.println("Ending integration");
	}

}
