package app;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.net.ssl.HttpsURLConnection;

/**
 * Library to get definitions of any word on the Dictionary Dictionary API and also
 * to get a random common word.
 * 
 * See: https://developer.oxforddictionaries.com/documentation
 * 
 * @author Katie Jergens
 * 
 */
public class Dictionary {

	private final static String app_id = "7d68c62b";
	private final static String app_key = "da9b8ac24ea7c4ecd9324e2a083b5ee7";

	/**
	 * Get a dictionary entry of the given word, in English. Format is Json.
	 * 
	 * @param word
	 * @return JsonObject
	 */
	private static JsonObject getEntry(String word) {
		return getEntry(word, "en");
	}

	/**
	 * Get a dictionary entry of a given word in the given language. Format is Json.
	 * 
	 * @param word
	 * @param language
	 * @return A JSON Object
	 */
	private static JsonObject getEntry(String word, String language) {

		String apiUrl = "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/"
				+ word.toLowerCase();
		JsonObject results = null;

		try {
            System.out.println("Processing...\n");
			URL url = new URL(apiUrl);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("app_id", app_id);
			urlConnection.setRequestProperty("app_key", app_key);

			// read the output from the server
			JsonReader jreader = Json.createReader(new InputStreamReader(urlConnection.getInputStream()));
			JsonObject allresults = (JsonObject) jreader.read();
			jreader.close();
			results = (JsonObject) allresults.getJsonArray("results").get(0);

		} catch (Exception e) {
			//e.printStackTrace();
            System.out.println("Trouble finding word. Please try again.");
		}

		return results;

	}

	/**
	 * Get all definitions of a word in a single String.
	 * 
	 * @param word
	 * @return String
	 */
	public static String getDefinition(String word) {

		String definitionSet = "";
		JsonArray entries = getEntry(word).getJsonArray("lexicalEntries");

		for (JsonValue entry : entries) {
			int count = 0;

			// Part of speech
			definitionSet += ((JsonObject) entry).getString("lexicalCategory");
			definitionSet += "\n";

			// All the definitions for this part of speech
			JsonArray entrycontent = ((JsonObject) entry).getJsonArray("entries");

			for (JsonValue content : entrycontent) {
				JsonArray senses = ((JsonObject) content).getJsonArray("senses");

				// Print each sense definition
				for (JsonValue sense : senses) {
					if (senses != null) {
						JsonArray defs = ((JsonObject) sense).getJsonArray("definitions");

						if (defs != null) {

							for (JsonValue def : defs) {
								definitionSet += ++count + ". " + def.toString().replaceAll("\"", ""); // strip
								// quotes
							}

							// Print each sub-sense definition
							JsonArray subsenses = ((JsonObject) sense).getJsonArray("subsenses");

							if (subsenses != null) {
								for (JsonValue subsense : subsenses) {
									JsonArray subdefs = ((JsonObject) subsense).getJsonArray("definitions");

									if (subdefs != null) {
										for (JsonValue subdef : subdefs) {
											definitionSet += "; " + subdef.toString().replaceAll("\"", ""); // strip
											// quotes
										}
									}
								}
							}
						}
					}

					definitionSet += "\n";
				}
			}
			definitionSet += "\n";
		}

		return definitionSet;
	}

	/**
	 * Get one of 3,000 common English words.
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getRandomWord() {

		// Pick a random wordlist file
		String filename = "resources/wordlist_";
		int fileNo = (int) (Math.random() * 11);
		String[] letterGroups = { "AB", "CD", "EG", "HK", "LN", "MN", "OP", "QR", "S", "T", "UZ" };
		filename += letterGroups[fileNo] + ".txt";

		InputStream stream = Dictionary.class.getResourceAsStream(filename);
		if (stream == null)
			System.err.println("Resource not located.");
		Scanner input = null;
		try {
			input = new Scanner(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Read words into ArrayList
		ArrayList<String> words = new ArrayList<String>();
		while (input.hasNextLine()) {
			words.add(input.nextLine());
		}

		// Get a random word
		int rand = (int) (Math.random() * words.size());

		return words.get(rand);
	}
}
