package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Comic;
import com.techelevator.model.ComicCharacter;
import com.techelevator.model.ComicCollection;

@Component
public class ComicSqlDAO implements ComicDAO {

	private JdbcTemplate jdbcTemplate;

    public ComicSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Override
	public void createCollection(ComicCollection newCollection) {
		String sqlStmt = "INSERT INTO collections (name, user_id, hidden) VALUES (?, ?, ?)";
		jdbcTemplate.update(sqlStmt, newCollection.getTitle(), newCollection.getUserId(), newCollection.isHidden());
	}

	@Override
	public void createComic(Comic newComic) {
		if (newComic.getUniverse() != null) {
			String sqlStmt = "INSERT INTO comics (comic_id, title, issue_num, genre, universe, author) VALUES (?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sqlStmt, newComic.getComicId(), newComic.getTitle(), newComic.getIssueNum(), newComic.getGenre(), newComic.getUniverse(), newComic.getAuthor());			
		}
		else {
			String sqlStmt = "INSERT INTO comics (comic_id, title, issue_num, genre, author) VALUES (?, ?, ?, ?, ?)";
			jdbcTemplate.update(sqlStmt, newComic.getComicId(), newComic.getTitle(), newComic.getIssueNum(), newComic.getGenre(), newComic.getAuthor());			
		}
	}


	@Override
	public void createCharacter(ComicCharacter newCharacter) {
		String sqlStmt = "INSERT INTO characters (character_id, name) VALUES (?, ?)";
		jdbcTemplate.update(sqlStmt, newCharacter.getCharacterId(), newCharacter.getName());
	}

	@Override
	public void addCharacterToComic(long characterId, long comicId) {
		String sqlStmt = "INSERT INTO character_comic (character_id, comic_id) VALUES (?, ?)";
		jdbcTemplate.update(sqlStmt, characterId, comicId);
	}

	@Override
	public void addComicToCollection(long comicId, long collectionId) {
		String sqlStmt = "INSERT INTO collection_comic (collection_id, comic_id) VALUES (?, ?)";
		jdbcTemplate.update(sqlStmt, collectionId, comicId);
	}

	@Override
	public List<ComicCollection> getVisibleCollections() {
		String sqlStmt = "SELECT * FROM collections WHERE hidden = false";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt);
		List<ComicCollection> output = new ArrayList<>();;
		while (results.next()) {
			ComicCollection newCollection = mapRowToCollection(results);
			output.add(newCollection);
		}
		return output;
	}


	@Override
	public List<ComicCollection> getCollectionsByUser(long userId) {
		String sqlStmt = "SELECT * FROM collections WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, userId);
		List<ComicCollection> output = new ArrayList<>();;
		while (results.next()) {
			ComicCollection newCollection = mapRowToCollection(results);
			output.add(newCollection);
		}
		return output;
	}

	@Override
	public ComicCollection getCollectionById(long collectionId) {
		String sqlStmt = "SELECT * FROM collections WHERE collection_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, collectionId);
		ComicCollection output = null;
		if (results.next()) {
			output = mapRowToCollection(results);
		}
		return output;
	}
	
	@Override
	public long getUserIdFromCollection(long collectionId) {
		String sqlStmt = "SELECT user_id FROM collections WHERE collection_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, collectionId);
		long output = 0;
		if (results.next()) {
			output = results.getLong("user_id");
		}
		return output;
	}

	@Override
	public List<ComicCollection> getCollectionsByCharacter(long characterId) {
		String sqlStmt = "SELECT * FROM collections WHERE collection_id IN (SELECT collection_id FROM collection_comic WHERE comic_id IN (SELECT comic_id FROM character_comic WHERE character_id = ?))";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, characterId);
		List<ComicCollection> output = new ArrayList<>();;
		while (results.next()) {
			ComicCollection newCollection = mapRowToCollection(results);
			output.add(newCollection);
		}
		return output;
	}
	
	@Override
	public List<ComicCharacter> getCharactersByComic(long comicId) {
		String sqlStmt = "SELECT * FROM characters WHERE character_id IN (SELECT character_id FROM character_comic WHERE comic_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, comicId);
		List<ComicCharacter> output = new ArrayList<>();
		while (results.next()) {
			ComicCharacter newCharacter = mapRowToCharacter(results);
			output.add(newCharacter);
		}
		return output;
	}

	@Override
	public boolean getPremiumStatus(long userId) {
		String sqlStmt = "SELECT is_premium FROM premium WHERE user_id = ?";
		return jdbcTemplate.queryForRowSet(sqlStmt).getBoolean("is_premium");
	}

	@Override
	public List<Comic> getComicsByCollection(long collectionId) {
		String sqlStmt = "SELECT * FROM comics WHERE comic_id IN (SELECT comic_id FROM collection_comic WHERE collection_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, collectionId);
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}
	
	@Override
	public List<Long> getAllComicsInCollection(long collectionId) {
		String sqlStmt = "SELECT comic_id FROM collection_comic WHERE collection_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, collectionId);
		List<Long> output = new ArrayList<>();
		while (results.next()) {
			output.add(results.getLong("comic_id"));
		}
		return output;
	}

	@Override
	public Comic getComicByComicId(long comicId) {
		String sqlStmt = "SELECT * FROM comics WHERE comic_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, comicId);
		Comic output = null;
		if (results.next()) {
			output = mapRowToComic(results);
		}
		return output;
	}



	@Override
	public List<Comic> getComicsByCharacterId(long characterId) {
		String sqlStmt = "SELECT * FROM comics WHERE comic_id IN (SELECT comic_id FROM character_comic WHERE character_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, characterId);
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}


	@Override
	public List<Comic> getComicsByCharacterName(String name) {
		String sqlStmt = "SELECT * FROM comics WHERE comic_id IN (SELECT comic_id FROM character_comic WHERE character_id IN (SELECT character_id FROM character WHERE name ILIKE ?))";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, '%' + name + '%');
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}

	@Override
	public List<Comic> getComicsByGenre(String genre) {
		String sqlStmt = "SELECT * FROM comics WHERE genre ILIKE ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, '%' + genre + '%');
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}

	@Override
	public List<Comic> getComicsByTitle(String title) {
		String sqlStmt = "SELECT * FROM comics WHERE title ILIKE ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, '%' + title + '%');
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}

	@Override
	public List<Comic> getComicsByAuthor(String author) {
		String sqlStmt = "SELECT * FROM comics WHERE author ILIKE ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, '%' + author + '%');
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}

	@Override
	public List<Comic> getComicsByUniverse(String universe) {
		String sqlStmt = "SELECT * FROM comics WHERE universe ILIKE ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStmt, '%' + universe + '%');
		List<Comic> output = new ArrayList<>();
		while (results.next()) {
			Comic newComic = mapRowToComic(results);
			output.add(newComic);
		}
		return output;
	}

	@Override
	public void removeComicFromCollection(long collectionId, long comicId) {
		String sqlStmt = "DELETE FROM collection_comic WHERE collection_id = ? AND comic_id = ?";
		jdbcTemplate.update(sqlStmt, collectionId, comicId);
	}

	@Override
	public void updateComicDetails(Comic updatedComic) {
		String sqlStmt = "UPDATE comics SET title = ?, issue_num = ?, genre = ?, universe = ?, author = ? WHERE comic_id = ?";
		jdbcTemplate.update(sqlStmt, updatedComic.getTitle(), updatedComic.getIssueNum(), updatedComic.getGenre(), updatedComic.getUniverse(), updatedComic.getAuthor(), updatedComic.getComicId());
	}

	private ComicCollection mapRowToCollection(SqlRowSet results) {
		ComicCollection output = new ComicCollection();
		output.setCollectionId(results.getLong("collection_id"));
		output.setTitle(results.getString("name"));
		output.setUserId(results.getLong("user_id"));
		output.setHidden(results.getBoolean("hidden"));
		List<Comic> comicList = getComicsByCollection(results.getLong("collection_id"));
		output.setComics(comicList);
		return output;
	}
	
	private Comic mapRowToComic(SqlRowSet results) {
		Comic output = new Comic();
		output.setComicId(results.getLong("comic_id"));
		output.setGenre(results.getString("genre"));
		output.setIssueNum(results.getInt("issue_num"));
		output.setTitle(results.getString("title"));
		output.setUniverse(results.getString("universe"));
		output.setAuthor(results.getString("author"));
		List<ComicCharacter> characterList = getCharactersByComic(results.getLong("comic_id"));
		output.setCharacters(characterList);
		return output;
	}
	
	private ComicCharacter mapRowToCharacter(SqlRowSet results) {
		ComicCharacter output = new ComicCharacter();
		output.setCharacterId(results.getLong("character_id"));
		output.setName(results.getString("name"));
		return output;
	}
}
