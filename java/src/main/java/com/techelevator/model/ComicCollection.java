package com.techelevator.model;

import java.util.List;

public class ComicCollection {

	long collectionId;
	String title;
	boolean hidden;
	long userId;
	
	List<Comic> comics;
	
	public ComicCollection() {
		
	}

	public ComicCollection(long collectionId, String title, boolean hidden, long userId, List<Comic> comics) {
		this.collectionId = collectionId;
		this.title = title;
		this.hidden = hidden;
		this.userId = userId;
		this.comics = comics;
	}

	public long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public List<Comic> getComics() {
		return comics;
	}

	public void setComics(List<Comic> comics) {
		this.comics = comics;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void addComic(Comic comicToAdd) {
		this.comics.add(comicToAdd);
	}
	
}
