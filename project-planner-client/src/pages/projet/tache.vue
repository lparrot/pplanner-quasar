<template>
  <q-page padding>
    <ProjetPage>
      <q-banner class="text-white bg-info q-mb-sm" inline-actions v-if="help">
        Cliquez sur le bouton de menu en bas à droite de la page pour créer un nouveau groupe ou une nouvelle tâche etc...
        <template v-slot:action>
          <q-btn @click="hideHelpBanner" color="white" flat label="Ne plus afficher" />
        </template>
      </q-banner>

      <template v-if="selectedProjet != null">
        <GroupeTache />
      </template>
    </ProjetPage>

    <q-dialog @show="$nextTick(() => $refs.inputGroupeNom.focus())" ref="dialogEditGroupe">
      <div style="max-width: 80vw; width: 800px" v-if="action === 'groupe'">
        <q-form>
          <q-card flat square>
            <q-card-section>
              <div class="row">
                <q-input :error="errors.has('nom')" :error-message="errors.first('nom')" bottom-slots class="col q-ma-ws" data-vv-as="nom" dense filled label="Nom" name="nom" ref="inputGroupeNom" v-model="groupe.nom" v-validate="{ required: true }" />
              </div>
            </q-card-section>
            <q-separator />
            <q-card-actions class="row justify-center">
              <q-btn @click="ajouterGroupe" class="col" flat icon="add" type="submit">Créer le groupe</q-btn>
            </q-card-actions>
          </q-card>
        </q-form>
      </div>
    </q-dialog>

    <q-dialog @show="$nextTick(() => $refs.selectTacheGroupe.focus())" ref="dialogEditTache">
      <div style="max-width: 80vw; width: 800px" v-if="action === 'tache'">
        <q-form>
          <q-card flat square>
            <q-card-section>
              <div class="row">
                <q-select :error="errors.has('groupe')" :error-message="errors.first('groupe')" :options="groupeOptions" bottom-slots class="col q-ma-xs" data-vv-as="groupe" dense emit-value filled label="Groupe" map-options name="groupe" options-dense ref="selectTacheGroupe" v-model="tache.groupe" v-validate="{ required: true }" />
              </div>
              <div class="row">
                <q-input :error="errors.has('titre')" :error-message="errors.first('titre')" bottom-slots class="col q-ma-xs" data-vv-as="titre" dense filled label="Titre" name="titre" v-model="tache.titre" v-validate="{ required: true }" />
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
              <q-btn @click="ajouterTache" class="col" flat icon="add" type="submit">Créer la tâche</q-btn>
            </q-card-actions>
          </q-card>
        </q-form>
      </div>
    </q-dialog>

    <q-dialog maximized persistent ref="dialogEstimationCharge">
      <PageProjetEstimation v-if="action === 'estimation'" />
    </q-dialog>

    <q-page-sticky :offset="[18, 18]" position="bottom-right" v-if="selectedProjet != null">
      <q-btn @click="showAddMenu" color="primary" fab icon="fas fa-bars" />
    </q-page-sticky>
  </q-page>
</template>

<script>
import ProjetPage from 'components/ProjetPage'
import { LocalStorage } from 'quasar'
import GroupeTache from 'components/GroupeTache'
import projetMixin from 'mixins/projet'
import PageProjetEstimation from './estimation'

export default {
  name: 'PageProjetTache',
  mixins: [projetMixin],
  components: { PageProjetEstimation, GroupeTache, ProjetPage },
  data() {
    return {
      help: false,
      intro: null,
      action: null,
      groupe: {},
      tache: {},
    }
  },
  computed: {
    groupeOptions() {
      if (this.selectedProjet == null) {
        return []
      }
      return this.selectedProjet.groupes.map(g => {
        return { value: g, label: g.nom }
      })
    },
  },
  created() {
    this.help = LocalStorage.getItem('help-banner.tache') !== false
  },
  methods: {
    hideHelpBanner() {
      this.help = false
      LocalStorage.set('help-banner.tache', false)
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
        const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes/${ this.tache.groupe.id }`, this.tache)
        this.selectedProjet = res.data
        this.tache = {}
        this.$refs.dialogEditTache.hide()
      }
    },
    showAddMenu() {
      const options = {
        message: 'Ajouter un élément',
        actions: [
          {
            label: 'Groupe',
            icon: 'fas fa-object-group',
            id: 'groupe',
            color: 'indigo-4',
          },
        ],
      }
      if (this.groupeOptions.length > 0) {
        options.actions.push({
          label: 'Tâche',
          icon: 'fas fa-tasks',
          id: 'tache',
          color: 'orange-4',
        })
      }
      options.actions.push({
        label: 'Estimation de charge',
        icon: 'fas fa-stopwatch',
        id: 'estimation',
        color: 'light-green-6',
      })
      this.$q.bottomSheet(options).onOk(action => {
        this.action = action.id
        this.$nextTick(() => {
          switch (this.action) {
            case 'groupe':
              this.$refs.dialogEditGroupe.show()
              break
            case 'tache':
              this.$refs.dialogEditTache.show()
              break
            case 'estimation':
              this.$refs.dialogEstimationCharge.show()
              break
            default:
              break
          }
        })
      })
    },
  },
}
</script>
