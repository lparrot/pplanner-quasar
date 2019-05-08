import { axiosInstance } from './axios'

export default async function ({ app, store, router, Vue }) {
  if (localStorage.getItem('selected_projet') != null) {
    try {
      const res = await axiosInstance.get(`/api/projets/${ localStorage.getItem('selected_projet') }`)
      store.dispatch('projet/update', res.data)
    } catch (e) {}
  }
}
