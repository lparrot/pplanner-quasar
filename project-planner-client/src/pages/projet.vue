<template>
  <q-page padding>
    <q-card class="q-mb-xs" flat>
      <q-card-section>
        <div class="text-h6">Projets</div>
      </q-card-section>
      <q-card-section>
        <q-select :options="projets" @filter="searchProjets" clearable emit-value filled hide-dropdown-icon option-label="nom" v-model="selectedProjet">
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
      </q-card-section>
    </q-card>

    <template v-if="selectedProjet">
      <q-separator />

      <q-card flat>
        <q-card-section>
          <q-tabs align="justify" class="text-white bg-primary" dense v-model="tab">
            <q-tab icon="info" label="Informations" name="tab1" />
            <q-tab icon="calendar_today" label="Planning" name="tab2" />
            <q-tab icon="people" label="Equipe" name="tab3" />
          </q-tabs>

          <q-tab-panels animated transition-next="jump-up" transition-prev="jump-up" v-model="tab">
            <q-tab-panel name="tab1">
              <section class="text-center">
                <FileUpload @on-upload="uploadLogo">
                  <template v-slot="{fireChooseFile}">
                    <img :src="'data:image/*;base64,' + selectedProjet.logo" @click="fireChooseFile" alt="Logo" class="cursor-pointer" height="150" v-if="selectedProjet.logo != null" />
                  </template>
                </FileUpload>
                <p>
                  <section class="inline">
                    <span class="text-h2">{{ selectedProjet.nom }}</span>
                    <q-popup-edit @save="modifierProjet" v-model="selectedProjet.nom">
                      <q-input autofocus counter dense v-model="selectedProjet.nom" />
                    </q-popup-edit>
                  </section>
                </p>
                <p>
                  <section class="inline">
                    <span class="text-h4">{{ selectedProjet.description }}</span>
                    <q-popup-edit @save="modifierProjet" v-model="selectedProjet.description">
                      <q-input autofocus counter dense v-model="selectedProjet.description" />
                    </q-popup-edit>
                  </section>
                </p>
              </section>
            </q-tab-panel>

            <q-tab-panel name="tab2">

            </q-tab-panel>

            <q-tab-panel name="tab3">
              <q-select :options="membres" @filter="searchMembres" clearable filled hide-dropdown-icon multiple v-model="selectedMembres">
                <template v-slot:after>
                  <q-btn @click="ajouterMembres" dense flat icon="check" round />
                </template>
                <template v-slot:selected-item="scope">
                  <q-chip :tabindex="scope.tabindex" @remove="scope.removeAtIndex(scope.index)" color="white" dense removable text-color="secondary">
                    <q-avatar color="secondary" icon="people" text-color="white" />
                    {{ scope.opt.value.nom }}
                  </q-chip>
                </template>
                <template v-slot:option="scope">
                  <q-item v-bind="scope.itemProps" v-if="!scope.selected" v-on="scope.itemEvents">
                    <q-item-section>
                      <q-item-label v-text="scope.opt.value.nom" />
                      <q-item-label caption v-text="scope.opt.value.compagnie" />
                    </q-item-section>
                  </q-item>
                </template>
                <template v-slot:no-option>
                  <q-item>
                    <q-item-section class="text-grey">
                      Aucun membre
                    </q-item-section>
                  </q-item>
                </template>
              </q-select>

              <q-separator class="q-my-md" />

              <div class="row">
                <MembreDetail :membre="selectedProjet.proprietaire" @delete-membre="supprimerMembre" @show-informations="afficherInformations" class="col-xs-12 col-md-3" proprietaire />
                <MembreDetail :key="i" :membre="membre" @delete-membre="supprimerMembre" @show-informations="afficherInformations" class="col-xs-12 col-md-3" v-for="(membre,i) in selectedProjet.utilisateurs" />
              </div>
            </q-tab-panel>
          </q-tab-panels>
        </q-card-section>
      </q-card>
    </template>

    <q-dialog ref="dialogInformations">
      <template v-if="selectedMembre">
        <q-card style="width: 800px">
          <q-card-section>
            <q-list>
              <q-item>
                <q-item-section avatar>
                  <q-icon color="indigo-5" name="account_box" />
                </q-item-section>
                <q-item-section>
                  <q-item-label overline>NOM</q-item-label>
                  <q-item-label caption>{{ selectedMembre.nom }}</q-item-label>
                </q-item-section>
              </q-item>
              <q-item>
                <q-item-section avatar>
                  <q-icon color="indigo-5" name="mail" />
                </q-item-section>
                <q-item-section>
                  <q-item-label overline>E-MAIL</q-item-label>
                  <q-item-label caption>{{ selectedMembre.email }}</q-item-label>
                </q-item-section>
              </q-item>
              <q-item>
                <q-item-section avatar>
                  <q-icon color="indigo-5" name="account_circle" />
                </q-item-section>
                <q-item-section>
                  <q-item-label overline>NOM D'UTILISATEUR</q-item-label>
                  <q-item-label caption>{{ selectedMembre.username }}</q-item-label>
                </q-item-section>
              </q-item>
              <q-item>
                <q-item-section avatar>
                  <q-icon color="indigo-5" name="work" />
                </q-item-section>
                <q-item-section>
                  <q-item-label overline>COMPAGNIE</q-item-label>
                  <q-item-label caption>{{ selectedMembre.compagnie }}</q-item-label>
                </q-item-section>
              </q-item>
              <q-item>
                <q-item-section avatar>
                  <q-icon color="indigo-5" name="phone" />
                </q-item-section>
                <q-item-section>
                  <q-item-label overline>FIXE</q-item-label>
                  <q-item-label caption>{{ selectedMembre.fixe }}</q-item-label>
                </q-item-section>
              </q-item>
              <q-item>
                <q-item-section avatar>
                  <q-icon color="indigo-5" name="smartphone" />
                </q-item-section>
                <q-item-section>
                  <q-item-label overline>PORTABLE</q-item-label>
                  <q-item-label caption>{{ selectedMembre.portable }}</q-item-label>
                </q-item-section>
              </q-item>
            </q-list>
          </q-card-section>

          <q-card-actions>
            <q-space />
            <q-btn @click="$refs.dialogInformations.hide()" flat>Fermer</q-btn>
          </q-card-actions>
        </q-card>
      </template>
    </q-dialog>
  </q-page>
</template>

<script>
import FileUpload from 'components/FileUpload'
import MembreDetail from '../components/MembreDetail'

export default {
  name: 'projets',
  components: {
    MembreDetail,
    FileUpload,
  },
  data () {
    return {
      membres: null,
      projets: null,
      selectedProjet: null,
      selectedMembre: null,
      selectedMembres: null,
      tab: 'tab1',
    }
  },
  methods: {
    async searchProjets (val, update, abort) {
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

    async searchMembres (val, update, abort) {
      const res = await this.$axios.get(`/api/projets/${ this.selectedProjet.id }/membres`)
      this.membres = res.data.map(data => {
        return { value: data }
      })
      update()
    },

    async ajouterMembres () {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/membres`, this.selectedMembres.map(data => data.value.id))
      this.selectedProjet = res.data
      this.selectedMembres = null
      this.$q.notify('Les membres ont bien été ajoutés')
    },

    async supprimerMembre (event) {
      await this.$q.dialog({
        title: 'Confirmation',
        message: 'Etes vous sûr de vouloir supprimer ce membre ?',
        ok: {
          color: 'primary',
        },
        cancel: {
          color: 'negative',
        },
        persistent: true,
      }).onOk(async () => {
        const res = await this.$axios.delete(`/api/projets/${ this.selectedProjet.id }/membres/${ event.id }`)
        this.selectedProjet = res.data
        this.$q.notify('Le membre a bien été supprimé')
      })
    },

    afficherInformations (event) {
      this.selectedMembre = event
      this.$refs.dialogInformations.show()
    },

    async uploadLogo (event) {
      const data = new FormData()
      data.append('file', event.file)
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/logo`, data)
      this.selectedProjet = res.data
      this.$q.notify('Le logo a bien été modifié')
    },

    async modifierProjet () {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }`, { nom: this.selectedProjet.nom, description: this.selectedProjet.description })
      this.selectedProjet = res.data
      this.$q.notify('Les informations du projet ont bien été modifiées')
    },
  },
}
</script>

<style scoped>

</style>
