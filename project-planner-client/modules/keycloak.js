import path from 'path';

export default function keycloakModule(moduleOptions) {
  this.addPlugin({
    src: path.resolve(__dirname, 'keycloak', 'keycloak.plugin.js'),
    options: this.options.keycloak,
  });
}
