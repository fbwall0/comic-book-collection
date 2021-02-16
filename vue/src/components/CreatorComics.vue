<template>
  <div id="main">
      <h1>List of Comics by the Creator: {{ this.creator }}</h1>
      <div class="loading" v-if="isLoading">
      <img src="../assets/Marvel_loading.gif" />
    </div>
      <div class="search-results" v-if="this.loaded">
          <div class="buttons">
            <button id="last-page" v-show="!firstPage" v-on:click="getLastPage">View Previous Entries</button>
            <router-link :to="{name: 'collection-comics', params: { id: $route.params.id }}">
                <button id="back-to-collections">Return To Collection</button>
            </router-link>
            <button id="next-page" v-show="!lastPage" v-on:click="getNextPage">View Next Entries</button>
          </div>
          <div class="comics" v-for="comic in comics" v-bind:key="comic.id">
              <div class="comic-info">
                    <span class="title">{{ comic.title }}</span>
                    <br />
                    <img class="comic-image" v-if="comic.images.length > 0" :src="comic.images[0].path + '.' + comic.images[0].extension" />
                    <br />
                    <router-link :to="{name: 'creator-comic-details', params: { id: $route.params.id, creatorId: $route.params.creatorId , comicId: comic.id}} ">
                        <button id="creator-comic-details-btn" >View Comic Details</button>
                    </router-link>
                    <button class="add-to-collection" v-if="!collectionComics.find(collComic => {return collComic == comic.id}) && (collectionComics.length < 100 || $store.state.user.premium)" v-on:click="AddToCollection(comic.id)">Add to Collection</button>
                    <button class="remove-from-collection" v-if="collectionComics.find(collComic => {return collComic == comic.id})" v-on:click="RemoveFromCollection(comic.id)">Remove from Collection</button>
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
import marvelService from "@/services/MarvelService";
import collectionService from "@/services/CollectionService";

export default {
    name: "creator-comics",
    data() {
        return {
            creator: '',
            loaded: false,
            comics: [],
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
        getComics() {
            this.isLoading = true;
            this.loaded = false;
            if (this.$store.state.currentOffset < 0) {
                this.$store.commit('SET_CURRENT_OFFSET', (Number) ((Number) (this.$store.state.currentOffset) + this.limit));
            } else if (this.$store.state.currentOffset > this.total) {
                this.$store.commit('SET_CURRENT_OFFSET', this.$store.state.currentOffset - this.limit);
            }
            marvelService.getComicsByCreator(this.$route.params.creatorId, this.$store.state.currentOffset)
            .then((response) => {
                this.comics = response.data.data.results;
                collectionService.getComicsInCollection(this.$route.params.id).then((response) => {
                    this.collectionComics = response.data;
                    this.loaded = true;
                    this.isLoading = false;
                });
            })
            .catch((err) => {
                console.error(err + ' uh oh no comics here');
            });
        },

        getNextPage() {
            this.$store.commit('SET_CURRENT_OFFSET', (Number) ((Number) (this.$store.state.currentOffset) + this.limit));
            this.getComics();
        },

        getLastPage() {
            this.$store.commit('SET_CURRENT_OFFSET', this.$store.state.currentOffset - this.limit);
            this.getComics();
        },

        AddToCollection(comicId) {
            collectionService.addComicToCollection(this.collectionId, comicId);
            this.getComics();
        },

        RemoveFromCollection(comicId) {
            collectionService.removeComicFromCollection(this.collectionId, comicId);
            this.getComics();
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
        marvelService.getComicsByCreator(this.$route.params.creatorId, this.$store.state.currentOffset)
        .then((response) => {
            this.comics = response.data.data.results;
            this.total = response.data.data.total;
            this.limit = response.data.data.limit;
            marvelService.getCreatorById(this.$route.params.creatorId)
            .then((response) => {
                this.creator = response.data.data.results[0].fullName;
                this.collectionId = this.$route.params.id;
                collectionService.getComicsInCollection(this.$route.params.id).then((response) => {
                    this.collectionComics = response.data;
                    this.loaded = true;
                });
            });
        })
        .catch((err) => {
            console.error(err + ' uh oh no comics here');
        })
    }
}
</script>

<style>

</style>