const routes = [
  {
    path: '/',
    component: () => import('layouts/default.vue'),
    children: [
      { name: 'route_accueil', path: '', component: () => import('pages/accueil.vue') },
      { name: 'route_login', path: '/login', component: () => import('pages/login.vue') },
      { name: 'route_account', path: '/account', component: () => import('pages/account.vue'), meta: { auth: true } },
      { name: 'route_projet_configuration', path: '/projet/configuration', component: () => import('pages/projet/configuration.vue'), meta: { auth: true } },
      { name: 'route_projet_tache', path: '/projet/tache', component: () => import('pages/projet/tache.vue'), meta: { auth: true } },
      { name: 'route_referentiel_utilisateur', path: '/referentiels/utilisateurs', component: () => import('pages/referentiel/utilisateur'), meta: { auth: true } },
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
