import { axiosInstance } from '../../boot/axios'

export async function update(context, projet) {
  context.commit('update', projet)

  // Récupération de la liste des statuts
  const res = await axiosInstance.get('/api/projets/statuts')
  context.commit('updateStatuts', res.data)
}

export async function updateGroupeOrder(context) {
  const groupeOrders = []
  let index = 1;
  context.state.selected.groupes.forEach(data => {
    groupeOrders.push({
      id: data.id,
      ordre: index
    })
    index++;
  })
  await axiosInstance.put(`/api/groupes/ordre`, groupeOrders)
}

export async function updateTacheOrder(context, groupe) {
  const tacheOrders = []
  let index = 1;
  groupe.taches.forEach(data => {
    tacheOrders.push({
      id: data.id,
      ordre: index
    })
    index++;
  })
  await axiosInstance.put(`/api/groupes/taches/ordre`, tacheOrders)
}
