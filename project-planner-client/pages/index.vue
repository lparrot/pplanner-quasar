<template>
  <v-container>
    <v-btn @click="login">
      Login
    </v-btn>
    <v-btn @click="logout">
      Logout
    </v-btn>
  </v-container>
</template>

<script>
import { Component, Vue } from 'vue-property-decorator';

@Component()
export default class HomePage extends Vue {
  async mounted() {
    await this.$keycloak.init();
    try {
    } catch (e) {
      //
    }
  }

  async login() {
    const res = await this.$keycloak.login();
    console.log(res);
  }

  async logout() {
    const res = await this.$keycloak.logout();
    console.log(res);
  }
};
</script>
