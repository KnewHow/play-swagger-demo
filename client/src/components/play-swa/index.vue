<template>
  <div>
    <div class="play-swa-header">
      <span class="title"> Play Swa </span>
    </div>
    <directory></directory>
    <apiDescrip></apiDescrip>
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
  background: green;
  height: 100px;
}

.play-swa-header .title {
  padding-top: 50%;
  padding-left: 50%;
  color: red;
}
</style>
