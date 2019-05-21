<template>
  <q-page>
    <q-page padding>
      <ProjetPage>
        <q-banner class="text-white bg-info q-mb-sm" inline-actions v-if="help">
          Cliquez sur le bouton de menu en bas à droite de la page pour créer un nouveau groupe ou une nouvelle tâche etc...
          <template v-slot:action>
            <q-btn @click="hideHelpBanner" color="white" flat label="Ne plus afficher" />
          </template>
        </q-banner>

        <template v-if="selectedProjet != null">
          <GroupeTache @on-add-groupe="beforeAjouterGroupe" @on-add-tache="beforeAjouterTache" />
        </template>
      </ProjetPage>

      <q-dialog @show="$nextTick(() => $refs.inputGroupeNom.focus())" ref="dialogEditGroupe">
        <div style="max-width: 80vw; width: 800px">
          <form @submit.prevent="ajouterGroupe">
            <q-card flat square>
              <q-card-section>
                <div class="row">
                  <q-input :error="errors.has('nom')" :error-message="errors.first('nom')" bottom-slots class="col q-ma-ws" data-vv-as="nom" dense filled label="Nom" name="nom" ref="inputGroupeNom" v-model="groupe.nom" v-validate="{ required: true }" />
                </div>
              </q-card-section>
              <q-separator />
              <q-card-actions class="row justify-center">
                <q-btn class="col" flat icon="add" type="submit">Créer le groupe</q-btn>
              </q-card-actions>
            </q-card>
          </form>
        </div>
      </q-dialog>

      <q-dialog @show="$nextTick(() => $refs.inputTacheTitre.focus())" ref="dialogEditTache">
        <div style="max-width: 80vw; width: 800px">
          <form @submit.prevent="ajouterTache">
            <q-card flat square>
              <q-card-section>
                <div class="row">
                  <q-select :error="errors.has('groupe')" :error-message="errors.first('groupe')" :options="groupes" bottom-slots class="col q-ma-xs" data-vv-as="groupe" dense filled label="Groupe" name="groupe" options-dense v-model="tache.groupe" v-validate="{ required: true }" />
                </div>
                <div class="row">
                  <q-input :error="errors.has('titre')" :error-message="errors.first('titre')" bottom-slots class="col q-ma-xs" data-vv-as="titre" dense filled label="Titre" name="titre" ref="inputTacheTitre" v-model="tache.titre" v-validate="{ required: true }" />
                </div>
                <div class="row">
                  <q-input bottom-slots class="col q-ma-xs" dense filled label="Description" type="textarea" v-model="tache.description" />
                </div>
                <div class="row">
                  <q-select class="col q-ma-xs" clearable dense filled hide-dropdown-icon input-debounce="0" label="Tags" multiple new-value-mode="add-unique" options-dense use-chips use-input v-model="tache.tags" />
                </div>
              </q-card-section>
              <q-separator />
              <q-card-actions class="row justify-center">
                <q-btn class="col" flat icon="add" type="submit">Créer la tâche</q-btn>
              </q-card-actions>
            </q-card>
          </form>
        </div>
      </q-dialog>
    </q-page>
  </q-page>
</template>

<script>
import ProjetPage from 'src/components/ProjetPage'
import { LocalStorage } from 'quasar'
import GroupeTache from 'src/components/GroupeTache'
import projetMixin from 'src/mixins/projet'

export default {
  name: 'PageProjetTache',
  meta: {
    title: 'Taches',
  },
  mixins: [projetMixin],
  components: { GroupeTache, ProjetPage },
  data() {
    return {
      help: false,
      intro: null,
      action: null,
      groupes: [],
      groupe: {},
      tache: {
        groupe: null,
      },
    }
  },
  created() {
    this.help = LocalStorage.getItem('help-banner.tache') !== false
  },
  methods: {
    hideHelpBanner() {
      this.help = false
      LocalStorage.set('help-banner.tache', false)
    },
    beforeAjouterGroupe() {
      this.$refs.dialogEditGroupe.show()
    },
    beforeAjouterTache(groupe) {
      this.groupes = this.selectedProjet.groupes.map((data) => {
        return { value: data, label: data.nom }
      })
      this.tache.groupe = { value: groupe, label: groupe.nom }
      this.$refs.dialogEditTache.show()
    },
    async ajouterGroupe() {
      const valid = await this.$validator.validateAll()
      if (valid) {
        const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes`, this.groupe)
        this.selectedProjet = res.data
        this.$refs.dialogEditGroupe.hide()
        this.groupe = {}
      }
    },
    async ajouterTache() {
      const valid = await this.$validator.validateAll()
      if (valid) {
        const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes/${ this.tache.groupe.value.id }`, this.tache)
        this.selectedProjet = res.data
        this.tache = {}
        this.$refs.dialogEditTache.hide()
      }
    },
  },
}
</script>
