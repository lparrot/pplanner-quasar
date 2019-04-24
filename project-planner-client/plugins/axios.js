export default function ({ $axios, redirect }) {
  $axios.onRequest((config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = 'Bearer ' + token;
    }
  });
}
