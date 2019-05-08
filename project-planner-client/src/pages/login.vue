<template>
  <q-page padding>
    <q-form @submit="submit" class="row justify-center">
      <q-card class="col-xs-12 col-md-6" flat>
        <q-card-section>
          <div class="text-h4">
            <q-icon color="indigo-5" name="power" />
            Connexion
          </div>
        </q-card-section>
        <q-card-section>
          <q-input :error="errors.has('username')" :error-message="errors.first('username')" bottom-slots class="q-ma-xs" data-vv-as="nom d'utilisateur" filled label="Nom d'utilisateur" name="username" v-model="formLogin.username" v-validate="{required: true}" />
          <q-input :error="errors.has('password')" :error-message="errors.first('password')" :type="togglePasswordVisibility ? 'text' : 'password'" bottom-slots class="q-ma-xs" data-vv-as="mot de passe" filled label="Mot de passe" name="password" v-model="formLogin.password" v-validate="{required: true}">
            <template v-slot:append>
              <q-icon :name="togglePasswordVisibility ? 'visibility' : 'visibility_off'" @click="togglePasswordVisibility = !togglePasswordVisibility" class="cursor-pointer" />
            </template>
          </q-input>
        </q-card-section>
        <q-card-actions class="justify-center">
          <q-btn flat type="submit">Se connecter</q-btn>
        </q-card-actions>
      </q-card>
    </q-form>
  </q-page>
</template>

<style></style>

<script>
export default {
  name: 'PageLogin',
  meta: {
    title: 'Connexion',
  },
  data() {
    return {
      formLogin: {
        username: null,
        password: null,
      },
      togglePasswordVisibility: false,
    }
  },
  methods: {
    async submit() {
      const valid = await this.$validator.validateAll()
      if (valid) {
        const redirect = localStorage.getItem('redirect')
        const user = await this.$auth.connect(this.formLogin)
        redirect ? this.$router.push({ name: redirect }) : this.$router.push({ name: 'route_accueil' })
        this.$q.notify(`Vous êtes maintenant connecté en tant que ${ user.sub }`)
      }
    },
  },
}
</script>
