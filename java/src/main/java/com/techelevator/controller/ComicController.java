package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.ComicDAO;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicCharacter;
import com.techelevator.model.ComicCollection;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ComicController {

	@Autowired
	ComicDAO theComics;
	
	@GetMapping(path= {"/collections", "/"})
	public List<ComicCollection> getVisibleCollections(){
		
		List<ComicCollection> allComics = theComics.getVisibleCollections();
		
		return allComics;
	}
	
	@GetMapping("/user/{userId}")   
	public List<ComicCollection> getCollectionsByUser(@PathVariable long userId){
	
		List<ComicCollection> userCollections = theComics.getCollectionsByUser(userId);
	     
		return userCollections;
	}
	
	@GetMapping("/collections/character/{characterId}")   
	public List<ComicCollection> getCollectionsByCharacter(@PathVariable long characterId){
	
		List<ComicCollection> characterCollections = theComics.getCollectionsByCharacter(characterId);
	     
		return characterCollections;
	}
	
	@GetMapping("/comic/{comicId}/characters")   
	public List<ComicCharacter> getCharactersByComic(@PathVariable long comicId){
	
		List<ComicCharacter> charactersByComic = theComics.getCharactersByComic(comicId);
	     
		return charactersByComic;
	}
	
	@GetMapping("/comics/characters/{name}")   
	public List<Comic> getComicsByCharacterName(@PathVariable String name){
	
		List<Comic> comicsByCharName = theComics.getComicsByCharacterName(name);
	     
		return comicsByCharName;
	}
	
	@GetMapping("/comics/character/{characterId}")   
	public List<Comic> getComicsByCharacterId(@PathVariable long characterId){
	
		List<Comic> comicsByCharId = theComics.getComicsByCharacterId(characterId);
		
		return comicsByCharId;
	}
	
	@GetMapping("/comics/genre/{genre}")   
	public List<Comic> getComicsByGenre(@PathVariable String genre){
	
		List<Comic> comicsByGenre = theComics.getComicsByGenre(genre);
		
		return comicsByGenre;
	}
	
	@GetMapping("/comics/title/{title}")   
	public List<Comic> getComicsByTitle(@PathVariable String title){
	
		List<Comic> comicsByTitle = theComics.getComicsByTitle(title);
		
		return comicsByTitle;
	}
	
	@GetMapping("/comics/author/{author}")   
	public List<Comic> getComicsByAuthor(@PathVariable String author){
	
		List<Comic> comicsByAuthor = theComics.getComicsByAuthor(author);
		
		return comicsByAuthor;
	}
	
	@GetMapping("/comics/universe/{universe}")   
	public List<Comic> getComicsByUniverse(@PathVariable String universe){
	
		List<Comic> comicsByUniverse = theComics.getComicsByUniverse(universe);
		
		return comicsByUniverse;
	}
	
	@GetMapping("/collection/{collectionId}")
	public ComicCollection getCollectionById(@PathVariable long collectionId) {
                         
		ComicCollection aCollection = theComics.getCollectionById(collectionId);      
		return aCollection;                               
	}
	
	@GetMapping("/collection/{collectionId}/all")
	public List<Long> getAllComicsInCollection(@PathVariable long collectionId) {
		List<Long> aList = theComics.getAllComicsInCollection(collectionId);
		return aList;
	}
	
	@GetMapping("/comic/{comicId}")
	public Comic getComicsByComicId(@PathVariable long comicId) {
                         
		Comic comicByID = theComics.getComicByComicId(comicId);      
		return comicByID;                               
	}
	
	@GetMapping("/users/collections/{collectionId}")
	public long getUserIdFromCollection(@PathVariable long collectionId) {
		long userId = theComics.getUserIdFromCollection(collectionId);
		return userId;
	}
	
	@PostMapping("/collections/add")
	public void createCollection(@Valid @RequestBody ComicCollection newCollection) {
        theComics.createCollection(newCollection);
    }
	
	@PostMapping("/comics")
	public void createComic(@Valid @RequestBody Comic newComic) {
        theComics.createComic(newComic);
    }
	
	@PostMapping("/characters")
	public void createCharacter(@Valid @RequestBody ComicCharacter newCharacter) {
        theComics.createCharacter(newCharacter);
    }
	
	@PostMapping("/comic/{comicId}/character/{characterId}") 
	public void addCharacterToComic(@PathVariable long characterId, @PathVariable long comicId){
		theComics.addCharacterToComic(characterId, comicId);
	}
	
	@PostMapping("/collection/{collectionId}/add/{comicId}") 
	public void addComicToCollection(@PathVariable long comicId, @PathVariable long collectionId){
		theComics.addComicToCollection(comicId, collectionId);
	}
	
	@DeleteMapping("/collection/{collectionId}/remove/{comicId}")
	public void removeComicFromCollection(@PathVariable long collectionId, @PathVariable long comicId) {
		theComics.removeComicFromCollection(collectionId, comicId);
	}
	
}
	
	
