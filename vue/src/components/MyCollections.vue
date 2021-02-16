<template>
  <div id="main">
    <div class="info">
      <h1>Your Personal Collections</h1>
      <h3>Click a Collection to see details!</h3>
    </div>
    <div class="collections-list">
      <div class="collections" v-for="collection in returnedCollections" :key="collection.collectionId" v-on:click="viewCollectionDetails(collection.collectionId)">
          {{ collection.title }}
      </div>
      <div class="add-collection">
        <router-link :to="{ name: 'add-collection' }"><button>Create a New Collection</button></router-link> 
      </div>
    </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService';

export default {
    name: 'my-collections',
    data() {
        return {
            returnedCollections: [],
            currentUserId: this.$store.state.user.id
        }
    },
    methods: {
      viewCollectionDetails(collectionId) {
        this.$router.push(`/collections/${collectionId}`);
      }
    },
    created() {
        collectionService.getCollectionsByUser(this.currentUserId)
        .then((response) => {
            this.returnedCollections = response.data;
        })
        .catch((err) => {
            console.error(err + ' uh oh no comics here');
        })
    }
}
</script>

<style>
#main {
  margin: auto;
  font-family: 'Bangers', cursive;
}
.info {
  margin-top: 5em;
  display: flex;
  flex-direction: column;

}
h1 {
  margin: 7px auto;
  border-radius: .5em;;
  padding: 15px;
}
h3 {
  margin: auto;
  font-size: 1.5em;
  background-color: #ee5454;
  display: inline-block;
  border-radius: .5em;
  padding: 15px;
}
.collections-list {
  margin-top: 5em;
  display: flex;
  flex-direction: column;
  align-content: center;
}
.collections {
    margin: 10px auto;
    font-size: 2em;
    padding: 15px;
    text-align: center;
    background-color: #ee5454;
    width: 25vw;
    border-radius: 1.5em;
    box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
    border: 2px solid rgb(0, 0, 0);
    cursor: pointer;

}
.add-collection {
  margin-top: 10px;
  text-align: center;
}

@import url('https://fonts.googleapis.com/css2?family=Bangers&display=swap');
</style>