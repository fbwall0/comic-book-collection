<template>
  <div id="main">
    <div class="add-container">
      <div class="collection-input-section">
        <form v-on:submit.prevent="addCollection">
          Title: <input id="collection-title" v-model="collection.title" />
            <div>
          <label for="private-collection">Will this Collection be Private?</label>
          <input type="checkbox" id="private-collection" name="private-collection" v-model="collection.hidden">
            </div>
          <button id="collection-add-btn">Add Collection</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import collectionService from "@/services/CollectionService";


export default {

  data() {
    return {
      collection: {
        id: 0,
        title: '',
        comics: [],
        userId: this.$store.state.user.id,
        hidden: false,
      }
    };
  },
  methods: {
    addCollection() {
        collectionService.createCollection(this.collection)
        .then((response) => {
          console.log(response);
          this.$store.commit('ADD_COLLECTION', this.collection);
          this.$router.push('/mycollections');
        })
        .catch((error) => {
          console.log(error + ' problem creating collection');
        })
        
    }
  },
};
</script>

<style>
.add-container {
  display: flex;
  justify-content: center;
}

.collection-input-section {
  font-size: 1.2vw;
   margin: 20px;
  padding: 20px;
  width: 20%;
  background-color: #ee5454ec;
  border-radius: 10px;
  text-align: center;
}
</style>