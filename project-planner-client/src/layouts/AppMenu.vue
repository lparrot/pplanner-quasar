<template>
  <q-list dense>
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
</template>
<script>
export default {
  name: 'AppMenu',
  data() {
    return {
      menu: [
        { type: 'title', label: 'Menu' },
        { type: 'menu', icon: 'event', label: 'Configuration', to: 'route_projet_configuration', renderIf: () => this.$auth.user != null },
        { type: 'menu', icon: 'playlist_add_check', label: 'Tâches', to: 'route_projet_tache', renderIf: () => this.$auth.user != null },
        { type: 'separator' },
        { type: 'title', label: 'Référentiels' },
        { type: 'menu', icon: 'face', label: 'Utilisateurs', to: 'route_referentiel_utilisateur', renderIf: () => this.$auth.user != null },
      ],
    }
  },
}
</script>
