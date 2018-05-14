<template>
  <div class="content">
    <div class="directory">
      <div class="show">
        <div class="d-name">
          目录
        </div>
        <div  v-for="(name, index) in firstCateName" :key="index" @click="expandSecondCategory(index)">
          <div class="first-cate-name">
            <span>
              {{index+1}}
            </span>
            <span>
              {{name}}
            </span>
          </div>
          <div class="second-cate" v-if="isShowSecondCate(index)" data-test="secondCateFlag[index]">
            二级目录
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
      firstCate: null,
      secondCateFlag: null,
      flag: false
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
      var secondCateFlag = []
      for (var index in c) {
        console.log('index' + Object.keys(c[index]))
        firstCateName[index] = Object.keys(c[index])[0]
        firstCate[index] = c[index]
        secondCateFlag[index] = false
      }
      this.firstCateName = firstCateName
      this.firstCate = firstCate
      this.secondCateFlag = secondCateFlag
    },
    abstractSecondCate () {

    },
    expandSecondCategory (index) {
      this.secondCateFlag[index] = true
      this.flag = true
      console.log('lala' + this.secondCateFlag[index])
    },
    isShowSecondCate (index) {
      console.log('is' + (this.secondCateFlag[index] === true))
      return this.secondCateFlag[index] === true
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
  text-align: center;
  background-color: #F2F1F0;
  font-size: 30px;
  color: #85736D
}
.show {
  background-color: #F2F1F0;
}
.first-cate-name {
  cursor:pointer;
  font-size: 25px;
  margin: 10px 0px 10px 10px;
}
.detail {
  float: left;
}
</style>
