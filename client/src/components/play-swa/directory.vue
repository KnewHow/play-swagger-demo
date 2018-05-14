<template>
  <div>
    <div v-for="(name, index) in firstCateName" :key="index">
      <span>{{name}}</span>
      <span>{{index}}</span>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      category: null,
      firstCateName: null,
      firstCate: null
    }
  },
  methods: {
    initTempData () {
      this.$http.get('http://localhost:9000/apis').then(response => {
        var c = response.body
        this.category = c
        console.log(this.category)
        this.initFirstCategory(c)
      }, response => {
      })
    },
    initFirstCategory (c) {
      var firstCateName = []
      var firstCate = []
      for (var index in c) {
        console.log('index' + Object.keys(c[index]))
        firstCateName[index] = Object.keys(c[index])[0]
        firstCate[index] = c[index]
      }
      this.firstCateName = firstCateName
      this.firstCate = firstCateName
    }
  },
  mounted () {
    this.initTempData()
  }
}
</script>

<style scoped>

</style>
