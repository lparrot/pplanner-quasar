<template>
  <q-page padding>

    <q-table title="Fichiers" flat selection="multiple" :selected.sync="selected" :pagination="pagination" dense :data="selectedProjet.fichiers" :columns="columns" row-key="nom">
      <template v-slot:top>
        <template v-if="selected.length">
          <q-btn flat dense icon="delete" color="primary" :label="getLabel('Supprimer')" @click="deleteFiles">
            <q-tooltip>Supprimer</q-tooltip>
          </q-btn>
          <q-btn flat dense icon="unarchive" color="primary" :label="getLabel('Télécharger')" @click="downloadFiles">
            <q-tooltip>Télécharger</q-tooltip>
          </q-btn>
          <q-separator class="q-mx-md" vertical />
        </template>
        <q-btn flat dense icon="archive" color="primary" :label="getLabel('Upload')" @click="$refs.modalUpload.show()">
          <q-tooltip>Upload</q-tooltip>
        </q-btn>
      </template>
    </q-table>

    <q-dialog ref="modalUpload">
      <q-layout container class="bg-white" style="max-height: 50vh">
        <q-header class="bg-primary">
          <q-toolbar>
            <q-toolbar-title>Chargement de fichiers</q-toolbar-title>
            <q-btn flat v-close-popup round dense icon="close" />
          </q-toolbar>
        </q-header>

        <q-page-container>
          <q-page padding>
            <q-uploader ref="uploader" flat batch multiple no-thumbnails @uploaded="uploaded" :url="'/api/projets/fichiers/' + selectedProjet.id" class="full-width" />
          </q-page>
        </q-page-container>

      </q-layout>
    </q-dialog>
  </q-page>
</template>

<script>
import projetMixin from 'src/mixins/projet'

export default {
  name: 'page-fichier',
  mixins: [projetMixin],
  data: () => ({
    rightDrawer: false,
    selected: [],
    fichier: null,
    pagination: {
      rowsPerPage: 15,
      sortBy: 'nom',
    },
    columns: [
      { label: 'Fichier', name: 'nom', field: 'nom', align: 'left', sortable: true },
      { label: 'Type de fichier', name: 'type', field: 'type', sortable: true },
    ],
  }),
  methods: {
    getLabel(label) {
      return this.$q.screen.gt.sm ? label : null
    },
    uploaded(info) {
      this.selectedProjet = JSON.parse(info.xhr.response)
      this.$refs.uploader.reset()
    },
    async deleteFiles() {
      await this.selected.forEach(async (data) => {
        await this.$axios.delete(`/api/projets/fichiers/${data.id}`)
        const index = this.selectedProjet.fichiers.findIndex(val => val.id === data.id)
        this.selectedProjet.fichiers.splice(index, 1)
      })
      this.selected = []
    },
    async downloadFiles() {
      const res = await this.$axios({
        url: `/api/projets/fichiers/download`,
        method: 'POST',
        responseType: 'blob',
        data: this.selected.map(data => {
          return { id: data.id }
        }),
      })

      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', res.headers['filename']); //or any other extension
      document.body.appendChild(link);
      link.click();
      this.selected = []
    },
  },
}
</script>
