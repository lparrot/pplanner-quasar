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
          <ProjetSelect dark style="width: 200px" />
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
      <AppMenu />
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import ProjetSelect from 'src/components/ProjetSelect'
import AppMenu from 'src/components/AppMenu'
import { bus } from '../utils/bus'

export default {
  name: 'DefaultLayout',
  components: { AppMenu, ProjetSelect },
  computed: {
    appName() {
      return process.env.APP_NAME
    },
  },
  data() {
    return {
      leftDrawerOpen: this.$q.platform.is.desktop,
    }
  },
  created() {
    bus.$on('projet-select-updated', (event) => {
      const route = this.$route.matched.find(data => data.name === 'route_projet')
      if (route == null) {
        this.$router.push({ name: 'route_projet_configuration' })
      }
    })
  },
  methods: {
    async logout() {
      await this.$auth.disconnect()
      this.$router.push({ name: 'route_login' })
    },
  },
}
</script>

