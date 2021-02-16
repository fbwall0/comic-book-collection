<template>
  <div id="register" class="text-center">
    <div class="form-container">
      <form class="form-register" @submit.prevent="register">
        <h1 class="register-h1">Create New Account</h1>
        <div class="alert alert-danger" role="alert" v-if="registrationErrors">
          {{ registrationErrorMsg }}
        </div>
        <label for="username" class="sr-only">Username</label>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <label for="password" class="sr-only">Password</label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
        <label for="first-name" class="sr-only">First Name</label>
        <input
          type="text"
          id="first-name"
          class="form-control"
          placeholder="First Name"
          v-model="user.firstName"
          required
          autofocus
        />
        <label for="last-name" class="sr-only">Last Name</label>
        <input
          type="text"
          id="last-name"
          class="form-control"
          placeholder="Last Name"
          v-model="user.lastName"
          required
          autofocus
        />
        <label for="email-address" class="sr-only">Email</label>
        <input
          type="email"
          id="email-address"
          class="form-control"
          placeholder="Email"
          v-model="user.email"
          required
          autofocus
        />
        <label for="premium" class="sr-only">Premium Account?</label>
        <input
          type="checkbox"
          id="premium"
          class="form-control"
          v-model="user.premium"
          autofocus
        />
        <router-link :to="{ name: 'login' }">Have an account?</router-link>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
          Create Account
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
        firstName: '',
        lastName: '',
        email: '',
        premium: false,
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style>
.form-container {
  margin-top: 5vh;
}
.register-h1 {
  font-family: 'Bangers', cursive; 
}
.form-register {
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #ee5454;
  padding: 10px;
  border-radius: 1em;
  box-shadow: rgb(0, 0, 0) 5px 5px 15px 5px; 
  border: 2px solid rgb(0, 0, 0);
}

</style>
