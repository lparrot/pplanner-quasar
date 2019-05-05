export default {
  computed: {
    selectedProjet: {
      get() {
        return this.$store.state.projet.selected
      },
      set(value) {
        this.$store.dispatch('projet/update', value)
      },
    },
  },
}
