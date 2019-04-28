<template>
  <v-container v-if="profil != null">
    <v-tabs centered dark grow v-model="tab">
      <v-tabs-slider color="yellow"/>
      <v-tab href="#tab1">
        Profil
      </v-tab>
      <v-tab href="#tab2">
        Notifications et mails
      </v-tab>
      <v-tab href="#tab3">
        Logs
      </v-tab>

      <v-tab-item value="tab1">
        <v-container grid-list-md>
          <v-form @submit.prevent="submitProfil" data-vv-scope="profil">
            <v-layout row wrap>
              <v-flex md6 xs12>
                <v-text-field :error-messages="errors.collect('profil.nom')" box data-vv-as="nom" label="Nom" name="nom" v-model="profil.nom" v-validate="{required: true}"/>
              </v-flex>
              <v-flex md6 xs12>
                <v-text-field :error-messages="errors.collect('profil.prenom')" box data-vv-as="prenom" label="Prénom" name="prenom" v-model="profil.prenom" v-validate="{required: true}"/>
              </v-flex>
              <v-flex md6 xs12>
                <v-text-field :error-messages="errors.collect('profil.email')" box data-vv-as="email" label="Email" name="email" v-model="profil.email" v-validate="{required: true, email: true}"/>
              </v-flex>
              <v-flex md6 xs12>
                <v-text-field :error-messages="errors.collect('profil.compagnie')" box data-vv-as="compagnie" label="Compagnie" name="compagnie" v-model="profil.compagnie"/>
              </v-flex>
              <v-flex md6 xs12>
                <v-text-field :error-messages="errors.collect('profil.fixe')" box data-vv-as="fixe" label="Téléphone fixe" mask="## ## ## ## ##" name="fixe" v-model="profil.fixe"/>
              </v-flex>
              <v-flex md6 xs12>
                <v-text-field :error-messages="errors.collect('profil.portable')" box data-vv-as="portable" label="Téléphone portable" mask="## ## ## ## ##" name="portable" v-model="profil.portable"/>
              </v-flex>
              <v-flex md6 xs12>
                <v-dialog :return-value.sync="profil.dateNaissance" full-width lazy ref="pickerDateNaissance" v-model="picker" width="290px">
                  <template v-slot:activator="{on}">
                    <v-text-field :error-messages="errors.collect('profil.dateNaissance')" :value="computedDateNaissance" box data-vv-as="date de naissance" label="Date de naissance" name="dateNaissance" readonly v-on="on"/>
                  </template>
                  <v-date-picker :max="new Date().toISOString().substr(0, 10)" locale="fr" min="1950-01-01" ref="dateNaissancePicker" v-model="profil.dateNaissance">
                    <v-spacer></v-spacer>
                    <v-btn @click="picker = false" color="primary" flat>Annuler</v-btn>
                    <v-btn @click="$refs.pickerDateNaissance.save(profil.dateNaissance)" color="primary" flat>OK</v-btn>
                  </v-date-picker>
                </v-dialog>
              </v-flex>
            </v-layout>
            <v-layout justify-center>
              <v-btn color="primary" type="submit">Modifier les informations</v-btn>
            </v-layout>
          </v-form>
        </v-container>
      </v-tab-item>
      <v-tab-item value="tab2">
        Modification des notifications et mails
      </v-tab-item>
      <v-tab-item value="tab3">
        Logs
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
import moment from 'moment';

export default {
  name: 'AccountPage',
  data: () => ({
    tab: null,
    profil: null,
    picker: false,
  }),
  computed: {
    computedDateNaissance() {
      if (this.profil.dateNaissance != null) {
        return moment(this.profil.dateNaissance).format('DD MMMM YYYY');
      }
      return null;
    },
  },
  watch: {
    'tab': {
      handler: function (after) {
        this.$router.replace({ query: { tab: after } });
      },
    },
    'picker': {
      handler: function (val) {
        val && setTimeout(() => (this.$refs.dateNaissancePicker.activePicker = 'YEAR'));
      },
    },
  },
  async created() {
    const params = { username: this.$auth.user.sub };
    this.profil = await this.$axios.$get(`/api/security/account`, { params });
  },
  mounted() {
    this.tab = this.$route.query.tab || 'tab1';
  },
  methods: {
    async submitProfil() {
      const valid = await this.$validator.validateAll('profil');
      if (valid) {
        this.profil = await this.$axios.$put(`/api/security/account/${this.profil.id}`, this.profil);
      }
    },
  },
};
</script>
