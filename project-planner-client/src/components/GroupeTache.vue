<template>
  <section>
    <q-bar @click="clickAddGroupe" class="bg-primary text-white cursor-pointer text-caption q-mb-xs">
      <span class="text-weight-light text-italic">Ajouter un groupe</span>
    </q-bar>
    <draggable v-model="selectedProjet.groupes" @end="updateGroupeOrder">
      <template v-for="(groupe,i) in selectedProjet.groupes">
        <q-expansion-item :key="i" :label="groupe.nom" default-opened dense expand-icon-toggle expand-separator header-class="bg-indigo-2 text-indigo-8" switch-toggle-side>

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
              <q-markup-table bordered dense flat square v-if="groupe.taches.length" wrap-cells>
                <thead>
                <tr>
                  <th></th>
                  <th></th>
                  <th>Affecté à</th>
                  <th>Statut</th>
                </tr>
                </thead>
                <draggable tag="tbody" v-model="groupe.taches" @end="updateTacheOrder(groupe)">
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
                            <q-list dense>
                              <q-item :key="m" @click="affecterUtilisateur(tache.id, membre.id)" clickable v-close-popup v-for="(membre,m) in allMembres" v-ripple>
                                <q-item-section avatar>
                                  <q-avatar color="grey-4" size="24px">{{ membre.initiales }}</q-avatar>
                                </q-item-section>
                                <q-item-section :class="{'text-bold': membre.me}">{{ membre.nom }}</q-item-section>
                              </q-item>
                            </q-list>
                          </q-card>
                        </q-popup-proxy>
                      </q-btn>
                    </td>
                    <td :class="['cell-statut', 'text-center', 'cursor-pointer', 'bg-' + tache.statut.couleur]">
                      {{ tache.statut.nom }}
                      <q-popup-proxy>
                        <q-card flat>
                          <q-list dense>
                            <q-item :class="['bg-' + statut.couleur, 'q-ma-xs', 'text-overline']" :key="s" @click="changeStatut(tache.id, statut.id)" clickable v-close-popup v-for="(statut,s) in $store.state.projet.statuts" v-ripple>
                              {{ statut.nom }}
                            </q-item>
                          </q-list>
                        </q-card>
                      </q-popup-proxy>
                    </td>
                  </tr>
                </draggable>
              </q-markup-table>

              <div v-else>Il n'y a aucune tâche dans ce groupe</div>

              <q-bar @click="clickAddTache(groupe)" class="bg-primary text-white cursor-pointer text-caption q-mb-xs">
                <span class="text-weight-light text-italic">Ajouter une tache</span>
              </q-bar>
            </q-card-section>
          </q-card>
        </q-expansion-item>
      </template>
    </draggable>
  </section>
</template>

<script>
import draggable from 'vuedraggable'

export default {
  name: 'GroupeTache',
  components: {
    draggable,
  },
  computed: {
    allMembres() {
      const membres = []
      // Récupération du propriétaire et de tous les membres du projet
      membres.push(this.selectedProjet.proprietaire)
      this.selectedProjet.utilisateurs.forEach(data => membres.push(data))
      // On cherche l'élément qui correspond à l'utilisateur courant, on modifie son nom en 'Vous',
      // on le monte en haut de liste et on ajoute un boolean pour le retrouver facilement
      membres.find((x, i) => {
        if (x.username === this.$auth.user.sub) {
          membres.splice(i, 1)
          x.nom = 'Vous'
          x.me = true
          membres.unshift(x)
        }
      })
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
    clickAddGroupe() {
      this.$emit('on-add-groupe')
    },
    clickAddTache(groupe) {
      this.$emit('on-add-tache', groupe)
    },
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
      })
    },
    async affecterUtilisateur(tacheId, utilisateurId) {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/taches/${ tacheId }/utilisateurs/${ utilisateurId }`)
      this.selectedProjet = res.data
    },
    async changeStatut(tacheId, statutId) {
      const res = await this.$axios.put(`/api/projets/${ this.selectedProjet.id }/taches/${ tacheId }/statuts/${ statutId }`)
      this.selectedProjet = res.data
    },
    async updateGroupeOrder() {
      await this.$store.dispatch('projet/updateGroupeOrder')
    },
    async updateTacheOrder(groupe) {
      await this.$store.dispatch('projet/updateTacheOrder', groupe)
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
