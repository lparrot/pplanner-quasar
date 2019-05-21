<template>
  <q-page padding>
    <ProjetPage v-if="selectedProjet != null">
      <q-table :columns="columns" :data="logs" :pagination="pagination" row-key="id" title="Activité"></q-table>
    </ProjetPage>
  </q-page>
</template>

<script>
import projetMixin from 'mixins/projet'
import ProjetPage from 'components/ProjetPage'
import { bus } from '../../utils/bus'

export default {
  name: 'PageProjetActivite',
  meta: {
    title: 'Activité',
  },
  components: { ProjetPage },
  mixins: [projetMixin],
  data() {
    return {
      logs: [],
      pagination: {
        rowsPerPage: 15,
      },
      columns: [
        { name: 'message', field: 'message', align: 'left' },
        { name: 'utilisateur', field: 'utilisateur', align: 'center' },
        { name: 'dateCreation', field: 'dateCreation', align: 'center' },
      ],
    }
  },
  async created() {
    bus.$on('projet-select-updated', async () => {
      await this.getData()
    })
    await this.getData()
  },
  methods: {
    async getData() {
      const res = await this.$axios.get(`/api/logs/${ this.selectedProjet.id }`)
      this.logs = res.data
    },
  },
}
</script>

<style scoped>

</style>
