<template>
  <q-page padding>
    <q-banner class="bg-blue text-white text-bold q-mb-sm">
      <div>L'estimation est en nombre jour/homme et peut être un nombre à virgule.</div>
      <div>Exemple : 1, 5, 13, 0.5</div>
    </q-banner>

    <q-expansion-item default-opened dense expand-icon-toggle expand-separator header-class="bg-indigo-2 text-indigo-8" label="Non estimées" switch-toggle-side>
      <TacheEstimation :taches="taches.nonEstimees" @on-save="save" />
    </q-expansion-item>
    <q-expansion-item default-opened dense expand-icon-toggle expand-separator header-class="bg-indigo-2 text-indigo-8" label="Estimées" switch-toggle-side v-if="taches.estimees.length">
      <TacheEstimation :taches="taches.estimees" @on-save="save" />
    </q-expansion-item>
  </q-page>
</template>

<script>
import projetMixin from 'mixins/projet'
import { extend } from 'quasar'
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
        await this.init()
      }
    },
  },
}
</script>

