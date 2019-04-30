import axios from 'axios'

const axiosInstance = axios.create()

export default async ({ Vue }) => {
  axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  })

  axiosInstance.interceptors.response.use(function (response) {
    return response
  }, function (error) {
    if (error.response.status === 401) {
      return error
    }
    return Promise.reject(error)
  })

  Vue.prototype.$axios = axiosInstance
}

export { axiosInstance }
