<template>
  <v-card class="mx-auto white--text" color="blue-grey darken-2">
    <v-card-text>
      <div>
        <h3 class="headline mb-2">
          {{ membre.nom }}
        </h3>
        <div class="blue--text text--lighten-4 mb-2">{{ membre.email }}</div>
        <div class="blue--text text--lighten-4 subheading font-weight-bold">{{ membre.username }}</div>
      </div>
      <v-divider dark/>
      <v-layout tag="v-card-text" text-xs-left wrap>
        <v-flex mb-2 mr-3 tag="strong" text-xs-right xs5>Company:</v-flex>
        <v-flex>Maison</v-flex>
        <v-flex mb-2 mr-3 tag="strong" text-xs-right xs5>Fixe:</v-flex>
        <v-flex>{{ membre.fixe}}</v-flex>
        <v-flex mb-2 mr-3 tag="strong" text-xs-right xs5>Portable:</v-flex>
        <v-flex>{{ membre.portable}}</v-flex>
      </v-layout>
    </v-card-text>
    <v-card-actions>
      <v-btn dark icon>
        <v-icon>info</v-icon>
      </v-btn>
      <v-spacer/>
      <v-btn @click="$emit('supprimer-membre', membre.id)" dark icon>
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
      default: null,
    },
    supprimerMembre: {
      type: Function,
      default: null,
    },
  },
};
</script>
