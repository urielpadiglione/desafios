package com.urielpadiglione.redditcrawlers.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.urielpadiglione.redditcrawlers.dto.CrawlerResponseDTO;
import com.urielpadiglione.redditcrawlers.dto.SubredditsDTO;
import com.urielpadiglione.redditcrawlers.service.CrawlerService;

@RestController
public class CrawlerRestController {
	Logger logger = LoggerFactory.getLogger(CrawlerRestController.class);

	@Autowired
	CrawlerService crawlerService;
	
	//HTTP POST
	@RequestMapping(value= "/redditcrawler/api/post",  method = RequestMethod.POST)
	public ResponseEntity<CrawlerResponseDTO> post(@RequestBody SubredditsDTO subredditsDTO){
		CrawlerResponseDTO crawlerResponseDTO = new CrawlerResponseDTO();

		crawlerResponseDTO.setResponse(crawlerService.getData(subredditsDTO.getSubreddits(), subredditsDTO.getUpvotes()));
		return new ResponseEntity<CrawlerResponseDTO>(crawlerResponseDTO,HttpStatus.ACCEPTED);
	}
	
	
	//HTTP GET
	@RequestMapping(value= "/redditcrawler/api/get/{tags}",  method = RequestMethod.GET)
	public ResponseEntity<CrawlerResponseDTO> get(@PathVariable(value="tags") String tags, @PathVariable(value="upvotes") int upvotes){
		CrawlerResponseDTO crawlerResponseDTO = new CrawlerResponseDTO();
		crawlerResponseDTO.setResponse(crawlerService.getData(tags, upvotes));
		return new ResponseEntity<CrawlerResponseDTO>(crawlerResponseDTO,HttpStatus.ACCEPTED);
	}
	
	
}
