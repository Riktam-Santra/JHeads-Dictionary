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

import javax.swing.*;

public class Main {
	static String word = "";
	static int i = 1;
	@SuppressWarnings("unchecked")
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
				//System.out.println("{ \"response\": " + response.body() + "}");
				try {
					Object obj = new JSONParser().parse("{ \"response\": " + response.body() + "}");
					JSONObject jo = (JSONObject) obj;
					JSONArray ja = (JSONArray) jo.get("response");
//					ja.forEach(item -> {
//						JSONObject subobj = (JSONObject) item;
//						System.out.println("requested word: " + subobj.get("word"));
//						System.out.println("phonetic: " + subobj.get("phonetic"));
//						JSONArray meaningArr = (JSONArray) subobj.get("meanings");
//						meaningArr.forEach(meanArrItem -> {
//							JSONObject meaningObj = (JSONObject) meanArrItem;
//							System.out.println("Type: " + meaningObj.get("partOfSpeech"));
//						});
//					});
					Object tarObj = ja.get(0);
					JSONObject subobj = (JSONObject) tarObj;
					System.out.println("requested word: " + subobj.get("word"));
					System.out.println("phonetic: " + subobj.get("phonetic"));
					JSONArray meaningArr = (JSONArray) subobj.get("meanings");
					JSONObject meaningArrItem = (JSONObject) meaningArr.get(0);
					JSONObject meaningObj = (JSONObject) meaningArrItem;
					System.out.println("Type: " + meaningObj.get("partOfSpeech"));
					System.out.println("Definations:");
					JSONArray defArr = (JSONArray) meaningObj.get("definitions");
					defArr.forEach(item -> {
						JSONObject defObj = (JSONObject) item;
						System.out.println("\t"+ i + "." + defObj.get("definition") + "\n");
						i++;
					});
					i = 1;
					JSONArray synarr = (JSONArray) meaningObj.get("synonyms");
					System.out.print("Synonyms: ");
					synarr.forEach(item -> {
						if(i%5 == 0) {
							System.out.print("\n\t");
						}
						System.out.print(item + ", ");
						i++;
					});
					
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
