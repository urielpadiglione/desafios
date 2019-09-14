package com.urielpadiglione.redditcrawlers.dto;

public class SubredditsDTO {
	String subreddits;
	int upvotes;
	
	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public String getSubreddits() {
		return subreddits;
	}

	public void setSubreddits(String subreddits) {
		this.subreddits = subreddits;
	}
	
	
}
