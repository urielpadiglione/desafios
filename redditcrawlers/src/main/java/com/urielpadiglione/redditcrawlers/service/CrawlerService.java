package com.urielpadiglione.redditcrawlers.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Service
public class CrawlerService {
	Logger logger = LoggerFactory.getLogger(CrawlerService.class);
	private final WebClient WEB_CLIENT = new WebClient(BrowserVersion.CHROME);

	public List<String> getData(String tags, int upvotes) {
		List<String> responseList = new ArrayList<String>();
		
		String[] subreddits = tags.split("-");
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
						
						if(votes>=upvotes) {
							//pega link dos comentários
							commentsLink = el.select("[class*=\"bylink comments may-blank\"]").attr("href").toString();
							
							//pega nome da thread
							threadName = el.select("[class*=\"title may-blank\"]").text();
							
							//pega o link do conteudo quando for externo
							threadLink = el.getElementsByClass("thumbnail invisible-when-pinned may-blank outbound")
									.attr("href").toString();
							
							/*
							 * logger.info("Subreddit: "+sub); logger.info("Votes:"+votes);
							 * logger.info("Name: "+threadName); logger.info("Comments Link:"+commentsLink);
							 * logger.info("External link: " +threadLink+"\n");
							 */
							
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
	
	public void login(){
		String user="idontspeakportuguese";
		String pwd="110500";
		
        String loginURL = "https://www.reddit.com/login";       
        try {
            //Create an HtmlPage and get the login page.
            HtmlPage loginPage = WEB_CLIENT.getPage(loginURL);

            //Create an HtmlForm by locating the form that pertains to logging in.
            //"//form[@id='login-form']" means "Hey, look for a <form> tag with the
            //id attribute 'login-form'" Sound familiar?
            //<form id="login-form" method="post" ...
            HtmlForm loginForm = loginPage.getFirstByXPath("//form[@id='login-form']");

            //This is where we modify the form. The getInputByName method looks
            //for an <input> tag with some name attribute. For example, user or passwd.
            //If we take a look at the form, it all makes sense.
            //<input value="" name="user" id="user_login" ...
            //After we locate the input tag, we set the value to what belongs.
            //So we're saying, "Find the <input> tags with the names "user" and "passwd"
            //and throw in our username and password in the text fields.
            loginForm.getInputByName("user").setValueAttribute(user);
            loginForm.getInputByName("passwd").setValueAttribute(pwd);

            //<button type="submit" class="c-btn c-btn-primary c-pull-right" ...
            //Okay, you may have noticed the button has no name. What the line
            //below does is locate all of the <button>s in the login form and
            //clicks the first and only one. (.get(0)) This is something that
            //you can do if you come across inputs without names, ids, etc.
            loginForm.getElementsByTagName("button").get(0).click();

        } catch (FailingHttpStatusCodeException e) {
        	logger.error(e.toString());
        } catch (MalformedURLException e) {
        	logger.error(e.toString());
        } catch (IOException e) {
        	logger.error(e.toString());
        }
    }

    public String get(String URL){
        try {
            //All this method does is return the HTML response for some URL.
            //We'll call this after we log in!
            return WEB_CLIENT.getPage(URL).getWebResponse().getContentAsString();
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
