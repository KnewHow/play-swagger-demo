import Vue from 'vue'
import Router from 'vue-router'
import PlaySwa from '@/components/play-swa/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'PlaySwa',
      component: PlaySwa
    }
  ]
})
