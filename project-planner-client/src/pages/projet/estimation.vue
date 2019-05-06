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
        <div :class="{'bg-grey-2': i % 2 === 0}" :key="i" class="row q-pa-sm items-center" v-for="(tache, i) in taches">
          <div class="col-6 col-md-9">{{ tache.titre }}</div>
          <div class="col-2 col-md-1 text-center">
            <q-avatar class="cursor-pointer" color="blue" size="24px" text-color="white" v-if="tache.utilisateur">
              {{ tache.utilisateur.initiales }}
              <q-tooltip>{{ tache.utilisateur.nom }}</q-tooltip>
            </q-avatar>
            <q-avatar class="cursor-pointer" color="grey-4" size="24px" v-else>
              ?
              <q-tooltip>Non attribué</q-tooltip>
            </q-avatar>
          </div>
          <div class="col-4 col-md-2">
            <q-input :error="errors.has('estimation' +i)" :name="'estimation' + i" data-vv-as="estimation" dense filled v-model="tache.estimation" v-validate="{regex: /^(-?)(0|([1-9][0-9]*))(\.[0-9]+)?$/}">
              <template v-slot:prepend>
                <q-icon name="fas fa-clock" />
              </template>
            </q-input>
          </div>
        </div>
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
import { extend } from 'quasar'

export default {
  name: 'PageProjetEstimation',
  data() {
    return {
      taches: [],
    }
  },
  mixins: [projetMixin],
  created() {
    let allTaches = []
    allTaches = allTaches.concat(...this.selectedProjet.groupes.map(g => g.taches))
    this.taches = extend(true, allTaches, this.taches)
  },
  methods: {
    async save() {
      const valid = await this.$validator.validateAll()
      // TODO : a implémenter
    },
  },
}
</script>

<style scoped>

</style>
