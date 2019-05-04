<template>
  <section>
    <q-expansion-item :key="i" :label="groupe.nom" default-opened dense expand-icon-toggle expand-separator header-class="bg-indigo-2 text-indigo-8" switch-toggle-side v-for="(groupe,i) in selectedProjet.groupes">

      <template v-slot:header>
        <q-item-section>
          <span class="text-bold">{{ groupe.nom }}</span>
        </q-item-section>
        <q-item-section avatar>
          <q-btn @click="supprimerGroupe(groupe.id)" dense flat round size="sm">
            <q-icon name="delete" />
          </q-btn>
        </q-item-section>
      </template>

      <q-card flat>
        <q-card-section class="text-indigo-8">
          <q-markup-table bordered dense flat v-if="groupe.taches.length" wrap-cells>
            <thead>
            <tr>
              <th></th>
              <th></th>
              <th>Affecté à</th>
              <th>Statut</th>
            </tr>
            </thead>
            <tbody>
            <tr :key="j" v-for="(tache,j) in groupe.taches">
              <td class="text-indigo-8 text-center cell-button">
                <q-btn @click="supprimerTache(tache.id)" dense flat icon="delete" size="sm" />
              </td>
              <td>
                <div class="text-bold">{{ tache.titre }}</div>
                <div class="text-indigo-3" v-if="$q.screen.gt.md">{{ tache.description }}</div>
              </td>
              <td class="cell-membre text-center">
                <q-btn flat icon round>
                  <template v-if="tache.utilisateur">
                    <q-avatar color="blue-8" size="24px" text-color="white">
                      <span>{{ tache.utilisateur.initiales }}</span>
                      <q-tooltip>
                        Parrot Laurent
                      </q-tooltip>
                    </q-avatar>
                  </template>
                  <template v-else>
                    <q-avatar color="grey-4" size="24px">
                      <span>?</span>
                    </q-avatar>
                  </template>
                  <q-popup-proxy>
                    <q-card flat>
                      <q-card-section>
                        <q-list dense>
                          <q-item :key="m" @click="affecterUtilisateur(tache.id, membre.id)" clickable v-close-popup v-for="(membre,m) in allMembres" v-ripple>
                            <q-item-section>{{ membre.nom }}</q-item-section>
                          </q-item>
                        </q-list>
                      </q-card-section>
                    </q-card>
                  </q-popup-proxy>
                </q-btn>
              </td>
              <td class="cell-statut text-center bg-blue-6 text-white">A réaliser</td>
            </tr>
            </tbody>
          </q-markup-table>
          <div v-else>Il n'y a aucune tâche dans ce groupe</div>
        </q-card-section>
      </q-card>
    </q-expansion-item>
  </section>
</template>

<script>
import { Notify } from 'quasar'

export default {
  name: 'GroupeTache',
  computed: {
    allMembres() {
      const membres = []
      membres.push(this.selectedProjet.proprietaire)
      this.selectedProjet.utilisateurs.forEach(data => membres.push(data))
      return membres
    },
    selectedProjet: {
      get() {
        return this.$store.state.projet.selected
      },
      set(value) {
        this.$store.dispatch('projet/update', value)
      },
    },
  },
  methods: {
    async supprimerTache(tacheId) {
      await this.$q.dialog({
        title: 'Confirmation',
        message: 'Etes vous sûr de vouloir supprimer cette tâche ?',
        ok: {
          color: 'negative',
          label: 'Supprimer la tâche',
        },
        cancel: {
          color: 'primary',
        },
        persistent: true,
      }).onOk(async () => {
        const res = await this.$axios.delete(`/api/projets/${ this.selectedProjet.id }/taches/${ tacheId }`)
        this.selectedProjet = res.data
        this.$q.notify('Tâche supprimée')
      })
    },
    async supprimerGroupe(id) {
      await this.$q.dialog({
        title: 'Confirmation',
        message: 'Etes vous sûr de vouloir supprimer ce groupe ainsi que toutes les tâches associées (Cette action est irréversible) ?',
        ok: {
          color: 'negative',
          label: 'Supprimer le groupe',
        },
        cancel: {
          color: 'primary',
        },
        persistent: true,
      }).onOk(async () => {
        const res = await this.$axios.delete(`/api/projets/${ this.selectedProjet.id }/groupes/${ id }`)
        this.selectedProjet = res.data
        this.$q.notify('Le groupe a bien été supprimé')
      })
    },
    async affecterUtilisateur(tacheId, utilisateurId) {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/taches/${ tacheId }/utilisateurs/${ utilisateurId }`)
      this.selectedProjet = res.data
      Notify.create({ message: 'Utilisateur affecté à la tâche' })
    },
  },
}
</script>
<style lang="scss" scoped>
  .cell-button {
    width: 70px;
  }

  .cell-membre {
    width: 100px;
  }

  .cell-statut {
    width: 100px;
  }
</style>
