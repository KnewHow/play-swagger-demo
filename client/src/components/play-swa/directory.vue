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
          <div class="second-cate" :id="'second-cate-' + index">
            <div v-for="(seName, seIndex) in abstractSecondCate(index)" :key="seIndex">
              <span>
                {{seIndex+1}}
              </span>
              <span>
                {{seName}}
              </span>
            </div>
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
      secondCate: null,
      secondCateFlag: null
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
      var secondCate = []
      var secondCateFlag = []
      for (var index in c) {
        var key = Object.keys(c[index])[0]
        firstCateName[index] = key
        firstCate[index] = c[index]
        secondCate[index] = c[index][key]
        secondCateFlag[index] = false
      }
      this.firstCateName = firstCateName
      this.firstCate = firstCate
      this.secondCate = secondCate
      this.secondCateFlag = secondCateFlag
    },
    abstractSecondCate (index) {
      var secondName = []
      for (var i in this.secondCate[index]) {
        var se = this.secondCate[index][i]
        for (var k in se) {
          secondName[i] = k
        }
      }
      return secondName
    },
    expandSecondCategory (index) {
      this.secondCateFlag[index] = !this.secondCateFlag[index]
      if (!this.secondCateFlag[index]) {
        document.getElementById('second-cate-' + index).style.display = 'none'
      } else {
        document.getElementById('second-cate-' + index).style.display = 'block'
      }
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
.second-cate {
  display: none
}
.detail {
  float: left;
}
</style>
