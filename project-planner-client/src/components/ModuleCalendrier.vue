<template>
  <FullCalendar :header="header" :locale="locale" :plugins="calendarPlugins" @dateClick="onDateClick" default-view="timeGridWeek" ref="calendar" />
</template>

<script>
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
  data: () => ({
    calendarPlugins: [dayGridPlugin, timeGridPlugin, interactionPlugin, listPlugin],
    locale: require('@fullcalendar/core/locales/fr'),
  }),
  computed: {
    header() {
      return this.$q.screen.gt.md ? { left: 'title', center: 'dayGridMonth,timeGridWeek,timeGridDay', right: 'today prev,next' } : { left: 'dayGridMonth,timeGridWeek,timeGridDay', right: 'today prev,next' }
    },
  },
  methods: {
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
