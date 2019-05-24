<template>
  <q-page padding>
    <q-uploader ref="uploader" batch multiple @uploaded="uploaded" :url="'/api/projets/fichiers/' + selectedProjet.id" class="full-width" />

    <q-separator class="q-my-sm" />

    <q-table title="Fichiers" :pagination="pagination" dense :data="selectedProjet.fichiers" :columns="columns" row-key="nom" />
  </q-page>
</template>

<script>
import projetMixin from 'src/mixins/projet'

export default {
  name: 'page-fichier',
  mixins: [projetMixin],
  data: () => ({
    fichier: null,
    pagination: {
      rowsPerPage: 15,
    },
    columns: [
      { name: 'nom', field: 'nom', align: 'left' },
      { name: 'type', field: 'type' },
    ],
  }),
  methods: {
    uploaded(info) {
      this.selectedProjet = JSON.parse(info.xhr.response)
      this.$refs.uploader.reset()
    },
  },
}
</script>
