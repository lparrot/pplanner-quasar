<template>
  <div id="q-app">
    <router-view />
  </div>
</template>

<script>

export default {
  name: 'App',
  meta: {
    htmlAttr: {
      lang: 'fr',
    },
    // sets document title
    title: 'Accueil',
    // optional; sets final title as "Index Page - My Website", useful for multiple level meta
    titleTemplate: (title) => `${ title } - ${ process.env.APP_NAME }`,

    // meta tags
    meta: {
      keywords: { name: 'keywords', content: process.env.APP_NAME },
      equiv: { 'http-equiv': 'Content-Type', content: 'text/html; charset=UTF-8' },
    },

    // CSS tags
    link: {
      material: { rel: 'stylesheet', href: 'https://fonts.googleapis.com/icon?family=Material+Icons' },
    },

    // <noscript> tags
    noscript: {
      default: 'This is content for browsers with no JS (or disabled JS)',
    },
  },
}
</script>

<style></style>
