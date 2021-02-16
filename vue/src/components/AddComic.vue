<template>
  <div id="main">
    <div id="input-section">
      <div class="add-comics-by-search">
          <button @click="clearSearch" >Search for a Comic to Add</button>
      </div>
      <div class="add-comics-by-search">
          <router-link :to="{ name: 'search-creator', params: { id: this.$route.params.id } }"><button>Search for a Comic to Add By Creator</button></router-link> 
      </div>
      <div class="add-comics-by-search">
          <router-link :to="{ name: 'search-character', params: { id: this.$route.params.id } }"><button>Search for a Comic to Add By Character</button></router-link> 
      </div>
        <div class="add-comics-cancel-button">
          <router-link :to="{ name: 'collection-comics', params: { id: this.$route.params.id } }"><button>Cancel</button></router-link> 
      </div>
    </div>
  </div>
</template>

<script>

import collectionService from '@/services/CollectionService.js';
import comicVineService from '@/services/ComicVineService.js';

export default {
  data() {
    return {
      comic: {
        comicId: Math.floor(Math.random() * 10000) + 1,
        title: '',
        issueNum: Number,
        author: '',
        genre: '',
        universe: 'Unaffiliated',
        collectionId: this.$route.params.id
      }
    };
  },
  methods: {
    addComic() {
        collectionService.createNewComic(this.comic); // Works
        collectionService.createComicInCollection(this.comic.collectionId, this.comic);
        this.$store.commit('SET_ACTIVE_COLLECTION', this.$route.params.id)
        // this.$store.commit('ADD_COMIC', this.comic);
        this.$router.push(`/collections/${this.$route.params.id}`);
    },
    clearSearch() {
      this.$store.commit('SET_CURRENT_SEARCH_TERM', '');
      this.$router.push(`/collections/${this.$route.params.id}/search`);
    }
  },
  created() {
    comicVineService.list().then((response) => {
        this.testAPI = response.data;
      })
      .catch((err) => {
        console.error(err);
      })
  }
};
</script>

<style>
#input-section {
  display: flex;
  flex-direction: column;
  margin: auto;
  padding: 20px;
  width: 20%;
  text-align: center;
  align-items: center;
}
input {
  margin: 5px;
}
button {
  margin: 5px;
}
</style>