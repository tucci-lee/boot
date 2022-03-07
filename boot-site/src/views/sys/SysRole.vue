<template>
  <el-form inline>
    <el-form-item label="搜索">
      <el-input v-model="role.query.name" placeholder="角色名称" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="queryRole">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="openAddForm()">添加</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="role.data"
      v-loading="tableLoading"
      row-key="id"
      border>
    <el-table-column
        prop="name"
        label="角色名称">
    </el-table-column>
    <el-table-column
        prop="roleChar"
        label="角色字符">
    </el-table-column>
    <el-table-column
        prop="remarks"
        label="备注"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="createTime"
        label="创建时间"
        :formatter="createTimeFormatter">
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button
            @click="openResForm(scope.row)">资源
        </el-button>
        <el-button
            @click="openEditForm(scope.row)">编辑
        </el-button>
        <el-button
            type="danger"
            @click="deleteRole(scope.row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <div style="display: flex; justify-content: center;">
    <el-pagination
        background
        layout="prev, pager, next"
        :total="role.total"
        :current-page="role.query.pageNo"
        @current-change="pageRole">
    </el-pagination>
  </div>

  <el-dialog title="添加角色" width="30%" center v-model="show.add">
    <el-form :model="body.add" :rules="rules.add" ref="addForm" label-width="80px">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="body.add.name"></el-input>
      </el-form-item>
      <el-form-item label="角色字符" prop="roleChar">
        <el-input v-model="body.add.roleChar"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="body.add.remarks" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="资源" prop="resIds">
        <el-tree
            node-key="id"
            show-checkbox
            ref="addResTree"
            :data="res.data"
            :props="props.resTree"
        >
        </el-tree>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="addRole">添加</el-button>
      <el-button @click="closeAddForm()">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="修改角色" width="30%" center v-model="show.edit">
    <el-form :model="body.edit" :rules="rules.edit" ref="editForm" label-width="80px">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="body.edit.name"></el-input>
      </el-form-item>
      <el-form-item label="角色字符" prop="roleChar">
        <el-input v-model="body.edit.roleChar"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="body.edit.remarks" type="textarea"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editRole">修改</el-button>
      <el-button @click="closeEditForm">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="资源" width="30%" center v-model="show.editRes">
    <el-form :model="body.editRes" :rules="rules.editRes" ref="editResForm">
      <el-form-item prop="resIds">
        <el-tree
            node-key="id"
            show-checkbox
            ref="editResTree"
            :data="res.data"
            :default-checked-keys="body.editRes.resIds"
            :props="props.resTree"
        >
        </el-tree>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editRes()">修改</el-button>
      <el-button @click="closeResForm()">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import {treeNode} from "@/plugins/util";
import {
  resListUrl,
  roleAddUrl,
  roleDeleteUrl,
  roleEditUrl,
  roleListUrl,
  roleResEditUrl,
  roleResListUrl
} from "@/plugins/request";

export default {
  name: "SysRole",
  data() {
    return {
      tableLoading: true, // table加载
      role: { // 角色数据
        data: [],
        total: 0,
        query: {      // 查询数据
          name: '',
          pageNo: 1,
        },
      },
      res: { // 资源
        data: []
      },
      show: {     // 控制form显示
        add: false,
        edit: false,
        editRes: false
      },
      body: {  // body数据
        add: {},
        edit: {},
        editRes: {}
      },
      rules: {         // 数据校验
        add: {
          name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          roleChar: [
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          remarks: [
            {max: 200, message: '长度在200个字符', trigger: 'blur'}
          ],
          resIds: [
            {required: true, message: '请选择资源', trigger: 'blur'},
          ]
        },
        edit: {
          name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          roleChar: [
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          remarks: [
            {max: 200, message: '长度在200个字符', trigger: 'blur'}
          ],
        },
        editRes: {
          resIds: [
            {required: true, message: '请选择资源', trigger: 'blur'},
          ]
        },
      },
      props: {
        resTree: {
          children: 'children',
          label: 'name',
        }
      }
    }
  },
  created() {
    this.loadRole();
    this.loadRes();
  },
  methods: {
    loadRole() {
      this.tableLoading = true;

      this.$axios.get(roleListUrl, {
        params: this.role.query,
      }).then(resp => {
        this.tableLoading = false;
        if (!resp.status) {
          return;
        }
        this.role.data = resp.data;
        this.role.total = resp.total;
      })
    },
    pageRole(page) {
      this.role.query.pageNo = page;
      this.loadRole();
    },
    queryRole() {
      this.role.query.pageNo = 1;
      this.loadRole();
    },
    loadRes() {
      this.$axios.get(resListUrl)
          .then(resp => {
            if (!resp.status) {
              return;
            }
            let resData = resp.data;
            this.res.data = treeNode(resData, "0");
          })
    },
    openAddForm() {
      this.show.add = true;
    },
    closeAddForm() {
      this.show.add = false;
    },
    addRole() {
      let resTree = this.$refs['addResTree'];
      // 选中的节点
      let checkedResIds = resTree.getCheckedKeys();
      // 半选中的节点
      let checkedParentIds = resTree.getHalfCheckedKeys();
      this.body.add.resIds = checkedResIds.concat(checkedParentIds);

      this.$refs['addForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.post(roleAddUrl, this.body.add)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("添加成功");
              // 隐藏添加的form
              this.closeAddForm();
              // 重置添加form中的数据，form-item中必须有prop属性才可以重置
              this.$refs['addForm'].resetFields();
              // 清空资源tree中的选中数据
              this.$refs['addResTree'].setCheckedKeys([]);
              // 重新加载table
              this.loadRole();
            })
      });
    },
    openEditForm(role) {
      // 克隆当前行的数据给form表单数据，直接使用 = 是引用传递，修改form中的数据，table中的数据会跟着改变
      this.body.edit = JSON.parse(JSON.stringify(role));
      this.show.edit = true;
    },
    closeEditForm() {
      this.show.edit = false;
    },
    editRole() {
      this.$refs['editForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(roleEditUrl, this.body.edit)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 隐藏添加资源的form
              this.closeEditForm();
              // 重新加载table
              this.loadRole();
            })
      })
    },
    openResForm(role) {
      this.$axios.get(roleResListUrl + role.id)
          .then(resp => {
            if (resp.status) {
              this.body.editRes.resIds = this.getCheckedIds(this.res.data, resp.data);
            }
          })
      this.body.editRes.id = role.id;
      this.show.editRes = true;
    },
    closeResForm() {
      this.show.editRes = false;
      // 清空tree中的选中数据
      this.$refs['editResTree'].setCheckedKeys([]);
      // 清除校验
      this.$refs['editResForm'].clearValidate();
    },
    editRes() {
      let resTree = this.$refs['editResTree'];
      // 选中的节点
      let checkedResIds = resTree.getCheckedKeys();
      // 半选中的节点
      let checkedParentIds = resTree.getHalfCheckedKeys();

      this.body.editRes.resIds = checkedResIds.concat(checkedParentIds);

      this.$refs['editResForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(roleResEditUrl, this.body.editRes)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              this.closeResForm();
            })
      });
    },
    deleteRole(data) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        type: 'warning',
        center: true
      }).then(() => {
        this.$axios.delete(roleDeleteUrl + data.id)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("删除成功");
              this.loadRole();
            })
      })
    },
    /**
     * elementui的tree组件，选中父节点后默认所有字节也被选中。
     * 所以设置默认选中的数据时，只将所有的子节点选中，父节点默认变成半选中个
     *
     * @param resData 所有的资源数据
     * @param resIds 角色关联的资源id
     */
    getCheckedIds(resData, resIds) {
      let checkedIds = [];
      for (let i of resData) {
        if (i.children && i.children.length > 0) {
          let childArr = this.getCheckedIds(i.children, resIds);
          checkedIds = checkedIds.concat(childArr);
        } else {
          for (let j of resIds) {
            if (i.id === j) {
              checkedIds.push(i.id);
            }
          }
        }
      }
      return checkedIds;
    },
    createTimeFormatter(row, column, cellValue) {
      return this.$moment(parseInt(cellValue)).format("YYYY-MM-DD HH:mm:ss");
    },
  }
}
</script>

<style scoped>
/*form行内元素距离太远*/
.el-form--inline .el-form-item {
  margin-right: 10px;
}
</style>