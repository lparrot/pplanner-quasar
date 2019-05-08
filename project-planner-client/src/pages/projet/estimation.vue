<template>
  <q-layout class="bg-white" container view="Lhh lpR fff">
    <q-header class="bg-primary">
      <q-toolbar>
        <q-toolbar-title>Estimation de charge</q-toolbar-title>
        <q-btn dense flat icon="close" round v-close-popup />
      </q-toolbar>
    </q-header>

    <q-page-container>
      <q-page padding>
        <q-banner class="bg-blue text-white text-bold q-mb-sm">
          <div>L'estimation est en nombre jour/homme et peut être un nombre à virgule.</div>
          <div>Exemple : 1, 5, 13, 0.5</div>
        </q-banner>

        <q-expansion-item default-opened dense expand-icon-toggle expand-separator header-class="bg-indigo-2 text-indigo-8" label="Non estimées" switch-toggle-side>
          <TacheEstimation :taches="taches.nonEstimees" />
        </q-expansion-item>
        <q-expansion-item default-opened dense expand-icon-toggle expand-separator header-class="bg-indigo-2 text-indigo-8" label="Estimées" switch-toggle-side>
          <TacheEstimation :taches="taches.estimees" />
        </q-expansion-item>
      </q-page>
    </q-page-container>

    <q-footer>
      <q-toolbar>
        <q-space />
        <q-btn @click="save" dense flat icon="save" label="Enregistrer" />
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script>
import projetMixin from 'mixins/projet'
import { extend, Notify } from 'quasar'
import TacheEstimation from 'src/components/TacheEstimation'

export default {
  name: 'PageProjetEstimation',
  components: { TacheEstimation },
  data() {
    return {
      taches: {},
    }
  },
  mixins: [projetMixin],
  async created() {
    await this.init()
  },
  methods: {
    async init() {
      this.taches = {
        estimees: [],
        nonEstimees: [],
      }
      const allTaches = [].concat(...this.selectedProjet.groupes.map(g => g.taches))
      this.taches.estimees = extend(true, allTaches.filter(data => data.estimation != null), this.taches.estimees)
      this.taches.nonEstimees = extend(true, allTaches.filter(data => data.estimation == null), this.taches.nonEstimees)
    },
    async save() {
      const valid = await this.$validator.validateAll()
      if (valid) {
        const allTaches = [...this.taches.estimees, ...this.taches.nonEstimees]
        const taches = allTaches.map(data => {
          return {
            id: data.id,
            estimation: parseFloat(data.estimation),
          }
        })
        const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/estimations`, taches)
        this.selectedProjet = res.data
        Notify.create({ message: 'Modifications effectuées' })
        await this.init()
        this.$emit('after-save')
      }
    },
  },
}
</script>

