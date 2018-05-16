<template>
  <div class="content">
    <div class="directory">
      <div class="show">
        <div class="d-name">
          目录
        </div>
        <div  v-for="(name, index) in firstCateName" :key="index">
          <div class="first-cate-name" @click="expandSecondCategory(index)">
            <span>
              {{index+1}}
            </span>
            <span>
              {{name}}
            </span>
          </div>
          <div class="second-cate" :id="'second-cate-' + index">
            <div v-for="(seName, seIndex) in abstractSecondCate(index)" :key="seIndex">
              <div class="second-cate-name" @click="expandThirdCategory(index,seIndex)" :tabindex="seIndex">
                <span>
                  {{index+1}}.{{seIndex+1}}
                </span>
                <span>
                  {{seName}}
                </span>
              </div>
              <div class="third-cate" :id="'third-cate-' + index + seIndex">
                <div v-for="(thName,thIndex) in abstractThirdCate(index,seIndex)" :key="thIndex">
                  <div class="third-cate-name" :tabindex="thIndex" @click="showApiDatail(index, seIndex, thIndex)">
                    <span>
                      {{index+1}}.{{seIndex+1}}.{{thIndex+1}}
                    </span>
                    <span>
                      {{thName}}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
    <div class="detail" v-if="isShowDetail">
      <div class="overview">
        <div class="descrip">{{api.describe}}</div>
        <div class="route">
          <span>路由：</span>
          <span>{{api.route}}</span>
        </div>
        <div class="method">
          <span>
            请求方式：
          </span>
          <span>
            {{api.method}}
          </span>
        </div>
      </div>

      <div class="parameters">
        <div class="request">
          <div>请求参数列表:</div>
          <div class="list">
            <table class="table" border="1">
              <tr>
                <th>参数名称</th>
                <th>参数类型</th>
                <th>描述</th>
              </tr>
              <tr v-for="(v,k) in api.req" :key="k">
                <td>{{k}}</td>
                <td>{{v.type}}</td>
                <td>{{v.describe}}</td>
              </tr>
            </table>
          </div>
        </div>

        <div class="response">
          <div>响应参数列表:</div>
          <div class="list">
            <table class="table" border="1">
              <tr>
                <th>参数名称</th>
                <th>参数类型</th>
                <th>描述</th>
              </tr>
              <tr v-for="(v,k) in api.res" :key="k">
                <td>{{k}}</td>
                <td>{{v.type}}</td>
                <td>{{v.describe}}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
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
      secondCateFlag: null,
      thirdCateFlag: null,
      api: Object,
      isShowDetail: false
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
      var thirdCateFlag = []
      for (var index in c) {
        var key = Object.keys(c[index])[0]
        firstCateName[index] = key
        firstCate[index] = c[index]
        secondCate[index] = c[index][key]
        secondCateFlag[index] = false
      }
      for (var i in c) {
        thirdCateFlag[i] = []
        for (var j in secondCate) {
          thirdCateFlag[i][j] = false
        }
      }
      this.firstCateName = firstCateName
      this.firstCate = firstCate
      this.secondCate = secondCate
      this.secondCateFlag = secondCateFlag
      this.thirdCateFlag = thirdCateFlag
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
    abstractThirdCate (firstIndex, secondIndex) {
      var thirdCate = []
      var se = this.secondCate[firstIndex][secondIndex]
      for (var k in se) {
        var thirdCateArr = se[k]
        for (var i in thirdCateArr) {
          thirdCate[i] = thirdCateArr[i].describe
        }
      }
      return thirdCate
    },
    expandSecondCategory (index) {
      this.secondCateFlag[index] = !this.secondCateFlag[index]
      if (!this.secondCateFlag[index]) {
        document.getElementById('second-cate-' + index).style.display = 'none'
      } else {
        document.getElementById('second-cate-' + index).style.display = 'block'
      }
    },
    expandThirdCategory (fi, si) {
      this.thirdCateFlag[fi][si] = !this.thirdCateFlag[fi][si]
      if (!this.thirdCateFlag[fi][si]) {
        document.getElementById('third-cate-' + fi + si).style.display = 'none'
      } else {
        document.getElementById('third-cate-' + fi + si).style.display = 'block'
      }
    },
    showApiDatail (fi, si, ti) {
      var se = this.secondCate[fi][si]
      for (var k in se) {
        var thirdCateArr = se[k]
        this.api = thirdCateArr[ti]
      }
      this.isShowDetail = true
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
.content:after {
  content: " ";
  display: block;
  clear: both;
  height: 0;
}
.content {
  zoom: 1;
}
.content {
  position: absolute;
  background-color: #fff;
  margin-top: 4px;
  height: 82%;
}
.directory {
  width: 300px;
  float: left;
  border-right: 5px solid #dadada;
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
  display: none;
}
.second-cate-name {
  cursor:pointer;
  font-size: 20px;
  margin: 10px 0px 10px 20px;
}
.third-cate {
  display: none;
}
.third-cate-name {
  cursor:pointer;
  font-size: 15px;
  margin: 10px 0px 10px 25px;
}
div:focus {
  background-color: rgb(233, 104, 107);
  color: #fff;
  border: 0px solid #dadada;
}
.detail {
  padding-top: 10px;
  float: left;
}
.overview {
  padding-left: 30px;
  width: 900px;
}
.descrip {
  text-align: center;
  font-size: 30px;
}
.route {
  margin: 50px 0px 10px 0px
}
.method {
  margin: 10px 0px 10px 0px;
}
.parameters {
  padding-left: 30px;
}
.table {
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  margin-top: 10px;
  width:500px;
}
.parameters table {
  border: 1px solid black;
}
.parameters th, .parameters td {
  font-size: 14px;
  border:1px solid #000;
  padding:3px 7px 2px 7px;
}
.parameters th {
  font-size: 14px;
  text-align: center;
  padding-top: 5px;
  padding-bottom: 4px;
  color: #fff;
  background-color: green;
}

.response {
  margin-top: 20px;
}

.h-show {
  margin:0px 20px 0px 10px
}
</style>
