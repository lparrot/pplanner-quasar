<template>
  <q-layout view="lHh Lpr lFf">
    <q-header>
      <q-toolbar>
        <q-btn @click="leftDrawerOpen = !leftDrawerOpen" aria-label="Menu" dense flat round>
          <q-icon name="menu" />
        </q-btn>
        <q-toolbar-title>
          <q-btn :to="{name : 'route_accueil'}" dense flat>
            <q-avatar class="q-mr-md" size="36px">
              <img src="statics/logo.png">
            </q-avatar>
            <span v-if="$q.screen.gt.xs">{{ appName }}</span>
          </q-btn>
        </q-toolbar-title>
        <div v-if="$auth.user && $q.screen.gt.xs">
          <ProjetSelect @input="selectProjet" dark style="width: 200px" />
        </div>
        <q-btn class="q-mx-xs" dense flat round>
          <q-icon name="apps" />
        </q-btn>
        <q-btn class="q-mx-xs" dense flat round>
          <q-badge color="red" floating>0</q-badge>
          <q-icon name="notifications" />
        </q-btn>
        <q-btn class="q-mx-xs" dense flat round v-if="$auth.user">
          <q-icon name="face" />
          <q-menu>
            <div class="row no-wrap q-pa-md">
              <div class="column">
                <div class="text-h6 q-mb-md">Compte</div>
                <q-toggle :value="true" label="Recevoir les notifications" />
                <q-toggle :value="false" label="Bluetooth" />
              </div>

              <q-separator class="q-mx-lg" inset vertical />

              <div class="column items-center">
                <router-link :to="{name: 'route_account'}" v-close-popup>
                  <q-avatar size="72px">
                    <img src="http://i.pravatar.cc/64">
                  </q-avatar>
                </router-link>

                <div class="text-subtitle1 q-mt-md q-mb-xs">{{ $auth.user.sub }}</div>

                <q-btn @click="logout" color="primary" label="Deconnexion" push size="sm" v-close-popup />
              </div>
            </div>
          </q-menu>
        </q-btn>
        <q-btn :to="{name: 'route_login'}" class="q-mx-xs" dense flat round v-else>
          <q-icon name="security" />
        </q-btn>
      </q-toolbar>
      <q-toolbar v-if="$auth.user && $q.screen.lt.sm">
        <ProjetSelect @input="selectProjet" class="full-width" dark />
      </q-toolbar>
    </q-header>

    <q-drawer bordered content-class="bg-grey-2" v-model="leftDrawerOpen">
      <q-list>
        <template v-for="(m,i) in menu">
          <q-item-label :key="i" header v-if="m.type === 'title'">{{ m.label }}</q-item-label>
          <q-item :key="i" :to="{name: m.to}" clickable tag="a" v-if="m.type === 'menu' && (m.renderIf == null || m.renderIf() == true)">
            <q-item-section avatar>
              <q-icon :name="m.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ m.label }}</q-item-label>
              <q-item-label caption v-if="m.caption">{{ m.caption }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-separator :key="i" v-if="m.type === 'separator'" />
        </template>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import ProjetSelect from '../components/ProjetSelect'

export default {
  name: 'DefaultLayout',
  components: { ProjetSelect },
  computed: {
    appName () {
      return process.env.APP_NAME
    },
  },
  data () {
    return {
      menu: [
        { type: 'title', label: 'Menu' },
        { type: 'menu', icon: 'event', label: 'Configuration', to: 'route_projet_configuration', renderIf: () => this.$auth.user != null },
        { type: 'separator' },
        { type: 'title', label: 'Référentiels' },
        { type: 'menu', icon: 'face', label: 'Utilisateurs', to: 'route_referentiel_utilisateur', renderIf: () => this.$auth.user != null },
      ],
      leftDrawerOpen: this.$q.platform.is.desktop,
    }
  },
  methods: {
    async logout () {
      await this.$auth.disconnect()
      this.$router.push({ name: 'route_login' })
    },
    selectProjet (event) {
      this.$router.push({ name: 'route_projet_configuration' })
    },
  },
}
</script>

<style></style>
