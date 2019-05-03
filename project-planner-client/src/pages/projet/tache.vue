<template>
  <q-page padding>
    <ProjetPage>
      <q-banner class="text-white bg-info" inline-actions v-if="help">
        Cliquez sur le bouton de menu en bas à droite de la page pour créer un nouveau groupe ou une nouvelle tâche etc...
        <template v-slot:action>
          <q-btn @click="hideHelpBanner" color="white" flat label="Ne plus afficher" />
        </template>
      </q-banner>

      <template v-if="selectedProjet != null">
        <q-card :key="i" class="q-mt-md" flat v-for="(groupe,i) in selectedProjet.groupes">
          <q-card-section class="text-indigo-8">
            <q-toolbar class="bg-indigo-1">
              <div class="q-pl-md">{{ groupe.nom }}</div>
              <q-space />
              <q-btn @click="supprimerGroupe(groupe.id)" flat icon="delete" />
            </q-toolbar>
            <q-list bordered dense separator>
              <q-item :key="j" v-for="(tache,j) in groupe.taches">
                <q-item-section avatar>
                  <q-btn @click="supprimerTache(tache.id)" dense flat icon="delete" />
                </q-item-section>
                <q-item-section>
                  <div class="text-bold">{{ tache.titre }}</div>
                  <div class="text-indigo-3">{{ tache.description }}</div>
                </q-item-section>
              </q-item>
            </q-list>
          </q-card-section>
        </q-card>
      </template>
    </ProjetPage>

    <q-form>
      <q-dialog ref="dialogEditGroupe">
        <template v-if="action === 'groupe'">
          <q-card flat square style="width: 400px">
            <q-card-section>
              <div class="row">
                <q-input :error="errors.has('nom')" :error-message="errors.first('nom')" bottom-slots class="col q-ma-ws" data-vv-as="nom" dense label="Nom" name="nom" v-model="groupe.nom" v-validate="{ required: true }" />
              </div>
              <div class="row justify-center">
                <q-btn @click="ajouterGroupe" class="col q-pa-xs" dense flat icon="add">Créer le groupe</q-btn>
              </div>
            </q-card-section>
          </q-card>
        </template>
      </q-dialog>
    </q-form>

    <q-form>
      <q-dialog ref="dialogEditTache">
        <template v-if="action === 'tache'">
          <q-card flat square style="width: 400px">
            <q-card-section>
              <div class="row">
                <q-select :error="errors.has('groupe')" :error-message="errors.first('groupe')" :options="groupeOptions" bottom-slots class="col q-ma-xs" data-vv-as="groupe" dense emit-value filled label="Groupe" map-options name="groupe" v-model="tache.groupe" v-validate="{ required: true }" />
              </div>
              <div class="row">
                <q-input :error="errors.has('titre')" :error-message="errors.first('titre')" bottom-slots class="col q-ma-xs" data-vv-as="titre" dense filled label="Titre" name="titre" v-model="tache.titre" v-validate="{ required: true }" />
              </div>
              <div class="row">
                <q-input bottom-slots class="col q-ma-xs" dense filled label="Description" type="textarea" v-model="tache.description" />
              </div>
              <div class="row justify-center">
                <q-btn @click="ajouterTache" class="col q-pa-xs" dense flat icon="add">Créer la tâche</q-btn>
              </div>
            </q-card-section>
          </q-card>
        </template>
      </q-dialog>
    </q-form>

    <q-page-sticky :offset="[18, 18]" position="bottom-right">
      <q-btn @click="showAddMenu" color="primary" fab icon="fas fa-bars" />
    </q-page-sticky>
  </q-page>
</template>

<script>
import ProjetPage from 'components/ProjetPage'

export default {
  name: 'PageProjetTache',
  components: { ProjetPage },
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
    selectedProjet: {
      get() {
        return this.$store.state.projet.selected
      },
      set(value) {
        this.$store.dispatch('projet/update', value)
      },
    },
  },
  created() {
    this.help = localStorage.getItem('help-banner.tache') !== 'false'
  },
  methods: {
    hideHelpBanner() {
      this.help = false
      localStorage.setItem('help-banner.tache', 'false')
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
    async supprimerGroupe(id) {
      await this.$q.dialog({
        title: 'Confirmation',
        message: 'Etes vous sûr de vouloir supprimer ce groupe ainsi que toutes les tâches associées (Cette action est irréversible) ?',
        ok: {
          color: 'negative',
          label: 'Supprimer le groupe',
        },
        cancel: {
          color: 'primary',
        },
        persistent: true,
      }).onOk(async () => {
        const res = await this.$axios.delete(`/api/projets/${ this.selectedProjet.id }/groupes/${ id }`)
        this.selectedProjet = res.data
        this.$q.notify('Le groupe a bien été supprimé')
      })
    },
    async ajouterTache() {
      const valid = await this.$validator.validateAll()
      if (valid) {
        const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes/${ this.tache.groupe.id }`, this.tache)
        this.selectedProjet = res.data
        this.tache = {}
        this.$q.notify('Tâche ajoutée')
        this.$refs.dialogEditTache.hide()
      }
    },
    async supprimerTache(tacheId) {
      await this.$q.dialog({
        title: 'Confirmation',
        message: 'Etes vous sûr de vouloir supprimer cette tâche ?',
        ok: {
          color: 'negative',
          label: 'Supprimer la tâche',
        },
        cancel: {
          color: 'primary',
        },
        persistent: true,
      }).onOk(async () => {
        const res = await this.$axios.delete(`/api/projets/${ this.selectedProjet.id }/taches/${ tacheId }`)
        this.selectedProjet = res.data
        this.$q.notify('Tâche supprimée')
      })
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
            default:
              break
          }
        })
      })
    },
  },
}
</script>
