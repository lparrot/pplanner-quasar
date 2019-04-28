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
    <v-toolbar :clipped-left="$vuetify.breakpoint.lgAndUp" app dark fixed flat>
      <v-toolbar-title class="ml-0 pl-3" style="width: 300px">
        <v-toolbar-side-icon @click.stop="drawer = !drawer"/>
        <nuxt-link class="hidden-sm-and-down pointer" tag="span" to="/">
          <v-avatar size="36px" tile><img src="~/static/logo.png"></v-avatar>
          {{ title }}
        </nuxt-link>
      </v-toolbar-title>
      <v-text-field class="hidden-sm-and-down" flat hide-details label="Search" prepend-inner-icon="search" solo-inverted/>
      <v-spacer/>
      <v-btn icon>
        <v-icon>apps</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>notifications</v-icon>
      </v-btn>

      <v-menu bottom lazy left offset-y transition="slide-y-transition" v-if="$auth.loggedIn">
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>person</v-icon>
          </v-btn>
        </template>

        <v-list dense>
          <v-list-tile to="/account">
            <v-list-tile-title>Compte</v-list-tile-title>
          </v-list-tile>
          <v-list-tile @click="logout">
            <v-list-tile-title>Deconnexion</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>
      <v-btn icon to="/login" v-else>
        <v-icon>security</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <nuxt/>
    </v-content>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    isAuthenticated: false,
    drawer: null,
    title: process.env.title,
    items: [
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
    ],
    profile: {},
  }),
  methods: {
    async logout() {
      await this.$auth.logout();
      this.$router.push('/login');
    },
  },
}
;
</script>
