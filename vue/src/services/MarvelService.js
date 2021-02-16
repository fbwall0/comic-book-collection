import axios from 'axios';

const http = axios.create({
  baseURL: "https://gateway.marvel.com/v1/public/"
});

export default {
    hash: 'ts=teelevator&apikey=70b4068dd49a2f9d32a6d6743bef6fce&hash=c94d31205708f6fe0a1742c3c1f84744',

    searchComic(title, issueNumber) {
        return http.get(`comics?titleStartsWith=${title}&issueNumber=${issueNumber}&${this.hash}`);
    },

    searchComicsByName(title, offset) {
      return http.get(`comics?titleStartsWith=${title}&offset=${offset}&${this.hash}`);
    },
    
    async searchComicById(comicId) {
        return await http.get(`comics/${comicId}?${this.hash}`);
    },

    searchCreator(creatorName) {
      return http.get(`creators?nameStartsWith=${creatorName}&${this.hash}`);
    },

    searchCharacter(characterName) {
      return http.get(`characters?nameStartsWith=${characterName}&${this.hash}`);
    },

    getComicsByCreator(creatorId, offset) {
      return http.get(`creators/${creatorId}/comics?orderBy=title&offset=${offset}&${this.hash}`);
    },

    getComicsByCharacter(characterId, offset) {
      return http.get(`characters/${characterId}/comics?orderBy=title&offset=${offset}&${this.hash}`);
    },

    getCreatorById(creatorId) {
      return http.get(`creators/${creatorId}?${this.hash}`);
    },

    getCharacterById(characterId) {
      return http.get(`characters/${characterId}?${this.hash}`);
    }


}