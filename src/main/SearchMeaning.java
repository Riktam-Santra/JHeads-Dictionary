package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SearchMeaning {
	int i = 1;
	
	public String word = "";
	public String phonetic = "";
	public String synonyms = "";
	public String definitions = "";
	public String type = "";
	
	@SuppressWarnings("unchecked")
	SearchMeaning (String query) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + query))
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			try {
				Object obj = new JSONParser().parse("{ \"response\": " + response.body() + "}");
				JSONObject jo = (JSONObject) obj;
				JSONArray ja = (JSONArray) jo.get("response");
				Object tarObj = ja.get(0);
				JSONObject subobj = (JSONObject) tarObj;
				word = (String) subobj.get("word");
				System.out.println("requested word: " + word);
				phonetic = (String) subobj.get("phonetic");
				System.out.println("phonetic: " + phonetic);
				JSONArray meaningArr = (JSONArray) subobj.get("meanings");
				JSONObject meaningArrItem = (JSONObject) meaningArr.get(0);
				JSONObject meaningObj = (JSONObject) meaningArrItem;
				type = (String) meaningObj.get("partOfSpeech");
				System.out.println("Type: " + type);
				
				System.out.println("Definations: ");
				JSONArray defArr = (JSONArray) meaningObj.get("definitions");
				defArr.forEach(item -> {
					JSONObject defObj = (JSONObject) item;
					definitions = definitions + i+ ". " + defObj.get("definition") + "\n\t\t";
					System.out.println("\t"+ i + "." + defObj.get("definition") + "\n");
					i++;
				});
				i = 1;
				JSONArray synarr = (JSONArray) meaningObj.get("synonyms");
				
				System.out.print("Synonyms: ");
				synarr.forEach(item -> {
					if(i%10 == 0) {
						synonyms = synonyms + " \n\t\t" + item;
						System.out.print("\n\t");
					}
					else if (i == 1) {
						synonyms = "\t\t" + synonyms + item;
					}
					else {
						synonyms = synonyms +", " + item;
						System.out.print(item + ", ");
					}
					i++;
				});
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
