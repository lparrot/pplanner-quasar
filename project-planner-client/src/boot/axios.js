import axios from 'axios'
import { Notify } from 'quasar'

const axiosInstance = axios.create()

export default async ({ Vue }) => {
  axiosInstance.interceptors.request.use((config) => {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = 'Bearer ' + token
      }
      return config
    },
    (err) => {
      Notify.create({
        message: e.response.data,
      })
    })

  axiosInstance.interceptors.response.use((response) => {
      return response
    },
    (error) => {
      if (error.response.status === 401) {
        return error
      }
      Notify.create({
        message: error.response.data,
      })
      return Promise.reject(error)
    })

  Vue.prototype.$axios = axiosInstance
}

export { axiosInstance }
