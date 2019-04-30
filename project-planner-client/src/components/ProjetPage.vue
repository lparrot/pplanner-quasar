<template>
  <section>
    <template v-if="$store.state.projet.selected == null">
      <q-banner class="text-orange-7 bg-orange-1" inline-actions>
        Vous devez selectionner un projet dans la liste déroulante dans le bandeau en haut de page
      </q-banner>
    </template>

    <template v-else>
      <slot></slot>
    </template>
  </section>
</template>

<script>
export default {
  name: 'ProjetPage',
}
</script>

<style scoped>

</style>
