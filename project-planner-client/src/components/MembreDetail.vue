<template>
  <section class="q-pa-xs membre-details">
    <q-card bordered class="bg-grey-9" dark flat>
      <q-card-section class="text-center">
        <div class="text-h6">{{ membre.nom }}</div>
        <div class="text-subtitle1">{{ membre.email }}</div>
        <div class="text-subtitle2">{{ membre.username }}</div>
      </q-card-section>

      <q-separator inset />

      <q-card-section class="text-center">
        <q-list dark separator>
          <q-item>
            <q-item-section>
              <q-item-label>Compagnie</q-item-label>
              <q-item-label caption>{{ membre.compagnie }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item>
            <q-item-section>
              <q-item-label>Téléphone fixe</q-item-label>
              <q-item-label caption>{{ membre.fixe }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item>
            <q-item-section>
              <q-item-label>Téléphone portable</q-item-label>
              <q-item-label caption>{{ membre.portable }}</q-item-label>
            </q-item-section>
          </q-item>
        </q-list>
      </q-card-section>

      <q-card-actions>
        <q-btn @click="$emit('show-informations', membre)" flat icon round>
          <q-icon name="info" />
        </q-btn>
        <q-space />
        <q-btn @click="$emit('delete-membre', membre)" flat icon round v-if="!proprietaire">
          <q-icon name="delete" />
        </q-btn>
      </q-card-actions>
    </q-card>
  </section>
</template>

<script>
export default {
  name: 'MembreDetail',
  props: {
    membre: {
      type: Object,
      default: null,
    },
    proprietaire: {
      type: Boolean,
      default: false,
    },
  },
}
</script>
