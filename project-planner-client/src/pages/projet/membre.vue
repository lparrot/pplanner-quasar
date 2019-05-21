<template>
  <q-page padding>
    <ProjetPage>
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
    </ProjetPage>
  </q-page>
</template>

<script>
import MembreDetail from 'src/components/MembreDetail'
import ProjetPage from 'src/components/ProjetPage'
import mixinProjet from 'src/mixins/projet'

export default {
  name: 'page-membre',
  meta: {
    title: 'Membres',
  },
  components: {
    MembreDetail,
    ProjetPage,
  },
  mixins: [mixinProjet],
  data: () => ({
    membres: null,
    selectedMembre: null,
    selectedMembres: null,
  }),
  methods: {
    async searchMembres(val, update, abort) {
      const res = await this.$axios.get(`/api/projets/${ this.selectedProjet.id }/membres`)
      this.membres = res.data.map(data => {
        return { value: data }
      })
      update()
    },

    async ajouterMembres() {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/membres`, this.selectedMembres.map(data => data.value.id))
      this.selectedProjet = res.data
      this.selectedMembres = null
    },

    async supprimerMembre(event) {
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
      })
    },

    afficherInformations(event) {
      this.selectedMembre = event
      this.$refs.dialogInformations.show()
    },
  },
}
</script>
