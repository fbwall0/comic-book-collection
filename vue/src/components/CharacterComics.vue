<template>
  <div id="char-main">
      <h1>List of Comics with Character: {{this.character}}</h1>
      <div class="loading" v-if="isLoading">
        <img src="../assets/Deadpool_Loading.gif" />
      </div>
      <div class="char-comic-info" v-if="this.loaded" v-show="!isLoading">
          <div class="buttons">
            <button id="last-page" v-show="!firstPage" v-on:click="getLastPage">View Previous Entries</button>
            <router-link :to="{name: 'collection-comics', params: { id: $route.params.id }}">
                <button id="back-to-collections">Return To Collection</button>
            </router-link>
            <button id="next-page" v-show="!lastPage" v-on:click="getNextPage">View Next Entries</button>
          </div>
          <div class="char-comics" v-for="comic in comics" v-bind:key="comic.id">
              <span class="title">{{ comic.title }}</span>
              <br />
              <img class="comic-image" v-if="comic.images.length > 0" :src="comic.images[0].path + '.' + comic.images[0].extension" />
              <br />
              <router-link :to="{name: 'character-comic-details', params: { id: $route.params.id, characterId: $route.params.characterId , comicId: comic.id}} ">
                <button id="character-comic-details-btn" >View Comic Details</button>
              </router-link>
              <button class="add-to-collection" v-if="!collectionComics.find(collComic => {return collComic == comic.id}) && (collectionComics.length < 100 || $store.state.user.premium)" v-on:click="AddToCollection(comic.id)">Add to Collection</button>
              <button class="remove-from-collection" v-if="collectionComics.find(collComic => {return collComic == comic.id})" v-on:click="RemoveFromCollection(comic.id)">Remove from Collection</button>
          </div>
        <div class="buttons">
            <button id="last-page" v-show="!firstPage" v-on:click="getLastPage">View Previous Entries</button>
            <router-link :to="{name: 'collection-comics', params: { id: $route.params.id }}">
                <button id="back-to-collections">Return To Collection</button>
            </router-link>
            <button id="next-page" v-show="!lastPage" v-on:click="getNextPage">View Next Entries</button>
          </div>
      </div>
  </div>
</template>

<script>
import marvelService from '@/services/MarvelService';
import collectionService from '@/services/CollectionService';
export default {
    name: "character-comics",
    data() {
        return {
            character: '',
            loaded: false,
            comics: [],
            offset: 0,
            limit: 20,
            total: 1000,
            isLastPage: false,
            isFirstPage: true,
            collectionComics: [],
            collectionId: 0,
            isLoading: false
        }
    },
    methods: {
        getCharComics() {
            this.isLoading = true;
            this.loaded= false;
            if (this.$store.state.currentOffset < 0) {
                this.$store.commit('SET_CURRENT_OFFSET', (Number) ((Number) (this.$store.state.currentOffset) + this.limit));
            } else if (this.$store.state.currentOffset > this.total) {
                this.$store.commit('SET_CURRENT_OFFSET', this.$store.state.currentOffset - this.limit);
            }
            marvelService.getComicsByCharacter(this.$route.params.characterId, this.$store.state.currentOffset)
            .then((response) => {
                this.comics = response.data.data.results;
                collectionService.getComicsInCollection(this.$route.params.id).then((response) => {
                    this.collectionComics = response.data;
                    this.loaded = true;
                });
                setTimeout(() => {
                    this.isLoading = false;
                }, 5000)
        })
        .catch((err) => {
            console.error(err + ' no character comics here');
        })
        },
        getNextPage() {
            this.$store.commit('SET_CURRENT_OFFSET', (Number) ((Number) (this.$store.state.currentOffset) + this.limit));
            this.getCharComics();
        },

        getLastPage() {
            this.$store.commit('SET_CURRENT_OFFSET', this.$store.state.currentOffset - this.limit);
            this.getCharComics();
        },

        AddToCollection(comicId) {
            collectionService.addComicToCollection(this.collectionId, comicId);
            this.getCharComics();
        },

        RemoveFromCollection(comicId) {
            collectionService.removeComicFromCollection(this.collectionId, comicId);
            this.getCharComics();
        }
    },
    computed: {
        lastPage() {
            return this.$store.state.currentOffset >= this.total - this.limit;
        },
        firstPage() {
            return this.$store.state.currentOffset < this.limit;
        }

    },
    created() {
        marvelService.getComicsByCharacter(this.$route.params.characterId, this.$store.state.currentOffset)
        .then((response) => {
            this.comics = response.data.data.results;
            this.total = response.data.data.total;
            this.limit = response.data.data.limit;
            marvelService.getCharacterById(this.$route.params.characterId)
            .then((response) => {
                this.character = response.data.data.results[0].name;
                this.collectionId = this.$route.params.id;
                collectionService.getComicsInCollection(this.$route.params.id).then((response) => {
                    this.collectionComics = response.data;
                    this.loaded = true;
                });
            });
        })
        .catch((err) => {
            console.error(err + ' no character comics here');
        })
    }
}
</script>

<style>
.char-comics {
     margin: 10px auto;
  background-color: #ee5454ee;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  width: 75vw;
  display: flex;
  flex-direction: column;
  align-content: center;
  justify-content: center;
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
  border: 2px solid rgb(0, 0, 0);
   font-family: 'Bangers', cursive; font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
}
#character-comic-details-btn {
    display: flex;
     color: #000000;; font-size: 18px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive;
      font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
       background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc310ec 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
        border: 2px solid rgb(0, 0, 0); display: inline-block;
}
.add-to-collection {
     color: #000000;; font-size: 18px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive;
      font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
       background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc310ec 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
        border: 2px solid rgb(0, 0, 0); display: inline-block; margin: auto;
}
.remove-from-collection {
     color: #000000;; font-size: 18px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive;
      font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
       background-image: linear-gradient(to right,	#ee5454ec 0%rgba(255, 195, 16, 0.932)10 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
        border: 2px solid rgb(0, 0, 0); display: inline-block; margin: auto;
}
#last-page {
     color: #000000;; font-size: 18px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive;
      font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
       background-image: linear-gradient(to right,	#ee5454f1 0%rgba(255, 195, 16, 0.925)10 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
        border: 2px solid rgb(0, 0, 0);
}
#next-page {
     color: #000000;; font-size: 18px; line-height: 18px; padding: 6px; border-radius: 10px; font-family: 'Bangers', cursive;
      font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
       background-image: linear-gradient(to right,	#ee5454f1 0%, #ffc310ee 100%); box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
        border: 2px solid rgb(0, 0, 0); display: inline-block;
}
.buttons {
    display: flex;
    align-content: space-between;
    justify-content: space-between;
}
</style>