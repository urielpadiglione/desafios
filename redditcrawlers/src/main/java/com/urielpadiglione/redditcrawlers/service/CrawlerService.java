package com.urielpadiglione.redditcrawlers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService {
	Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	public List<String> getData(String tags) {
		List<String> responseList = new ArrayList<String>();
		
		String[] subreddits = tags.split(";");
		String threadLink="";
		String commentsLink="";
		String threadName="";
	
		try {//https://www.reddit.com/r/datasets/comments/8c9f4j/i_have_implemented_a_crawler_for_reddit_data/
			String url = "https://old.reddit.com";
			
			for(String sub : subreddits) {
				logger.info("Subreddit: "+sub);
				String response="";
				
				Document doc = Jsoup.connect(url+"/r/"+sub).get();
				Elements posts = doc.select("div[class*=\"thing id-\"]");

				for(Element el : posts) {
					//pega upvotes e garante que não esteja vazio
					if(!el.getElementsByClass("score unvoted").attr("title").toString().equals("")) {
						//pega upvotes
						Integer votes = Integer.valueOf(el.getElementsByClass("score unvoted").attr("title").toString());
						
						if(votes>=5000) {
							//pega link dos comentários
							commentsLink = el.select("[class*=\"bylink comments may-blank\"]").attr("href").toString();
							
							//pega nome da thread
							threadName = el.select("[class*=\"title may-blank\"]").text();
							
							//pega o link do conteudo quando for externo
							threadLink = el.getElementsByClass("thumbnail invisible-when-pinned may-blank outbound")
									.attr("href").toString();
							
							
							logger.info("Subreddit: "+sub); 
							logger.info("Votes:"+votes);
							logger.info("Name: "+threadName); 
							logger.info("Comments Link:"+commentsLink);
							logger.info("External link: " +threadLink+"\n");
							 
							
							response+="\nSubreddit: "+sub+"\n";
							response+="Votes:"+votes+"\n";
							response+="Name: "+threadName+"\n";
							response+="Link:"+commentsLink+"\n";
							response+="External link: " +threadLink+"\n\n\n";
							responseList.add(response);
							
						}

					}

				}
			}
			
			return responseList;
		} catch (IOException e) {
			logger.error("Erro ao ler página. Erro: "+e);
			return null;
		}
		
	}
}
