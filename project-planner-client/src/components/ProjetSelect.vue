<template>
  <q-select :clearable="clearable" :dark="dark" :options="projets" :value="$store.state.projet.selected" @filter="searchProjets" @input="selectProjet" dense emit-value filled label="Projet" option-label="nom">
    <template v-slot:option="scope">
      <q-item v-bind="scope.itemProps" v-on="scope.itemEvents">
        <q-item-section avatar>
          <q-avatar size="36px" square>
            <img :src="'data:image/*;base64,' + scope.opt.value.logo">
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <q-item-label v-text="scope.opt.value.nom" />
          <q-item-label caption v-text="scope.opt.value.description" />
        </q-item-section>
      </q-item>
    </template>
    <template v-slot:no-option>
      <q-item>
        <q-item-section class="text-grey">
          Aucun projet
        </q-item-section>
      </q-item>
    </template>
  </q-select>
</template>
<script>
import { bus } from '../utils/bus'

export default {
  name: 'ProjetSelect',
  props: {
    dark: {
      type: Boolean,
    },
    clearable: {
      type: Boolean,
    },
  },
  data() {
    return {
      projets: null,
    }
  },
  methods: {
    async searchProjets(val, update, abort) {
      if (this.projets != null) {
        update()
        return null
      }
      const res = await this.$axios.get('/api/projets')
      this.projets = res.data.map(data => {
        return { value: data }
      })
      update()
    },
    async selectProjet(event) {
      // Enregistrement du projet selectionné
      localStorage.setItem('selected_projet', event.id)
      this.$store.dispatch('projet/update', event)
      bus.$emit('projet-select-updated', event)
    },
  },
}
</script>
