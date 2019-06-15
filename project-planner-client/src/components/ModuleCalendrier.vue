<template>
  <FullCalendar :header="header" :events="events" :locale="locale" :plugins="calendarPlugins" @dateClick="onDateClick" default-view="timeGridWeek" ref="calendar" />
</template>

<script>
import projetMixin from '../mixins/projet'
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import listPlugin from '@fullcalendar/list'

export default {
  name: 'ModuleCalendrier',
  components: {
    FullCalendar,
  },
  mixins: [projetMixin],
  data: () => ({
    events: [],
    calendarPlugins: [dayGridPlugin, timeGridPlugin, interactionPlugin, listPlugin],
    locale: require('@fullcalendar/core/locales/fr'),
  }),
  computed: {
    header() {
      return this.$q.screen.gt.md ? { left: 'title', center: 'dayGridMonth,timeGridWeek,timeGridDay', right: 'today prev,next' } : { left: 'dayGridMonth,timeGridWeek,timeGridDay', right: 'today prev,next' }
    },
  },
  async mounted() {
    this.getEvents()
  },
  methods: {
    async getEvents() {
      let api = this.$refs.calendar.getApi()
      const debut = this.$moment(api.state.dateProfile.currentRange.start).format('YYYY-MM-DD')
      const fin = this.$moment(api.state.dateProfile.currentRange.end).subtract(1, 'days').format('YYYY-MM-DD')
      const res = await this.$axios.get(`/api/projets/calendriers/${this.selectedProjet.id}/events`, { params: { debut, fin } })
      this.events = res.data.map(data => {
        return {
          title: data.nom,
          start: this.$moment(data.debut, 'YYYY-MM-DD HH:mm:ss').toDate(),
          end: this.$moment(data.fin, 'YYYY-MM-DD HH:mm:ss').toDate()
        }
      })
      console.log(this.events);
    },
    onDateClick(arg) {
      console.log(arg)
      let api = this.$refs.calendar.getApi()
      if (arg.view.type === 'dayGridMonth') {
        api.changeView('timeGridDay', arg.date)
      }
    },
  },
}
</script>

<style scoped>
  @import '~@fullcalendar/core/main.css';
  @import '~@fullcalendar/daygrid/main.css';
  @import '~@fullcalendar/timegrid/main.css';
</style>
