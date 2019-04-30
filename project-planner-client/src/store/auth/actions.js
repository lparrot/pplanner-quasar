import { axiosInstance } from '../../boot/axios'

const config = process.env.CONFIG

export async function connexion (ctx, { username, password }) {
  try {
    const res = await axiosInstance.post(config.auth.api.login, { username, password })
    localStorage.setItem(config.auth.storage.token, res.data.token)
  } catch (e) {
    Vue.prototype.$q.notify(e.message)
  }
}

export async function user (ctx) {
  if (localStorage.getItem(config.auth.storage.token)) {
    const res = await axiosInstance.get(config.auth.api.me)
    ctx.commit('user', res.data.user)
    localStorage.removeItem(config.auth.storage.redirect)
  }
}

export async function deconnexion (ctx) {
  localStorage.removeItem(config.auth.storage.token)
  ctx.commit('user', null)
}
