package me.stephen.colias.whoswho.utils;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

final public class NetworkDao {
	public static Elements getFaces() throws IOException {
		URL url = new URL(Constants.URL_GET_FACES);
		Document document = Jsoup.parse(url, Constants.TIMEOUT);
		return document.select(Constants.FACES_ELEMENTS_SELECT);		
	}
}
