<template>
<div id="background" >
  <div id="app">
    <div id="nav">
      <div class="left-nav">
        <router-link id="homeButton" v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link id="collectionButton" v-bind:to="{ name: 'collections' }">&nbsp;|&nbsp;Public Collections&nbsp;|&nbsp;</router-link>
        <router-link id="myCollectionButton" v-bind:to="{ name: 'my-collections' }" v-if="$store.state.token != ''"> My Collections&nbsp;|&nbsp;</router-link>
        <router-link id="loginButton" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Login</router-link>
        <router-link id="logoutButton" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      </div>
      <div class="right-nav">
        <div v-if="$store.state.token != ''"><span class="user-name">Current Logged In User:</span> {{ $store.state.user.username }} </div>
      </div>
    </div>
    <div id="router-view">
      <router-view />
    </div>
    <div id="footer">
      <a href="http://marvel.com\">Data provided by Marvel. © 2021 MARVEL</a>
    </div>
  </div>
</div>

</template>

<script>
import Vue from 'vue';
import KonamiCode from 'vue-konami-code';
Vue.use(KonamiCode, {callback: function(){
    alert('Breaking the wall, breaking the wall.....  Your friendly neighborhood mercenary, the merc with the mouth Deadpool!')
  }})
export default {
 
}
</script>


<style>

html {
   height:100vh;
  background: url('assets/background.jpg') no-repeat center center fixed;
  background-color: #ee54549f;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  display: flex;
  justify-content: center;
}
body {
  display: flex;
  flex-grow: 1;
}
#background {
  flex-grow: 1;
}
h1 {
  background-color: #ffc310e7;
   font-family: 'Bangers', cursive; font-weight: normal; text-decoration: none; font-style: normal; font-variant: normal; text-transform: none;
}
#app {
  display: flex;
  flex-direction: column;
  align-content: center;
  min-height: 100%;
}
#router-view {
  align-self: center;
  width: 79%;
  flex-grow: 2;
  min-height: 100%;
}
#footer {
  display: flex;
  flex-shrink: 1;
  margin-top: auto;
  color: #ee5454;
  font-size: 18px; 
  line-height: 18px; 
  padding: 6px; 
  border-radius: 10px; 
  font-family: 'Bangers', cursive;
  font-weight: normal; 
  text-decoration: none; 
  font-style: normal; 
  font-variant: normal; 
  text-transform: none; 
  background-image: linear-gradient(to right,	#f6dc35ec 0%, #ffc310ea 100%); 
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
  border: 2px solid rgb(0, 0, 0); 
}
#nav {
  color: #ee5454;
  font-size: 18px; 
  line-height: 18px; 
  padding: 6px; 
  border-radius: 10px; 
  font-family: 'Bangers', cursive;
  font-weight: normal; 
  text-decoration: none; 
  font-style: normal; 
  font-variant: normal; 
  text-transform: none; 
  background-image: linear-gradient(to right,	#f6dc35ee 0%, #ffc310ec 100%); 
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
  border: 2px solid rgb(0, 0, 0); 
  display: flex;
  justify-content: space-between;
}
.right-nav {
  color: black;
}
.user-name {
  text-decoration: underline;
}


</style>