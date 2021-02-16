package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Comic;
import com.techelevator.model.ComicCharacter;
import com.techelevator.model.ComicCollection;

public interface ComicDAO {
	
	void createCollection(ComicCollection newCollection);
	
	void createComic(Comic newComic);
	
	void createCharacter(ComicCharacter newCharacter);
	
	void addCharacterToComic(long characterId, long comicId);
	
	void addComicToCollection(long comicId, long collectionId);
	
	List<ComicCollection> getVisibleCollections();
	
	List<ComicCollection> getCollectionsByUser(long userId);
	
	ComicCollection getCollectionById(long collectionId);
	
	List<ComicCollection> getCollectionsByCharacter(long characterId);
	
	List<ComicCharacter> getCharactersByComic(long comicId);
	
	boolean getPremiumStatus(long userId);
	
	List<Comic> getComicsByCollection(long collectionId);
	
	Comic getComicByComicId(long comicId);
	
	List<Comic> getComicsByCharacterId(long characterId);
	
	List<Comic> getComicsByCharacterName(String name);
	
	List<Comic> getComicsByGenre(String genre);
	
	List<Comic> getComicsByTitle(String title);
	
	List<Comic> getComicsByAuthor(String author);
	
	List<Comic> getComicsByUniverse(String universe);
	
	void removeComicFromCollection(long collectionId, long comicId);
	
	void updateComicDetails(Comic updatedComic);
	
	List<Long> getAllComicsInCollection(long collectionId);
	
	long getUserIdFromCollection(long collectionId);

}
