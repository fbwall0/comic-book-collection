import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Collections from '../views/Collections.vue'
import AddCollection from '../views/AddCollection.vue'
import Comics from '../views/Comics.vue'
import AddComic from '../views/AddComic.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import SearchComic from '../views/SearchComic.vue'
import SearchCreator from '../views/SearchCreator.vue'
import SearchCharacter from '../views/SearchCharacter.vue'
import CreatorComics from '../views/CreatorComics.vue'
import CharacterComics from '../views/CharacterComics.vue'
import ComicDetails from '../views/ComicDetails.vue'
import CreatorComicDetails from '../views/CreatorComicDetails.vue'
import CharacterComicDetails from '../views/CharacterComicDetails.vue'
import MyCollections from '../views/MyCollections.vue'
import SearchComicDetails from '../views/SearchComicDetails.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections',
      name: 'collections',
      component: Collections,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/mycollections',
      name: 'my-collections',
      component: MyCollections,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/add',
      name: 'add-collection',
      component: AddCollection,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id',
      name: 'collection-comics',
      component: Comics,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/add',
      name: 'add-comic',
      component: AddComic,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/creator/:creatorId/comic/:comicId',
      name: 'creator-comic-details',
      component: CreatorComicDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/comic/:comicId/details',
      name: 'search-comic-details',
      component: SearchComicDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/character/:characterId/comic/:comicId',
      name: 'character-comic-details',
      component: CharacterComicDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/creator/:creatorId',
      name: 'creator-comics',
      component: CreatorComics,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/character/:characterId',
      name: 'character-comics',
      component: CharacterComics,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/search',
      name: 'search-comic',
      component: SearchComic,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/searchCreator',
      name: 'search-creator',
      component: SearchCreator,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/searchCharacter',
      name: 'search-character',
      component: SearchCharacter,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id/comic/:comicId',
      name: 'comic-details',
      component: ComicDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
