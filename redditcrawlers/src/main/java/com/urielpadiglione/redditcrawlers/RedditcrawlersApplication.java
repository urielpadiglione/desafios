package com.urielpadiglione.redditcrawlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.urielpadiglione.redditcrawlers.service.CrawlerService;


@SpringBootApplication
public class RedditcrawlersApplication{

	@Autowired
	static
	CrawlerService crawlerService;
	
	public static void main(String[] args) {
		SpringApplication.run(RedditcrawlersApplication.class, args);
	}


}

