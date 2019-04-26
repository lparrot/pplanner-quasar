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
            <v-list-tile-avatar>
              <v-img :src="'data:image/*;base64,' + item.logo" alt="Logo" v-if="item.logo != null"/>
            </v-list-tile-avatar>
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
          <v-container>
            <v-list subheader two-line>
              <v-list-tile>
                <v-list-tile-action>
                  <v-icon @click="modifierLogo = !modifierLogo" v-if="!modifierLogo">edit</v-icon>
                  <v-icon @click="modifierLogo = !modifierLogo" v-else>close</v-icon>
                </v-list-tile-action>
                <v-list-tile-avatar>
                  <v-img :src="'data:image/*;base64,' + selectedProjet.logo" alt="Logo" v-if="selectedProjet.logo != null"/>
                </v-list-tile-avatar>
                <v-list-tile-content>
                  <v-list-tile-sub-title>
                    <span v-if="!modifierLogo && selectedProjet.logo == null">Pas de logo</span>
                    <FileUpload @on-upload="uploadLogo" v-if="modifierLogo"/>
                  </v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-list>
          </v-container>
        </v-tab-item>

        <v-tab-item value="tab-2">
          <v-container>
            <v-layout row wrap>
              <v-flex class="pa-3 mb-3 feature-pane" lg3 sm12>
                <v-btn @click="$refs.calendar.prev()" absolute color="primary" fab left outline small>
                  <v-icon dark>
                    keyboard_arrow_left
                  </v-icon>
                </v-btn>
                <v-btn @click="$refs.calendar.next()" absolute color="primary" fab outline right small>
                  <v-icon dark>
                    keyboard_arrow_right
                  </v-icon>
                </v-btn>
                <br><br><br>
                <v-select :items="calendar.types" label="Affichage" v-model="calendar.type"/>
              </v-flex>
              <v-flex class="pl-3" lg9 sm12>
                <v-sheet height="500">
                  <v-calendar :type="calendar.type" :weekdays="[1, 2, 3, 4, 5, 6, 0]" @click:day="onCalendarDayClick" @click:time="onCalendarDayClick" color="primary" locale="fr" ref="calendar" v-model="calendar.start"/>
                </v-sheet>
              </v-flex>
            </v-layout>
          </v-container>
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
            <MembreProjet :selected-projet="selectedProjet" @on-delete="supprimerMembre"/>
          </v-container>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-container>
</template>

<script>
import MembreProjet from '../../components/MembreProjet';
import FileUpload from '../../components/FileUpload';

export default {
  name: 'ProjetsPage',
  components: { FileUpload, MembreProjet },
  data: () => ({
    membres: [],
    selectedMembres: null,
    membreInformations: null,
    projets: [],
    selectedProjet: null,
    modifierLogo: false,
    calendar: {
      start: null,
      type: null,
      types: [
        { text: 'Jour', value: 'day' },
        { text: 'Semaine', value: 'week' },
        { text: 'Mois', value: 'month' },
      ],
    },
  }),
  watch: {
    'calendar.type': {
      handler: function (after) {
        this.$router.replace({ query: { calendar_type: after } });
      },
    },
  },
  async created() {
    this.projets = await this.$axios.$get('/api/projets');
  },
  mounted() {
    this.calendar.type = this.$route.query.calendar_type || 'month';
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
    async uploadLogo(event) {
      const data = new FormData();
      data.append('file', event.file);
      this.selectedProjet = await this.$axios.$put(`/api/projets/${this.selectedProjet.id}/logo`, data);
      this.modifierLogo = false;
    },
    onCalendarDayClick(event) {
      this.calendar.start = event.date;
      this.calendar.type = 'day';
    },
  },
};
</script>

<style scoped>
  .feature-pane {
    position: relative;
    padding-top: 30px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  }
</style>
