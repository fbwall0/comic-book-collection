<template>
  <div>
      <form id="creatorSearch" v-on:submit.prevent="searchForCreator">
          <label for="creators">Creator:</label>
          <input type="text" id="creators"
          v-model="creatorToFind.name" />
          <br>
          <button @click="hasSearched = !hasSearched">Search</button>
          <router-link :to="{ name: 'add-comic' }"><button>Cancel</button></router-link> 
      </form>
      <div class="loading" v-if="isLoading">
      <img src="../assets/Deadpool_Loading.gif" />
    </div>
      <div class="creatorSearchResults" v-if="hasSearched" v-show="!isLoading">
          <div class="creatorDetails" v-for="creator in this.returnedCreators" v-bind:key="creator.id">
              <router-link :to="{ name: 'creator-comics', params: { id: collectionId, creatorId: creator.id }}">
                <!-- <img class="creatorImage" :src="creator.thumbnail.path + '.' + creator.thumbnail.extension"/> -->
                <h4 class="creatorName">{{ creator.fullName }}</h4>
              </router-link>
          </div>
      </div>
  </div>
</template>

<script>
import marvelService from '@/services/MarvelService.js';
export default {
    name: "search-creators",
    data(){
        return {
            collectionId: 0,
            hasSearched: false,
            creatorToFind: {
                name: '',

            },
            returnedCreators: [],
            returnedCreatorImgPath: '',
            returnedCreatorImgExt: 'jpg',

            resultsArr: {},
            resultsImg: {},
            isLoading: false
        }
    },
    methods: {
        searchForCreator() {
            this.isLoading = true;
            this.$store.commit('SET_CURRENT_OFFSET', 0);
            marvelService.searchCreator(this.creatorToFind.name)
            .then((response) => {
                this.collectionId = this.$route.params.id;
                this.returnedCreators = response.data.data.results;
                this.resultsArr = this.returnedCreator.data.results[0];
                this.resultsImg = this.resultsArr.images[0];
                this.returnedCreatorImgPath = this.resultsImg.path;
                this.returnedCreatorImgExt = this.resultsImg.extension;
                var creatorImage = document.getElementsByClassName('creatorImage');
                creatorImage.src = this.returnedcreatorImgPath + '.' + this.returnedcreatorImgExt;
                
                 // 5 second timer for loading screen
                
            })
            setTimeout(() => {
                    this.isLoading = false;
                }, 2000)
        }
    }
}
</script>

<style>
#creatorSearch {
     color: #000000;; font-size: 22px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive; font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none; background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc510 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; border: 2px solid rgb(0, 0, 0); display: inline-block;
}
.creatorName {
    color: #000000;; font-size: 22px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive; font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none; background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc310e5 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; border: 2px solid rgb(0, 0, 0); display: inline-block;
    }

</style>