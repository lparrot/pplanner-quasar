const config = process.env.CONFIG

export default async ({ app, router, Vue, store }) => {
  const authService = new AuthService(store)
  Vue.prototype.$auth = authService

  if (localStorage.getItem(config.auth.storage.token)) {
    await authService.fetchUser()
  }

  router.beforeEach((to, from, next) => {
    if (to.meta.auth && store.state.auth.user == null) {
      // On stocke le nom de la route pour la redirection et on redirige vers la page de login
      localStorage.setItem(config.auth.storage.redirect, to.name)
      next({ name: 'login' })
      return
    }
    next()
  })
}

class AuthService {

  store = null
  user = null

  constructor (store) {
    this.store = store
  }

  async connect ({ username, password }) {
    await this.store.dispatch('auth/connexion', { username, password })
    await this.fetchUser()
  }

  async disconnect () {
    await this.store.dispatch('auth/deconnexion')
  }

  isLoggedIn () {
    return this.store.getters['auth/isLoggedIn']
  }

  async fetchUser () {
    await this.store.dispatch('auth/user')
    this.user = this.store.state.auth.user
  }

}
