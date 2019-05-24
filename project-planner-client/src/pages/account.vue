<template>
  <q-page padding>
    <q-tabs align="justify" class="text-white bg-primary" dense v-model="tab">
      <q-tab label="Profil" name="tab1" />
      <q-tab label="Notifications et mails" name="tab2" />
      <q-tab label="Logs" name="tab3" />
    </q-tabs>

    <q-tab-panels animated transition-next="jump-up" transition-prev="jump-up" v-model="tab">
      <q-tab-panel name="tab1">
        <div class="row">
          <q-card class="col-12 col-md-9" flat>
            <form @submit.prevent="submit" data-vv-scope="info">
              <q-card-section>
                <div class="text-h6">Informations</div>
              </q-card-section>
              <q-card-section>
                <div class="row">
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.nom')" :error-message="errors.first('info.nom')" bottom-slots class="q-ma-xs" data-vv-as="nom" filled label="Nom" name="nom" v-model="formProfil.nom" v-validate="{required: true}" />
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.prenom')" :error-message="errors.first('info.prenom')" bottom-slots class="q-ma-xs" data-vv-as="prénom" filled label="Prénom" name="prenom" v-model="formProfil.prenom" v-validate="{required: true}" />
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.email')" :error-message="errors.first('info.email')" bottom-slots class="q-ma-xs" data-vv-as="e-mail" filled label="E-mail" name="email" v-model="formProfil.email" v-validate="{required: true, email: true}" />
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.compagnie')" :error-message="errors.first('info.compagnie')" bottom-slots class="q-ma-xs" data-vv-as="compagnie" filled label="Compagnie" name="compagnie" v-model="formProfil.compagnie" v-validate="{required: true}" />
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.fixe')" :error-message="errors.first('info.fixe')" bottom-slots class="q-ma-xs" data-vv-as="téléphone fixe" filled label="Téléphone fixe" mask="## ## ## ## ##" name="fixe" v-model="formProfil.fixe" v-validate="{required: true}" />
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.portable')" :error-message="errors.first('info.portable')" bottom-slots class="q-ma-xs" data-vv-as="téléphone portable" filled label="Téléphone portable" mask="## ## ## ## ##" name="portable" v-model="formProfil.portable" v-validate="{required: true}" />
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <q-input :error="errors.has('info.dateNaissance')" :error-message="errors.first('info.dateNaissance')" bottom-slots class="q-ma-xs" data-vv-as="date de naissance" filled label="Date de naissance" mask="##/##/####" name="dateNaissance" v-model="formProfil.dateNaissance" v-validate="{required: true}">
                      <template v-slot:append>
                        <q-icon class="cursor-pointer" name="event">
                          <q-popup-proxy ref="qDateProxy" transition-hide="scale" transition-show="scale">
                            <q-date @input="() => $refs.qDateProxy.hide()" mask="DD/MM/YYYY" v-model="formProfil.dateNaissance" />
                          </q-popup-proxy>
                        </q-icon>
                      </template>
                    </q-input>
                  </div>
                </div>
              </q-card-section>
              <q-card-actions class="justify-center">
                <q-btn flat type="submit">Modifier les informations</q-btn>
              </q-card-actions>
            </form>
          </q-card>

          <q-card class="col-12 col-md-3" flat>
            <form @submit.prevent="changerMotDePasse" data-vv-scope="motDePasse">
              <q-card-section>
                <div class="text-h6">Mot de passe</div>
              </q-card-section>
              <q-card-section>
                <div class="row">
                  <div class="col-12">
                    <q-input type="password" :error="errors.has('motDePasse.mot_de_passe')" :error-message="errors.first('motDePasse.mot_de_passe')" bottom-slots class="q-ma-xs" data-vv-as="mot de passe" filled label="Mot de passe" name="mot_de_passe" v-model="formMotDePasse.motDePasse" v-validate="{required: true, is: formMotDePasse.confirmation}" />
                  </div>
                  <div class="col-12">
                    <q-input type="password" :error="errors.has('motDePasse.confirmation_mot_de_passe')" :error-message="errors.first('motDePasse.confirmation_mot_de_passe')" bottom-slots class="q-ma-xs" data-vv-as="confirmation" filled label="Confirmation" name="confirmation_mot_de_passe" v-model="formMotDePasse.confirmation" v-validate="{required: true}" />
                  </div>
                </div>
              </q-card-section>
              <q-card-actions class="justify-center">
                <q-btn flat type="submit">Modifier le mot de passe</q-btn>
              </q-card-actions>
            </form>
          </q-card>
        </div>
      </q-tab-panel>

      <q-tab-panel name="tab2"></q-tab-panel>

      <q-tab-panel name="tab3"></q-tab-panel>
    </q-tab-panels>
  </q-page>
</template>

<style></style>

<script>
export default {
  name: 'PageAccount',
  meta: {
    title: 'Compte',
  },
  data() {
    return {
      tab: 'tab1',
      formProfil: {
        nom: null,
        prenom: null,
        email: null,
        compagnie: null,
        fixe: null,
        portable: null,
        dateNaissance: null,
      },
      formMotDePasse: {
        motDePasse: null,
        confirmation: null,
      },
    }
  },
  async created() {
    const params = { username: this.$auth.user.sub }
    const res = await this.$axios.get(`/api/security/account`, { params })
    this.formProfil = res.data
  },
  mounted() {
    this.tab = this.$route.query.tab || 'tab1'
  },
  watch: {
    'tab': {
      handler: function (newVal) {
        this.$router.replace({ query: { tab: newVal } })
      },
    },
  },
  methods: {
    async submit() {
      const valid = await this.$validator.validateAll('info')
      if (valid) {
        const res = await this.$axios.put(`/api/security/account/${ this.formProfil.id }`, this.formProfil)
        this.formProfil = res.data
        this.$q.notify('Modifications effectuées')
      }
    },
    async changerMotDePasse() {
      const valid = await this.$validator.validateAll('motDePasse')
      if (valid) {
        console.log(this.formProfil)
        await this.$axios.put(`/api/security/account/${ this.formProfil.id }/password`, { password: this.formMotDePasse.motDePasse })
        this.formMotDePasse = {
          motDePasse: null,
          confirmation: null,
        }
        this.$q.notify('Mot de passe modifié')
        await this.$validator.reset();
      }
    },
  },
}
</script>
