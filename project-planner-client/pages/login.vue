<template>
  <v-container>
    <v-layout align-center justify-center row>
      <v-flex md4>
        <v-card class="pa-3" flat>
          <v-flex class="text-xs-center">
            <v-avatar size="56" tile>
              <img src="~/static/logo.png">
            </v-avatar>
            <div class="display-1 font-weight-thin text-uppercase">{{title}}</div>

            <v-divider class="my-4"/>

            <v-tabs class="mb-4" grow v-model="tab">
              <v-tabs-slider color="yellow"/>
              <v-tab key="signin">Connexion</v-tab>
              <v-tab key="signup">Enregistrement</v-tab>
            </v-tabs>

            <v-tabs-items v-model="tab">
              <v-tab-item key="signin">
                <form data-vv-scope="signin">
                  <v-text-field :error-messages="errors.collect('signin.username')" box data-vv-as="nom d'utilisateur" label="Nom d'utilisateur" name="username" v-model="formSignin.username" v-validate="{required: true}"/>
                  <v-text-field :error-messages="errors.collect('signin.password')" box data-vv-as="mot de passe" label="Mot de passe" name="password" type="password" v-model="formSignin.password" v-validate="{required: true}"/>
                </form>
              </v-tab-item>
              <v-tab-item key="signup">
                <form data-vv-scope="signup">
                  <v-text-field :error-messages="errors.collect('signup.username')" box data-vv-as="nom d'utilisateur" label="Nom d'utilisateur" name="username" v-model="formSignup.username" v-validate="{required: true}"/>
                  <v-text-field :error-messages="errors.collect('signup.password')" box data-vv-as="mot de passe" label="Mot de passe" name="password" type="password" v-model="formSignup.password" v-validate="{required: true}"/>
                  <v-text-field :error-messages="errors.collect('signup.confirmation')" box label="Confirmation" name="confirmation" type="password" v-validate="{required: true, confirmed:formSignup.password}"/>
                  <v-text-field :error-messages="errors.collect('signup.nom')" box data-vv-as="nom" label="Nom" name="nom" v-model="formSignup.nom" v-validate="{required: true}"/>
                  <v-text-field :error-messages="errors.collect('signup.prenom')" box data-vv-as="prénom" label="Prénom" name="prenom" v-model="formSignup.prenom" v-validate="{required: true}"/>
                  <v-text-field :error-messages="errors.collect('signup.email')" box data-vv-as="email" label="E-mail" name="email" v-model="formSignup.email" v-validate="{required: true, email: true}"/>
                  <v-text-field :error-messages="errors.collect('signup.dateNaissance')" box data-vv-as="date de naissance" label="Date de naissance" mask="##/##/####" name="dateNaissance" v-model="formSignup.dateNaissance" v-validate="{required: true}"/>
                  <v-text-field :error-messages="errors.collect('signup.fixe')" box data-vv-as="numéro de fixe" label="Numéro de fixe" mask="## ## ## ## ##" name="fixe" v-model="formSignup.fixe"/>
                  <v-text-field :error-messages="errors.collect('signup.portable')" box data-vv-as="numéro de portable" label="Numéro de portable" mask="## ## ## ## ##" name="portable" v-model="formSignup.portable"/>
                </form>
              </v-tab-item>
            </v-tabs-items>

            <v-card-actions>
              <v-flex class="align-center">
                <v-btn @click="submit" flat primary v-if="tab === 0">Se connecter</v-btn>
                <v-btn @click="submit" flat primary v-if="tab === 1">S'enregistrer</v-btn>
              </v-flex>
            </v-card-actions>
          </v-flex>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  auth: false,
  name: 'LoginPage',
  data: () => ({
    tab: null,
    formSignin: {
      username: null,
      password: null,
    },
    formSignup: {
      username: null,
      password: null,
      nom: null,
      prenom: null,
      email: null,
      dateNaissance: null,
      fixe: null,
      portable: null,
    },
  }),
  computed: {
    title() {
      return process.env.title;
    },
  },
  methods: {
    async submit() {
      switch (this.tab) {
        case 0 :
          const signinValid = await this.$validator.validateAll('signin');
          if (signinValid) {
            const redirect = localStorage.getItem('auth.redirect');
            await this.$auth.login({ data: this.formSignin });
            redirect ? this.$router.push(redirect) : this.$router.push('/');
          }
          break;
        case 1 :
          const signupValid = await this.$validator.validateAll('signup');
          if (signupValid) {}
          break;
        default:
          break;
      }
    },
  },
};
</script>
