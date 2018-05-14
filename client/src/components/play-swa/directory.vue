<template>
  <div class="content">
    <div class="directory">
      <div class="show">
        <div class="d-name">
          目录
        </div>
        <div  v-for="(name, index) in firstCateName" :key="index">
          <div class="first-cate-name">
            <span>
              {{index+1}}
            </span>
            <span>
              {{name}}
            </span>
          </div>
        </div>
      </div>

    </div>
    <div class="detail">
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
.content {
  position: absolute;
  background-color: #fff;
  border: 1px solid #dadada;
  margin-top: 4px;
  height: 82%;
  border-right: 5px solid #dadada;
}
.directory {
  width: 300px;
  float: left;
}
.d-name {
  background-color: #F2F1F0;
  font-size: 30px;
  color: #85736D
}
.show {
  background-color: #F2F1F0;
}
.first-cate-name {
  font-size: 25px;
  margin: 10px 0px 10px 0px;
}
.detail {
  float: left;
}
</style>
