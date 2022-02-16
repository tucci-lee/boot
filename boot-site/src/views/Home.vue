<template>
  <el-container style="height: 100%">
    <el-header style="background-color: #545c64">
      <Header></Header>
    </el-header>
    <el-container>
      <el-aside style="background-color: #545c64">
        <Menu></Menu>
      </el-aside>
      <el-main>
        <Tag></Tag>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Header from "../components/Header";
import Menu from "../components/Menu";
import Tag from "@/components/Tag";

export default {
  name: "Home",
  components: {
    Header, Menu, Tag
  },

}
</script>

<style scoped>

</style>