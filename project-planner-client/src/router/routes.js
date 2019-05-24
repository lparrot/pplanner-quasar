const empty = {
  template: `<router-view></router-view>`,
}

const routes = [
  {
    path: '/',
    component: () => import('layouts/default.vue'),
    children: [
      { name: 'route_accueil', path: '', component: () => import('pages/accueil.vue') },
      { name: 'route_login', path: 'login', component: () => import('pages/login.vue') },
      { name: 'route_account', path: 'account', component: () => import('pages/account.vue'), meta: { auth: true } },
      {
        name: 'route_projet', path: 'projet', component: empty, children: [
          { name: 'route_projet_information', path: 'information', component: () => import('pages/projet/information.vue'), meta: { auth: true } },
          { name: 'route_projet_membre', path: 'membre', component: () => import('pages/projet/membre.vue'), meta: { auth: true } },
          { name: 'route_projet_tache', path: 'tache', component: () => import('pages/projet/tache.vue'), meta: { auth: true } },
          { name: 'route_projet_activite', path: 'activite', component: () => import('pages/projet/activite.vue'), meta: { auth: true } },
          { name: 'route_projet_estimation', path: 'estimation', component: () => import('pages/projet/estimation.vue'), meta: { auth: true } },
          { name: 'route_projet_fichier', path: 'fichier', component: () => import('pages/projet/fichier.vue'), meta: { auth: true } },
        ],
      },
      {
        path: 'referentiels', component: empty, children: [
          { name: 'route_referentiel_utilisateur', path: 'utilisateurs', component: () => import('pages/referentiel/utilisateur'), meta: { auth: true } },
        ],
      },
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
