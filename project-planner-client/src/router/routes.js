const routes = [
  {
    path: '/',
    component: () => import('layouts/default.vue'),
    children: [
      { name: 'accueil', path: '', component: () => import('pages/accueil.vue') },
      { name: 'login', path: '/login', component: () => import('pages/login.vue') },
      { name: 'account', path: '/account', component: () => import('pages/account.vue'), meta: { auth: true } },
      { name: 'projet', path: '/projets', component: () => import('pages/projet.vue'), meta: { auth: true } },
      { name: 'referentiel_utilisateur', path: '/referentiels/utilisateurs', component: () => import('pages/referentiel/utilisateur'), meta: { auth: true } },
    ],
  },
]

// Always leave this as last one
if (process.env.MODE !== 'ssr') {
  routes.push({
    path: '*',
    component: () => import('pages/errors/404.vue'),
  })
}

export default routes
