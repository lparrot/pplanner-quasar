import moment from 'moment'

export default async ({ Vue }) => {
  moment.locale('fr')
  Vue.prototype.$moment = moment
}
