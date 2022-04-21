<template>
  <iframe :src="druidPath" width="100%" height="1000px" frameborder="no"></iframe>
</template>

<script>
export default {
  name: "Druid",
  data() {
    return {
      druidPath: this.$axios.defaults.baseURL + "/druid/index.html"
    }
  }
}
</script>

<style scoped>

</style>