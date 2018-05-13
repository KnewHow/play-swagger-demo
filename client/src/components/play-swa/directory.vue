<template>
  <div>play-swa</div>
</template>

<script>
export default {
  data () {
    return {
      category: null
    }
  },
  methods: {
    initTempData () {
      this.$http.get('http://localhost:9000/apis').then(r => {
        console.log(r)
      })
    }
  },
  mounted () {
    this.initTempData()
    console.log('lala')
  }
}
</script>

<style scoped>

</style>
