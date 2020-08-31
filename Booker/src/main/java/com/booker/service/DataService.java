package com.booker.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class DataService {
		
		private final Logger log = LoggerFactory.getLogger(this.getClass());
		private final OkHttpClient client = new OkHttpClient();
		private String APIkey = "3bae16f4f3e88474695e0f0cd5000109";
		
		public String getBookByISBN(String isbn) throws IOException
		{
			log.debug("Könyv lekérdezése ISBN alapján: " + isbn);
		Request request = new Request.Builder()
				.url("https://moly.hu/api/book_by_isbn.json?q=" + isbn + "&key=" + APIkey)
				.get()
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
		}
		
		public String getBookByTitle(String title) throws IOException
		{
			log.debug("Könyv lekérdezése ISBN alapján: " + title);
		Request request = new Request.Builder()
				.url("https://moly.hu/api/books.json?q=" + title + "&key=" + APIkey)
				.get()
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
		}
		
		public String getBookByMolyID(Long id) throws IOException
		{
			log.debug("Könyv lekérdezése Moly ID alapján: " + id);
		Request request = new Request.Builder()
				.url("	https://moly.hu/api/book/" + id + ".json?key=" + APIkey)
				.get()
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
		}

}
