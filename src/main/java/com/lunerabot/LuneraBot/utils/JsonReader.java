package com.lunerabot.LuneraBot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonReader {
	public JSONObject getJsonToString() {

		try {
			URL url = new URL(
					"https://raw.githubusercontent.com/Bootcamp-4/lunerabot/main/src/main/resources/teamdata.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = br.read()) != -1) {
				sb.append((char) cp);
			}
			String output = sb.toString();
			// JsonObject json = new Gson().fromJson(output, JsonObject.class);
			// System.out.println("salida como JSON" + json);
			JSONObject json = new JSONObject(output);
			conn.disconnect();
			return json;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
