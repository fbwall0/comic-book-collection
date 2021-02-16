<template>
  <div>
      <form id="titleSearch">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="comicToFind" />
        <br>
        <!-- <label for="issue">Issue Number:</label>
        <input type="number" id="issue" v-model="comicToFind.issueNum" />
        <br> -->
        <button v-on:click="SearchForComicByName">Search</button>
        <router-link :to="{ name: 'add-comic' }"><button>Cancel</button></router-link> 
      </form>
      <div class="loading" v-if="isLoading">
      <img src="../assets/Deadpool_Loading.gif" />
    </div>
    
        <div class="searchresults" v-if="hasSearched">
        <div class="buttons">
            <button id="last-page" v-show="!firstPage" v-on:click="getLastPage">View Previous Entries</button>
            <router-link :to="{name: 'collection-comics', params: { id: $route.params.id }}">
                <button id="back-to-collections">Return To Collection</button>
            </router-link>
            <button id="next-page" v-show="!lastPage" v-on:click="getNextPage">View Next Entries</button>
          </div>
            <div class="comics" v-for="comic in searchResults" v-bind:key="comic.id">
                <div class="comic-info">
                    <span class="title">{{ comic.title }}</span>
                    <br />
                    <img class="comic-image" v-bind:src="comic.img" />
                    <br />
                    <router-link :to="{name: 'search-comic-details', params: { id: $route.params.id, comicId: comic.id}} ">
                        <button id="creator-comic-details-btn" >View Comic Details</button>
                    </router-link>
                    <button class="add-to-collection" v-if="!collectionComics.find(collComic => 
                        {return collComic === comic.id}) && (collectionComics.length < 100 || $store.state.user.premium)" v-on:click="addComicToCollection(comic.id)">Add to Collection</button>
                    <button class="remove-from-collection" v-if="collectionComics.find(collComic => 
                        {return collComic === comic.id})" v-on:click="removeFromCollection(comic.id)">Remove from Collection</button>
                </div>
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
//v-on:submit.prevent="didSearch"
import marvelService from '@/services/MarvelService.js';
import collectionService from '@/services/CollectionService.js';

export default {
    name: "search-comic",
    data() {
        return {
            searchResults: [],
            comicIdToAdd: 0,
            thisCollectionId: this.$route.params.id,
            hasSearched: false,
            comicToFind: this.$store.state.searchTerm,
            // previous and next search page stuff
            limit: 20,
            total: Number,

            collectionComics: [],
            returnedComicImgPath: '',
            returnedComicImgExt: 'jpg',
            returnedComicId: 0,

            resultsArr: {},
            resultsImg: {},
            isLoading: false,
            isFirstPage: true,
            isLastPage: false,
        }
    },
    methods: {
        SearchForComicByName() {
            this.hasSearched = false;
            this.$store.commit('SET_CURRENT_SEARCH_TERM', this.comicToFind);
            this.$store.commit('SET_CURRENT_OFFSET', 0);
            this.searchForComic();
        },
        searchForComic() {
            this.searchResults = [];
            let newOffset = Number;
            if (this.$store.state.currentOffset < 0) {
                newOffset = (Number) ((Number) (this.$store.state.currentOffset) + this.limit);
                this.$store.commit('SET_CURRENT_OFFSET', newOffset);
            } else if (this.$store.state.currentOffset > this.total) {
                newOffset = (Number) (this.$store.state.currentOffset - this.limit);
                this.$store.commit('SET_CURRENT_OFFSET', newOffset);
            }
            if (this.comicToFind.length > 0) {
                this.isLoading = true;
                marvelService.searchComicsByName(this.comicToFind, this.$store.state.currentOffset)
                .then((response) => {
                    console.log(response);

                    this.returnedComic = response.data;
                    this.resultsArr = this.returnedComic.data.results;
                    this.total = this.returnedComic.data.total;
                    for (let r of this.resultsArr) {
                        let coverImg = 'http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg';
                        if (r.images.length > 0) {
                            coverImg = r.images[0].path + '.' + r.images[0].extension;
                        }
                        let aComic = {
                            img : coverImg,
                            id : r.id,
                            title : r.title,
                        }
                        this.searchResults.push(aComic);
                    }
                    collectionService.getComicsInCollection(this.$route.params.id).then((response) => {
                        this.collectionComics = response.data;
                    });
                // this.resultsImg = this.resultsArr.images[0];
                // this.returnedComicImgPath = this.resultsImg.path;
                // this.returnedComicImgExt = this.resultsImg.extension;
                // var image = document.getElementById('comicimage');
                // image.src = this.returnedComicImgPath + '.' + this.returnedComicImgExt;
                // this.returnedComicId = this.resultsArr.id;
                    this.isLoading = false;
                    this.hasSearched = true;
                })
            }
            
        },
        addComicToCollection(comicId) {
            collectionService.createComicInCollection(this.$route.params.id, comicId);
            this.searchForComic();
        },

        removeFromCollection(comicId) {
            collectionService.removeComicFromCollection(this.$route.params.id, comicId);
            this.searchForComic();
        },

        getNextPage() {
            console.log(this.$store.state.currentOffset);
            let newOffset = Number;
            newOffset = (Number) ((Number) (this.$store.state.currentOffset) + this.limit);
            console.log(newOffset);
            this.$store.commit('SET_CURRENT_OFFSET', newOffset);
            this.searchForComic();
        },

        getLastPage() {
            let newOffset = Number;
            newOffset = (Number) (this.$store.state.currentOffset - this.limit);
            this.$store.commit('SET_CURRENT_OFFSET', newOffset);
            this.searchForComic();
        },
    },
    created() {
        console.log(this.$store.state.currentSearchTerm);
        if (this.$store.state.currentSearchTerm != undefined) {
            this.comicToFind = this.$store.state.currentSearchTerm;
        }
        if (this.$store.state.searchTerm != undefined) {
            this.comicToFind = this.$store.state.searchTerm;
        }
        collectionService.getComicsInCollection(this.$route.params.id)
        .then((response) => {
            this.collectionComics = response.data;
            if (this.$store.state.searchTerm != undefined && this.$store.state.searchTerm.length > 0) {
                this.searchForComic();
            }
            if (this.$store.state.currentSearchTerm != undefined && this.$store.state.currentSearchTerm.length > 0) {
                this.searchForComic();
            }
        })
    },
    computed: {
        lastPage() {
            return this.$store.state.currentOffset >= this.total - this.limit;
        },
        firstPage() {
            return this.$store.state.currentOffset < this.limit;
        }

    },
}
</script>

<style>
.comics {
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

.comic-info {
    display: flex;
    flex-direction: column;
}
.comic-image {
    display: flex;
    align-self: center;
}

#titleSearch {
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
     box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
     border: 2px solid rgb(0, 0, 0); 
     display: inline-block;
}
</style>