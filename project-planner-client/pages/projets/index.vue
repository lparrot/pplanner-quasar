<template>
  <v-container>
    <v-card class="mb-4">
      <v-card-title primary-title>
        <h3 class="headline mb-0">
          Selection du projet
        </h3>
      </v-card-title>
      <v-card-text>
        <v-autocomplete :filter="search" :items="projets" box hint="Selectionnez un projet dans la liste ci-dessus" item-text="label" label="Projets" persistent-hint return-object v-model="projet">
          <template v-slot:item="{item}">
            <v-list-tile-content>
              <v-list-tile-title>{{item.label}}</v-list-tile-title>
              <v-list-tile-sub-title>{{item.description}}</v-list-tile-sub-title>
            </v-list-tile-content>
          </template>
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <v-card v-if="projet">
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
          <v-container>Tab 3</v-container>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-container>
</template>

<script>
import { Component, Vue } from 'vue-property-decorator';

@Component()
export default class ProjetIndexPage extends Vue {
  projets = [
    { id: 1, label: 'CCS', description: 'Carte de Circulation Sécurisée' },
    { id: 1, label: 'VDD', description: 'Valise Diplomatique Défense' },
    { id: 1, label: 'PRDV', description: 'Eureka - Module Prise de Rendez-Vous' },
  ];
  projet = null;

  search(item, text) {
    // Si la recherche contient le libellé ou la description
    return item.label.toLowerCase().indexOf(text.toLowerCase()) > -1 || item.description.toLowerCase().indexOf(text.toLowerCase()) > -1;
  }
}
</script>
