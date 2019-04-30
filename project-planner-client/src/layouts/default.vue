<template>
  <q-layout view="lHh Lpr lFf">
    <q-header>
      <q-toolbar>
        <q-btn @click="leftDrawerOpen = !leftDrawerOpen" aria-label="Menu" dense flat round>
          <q-icon name="menu" />
        </q-btn>
        <q-toolbar-title>
          <q-btn :to="{name : 'accueil'}" dense flat>
            <q-avatar class="q-mr-md" size="36px">
              <img src="statics/logo.png">
            </q-avatar>
            {{ appName }}
          </q-btn>
        </q-toolbar-title>
        <q-btn class="q-mx-xs" dense flat round>
          <q-icon name="apps"></q-icon>
        </q-btn>
        <q-btn class="q-mx-xs" dense flat round>
          <q-badge color="red" floating>0</q-badge>
          <q-icon name="notifications"></q-icon>
        </q-btn>
        <q-btn class="q-mx-xs" dense flat round v-if="$auth.user">
          <q-icon name="face"></q-icon>
          <q-menu>
            <div class="row no-wrap q-pa-md">
              <div class="column">
                <div class="text-h6 q-mb-md">Compte</div>
                <q-toggle :value="true" label="Recevoir les notifications" />
                <q-toggle :value="false" label="Bluetooth" />
              </div>

              <q-separator class="q-mx-lg" inset vertical />

              <div class="column items-center">
                <router-link :to="{name: 'account'}" v-close-popup>
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
        <q-btn :to="{name: 'login'}" class="q-mx-xs" dense flat round v-else>
          <q-icon name="security" />
        </q-btn>
      </q-toolbar>
    </q-header>

    <q-drawer bordered content-class="bg-grey-2" v-model="leftDrawerOpen">
      <q-list>
        <template v-for="(m,i) in menu">
          <q-item-label :key="i" header v-if="m.type === 'title'">{{ m.label }}</q-item-label>
          <q-item :key="i" :to="{name: m.to}" clickable tag="a" v-if="m.type === 'menu'">
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
export default {
  name: 'DefaultLayout',
  computed: {
    appName () {
      return process.env.APP_NAME
    },
  },
  data () {
    return {
      menu: [
        { type: 'title', label: 'Menu' },
        { type: 'menu', icon: 'event', label: 'Projets', caption: 'Liste des projets', to: 'projet' },
        { type: 'separator' },
        { type: 'title', label: 'Référentiels' },
        { type: 'menu', icon: 'face', label: 'Utilisateurs', to: 'referentiel_utilisateur' },
      ],
      leftDrawerOpen: this.$q.platform.is.desktop,
    }
  },
  methods: {
    async logout () {
      await this.$auth.disconnect()
      this.$router.push({ name: 'login' })
    },
  },
}
</script>

<style></style>
