<template>
  <div class="collection-comic-details">
      <div class="loading" v-if="isLoading">
      <img src="../assets/Marvel_loading.gif" />
    </div>
    <div class="search-comic-details" >
            <div class="search-details-results" v-show="!isLoading">
                <div class="comic-image">
                    <img class="the-image" id="cover-image" v-bind:src="comicImg" alt='http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg'/>
                    <br />
                </div>
                <div class="comic-info">
                    <br />
                    <div>Comic Title: {{ this.comicTitle }}</div>
                    <br />
                    <div>Issue Number: {{ this.issueNum }}</div>
                    <br />
                    <div v-if="this.characters.length > 0">Characters: </div>
                    <div class="character-list" v-for="character in this.characters" :key="character.id">
                        <div>- {{ character.name }}</div>
                        </div>
                        <br v-if="this.characters.length > 0">
                        <div v-if="this.comicDescription != null">Description:</div>
                        <div>{{ this.comicDescription }}</div>
                    </div>
                </div>
            </div>
            <div class="return-to-collection-btn">
                    <router-link :to="{ name: 'collection-comics', params: { id: this.$route.params.id }}" >
                        <button>Back to Collection</button>
                    </router-link>
            </div>
    </div>
</template>

<script>
import marvelService from '@/services/MarvelService.js';

export default {
    name: "comic-details",
    data() {
        return {
            hasSearched: false,
            comicToFind: 0,
            returnedComic: [],
            comicId: this.$route.params.comicId, 
            comicTitle: '',
            comicImg: '',
            issueNum: '',
            characters: [],
            comicDescription: '',
            isLoading: false,
        }
    },
    methods: {
        searchForComic() {
            
        }
    },
    created() {
      this.isLoading = true;
            marvelService.searchComicById(this.comicId)
            .then((response) => {
                this.returnedComic = response.data;
                const resultsArr = this.returnedComic.data.results[0];
                this.comicTitle = resultsArr.title;
                this.issueNum = resultsArr.issueNumber;
                this.characters = resultsArr.characters.items;
                this.comicDescription = resultsArr.description;
                let imgArr = resultsArr.images[0]; 
                // let theImage = document.getElementById('cover-image');
                this.comicImg = imgArr.path + '.' + imgArr.extension;
            })
            setTimeout(() => {
                    this.isLoading = false;
                }, 2000)
      .catch((err) => {
        console.error(err + ' uh oh no collections here');
      });
    }

}
</script>

<style>
.search-comic-details {
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-content: center;
}
.return-to-collection-btn {
    align-self: center;
    align-content: center;
}
.collection-comic-details {
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-content: center;
}
</style>