<template>
  <div class="fixed-center text-center">
    <p>
      <img src="~assets/sad.svg" style="width:30vw;max-width:150px;" />
    </p>
    <p class="text-faded">
      Désolé, l'adresse entrée est incorrecte ou la page n'existe pas...
      <strong>(404)</strong>
    </p>
    <q-btn @click="$router.push('/')" color="secondary" style="width:200px;">Page d'accueil</q-btn>
  </div>
</template>

<script>
export default {
  name: 'Error404',
}
</script>
