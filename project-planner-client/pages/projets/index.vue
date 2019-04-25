<template>
  <v-container>
    <v-card class="mb-4">
      <v-card-title primary-title>
        <h3 class="headline mb-0">
          Selection du projet
        </h3>
      </v-card-title>
      <v-card-text>
        <v-autocomplete :filter="searchProjet" :items="projets" @change="rechercherMembres" box dense hint="Selectionnez un projet dans la liste ci-dessus" item-text="nom" label="Projets" persistent-hint return-object v-model="selectedProjet">
          <template v-slot:item="{item}">
            <v-list-tile-content>
              <v-list-tile-title>{{item.nom}}</v-list-tile-title>
              <v-list-tile-sub-title>{{item.description}}</v-list-tile-sub-title>
            </v-list-tile-content>
          </template>
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <v-card v-if="selectedProjet">
      <v-tabs centered dark icons-and-text>
        <v-tabs-slider color="yellow"/>

        <v-tab href="#tab-1">
          Informations
          <v-icon>info</v-icon>
        </v-tab>

        <v-tab href="#tab-2">
          Planning
          <v-icon>calendar_today</v-icon>
        </v-tab>

        <v-tab href="#tab-3">
          Equipe
          <v-icon>people</v-icon>
        </v-tab>

        <v-tab-item value="tab-1">
          <v-container>Tab 1</v-container>
        </v-tab-item>

        <v-tab-item value="tab-2">
          <v-container>Tab 2</v-container>
        </v-tab-item>

        <v-tab-item value="tab-3">
          <v-container>
            <v-autocomplete :filter="searchMembre" :items="membres" @click:append-outer="ajouterMembres" append-outer-icon="add_circle" box chips deletable-chips
                            dense hide-no-data hide-selected item-text="nom" label="Membres" multiple return-object v-model="selectedMembres"
            >
              <template v-slot:item="{item}">
                <v-list-tile-content>
                  <v-list-tile-title>{{item.nom}}</v-list-tile-title>
                </v-list-tile-content>
              </template>
            </v-autocomplete>
            <MembreProjet :selected-projet="selectedProjet" @on-delete="supprimerMembre" @on-show-informations="afficherInformations"/>
          </v-container>
        </v-tab-item>
      </v-tabs>
    </v-card>

    <v-dialog v-model="modalInformations">
      <v-card>
        <v-card-title class="headline">Informations du membre</v-card-title>
        <v-card-text>

        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" flat @click="modalInformations = false">
            Fermer
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import MembreProjet from '../../components/MembreProjet';

export default {
  name: 'ProjetsPage',
  components: { MembreProjet },
  data: () => ({
    membres: [],
    selectedMembres: null,
    membreInformations: null,
    projets: [],
    selectedProjet: null,
    modalInformations: false
  }),
  async created() {
    this.projets = await this.$axios.$get('/api/projets');
  },
  methods: {
    searchProjet(item, text) {
      // Si la recherche contient le libellé ou la description
      return item.nom.toLowerCase().indexOf(text.toLowerCase()) > -1 || item.description.toLowerCase().indexOf(text.toLowerCase()) > -1;
    },
    searchMembre(item, text) {
      // Si la recherche contient le libellé ou la description
      return item.nom.toLowerCase().indexOf(text.toLowerCase()) > -1;
    },
    async rechercherMembres() {
      this.membres = await this.$axios.$get(`/api/projets/${this.selectedProjet.id}/membres`);
    },
    async ajouterMembres() {
      this.selectedProjet = await this.$axios.$put(`/api/projets/${this.selectedProjet.id}/membres`, this.selectedMembres.map(m => m.id));
      this.selectedMembres = null;
      await this.rechercherMembres();
    },
    async supprimerMembre(id) {
      this.selectedProjet = await this.$axios.$delete(`/api/projets/${this.selectedProjet.id}/membres/${id}`);
      await this.rechercherMembres();
    },
    async afficherInformations(id) {
      this.membreInformations = await this.$axios.$get(`/api/projets/membres/${id}`);
      this.modalInformations = true;
    }
  },
};
</script>
