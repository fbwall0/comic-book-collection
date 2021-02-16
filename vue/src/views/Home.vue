<template>
  <div class="home">
    <h1>Welcome to the Comics Collection Home Page</h1>
    <div class="image-container">
      <img class="title-image" src="../assets/splash-screen.jpg" />
    </div>
    <p v-if="$store.state.token == ''">Here you can sign in, register, or just view public comic collections!</p>
    <br>
    <div class="links">
    <router-link :to="{ name: 'login' }"><button id="login" v-if="$store.state.token == ''">   Login   </button></router-link>
    <router-link :to="{ name: 'collections' }"><button id="see-collections">   See Public Collections   </button></router-link>
    <router-link :to="{ name: 'my-collections' }"><button id="my-collections" v-if="$store.state.token != ''">   See Your Collections   </button></router-link>
    <router-link :to="{ name: 'register' }"><button id="register" v-if="$store.state.token == ''">  Register   </button></router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>


<style scoped>
.home {
  text-align: center;
  display: flex;
  flex-direction: column;
}
h1 {
  display: inline-block;
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
    border: 2px solid rgb(28, 110, 164);
  margin-bottom: 1.5em;
}
p {
  display: inline-block;
  font-size: 1.2vw;
  background-color:#4c94f6;
  width: 20vw;
  margin: auto;
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
  border: 2px solid rgb(28, 110, 164);
}
.image-container {
  margin-bottom: 2em;
}
.title-image {
  margin: auto;
 border-radius: 3px;
  text-align: center;
  display: inline-block;
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
  border: 2px solid rgb(28, 110, 164);
}
.links {
  font-size: 1rem;
}
button {
  width: 10vw;
  font-size: 1.1vw;
}
</style>