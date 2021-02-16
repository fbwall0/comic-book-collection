package com.techelevator.model;

public class ComicCharacter {
	
	Long characterId;
	String name;
	
	public ComicCharacter() {
		
	}
	
	public ComicCharacter(Long characterId, String name) {
		this.characterId = characterId;
		this.name = name;
	}



	public Long getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
