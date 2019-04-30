import VeeValidate, { Validator } from 'vee-validate'
import fr from 'vee-validate/dist/locale/fr'

export default async ({ Vue }) => {
  Vue.use(VeeValidate, {
    inject: true,
    fieldsBagName: 'veeFields',
  })
  Validator.localize('fr', fr)
}
