<template>
  <el-form inline>
    <el-form-item>
      <el-button type="primary" @click="openAddForm()">添加</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="res.data"
      v-loading="tableLoading"
      row-key="id"
      border>
    <el-table-column
        prop="name"
        label="资源名称">
    </el-table-column>
    <el-table-column
        prop="type"
        label="类型"
        :formatter="resTypeFormatter">
    </el-table-column>
    <el-table-column
        prop="url"
        label="url">
    </el-table-column>
    <el-table-column
        prop="resChar"
        label="权限字符">
    </el-table-column>
    <el-table-column
        prop="seq"
        label="排序">
    </el-table-column>
    <el-table-column
        prop="createTime"
        label="创建时间"
        :formatter="createTimeFormatter">
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button
            @click="openEditForm(scope.row)">编辑
        </el-button>
        <el-button
            type="danger"
            @click="deleteRes(scope.row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog title="添加资源" width="30%" center v-model="show.add">
    <el-form :model="body.add" :rules="rules.add" ref="addForm" label-width="80px">
      <el-form-item label="上级资源" prop="pid">
        <el-cascader v-model="body.add.pid" :options="res.menu" :props="props.menu" clearable
                     placeholder="不选默认顶级"></el-cascader>
      </el-form-item>
      <el-form-item label="资源名称" prop="name">
        <el-input v-model="body.add.name"></el-input>
      </el-form-item>
      <el-form-item label="资源类型" prop="type">
        <el-radio v-model="body.add.type" label="1">菜单</el-radio>
        <el-radio v-model="body.add.type" label="2">权限</el-radio>
      </el-form-item>
      <el-form-item label="url" prop="url">
        <el-input v-model="body.add.url"></el-input>
      </el-form-item>
      <el-form-item label="资源字符" prop="resChar">
        <el-input v-model="body.add.resChar"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="seq">
        <el-input v-model="body.add.seq" type="number"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="addRes()">添加</el-button>
      <el-button @click="closeAddForm()">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="修改资源" width="30%" center v-model="show.edit">
    <el-form :model="body.edit" :rules="rules.edit" ref="editForm" label-width="80px">
      <el-form-item label="上级资源" prop="pid">
        <el-cascader v-model="body.edit.pid" :options="res.menu" :props="props.menu" clearable
                     placeholder="不选默认顶级"></el-cascader>
      </el-form-item>
      <el-form-item label="资源名称" prop="name">
        <el-input v-model="body.edit.name"></el-input>
      </el-form-item>
      <el-form-item label="资源类型" prop="type">
        <el-radio v-model="body.edit.type" :label="1" disabled>菜单</el-radio>
        <el-radio v-model="body.edit.type" :label="2" disabled>权限</el-radio>
      </el-form-item>
      <el-form-item label="url" prop="url">
        <el-input v-model="body.edit.url"></el-input>
      </el-form-item>
      <el-form-item label="资源字符" prop="resChar">
        <el-input v-model="body.edit.resChar"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input v-model="body.edit.icon"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="seq">
        <el-input v-model="body.edit.seq" type="number"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editRes()">修改</el-button>
      <el-button @click="closeEditForm()">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import {treeNode} from "@/plugins/util";
import {resAddUrl, resDeleteUrl, resEditUrl, resListUrl} from "@/plugins/request";

export default {
  name: "SysRes",
  data() {
    return {
      tableLoading: true, // table加载
      res:{ 
        data: [],
        menu: [],
      },
      show: {             // 资源添加、修改dialog是否显示
        add: false,
        edit: false,
      },
      body: {           // 添加、修改资源的数据
        add: {},
        edit: {}
      },
      rules: {        
        add: {
          name: [
            {required: true, message: '请输入资源名称', trigger: 'blur'},
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请输入资源类型', trigger: 'blur'},
          ],
          url: [
            {max: 100, message: '长度在100个字符', trigger: 'blur'}
          ],
          resChar: [
            {max: 50, message: '长度在50个字符', trigger: 'blur'}
          ]
        },
        edit: {
          name: [
            {required: true, message: '请输入资源名称', trigger: 'blur'},
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          url: [
            {max: 100, message: '长度在100个字符', trigger: 'blur'}
          ],
          resChar: [
            {max: 50, message: '长度在50个字符', trigger: 'blur'}
          ]
        }
      },
      props:{
        menu: {
          checkStrictly: true,
          label: 'name',
          value: 'id',
        },
      }
    }
  },
  created() {
    this.loadRes();
  },
  methods: {
    loadRes() {
      this.tableLoading = true;
      this.$axios.get(resListUrl)
          .then(resp => {
            this.tableLoading = false;
            if (!resp.status) {
              return;
            }
            // 资源数据
            let tableData = resp.data;
            this.res.data = treeNode(tableData, "0");
            // 菜单数据
            tableData = tableData.filter(res => {
              if (res.type === 1) {
                return res;
              }
            })
            this.res.menu = treeNode(tableData, "0");
          })
    },
    openAddForm() {
      this.show.add = true;
    },
    closeAddForm() {
      this.show.add = false;
    },
    addRes() {
      this.$refs['addForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        // 设置pid，cascader组件返回的是一个数组，包含所有父级，只取最后一级
        let pid = this.body.add.pid;
        if (pid) {
          this.body.add.pid = pid[pid.length - 1];
        }

        this.$axios.post(resAddUrl, this.body.add)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("添加成功");
              // 隐藏添加资源的form
              this.closeAddForm();
              // 重置添加资源form中的数据，form-item中必须有prop属性才可以重置
              this.$refs['addForm'].resetFields();
              // 重新加载table
              this.loadRes();
            });
      });
    },
    openEditForm(data) {
      // 克隆当前行的数据给form表单数据，直接使用 = 是引用传递，修改form中的数据，table中的数据会跟着改变
      this.body.edit = JSON.parse(JSON.stringify(data));
      this.show.edit = true;
    },
    closeEditForm() {
      this.show.edit = false;
    },
    editRes() {
      this.$refs['editForm'].validate(valid => {
        if (!valid) {
          return false;
        }

        let pid = this.body.edit.pid;
        if (Array.isArray(pid)) {
          this.body.edit.pid = pid[pid.length - 1];
        }
        if (this.body.edit.pid === this.body.edit.id) {
          this.$message.error("资源上级不可以是当前资源");
          return;
        }
        this.$axios.put(resEditUrl, this.body.edit)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 隐藏添加资源的form
              this.closeEditForm();
              // 重新加载table
              this.loadRes();
            })
      });
    },
    deleteRes(data) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        type: 'warning',
        center: true
      }).then(() => {
        this.$axios.delete(resDeleteUrl + data.id)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("删除成功");
              // 重新加载table
              this.loadRes();
            })
      })
    },
    resTypeFormatter(row, column, cellValue) {
      if (cellValue === 1) {
        return "菜单";
      } else {
        return "权限";
      }
    },
    createTimeFormatter(row, column, cellValue) {
      return this.$moment(parseInt(cellValue)).format("YYYY-MM-DD HH:mm:ss");
    }
  }
}
</script>

<style scoped>

</style>