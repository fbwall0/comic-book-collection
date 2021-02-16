import axios from 'axios';

const http = axios.create({
  baseURL: "https://gateway.marvel.com/v1/public/"
});

// backupt api hash :  ts=prankster&apikey=06a27e3408b18518997788e672bb31bd&hash=43417035701626dd872425850ec06dae

export default {
    hash: 'ts=thesoer&apikey=e3f13dcf4ff77039ddccc9f959b733d9&hash=3fefa0949426eff3dc9adad144d10133',

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