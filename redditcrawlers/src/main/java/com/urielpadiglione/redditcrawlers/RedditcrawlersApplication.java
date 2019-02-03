package com.urielpadiglione.redditcrawlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.urielpadiglione.redditcrawlers.component.CrawlerComponent;
import com.urielpadiglione.redditcrawlers.service.CrawlerService;

@SpringBootApplication
public class RedditcrawlersApplication/* implements CommandLineRunner */{

	/*
	 * @Autowired CrawlerComponent crawlerComponent;
	 
	 * @Autowired CrawlerService crawlerService;
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(RedditcrawlersApplication.class, args);
	}

	/*
	 * @Override public void run(String... args) throws Exception {
	 * crawlerService.getData(crawlerComponent.getTags());
	 * 
	 * }
	 */

}

