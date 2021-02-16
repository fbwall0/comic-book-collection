import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token');
const currentUser = JSON.parse(localStorage.getItem('user'));
const offset = localStorage.getItem('offset');
const currentSearchTerm = localStorage.getItem('searchTerm');

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    
    //Testing Purposes
    comicIdNumber: 0,
    activeCollection: 0,
    currentUser: 1,
    
    collections: [
      {
        id: 1,
        title: "Rare Comics",
        userOwner: 'admin',
        comics: [
          {
            title: 'Ultimate Superman',
            issue: 2,
            author: 'Stan Lee',
            genre: 'Action',
            universe: 'DC'
          },
          {
            title: 'Doomsday Witches',
            issue: 54,
            author: 'Don Lamarr',
            genre: 'Horror',
            universe: 'Unaffiliated'
          }
        ]
      },
      {
        id: 2,
        title: "My Hulk Comics",
        userOwner: "admin",
        comics: [
          {
            title: 'Hulk 3000',
            issue: 4,
            author: 'King Crimson',
            genre: 'SciFi',
            universe: 'Marvel'
          }
        ]
      },
    ],


    searchTerm: currentSearchTerm || '',
    token: currentToken || '',
    user: currentUser || {id: 0, premium: false},
    currentOffset: offset || 0,
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {id: 0, premium: false};
      axios.defaults.headers.common = {};
    },

    SET_CURRENT_OFFSET(state, offset) {
      state.currentOffset = offset;
      localStorage.setItem('offset', offset);
    },
    SET_CURRENT_SEARCH_TERM(state, searchTerm) {
      state.searchTerm = searchTerm;
      localStorage.setItem('searchTerm', searchTerm);
    },
    // temp for testing
    ADD_COLLECTION(state, collection) {
      state.collections.unshift(collection);
    },
    ADD_COMIC(state, comic) {
      const currentCollection = this.state.collections.find(collection =>
        collection.id == comic.collectionId
      );
      currentCollection.comics.unshift(comic);
    },
    SET_ACTIVE_COLLECTION(state, collectionId) {
      console.log(collectionId);
      this.state.activeCollection = collectionId;
      
    }
  }
})
