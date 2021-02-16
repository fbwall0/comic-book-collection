<template>
  <div id="main">
      <h1>List of Comics in the Collection</h1>
    <div class="links">
      <div class="add-comic">
          <router-link :to="{ name: 'add-comic' }" v-if="currentUserId == collectionOwnerId"><button>Add Comic to this Collection</button></router-link> 
        </div>
      <div class="view-public-collections">
          <router-link :to="{ name: 'collections' }"><button>View Public Collections</button></router-link> 
      </div>
      <div class="view-private-collections">
          <router-link :to="{ name: 'my-collections' }" v-if="$store.state.token != ''"><button>View Your Personal Collections</button></router-link> 
      </div>
    </div>
    <div class="comic-info" v-if="this.loaded">
      <div class="individual-comic-block">
        <div class="comics"  v-for="comic in collectionToPrint" :key="comic.id">
          <div class="comic-data">
            <span class="title">{{ comic.title }}</span>
            <br />
            <div class="cover-image">
            <img v-bind:src="comic.theImage" />
          </div>
            <br />
            <router-link :to="{ name: 'comic-details', params: { id: collectionId, comicId: comic.id }}" > 
              <button>See Details</button>
            </router-link> 
            <button class="remove-from-collection" v-if="currentUserId == collectionOwnerId" v-on:click="removeFromCollection(comic.id)">Remove from Collection</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import collectionService from "@/services/CollectionService";
import marvelService from "@/services/MarvelService";

export default {
    name: "display-comics",
    data() {
      return {
        collectionId: this.$route.params.id,
        currentUserId: this.$store.state.user.id,
        collectionOwnerId: 0,
        loaded: true,
        dbCollection: [],
        filteredCollection: [],
        filteredCollectionId: 0,


        // testCollection: [
        //   {
        //     comicId: 9357
        //   },
        //   {
        //     comicId: 30259
        //   },
        //   {
        //     comicId: 20138
        //   }
        // ],
        collectionToPrint: []
      }
    },
    methods: {
      fill() {
        this.filteredCollectionId = this.$store.state.activeCollection;
        // this.filteredCollection = this.$store.state.collections.find(collection => collection.id == this.filteredCollectionId);
        // this.loaded = true;
      },
      // removeComic(collectionId, comicId) {
      //   collectionService.removeComicFromCollection(collectionId, comicId);
      // }
      removeFromCollection(comicId) {
            collectionService.removeComicFromCollection(this.$route.params.id, comicId)
            .then((response) => {
              console.log(response);
              window.location.reload();
            })
            .catch((error) => {
              console.log(error + 'Uh Oh');
            })
        },
    },
    created() {
      collectionService.getUserIdByCollection(this.$route.params.id)
      .then((response) => {
        this.collectionOwnerId = response.data;
      })
      collectionService.getComicsInCollection(this.$route.params.id)
      .then((response) => {
        this.dbCollection = response.data;
         for (let e of this.dbCollection) {
          const newComic = {
            id: 0,
            title: '',
            issueNum: 0,
            theImage: ''
          }
          marvelService.searchComicById(e)
          .then((response) => {
            let theComic = response.data;
            let resultsArr = theComic.data.results[0];
            newComic.id = resultsArr.id;
            newComic.title = resultsArr.title;
            newComic.issueNum = resultsArr.issueNumber;
            let imgArr = resultsArr.images[0];
            newComic.theImage = imgArr.path + '.' + imgArr.extension;
            this.collectionToPrint.push(newComic);
          })
        }
        this.loaded = true;
        this.$router.push(`/collections/${this.$route.params.id}`).catch(err => {err});
      })
      .catch((err) => {
        console.error(err + ' uh oh no comics here');
      });
    },
    mounted() {
      this.$store.commit('SET_ACTIVE_COLLECTION', this.$route.params.id)
      this.loaded = false;
      this.fill();
    }
    
};
</script>

<style>
#main {
  margin: auto;
}
.individual-comic-block{
  padding: 10px;
}
.comics {
  margin: 10px auto;
  background-color: #ee5454ec;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  width: 33vw;
  display: inline-block;
    box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
    border: 2px solid rgb(0, 0, 0);
}
.comic-data {
  display: flex;
  margin: auto;
  flex-direction: column;
  justify-content: center;
}
.links {
  display: flex;
  justify-content: center;
}
button {
    height: 7vh;
  border: 0;
  border-radius: 10px;
  background-image: linear-gradient(to right,	#ee5454ee 0%, #ffc310ec 100%);
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px;
  border: 2px solid rgb(0, 0, 0); display: inline-block;
  font-family: 'Bangers', cursive;
  font-size: 1.25rem;
  line-height: 1.2;
  white-space: nowrap;
  text-decoration: none;
  padding: 0.25rem 0.5rem;
  margin-top: 1rem;
  cursor: pointer;
}
.title {
  font-size: 1.85em;
}
.issue {
  font-size: 1.3em;
}
.author {
  font-size: 1.3em;
}
.genre {
  font-size: 1.3em;
}
.universe {
  font-size: 1.3em;
}
.links {
  text-align: center;
}
img {
  width: 200px;
}
</style>