<template>
  <q-page padding>
    <q-tabs align="justify" class="text-white bg-primary" dense v-model="tab">
      <q-tab label="Profil" name="tab1" />
      <q-tab label="Notifications et mails" name="tab2" />
      <q-tab label="Logs" name="tab3" />
    </q-tabs>

    <q-tab-panels animated transition-next="jump-up" transition-prev="jump-up" v-model="tab">
      <q-tab-panel name="tab1">
        <q-form @submit="submit">
          <q-card>
            <q-card-section>
              <div class="row">
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('nom')" :error-message="errors.first('nom')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="nom" filled label="Nom" name="nom" v-model="formProfil.nom" v-validate="{required: true}" />
                </div>
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('prenom')" :error-message="errors.first('prenom')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="prénom" filled label="Prénom" name="prenom" v-model="formProfil.prenom" v-validate="{required: true}" />
                </div>
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('email')" :error-message="errors.first('email')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="e-mail" filled label="E-mail" name="email" v-model="formProfil.email" v-validate="{required: true, email: true}" />
                </div>
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('compagnie')" :error-message="errors.first('compagnie')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="compagnie" filled label="Compagnie" name="compagnie" v-model="formProfil.compagnie" v-validate="{required: true}" />
                </div>
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('fixe')" :error-message="errors.first('fixe')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="téléphone fixe" filled label="Téléphone fixe" mask="## ## ## ## ##" name="fixe" v-model="formProfil.fixe" v-validate="{required: true}" />
                </div>
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('portable')" :error-message="errors.first('portable')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="téléphone portable" filled label="Téléphone portable" mask="## ## ## ## ##" name="portable" v-model="formProfil.portable" v-validate="{required: true}" />
                </div>
                <div class="col-xs12 col-md-6">
                  <q-input :error="errors.has('dateNaissance')" :error-message="errors.first('dateNaissance')" bottom-slots class="q-pa-xs q-mb-lg" data-vv-as="date de naissance" filled label="Date de naissance" mask="##/##/####" name="dateNaissance" v-model="formProfil.dateNaissance" v-validate="{required: true, regex: /[0-9]{2}\/[0-9]{2}\/[0-9]{4}/}" />
                </div>
              </div>
            </q-card-section>
            <q-card-actions class="justify-center">
              <q-btn flat type="submit">Modifier les informations</q-btn>
            </q-card-actions>
          </q-card>
        </q-form>
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
  data () {
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
    }
  },
  async created () {
    const params = { username: this.$auth.user.sub }
    const res = await this.$axios.get(`/api/security/account`, { params })
    this.formProfil = res.data
  },
  mounted () {
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
    async submit () {
      const valid = await this.$validator.validateAll()
      if (valid) {
        const res = await this.$axios.put(`/api/security/account/${ this.formProfil.id }`, this.formProfil)
        this.formProfil = res.data
        this.$q.notify('Modifications effectuées')
      }
    },
  },
}
</script>
