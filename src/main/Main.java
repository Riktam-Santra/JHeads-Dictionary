package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {
	static String word = "";
	public static void main (String[] args) {
		try (Scanner scnObj = new Scanner(System.in)) {
			System.out.print("Enter word to search meaning for: ");
			word = scnObj.next();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + word))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				System.out.println("{ \"response\": " + response.body() + "}");
				try {
					Object obj = new JSONParser().parse("{ \"response\": " + response.body() + "}");
					JSONObject jo = (JSONObject) obj;
					JSONArray ja = (JSONArray) jo.get("response");
					System.out.println(ja.toString());
					
					System.out.println();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
