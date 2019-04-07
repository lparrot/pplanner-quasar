import VeeValidate, { Validator } from 'vee-validate';
import fr from 'vee-validate/dist/locale/fr';
import Vue from 'vue';

export default ({ app }) => {
  Vue.use(VeeValidate, {
    inject: true,
    fieldsBagName: 'veeFields',
  });
  Validator.localize('fr', fr);
}
