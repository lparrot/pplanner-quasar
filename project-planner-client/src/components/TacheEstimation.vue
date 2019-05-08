<template>
  <section>
    <div :class="{'bg-grey-2': i % 2 === 0}" :key="i" class="row q-pa-sm items-center" v-for="(tache, i) in taches">
      <div class="col-6 col-md-9">
        <div class="caption text-grey-6">{{ tache.groupe }}</div>
        <div>{{ tache.titre }}</div>
      </div>
      <div class="col-2 col-md-1 text-center">
        <q-avatar class="cursor-pointer" color="blue" size="24px" text-color="white" v-if="tache.utilisateur">
          {{ tache.utilisateur.initiales }}
          <q-tooltip>{{ tache.utilisateur.nom }}</q-tooltip>
        </q-avatar>
        <q-avatar class="cursor-pointer" color="grey-4" size="24px" v-else>
          ?
          <q-tooltip>Non attribué</q-tooltip>
        </q-avatar>
      </div>
      <div class="col-4 col-md-2">
        <q-input :error="errors.has('estimation' +i)" :name="'estimation' + i" data-vv-as="estimation" dense filled v-model="tache.estimation" v-validate="{regex: /^(-?)(0|([1-9][0-9]*))(\.[0-9]+)?$/}">
          <template v-slot:prepend>
            <q-icon name="fas fa-clock" />
          </template>
        </q-input>
      </div>
    </div>
  </section>
</template>
<script>
export default {
  name: 'TacheEstimation',
  props: {
    taches: {},
  },
}
</script>
