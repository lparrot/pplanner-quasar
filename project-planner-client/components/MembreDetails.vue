<template>
  <section class="membre-details">
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
        <v-dialog v-model="modalInformations" width="800">
          <template v-slot:activator="{ on }">
            <v-btn dark icon v-on="on">
              <v-icon>info</v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="headline">Informations sur le membre {{membre.nom}}</v-card-title>
            <v-card-text>
              <v-list subheader two-line>

                <v-list-tile>
                  <v-list-tile-action>
                    <v-icon color="indigo">account_box</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>Nom</v-list-tile-title>
                    <v-list-tile-sub-title>{{membre.nom}}</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>

                <v-list-tile>
                  <v-list-tile-action>
                    <v-icon color="indigo">mail</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>E-mail</v-list-tile-title>
                    <v-list-tile-sub-title>{{membre.email}}</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>

                <v-list-tile>
                  <v-list-tile-action>
                    <v-icon color="indigo">account_circle</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>Nom d'utilisateur</v-list-tile-title>
                    <v-list-tile-sub-title>{{membre.username}}</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>

                <v-list-tile>
                  <v-list-tile-action>
                    <v-icon color="indigo">work</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>Compagnie</v-list-tile-title>
                    <v-list-tile-sub-title>{{membre.compagnie}}</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>

                <v-list-tile>
                  <v-list-tile-action>
                    <v-icon color="indigo">phone</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>Numéro de téléphone fixe</v-list-tile-title>
                    <v-list-tile-sub-title>{{membre.fixe}}</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>

                <v-list-tile>
                  <v-list-tile-action>
                    <v-icon color="indigo">smartphone</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>Numéro de téléphone portable</v-list-tile-title>
                    <v-list-tile-sub-title>{{membre.portable}}</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>

              </v-list>
            </v-card-text>
            <v-card-actions>
              <v-spacer/>
              <v-btn @click="modalInformations = false" color="green darken-1" flat>
                Fermer
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-spacer/>
        <v-btn @click="$emit('supprimer-membre', membre.id)" dark icon v-if="!proprietaire">
          <v-icon>delete</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </section>
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
      default: false,
    },
  },
  data: () => ({
    modalInformations: false,
  }),
};
</script>
