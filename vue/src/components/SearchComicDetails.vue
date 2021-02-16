<template>
  <div class="search-comic-details">
        <div class="search-details-results" v-if="hasSearched">
            <br />
            <div class="comic-image" >
                <img id="cover-image" :src="this.imgPath + '.' + this.imgExt"/>
            </div>
            <br />
            <div>Comic Title: {{ this.comicTitle }}</div>
            <br />
            <div>Issue Number: {{ this.issueNum }}</div>
            <br />
            <div v-if="this.characters.length > 0">Characters: <p v-for="character in this.characters" v-bind:key="character.resourceURI">- {{ character.name }}</p>
            </div>
            <br />
            <div v-if="this.returnedComic.data.results[0].description != null">Description: {{ this.returnedComic.data.results[0].description }}</div>
            <button class="add-to-collection" v-if="!this.inCollection && (collectionSize < 100 || $store.state.user.premium)" v-on:click="EditCollection">Add to Collection</button>
            <button class="remove-from-collection" v-if="this.inCollection" v-on:click="EditCollection">Remove from Collection</button>
        </div>
        <div class="return-to-collection-btn">
            <router-link :to="{name: 'search-comic', params: { id: $route.params.id, creatorId: $route.params.creatorId }}">
                <button id="return-to-list" >Return to comic list</button>
            </router-link>
        </div>

  </div>
</template>

<script>
import marvelService from '@/services/MarvelService.js';
import collectionService from "@/services/CollectionService";

export default {
    name: "comic-details",
    data() {
        return {
            hasSearched: false,
            comicToFind: 0,
            returnedComic: [],
            comicId: this.$route.params.comicId, 
            collectionId: this.$route.params.id,
            comicTitle: '',
            comicImg: '',
            issueNum: '',
            characters: '',
            imgPath: 'http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available',
            imgExt: 'jpg',
            inCollection: false,
            collectionSize: 0
        }
    },
    methods: {
        EditCollection() {
            this.isInCollection();
            if (this.inCollection) {
                collectionService.removeComicFromCollection(this.collectionId, this.comicId)
            }
            else {
                collectionService.addComicToCollection(this.collectionId, this.comicId)
            }
            this.isInCollection();
            window.location.reload();
        },
        isInCollection() {
            collectionService.getComicsInCollection(this.collectionId).then((response) => {
                let collectionArr = response.data;
                this.collectionSize = collectionArr.length;
                let filteredCollection = collectionArr.filter((comic) => {
                    return comic == this.comicId;
                });
                this.inCollection = filteredCollection.length > 0;
            });
        }
    },
    created() {
        marvelService.searchComicById(this.$route.params.comicId)
        .then((response) => {
            this.hasSearched = true;
            this.returnedComic = response.data;
            const resultsArr = this.returnedComic.data.results[0];
            this.comicTitle = resultsArr.title;
            this.issueNum = resultsArr.issueNumber;
            this.characters = resultsArr.characters.items;
            if (resultsArr.images.length > 0){
                let imgArr = resultsArr.images[0]; 
                this.imgPath = imgArr.path;
                this.imgext = imgArr.extension;
            }
            this.isInCollection();
            
        })
        
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
}

</style>