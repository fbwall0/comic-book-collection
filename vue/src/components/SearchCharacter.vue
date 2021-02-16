<template>
  <div>
      <form id="char-search" v-on:submit.prevent="searchForCharacter">
          <label for="characters">Character Name:</label>
          <input type="text" id="characters"
          v-model="characterToFind.name" />
          <br>
          <button id="char-button" @click="hasSearched = !hasSearched">Search</button>
          <router-link :to="{ name: 'add-comic' }"><button>Cancel</button></router-link> 
      </form>
      <div class="loading" v-if="isLoading">
      <img src="../assets/Deadpool_Loading.gif" />
    </div>
      <div class="charSearchResults" v-if="hasSearched" v-show="!isLoading">
            <div class="characterDetails" v-for="character in this.returnedCharacters" v-bind:key="character.id">
                <router-link :to="{ name: 'character-comics', params: { id: collectionId, characterId: character.id }}">
                    <img class="characterImage" :src="character.thumbnail.path + '.' + character.thumbnail.extension"/>
                    <h4 class="characterName">{{ character.name }}</h4>
                </router-link>
            </div>
      </div>
  </div>
</template>

<script>
import marvelService from '@/services/MarvelService.js';
export default {
    name: "search-characters",
    data(){
        return {
            collectionId: 0,
            hasSearched: false,
            characterToFind: {
                name: '',

            },
            returnedCharacters: [],
            returnedCharacterImgPath: '',
            returnedCharacterImgExt: 'jpg',

            resultsArr: {},
            resultsImg: {},
            isLoading: false
        }
    },
    methods: {
        searchForCharacter() {
            this.isLoading = true;
            this.$store.commit('SET_CURRENT_OFFSET', 0);
            marvelService.searchCharacter(this.characterToFind.name,)
            .then((response) => {
                this.collectionId = this.$route.params.id;
                this.returnedCharacters = response.data.data.results;
            })

            setTimeout(() => {
                this.isLoading = false;
            }, 5000)
        }
    }
}
</script>

<style>

#char-search {
     color: #000000; 
     font-size: 22px; 
     line-height: 18px; 
     padding: 6px; 
     border-radius: 10px; 
     font-family: 'Bangers', cursive; 
     font-weight: normal; 
     text-decoration: none; 
     font-style: normal; 
     font-variant: normal; 
     text-transform: none; 
     background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc510 100%); 
     box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; border: 2px solid rgb(0, 0, 0); 
     display: inline-block;
}

.characterDetails {
    color: #000000;; font-size: 18px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive;
     font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
      background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc310ee 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
       border: 2px solid rgb(0, 0, 0); display: inline-block;
}



</style>