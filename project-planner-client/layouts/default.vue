<template>
  <v-app id="inspire">
    <v-navigation-drawer :clipped="$vuetify.breakpoint.lgAndUp" app fixed v-model="drawer">
      <v-list dense>
        <template v-for="item in items">
          <v-layout :key="item.heading" align-center row v-if="item.heading">
            <v-flex xs6>
              <v-subheader v-if="item.heading">
                {{ item.heading }}
              </v-subheader>
            </v-flex>
            <v-flex class="text-xs-center" xs6>
              <a class="body-2 black--text" href="#!">EDIT</a>
            </v-flex>
          </v-layout>
          <v-list-group :key="item.text" :prepend-icon="item.model ? item.icon : item['icon-alt']" append-icon="" v-else-if="item.children" v-model="item.model">
            <template v-slot:activator="">
              <v-list-tile>
                <v-list-tile-content>
                  <v-list-tile-title>
                    {{ item.text }}
                  </v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
            </template>
            <v-list-tile :key="i" :to="child.to" v-for="(child, i) in item.children">
              <v-list-tile-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>
                  {{ child.text }}
                </v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
          </v-list-group>
          <v-list-tile :key="item.text" :to="item.to" v-else>
            <v-list-tile-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>
                {{ item.text }}
              </v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar :clipped-left="$vuetify.breakpoint.lgAndUp" app dark flat fixed>
      <v-toolbar-title class="ml-0 pl-3" style="width: 300px">
        <v-toolbar-side-icon @click.stop="drawer = !drawer"/>
        <nuxt-link class="hidden-sm-and-down pointer" tag="span" to="/" v-text="title"/>
      </v-toolbar-title>
      <v-text-field class="hidden-sm-and-down" flat hide-details label="Search" prepend-inner-icon="search" solo-inverted/>
      <v-spacer/>
      <v-btn icon>
        <v-icon>apps</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>notifications</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>person</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <nuxt/>
    </v-content>
  </v-app>
</template>

<script>
import { Component, Vue } from 'vue-property-decorator';

@Component()
export default class LayoutPage extends Vue {
  drawer = null;
  title = 'PPlanner';
  items = [
    { icon: 'event', text: 'Projets', to: '/projets' },
    {
      icon: 'keyboard_arrow_up',
      'icon-alt': 'keyboard_arrow_down',
      text: 'Référentiels',
      model: true,
      children: [
        { icon: 'face', text: 'Utilisateurs', to: '/referentiels/utilisateurs' },
      ],
    },
  ];
};
</script>
