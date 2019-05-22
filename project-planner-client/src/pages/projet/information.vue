<template>
  <q-page padding>
    <ProjetPage>
      <section class="text-center">
        <q-form @submit="modifierProjet">
          <FileUpload @on-upload="uploadLogo">
            <template v-slot="{fireChooseFile}">
              <img :src="'data:image/*;base64,' + selectedProjet.logo" @click="fireChooseFile" alt="Logo" class="cursor-pointer" height="150" v-if="selectedProjet.logo != null" />
            </template>
          </FileUpload>
          <p>
            <section class="inline">
              <span class="text-h2">{{ selectedProjet.nom }}</span>
              <q-popup-edit buttons v-model="selectedProjet.nom">
                <q-input autofocus counter dense v-model="selectedProjet.nom" />
              </q-popup-edit>
            </section>
          </p>
          <p>
            <section class="inline">
              <span class="text-h4">{{ selectedProjet.description }}</span>
              <q-popup-edit buttons v-model="selectedProjet.description">
                <q-input autofocus counter dense v-model="selectedProjet.description" />
              </q-popup-edit>
            </section>
          </p>
          <div class="row justify-center q-mt-lg">
            <q-btn flat type="submit">Valider les modifications</q-btn>
          </div>
        </q-form>
      </section>
    </ProjetPage>
  </q-page>
</template>

<script>
import FileUpload from 'src/components/FileUpload'
import ProjetPage from 'src/components/ProjetPage'
import mixinProjet from 'src/mixins/projet'

export default {
  name: 'projets',
  meta: {
    title: 'Configuration',
  },
  components: {
    ProjetPage,
    FileUpload,
  },
  mixins: [mixinProjet],
  data() {
    return {
      tab: 'tab1',
    }
  },
  methods: {


    async uploadLogo(event) {
      const data = new FormData()
      data.append('file', event.file)
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/logo`, data)
      this.selectedProjet = res.data
    },

    async modifierProjet() {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }`, { nom: this.selectedProjet.nom, description: this.selectedProjet.description })
      this.selectedProjet = res.data
    },
  },
}
</script>

