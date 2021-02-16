import axios from 'axios';

const http = axios.create({
    baseURL: 'http://localhost:8080/api'
});

export default {

    getCollections() {
        return http.get('/collections');
    },

    getComicsByCollectionId(collectionId) {
        return http.get(`/collections/${collectionId}`);
    },

    getComicsInCollection(collectionId) {
        return http.get(`/collection/${collectionId}/all`);
    },

    async createCollection(collection) {
        return await http.post('/collections/add', collection);
    },

    createNewComic(newComic) {
        return http.post('/comics', newComic); // Makes comic entry in database
    },

    createComicInCollection(collectionId, comicId) {
        return http.post(`/collection/${collectionId}/add/${comicId}`); // make the collection comic entry
    },

    getCollectionsByUser(userId) {
        return http.get(`/user/${userId}`);
    },

    getCharactersByComic(comicId) {
        return http.get(`/comic/${comicId}/characters`);
    },

    getComicsByCharacterName(characterName) {
        return http.get(`/comics/characters/${characterName}`);
    },

    getComicsByCharacterId(characterId) {
        return http.get(`/comics/character/${characterId}`);
    },

    getComicsByGenre(genre) {
        return http.get(`/comics/genre/${genre}`);
    },

    getComicsByTitle(title) {
        return http.get(`/comics/title/${title}`);
    },

    getComicsByAuthor(author) {
        return http.get(`/comics/author/${author}`);
    },

    getComicsByUniverse(universe) {
        return http.get(`/comics/universe/${universe}`);
    },

    getCollectionById(collectionId) {
        return http.get(`/collection/${collectionId}`);
    },

    getComicsById(comicId) {
        return http.get(`/comic/${comicId}`);
    },

    createNewCollection(newCollection) {
        return http.post('/collections', newCollection);
    },


    createNewCharacter(newCharacter) {
        return http.post('/characters', newCharacter);
    },

    addCharacterToComic(characterId, comicId) {
        return http.post(`/comic/${comicId}/character/${characterId}`);
    },

    async addComicToCollection(collectionId, comicId) {
        return await http.post(`/collection/${collectionId}/add/${comicId}`);
    },

    removeComicFromCollection(collectionId, comicId) {
        return http.delete(`/collection/${collectionId}/remove/${comicId}`);
    },

    getUserIdByCollection(collectionId) {
        return http.get(`/users/collections/${collectionId}`)
    }



}