export default async ({ app }, inject) => {
  const options = JSON.parse('<%= JSON.stringify(options) %>');
  if (process.client) {
    const Keycloak = await import('keycloak-js/dist/keycloak');
    inject('keycloak', new KeycloakService(options, Keycloak.default));
  }
}

class KeycloakService {
  constructor(config, instance) {
    this.auth = {};
    this.config = config;
    this.instance = instance;
  }

  init() {
    const keycloakAuth = this.instance({
      url: this.config.url,
      realm: this.config.realm,
      clientId: this.config.clientId,
      'ssl-required': this.config.sslRequired,
      'public-client': this.config.publicClient,
    });

    this.auth.loggedIn = false;

    return new Promise((resolve, reject) => {
      keycloakAuth.init({ onLoad: 'check-sso', checkLoginInFrame: false })
        .success(() => {
          this.auth.loggedIn = false;
          this.auth.authz = keycloakAuth;
          console.log(this.auth.authz.tokenParsed);
          resolve();
        })
        .error(() => {
          reject(new Error('Erreur lors de l\'authentification'));
        });
    });
  }

  getToken() {
    return new Promise((resolve, reject) => {
      if (this.auth.authz.token) {
        this.auth.authz
          .updateToken(5)
          .success(() => {
            resolve(this.auth.authz.token);
          })
          .error(() => {
            reject(new Error('Failed to refresh token'));
          });
      } else {
        reject(new Error('Not logged in'));
      }
    });
  }

  getFullName() {
    return this.auth.authz.tokenParsed.name;
  }

  login() {
    return new Promise((resolve, reject) => {
      this.auth.authz.login().success(
        () => {
          this.auth.loggedIn = true;
        },
      );
    });
  }

  logout() {
    this.auth.authz.logout({ redirectUri: document.baseURI }).success(() => {
      this.auth.loggedIn = false;
      this.auth.authz = null;
    });
  }

  getKeycloakAuth() {
    return this.auth.authz;
  }
}
