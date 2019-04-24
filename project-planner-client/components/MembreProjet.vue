<template>
  <v-item-group>
    <v-container grid-list-md>
      <v-layout wrap>
        <v-flex :key="i" md3 v-for="(membre,i) in selectedProjet.utilisateurs" xs12>
          <v-item>
            <v-flex d-flex text-xs-center>
              <MembreDetails :membre="membre" @supprimer-membre="supprimerMembre"/>
            </v-flex>
          </v-item>
        </v-flex>
      </v-layout>
    </v-container>
  </v-item-group>
</template>

<script>
import MembreDetails from './MembreDetails';

export default {
  name: 'MembresProjet',
  components: { MembreDetails },
  props: {
    selectedProjet: {
      type: Object,
      default: null,
    },
  },
  methods: {
    supprimerMembre(id) {
      this.$emit('on-delete', id);
    },
  },
};
</script>
