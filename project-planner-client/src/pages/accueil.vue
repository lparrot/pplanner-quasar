<template>
  <q-page class="flex flex-center">
    <div class="column items-center">
      <img alt="PPlanner logo" src="statics/logo.png" />
      <div class="text-h2">{{ appName }}</div>
    </div>
  </q-page>
</template>

<style></style>

<script>
export default {
  name: 'PageIndex',
  computed: {
    appName () {
      return process.env.APP_NAME
    },
  },
}
</script>
