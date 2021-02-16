<template>
  <div id="main">
    <div class="info">
      <h1>Available Collections</h1>
      <h3>Click to see details!</h3>
    </div>
    <div class="collections-list">
      <div class="collections" v-for="collection in dbCollections" :key="collection.collectionId" v-on:click="viewCollectionDetails(collection.collectionId)">
          {{ collection.title }}
      </div>
    </div>
  </div>
</template>

<script>
import collectionService from "@/services/CollectionService";
// this.$store.state.collections
export default {
    name: 'display-collection',
    data() {
        return {
          dbCollections: [],
          testAPI: [],
          currentUserId: this.$store.state.user.id
        }
    },
    methods: {
      viewCollectionDetails(collectionId) {
        this.$router.push(`/collections/${collectionId}`);
      }
    },
    created() {
        collectionService.getCollections()
        .then((response) => {
          this.dbCollections = response.data;
        })
        .catch((err) => {
          console.error(err + ' uh oh no collections here');
        });
      }
}


</script>

<style>
#main {
  margin: auto;
  font-family: 'Bangers', cursive;
}

@import url('https://fonts.googleapis.com/css2?family=Bangers&display=swap');
</style>