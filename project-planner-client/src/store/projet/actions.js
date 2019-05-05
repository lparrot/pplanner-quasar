import { axiosInstance } from '../../boot/axios'

export async function update(context, projet) {
  context.commit('update', projet)

  // Récupération de la liste des statuts
  const res = await axiosInstance.get('/api/projets/statuts')
  context.commit('updateStatuts', res.data)
}
