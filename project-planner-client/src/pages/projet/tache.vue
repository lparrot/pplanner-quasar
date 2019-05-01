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
          <q-card-section>
            <q-bar class="bg-indigo-1 text-indigo-8">
              <div>{{ groupe.nom }}</div>
              <q-space />
              <q-btn @click="supprimerGroupe(groupe.id)" dense flat icon="delete" />
            </q-bar>
          </q-card-section>
        </q-card>
      </template>
    </ProjetPage>
  </q-page>
</template>

<script>
import ProjetPage from 'components/ProjetPage'

export default {
  name: 'PageProjetTache',
  components: { ProjetPage },
  data () {
    return {
      groupe: null,
    }
  },
  computed: {
    selectedProjet: {
      get () {
        return this.$store.state.projet.selected
      },
      set (value) {
        this.$store.dispatch('projet/update', value)
      },
    },
  },
  methods: {
    async ajouterGroupe () {
      const res = await this.$axios.post(`/api/projets/${ this.selectedProjet.id }/groupes`, { nom: this.groupe })
      this.selectedProjet = res.data
      this.groupe = null
    },
    async supprimerGroupe (id) {
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
  },
}
</script>

<style></style>
