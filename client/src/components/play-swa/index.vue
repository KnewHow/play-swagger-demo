<template>
  <div>
    <div class="play-swa-header">
      <div class="title"> Play Swa </div>
    </div>
    <div class="content">
      <directory></directory>
    </div>
  </div>
</template>

<script>
import Directory from './directory'
import ApiDescrip from './apiDescrip'
export default {
  components: {
    Directory,
    ApiDescrip
  }
}
</script>

<style scoped>
.play-swa-header {
  background: rgb(233, 104, 107);
  height: 100px;
}

.play-swa-header .title {
  padding-top: 28px;
  padding-left: 50%;
  font-size: 37px;
  color: #fff;
  font-style: italic;
  font-weight: bold;
}
</style>
