package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Comic {
	
	long comicId;
	String title;
	int issueNum;
	String genre;
	String universe = null;
	String author;
	
	List<ComicCharacter> characters = new ArrayList<>();;
	
	public Comic() {
		
	};

	public Comic(long comicId, String title, int issueNum, String genre, String universe,
			List<ComicCharacter> characters) {
		this.comicId = comicId;
		this.title = title;
		this.issueNum = issueNum;
		this.genre = genre;
		this.universe = universe;
		this.characters = characters;
	}
	
	public Comic(long comicId, String title, int issueNum, String genre, String universe, String author) {
		this.comicId = comicId;
		this.title = title;
		this.issueNum = issueNum;
		this.genre = genre;
		this.universe = universe;
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getComicId() {
		return comicId;
	}

	public void setComicId(long comicId) {
		this.comicId = comicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(int issueNum) {
		this.issueNum = issueNum;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getUniverse() {
		return universe;
	}

	public void setUniverse(String universe) {
		this.universe = universe;
	}

	public List<ComicCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(List<ComicCharacter> characters) {
		this.characters = characters;
	}
	
	

}
