<template>
  <v-card class="mx-auto white--text" color="blue-grey darken-2">
    <v-container>
      <div>
        <h3 class="headline mb-2">
          {{ membre.nom }}
        </h3>
        <div class="blue--text text--lighten-4 mb-2">{{ membre.email }}</div>
        <div class="blue--text text--lighten-4 subheading font-weight-bold">{{ membre.username }}</div>
      </div>
      <v-divider dark/>
      <v-layout text-xs-left wrap>
        <v-flex mb-2 mr-3 tag="strong" text-xs-right xs5>Company:</v-flex>
        <v-flex>{{ membre.compagnie}}</v-flex>
        <v-flex mb-2 mr-3 tag="strong" text-xs-right xs5>Fixe:</v-flex>
        <v-flex>{{ membre.fixe }}</v-flex>
        <v-flex mb-2 mr-3 tag="strong" text-xs-right xs5>Portable:</v-flex>
        <v-flex>{{ membre.portable }}</v-flex>
      </v-layout>
    </v-container>
    <v-card-actions>
      <v-btn dark icon @click="$emit('show-informations', membre.id)">
        <v-icon>info</v-icon>
      </v-btn>
      <v-spacer/>
      <v-btn v-if="!proprietaire" @click="$emit('supprimer-membre', membre.id)" dark icon>
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>
<script>
export default {
  name: 'MembreDetails',
  props: {
    membre: {
      type: Object,
      default: null,
    },
    supprimerMembre: {
      type: Function,
      default: null,
    },
    proprietaire: {
      type: Boolean,
      default: false
    }
  },
};
</script>
