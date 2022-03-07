<template>
  <el-form inline>
    <el-form-item>
      <el-button type="primary" @click="openAddForm">添加</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="dept.data"
      v-loading="tableLoading"
      row-key="id"
      border>
    <el-table-column
        prop="name"
        label="部门名称">
    </el-table-column>
    <el-table-column
        prop="manager"
        label="部门负责人">
    </el-table-column>
    <el-table-column
        prop="managerPhone"
        label="负责人手机">
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
            @click="deleteDept(scope.row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog title="添加部门" width="30%" center v-model="show.add">
    <el-form :model="body.add" :rules="rules" ref="addForm" label-width="100px">
      <el-form-item label="上级部门" prop="pid">
        <el-cascader v-model="body.add.pid" :options="dept.data" :props="props.deptTree" clearable
                     placeholder="不选默认为顶级部门"></el-cascader>
      </el-form-item>
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="body.add.name"></el-input>
      </el-form-item>
      <el-form-item label="负责人" prop="manager">
        <el-input v-model="body.add.manager"></el-input>
      </el-form-item>
      <el-form-item label="负责人手机" prop="managerPhone">
        <el-input v-model="body.add.managerPhone"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="seq">
        <el-input v-model="body.add.seq" type="number"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="addDept()">添加</el-button>
      <el-button @click="closeAddForm">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="修改部门" width="30%" center v-model="show.edit">
    <el-form :model="body.edit" :rules="rules" ref="editForm" label-width="100px">
      <el-form-item label="上级部门" prop="pid">
        <el-cascader v-model="body.edit.pid" :options="dept.data" :props="props.deptTree" clearable
                     placeholder="不选默认为顶级部门"></el-cascader>
      </el-form-item>
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="body.edit.name"></el-input>
      </el-form-item>
      <el-form-item label="负责人" prop="manager">
        <el-input v-model="body.edit.manager"></el-input>
      </el-form-item>
      <el-form-item label="负责人手机" prop="managerPhone">
        <el-input v-model="body.edit.managerPhone"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="seq">
        <el-input v-model="body.edit.seq" type="number"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editDept()">修改</el-button>
      <el-button @click="closeEditForm">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import {treeNode} from "@/plugins/util";
import {deptAddUrl, deptDeleteUrl, deptEditUrl, deptListUrl} from "@/plugins/request";
export default {
  name: "SysDept",
  data() {
    return {
      tableLoading: true, 
      dept:{
        data: [],
      },
      show: {
        add: false,
        edit: false,
      },
      body: {
        add: {},
        edit: {}
      },
      rules: {          // 添加、修改的校验
        name: [
          {required: true, message: '请输入部门名称', trigger: 'blur'},
          {max: 20, message: '长度在20个字符', trigger: 'blur'}
        ],
        manager: [
          {required: true, message: '请输入部门负责人', trigger: 'blur'},
          {max: 20, message: '长度在20个字符', trigger: 'blur'}
        ],
        managerPhone: [
          {required: true, message: '请输入负责人手机', trigger: 'blur'},
          {pattern: '^[1][3-9][0-9]{9}$', message: '手机号格式不正确', trigger: 'blur'}
        ],
      },
      props:{
        deptTree: {
          checkStrictly: true,
          label: 'name',
          value: 'id',
        }
      }
    }
  },
  created() {
    this.loadDept();
  },
  methods: {
    loadDept() {
      this.tableLoading = true;
      this.$axios.get(deptListUrl)
          .then(resp => {
            this.tableLoading = false;
            if (!resp.status) {
              return;
            }
            let tableData = resp.data;
            this.dept.data = treeNode(tableData, "0");
          })
    },
    openAddForm() {
      this.show.add = true;
    },
    closeAddForm() {
      this.show.add = false;
    },
    addDept() {
      this.$refs['addForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        // 设置pid，cascader组件返回的是一个数组，包含所有父级，只取最后一级
        let pid = this.body.add.pid;
        if (pid) {
          this.body.add.pid = pid[pid.length - 1];
        }

        this.$axios.post(deptAddUrl, this.body.add)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("添加成功");
              // 隐藏添加的form
              this.closeAddForm();
              // 重置添加form中的数据，form-item中必须有prop属性才可以重置
              this.$refs['addForm'].resetFields();
              // 重新加载table
              this.loadDept();
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
    editDept() {
      this.$refs['editForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        let pid = this.body.edit.pid;
        if (Array.isArray(pid)) {
          this.body.edit.pid = pid[pid.length - 1];
        }

        if (this.body.edit.pid === this.body.edit.id) {
          this.$message.error("部门上级不可以是当前部门");
          return;
        }

        this.$axios.put(deptEditUrl, this.body.edit)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 隐藏修改的form
              this.closeEditForm();
              // 重新加载table
              this.loadDept();
            })
      });
    },
    deleteDept(dept) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        type: 'warning',
        center: true
      }).then(() => {
        this.$axios.delete(deptDeleteUrl + dept.id)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("删除成功");
              // 重新加载table
              this.loadDept();
            })
      })
    },
    createTimeFormatter(row, column, cellValue) {
      return this.$moment(parseInt(cellValue)).format("YYYY-MM-DD HH:mm:ss");
    }
  }
}
</script>

<style scoped>

</style>