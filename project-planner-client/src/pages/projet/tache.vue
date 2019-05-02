<template>
  <q-page padding>
    <ProjetPage>
      <template v-if="selectedProjet != null">
        <p>
          <q-form @submit="ajouterGroupe" class="row justify-center">
            <q-input bottom-slots class="col-xs-12 col-md-6" dense label="Ajouter un groupe" v-model="groupe">
              <template v-slot:hint>
                Entrez un nom de groupe de tâche et appuyez sur entrée ou cliquez sur le bouton
              </template>

              <template v-slot:append>
                <q-btn dense flat icon="add" round type="submit" />
              </template>
            </q-input>
          </q-form>
        </p>

        <q-card :key="i" class="q-mt-md" flat v-for="(groupe,i) in selectedProjet.groupes">
          <q-card-section class="text-indigo-8">
            <q-toolbar class="bg-indigo-1">
              <q-btn flat icon="arrow_drop_down_circle" round>
                <q-menu>
                  <q-list>
                    <q-item @click="beforeAjoutTache(groupe.id)" clickable>
                      <q-item-section avatar>
                        <q-icon name="add_circle" />
                      </q-item-section>
                      <q-item-section>
                        Nouvelle tâche
                      </q-item-section>
                    </q-item>
                  </q-list>
                </q-menu>
              </q-btn>
              <div class="q-pl-md">{{ groupe.nom }}</div>
              <q-space />
              <q-btn @click="supprimerGroupe(groupe.id)" flat icon="delete" />
            </q-toolbar>
            <q-list dense separator>
              <q-item :key="j" v-for="(tache,j) in groupe.taches">
                <q-item-section avatar>
                  <q-btn @click="supprimerTache(tache.id)" dense flat icon="delete" />
                </q-item-section>
                <q-item-section>{{ tache.titre }}</q-item-section>
              </q-item>
            </q-list>
          </q-card-section>
        </q-card>
      </template>
    </ProjetPage>

    <q-form class="q-pl-md">
      <q-dialog ref="dialogAjoutTache">
        <q-card flat square style="width: 400px">
          <q-card-section>
            <div class="row">
              <q-input class="col q-pa-xs" dense filled label="Titre" v-model="tache.titre" />
            </div>
            <div class="row">
              <q-input class="col q-pa-xs" dense filled label="Description" type="textarea" v-model="tache.description" />
            </div>
            <div class="row justify-center">
              <q-btn @click="ajouterTache" class="col q-pa-xs" dense flat icon="add" v-close-popup>Créer la tâche</q-btn>
            </div>
          </q-card-section>
        </q-card>
      </q-dialog>
    </q-form>
  </q-page>
</template>

<script>
import ProjetPage from 'components/ProjetPage'

export default {
  name: 'PageProjetTache',
  components: { ProjetPage },
  data() {
    return {
      groupe: null,
      tache: {}
    }
  },
  computed: {
    selectedProjet: {
      get() {
        return this.$store.state.projet.selected
      },
      set(value) {
        this.$store.dispatch('projet/update', value)
      },
    },
  },
  methods: {
    async ajouterGroupe() {
      const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes`, { nom: this.groupe })
      this.selectedProjet = res.data
      this.groupe = null
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
      const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes/${ this.tache.groupe }`, this.tache)
      this.selectedProjet = res.data
      this.tache = {}
      this.$q.notify('Tâche ajoutée')
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
    beforeAjoutTache(groupeId) {
      this.tache.groupe = groupeId
      this.$refs.dialogAjoutTache.show()
    }
  },
}
</script>

<style></style>
