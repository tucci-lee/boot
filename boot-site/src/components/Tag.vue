<template>
  <el-tabs v-model="activeName" @tab-click="tagClick" @tab-remove="tagRemove" closable>
    <el-tab-pane v-for="tag in tags" :key="tag" :label="tag.lable" :name="tag.name">
      <!--  v-if="tag.name === activeName" 解决el-tab-pane的bug，会重复执行组件的初始化方法，created等
       :key="$route.path" 解决路由不跳转
       -->
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" v-if="tag.name === activeName" :key="$route.path"/>
        </keep-alive>
      </router-view>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  name: "Tag",
  data() {
    return {
      activeName: '/index',
      tags: [{lable: '首页', name: '/index'}],
    }
  },
  created() {
    this.init();
  },
  methods: {
    /**
     * 初始化，添加当前路由到tags
     */
    init() {
      let route = this.$route;
      this.tagAdd(route);
    },
    /**
     * 添加tag
     * @param route 路由
     */
    tagAdd(route) {
      let addTag = {lable: route.name, name: route.path};
      this.activeName = addTag.name;
      // 如果是相同的路由地址，不添加
      if (this.tags.some(tag => tag.name === addTag.name)) {
        return;
      }
      // 添加到tags数组中，并切换选中的tag
      this.tags.push(addTag);
    },
    /**
     * 点击tag
     * @param tag tag
     */
    tagClick(tag) {
      // 点击tag，切换选中的tag，路由也更换
      this.activeName = tag.props.name;
      this.$router.push(tag.props.name);
    },
    /**
     * 删除tag
     * @param name tag的name
     */
    tagRemove(name) {
      // 不能删除首页
      if (name === '/index') {
        return;
      }
      // 获取删除的tag下标并删除
      let index = this.tags.findIndex(tag => tag.name === name);
      this.tags.splice(index, 1);
      // 当前选中的tag更换为上一个tag下标
      this.activeName = this.tags[index - 1].name;
      // 路由更换为上一个tag的地址
      this.$router.push(this.activeName);
    }
  },
  watch: {
    /**
     * 监听路由切换
     * @param to
     */
    $route(to) {
      this.tagAdd(to);
    }
  }
}
</script>

<style scoped>
.el-tabs{
  height: 100%;
}
.el-tabs__content {
  height: 100%;
}
.el-tab-pane {
  height: 100%;
}
</style>