<template>
  <q-list dense>
    <template v-for="(m,i) in menu">
      <q-item-label :key="i" header v-if="m.type === 'title' && (m.renderIf == null || m.renderIf() == true)">{{ m.label }}</q-item-label>
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
import { mapState } from 'vuex'
import mixinProjet from 'mixins/projet'

export default {
  name: 'AppMenu',
  mixins: [mixinProjet],
  data() {
    return {
      menu: [
        { type: 'title', label: 'Projet', renderIf: () => this.user != null && this.selectedProjet != null },
        { type: 'menu', icon: 'event', label: 'Configuration', to: 'route_projet_configuration', renderIf: () => this.user != null && this.selectedProjet != null },
        { type: 'menu', icon: 'playlist_add_check', label: 'Tâches', to: 'route_projet_tache', renderIf: () => this.user != null && this.selectedProjet != null },
        { type: 'menu', icon: 'fas fa-database', label: 'Activité', to: 'route_projet_activite', renderIf: () => this.user != null && this.selectedProjet != null },
        { type: 'separator' },
        { type: 'title', label: 'Référentiels', renderIf: () => this.user != null },
        { type: 'menu', icon: 'face', label: 'Utilisateurs', to: 'route_referentiel_utilisateur', renderIf: () => this.user != null },
      ],
    }
  },
  computed: {
    ...mapState('auth', ['user']),
  },
}
</script>
