<template>
  <el-form inline :model="user.query">
    <el-form-item label="搜索">
      <el-input v-model="user.query.username" placeholder="账号" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-input v-model="user.query.phone" placeholder="手机号" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-select v-model="user.query.isLock" placeholder="状态" clearable>
        <el-option label="锁定" value=true></el-option>
        <el-option label="正常" value=false></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-cascader v-model="user.query.deptId" :options="dept.data" :props="props.deptTree" clearable
                   :show-all-levels="false" placeholder="部门"></el-cascader>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="queryUser">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="openAddForm">添加</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="user.data"
      v-loading="tableLoading"
      row-key="uid"
      border
  >
    <el-table-column
        prop="username"
        label="账号"
        min-width="100"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="phone"
        label="手机号"
        min-width="120">
    </el-table-column>
    <el-table-column
        prop="email"
        label="邮箱"
        min-width="180"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="nickname"
        label="昵称"
        min-width="120"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="isLock"
        label="状态"
        min-width="80">
      <template #default="scope">
        <el-tag @click="editLock(scope.row)"
                :type="scope.row.isLock? 'danger' : ''"
                disable-transitions>{{ scope.row.isLock ? '锁定' : '正常' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column
        prop="deptName"
        label="部门"
        min-width="150"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="remarks"
        label="备注"
        min-width="300"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="160"
        :formatter="createTimeFormatter">
    </el-table-column>
    <el-table-column label="操作" min-width="320" fixed="right">
      <template #default="scope">
        <el-button
            @click="openRoleForm(scope.row)">角色
        </el-button>
        <el-button
            @click="openEditForm(scope.row)">编辑
        </el-button>
        <el-button
            @click="openPasswordForm(scope.row)">密码
        </el-button>
        <el-button
            type="danger"
            @click="deleteUser(scope.row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <div style="display: flex; justify-content: center;">
    <el-pagination
        background
        layout="prev, pager, next"
        :total="user.total"
        :current-page="user.query.pageNo"
        @current-change="pageUser">
    </el-pagination>
  </div>

  <el-dialog title="角色" width="30%" center v-model="show.editRole">
    <el-form :model="body.editRole" :rules="rules.editRole" ref="editRoleForm">
      <el-form-item prop="roleIds">
        <el-checkbox-group v-model="body.editRole.roleIds">
          <el-checkbox v-for="role in role.data" :key="role.id" :label="role.id">
            {{ role.name }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <div style="display: flex; justify-content: center;">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="role.total"
          :current-page="role.query.pageNo"
          @current-change="pageRole">
      </el-pagination>
    </div>
    <template #footer>
      <el-button type="primary" @click="editRole">修 改</el-button>
      <el-button @click="closeRoleForm">关 闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="添加用户" width="30%" center v-model="show.add">
    <el-form :model="body.add" :rules="rules.add" ref="addForm" label-width="80px">
      <el-form-item label="账号" prop="username">
        <el-input v-model="body.add.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="body.add.password" type="password" show-password></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="body.add.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="body.add.email"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="body.add.nickname"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="body.add.remarks" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-cascader v-model="body.add.deptId" :options="dept.data" :props="props.deptTree" clearable
                     :show-all-levels="false" placeholder="请选择"></el-cascader>
      </el-form-item>
      <el-form-item label="角色" prop="roleIds">
        <el-select v-model="body.add.roleIds" multiple placeholder="请选择" @focus="queryRole">
          <el-option
              v-for="role in role.data"
              :key="role.id"
              :label="role.name"
              :value="role.id">
          </el-option>
          <div style="display: flex; justify-content: center;">
            <el-pagination
                background
                layout="prev, pager, next"
                :total="role.total"
                :current-page="role.query.pageNo"
                @current-change="pageRole">
            </el-pagination>
          </div>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="addUser">添加</el-button>
      <el-button @click="closeAddForm">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="修改用户" width="30%" center v-model="show.edit">
    <el-form :model="body.edit" :rules="rules.edit" ref="editForm" label-width="80px">
      <el-form-item label="账号" prop="username">
        <el-input v-model="body.edit.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="body.edit.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="body.edit.email"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="body.edit.nickname"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="body.edit.remarks" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-cascader v-model="body.edit.deptId" :options="dept.data" :props="props.deptTree" clearable
                     :show-all-levels="false" placeholder="请选择"></el-cascader>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editUser">修改</el-button>
      <el-button @click="closeEditForm">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="修改密码" width="30%" center v-model="show.editPassword">
    <el-form :model="body.editPassword" :rules="rules.editPassword" ref="editPwdForm" label-width="80px">
      <el-form-item label="密码" prop="password">
        <el-input v-model="body.editPassword.password" type="password" show-password></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editPassword">修改</el-button>
      <el-button @click="closePasswordForm">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import {treeNode} from "@/plugins/util";
import {
  deptListUrl,
  roleListUrl,
  userAddUrl,
  userDeleteUrl, userEditLockUrl, userEditPasswordUrl,
  userEditUrl,
  userListUrl, userRoleEditUrl,
  userRoleListUrl
} from "@/plugins/request";

export default {
  name: "SysUser",
  data() {
    return {
      tableLoading: true,
      user: {
        data: [],
        total: 0,
        query: {
          pageNo: 1,
          username: '',
          phone: '',
          isLock: '',
          deptId: '',
        },
      },
      dept: {
        data: [],
      },
      role: {
        data: [],
        total: 0,
        query: {
          pageNo: 1,
        }
      },
      show: {
        add: false,
        edit: false,
        editRole: false,
        editPassword: false,
      },
      body: {
        add: {},
        edit: {},
        editRole: {},
        editPassword: {},
      },
      rules: {
        add: {
          username: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            {pattern: '^[a-zA-Z][a-zA-Z0-9]{4,19}$', message: '5到20位的字母或数字(字母开头)', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 6, max: 32, message: '长度在6到32个字符', trigger: 'blur'}
          ],
          phone: [
            {pattern: '^[1][3-9][0-9]{9}$', message: '手机号格式不正确', trigger: 'blur'}
          ],
          email: [
            {type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
          ],
          nickname: [
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          remarks: [
            {max: 200, message: '长度在200个字符', trigger: 'blur'}
          ],
          roleIds: [
            {required: true, message: '请选择角色', trigger: 'blur'},
          ]
        },
        edit: {
          phone: [
            {pattern: '^[1][3-9][0-9]{9}$', message: '手机号格式不正确', trigger: 'blur'}
          ],
          email: [
            {type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
          ],
          nickname: [
            {max: 20, message: '长度在20个字符', trigger: 'blur'}
          ],
          remarks: [
            {max: 200, message: '长度在 200 个字符', trigger: 'blur'}
          ]
        },
        editPassword: {
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
          ],
        },
        editRole: {
          roleIds: [
            {required: true, message: '请选择角色', trigger: 'blur'},
          ]
        },
      },
      props: {
        deptTree: {
          checkStrictly: true,
          label: 'name',
          value: 'id',
        },
      }
    }
  },
  created() {
    this.loadDept();
    this.loadUser();
  },
  methods: {
    loadUser() {
      this.tableLoading = true;
      this.$axios.get(userListUrl, {
        params: this.user.query,
      }).then(resp => {
        this.tableLoading = false;
        if (!resp.status) {
          return;
        }
        this.user.data = resp.data;
        this.user.total = resp.total;
      })
    },
    queryUser() {
      if (this.user.query.deptId) {
        this.user.query.deptId = this.user.query.deptId[this.user.query.deptId.length - 1];
      }
      this.user.query.pageNo = 1;
      this.loadUser();
    },
    pageUser(page) {
      this.user.query.pageNo = page;
      this.loadUser();
    },
    loadDept() {
      this.$axios.get(deptListUrl)
          .then(resp => {
            if (!resp.status) {
              return;
            }
            let deptData = resp.data;
            this.dept.data = treeNode(deptData, "0");
          })
    },
    queryRole() {
      this.role.query.pageNo = 1;
      this.loadRole();
    },
    pageRole(pageNo) {
      this.role.query.pageNo = pageNo;
      this.loadRole();
    },
    loadRole() {
      this.$axios.get(roleListUrl, {
        params: this.role.query,
      }).then(resp => {
        if (!resp.status) {
          return;
        }
        this.role.data = resp.data;
        this.role.total = resp.total;
      })
    },
    openAddForm() {
      this.show.add = true;
    },
    closeAddForm() {
      this.show.add = false;
    },
    addUser() {
      let deptId = this.body.add.deptId;
      if (deptId) {
        this.body.add.deptId = deptId[deptId.length - 1];
      }
      this.$refs['addForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.post(userAddUrl, this.body.add)
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
              this.loadUser();
            })
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
    editUser() {
      let deptId = this.body.edit.deptId;
      if (Array.isArray(deptId)) {
        this.body.edit.deptId = deptId[deptId.length - 1];
      }
      this.$refs['editForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(userEditUrl, this.body.edit)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 隐藏修改的form
              this.closeEditForm();
              // 重新加载table
              this.loadUser();
            })
      })
    },
    deleteUser(user) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        type: 'warning',
        center: true
      }).then(() => {
        this.$axios.delete(userDeleteUrl + user.uid)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("删除成功");
              // 重新加载table
              this.loadUser();
            })
      })
    },
    openRoleForm(user) {
      this.queryRole();
      this.$axios.get(userRoleListUrl + user.uid)
          .then(resp => {
            if (resp.status) {
              this.body.editRole.roleIds = resp.data;
            }
          })
      this.body.editRole.uid = user.uid;
      this.show.editRole = true;
    },
    closeRoleForm() {
      this.show.editRole = false;
    },
    editRole() {
      this.$refs['editRoleForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(userRoleEditUrl, this.body.editRole)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              this.show.editRole = false;
              // 清空选中的角色
              this.body.editRole.roleIds = [];
            })
      });
    },
    editLock(user) {
      let msg = "";
      if (user.isLock) {
        msg = "此操作将启用账号, 是否继续?"
      } else {
        msg = "此操作将停用账号, 是否继续?";
      }
      this.$confirm(msg, '提示', {
        type: 'warning',
        center: true
      }).then(() => {
        let body = {};
        body.uid = user.uid;
        body.isLock = !user.isLock;
        this.$axios.put(userEditLockUrl, body)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 重新加载table
              this.loadUser();
            })
      })
    },
    openPasswordForm(data) {
      this.show.editPassword = true;
      this.body.editPassword.uid = data.uid;
    },
    closePasswordForm() {
      this.show.editPassword = false;
    },
    editPassword() {
      this.$refs['editPwdForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(userEditPasswordUrl, this.body.editPassword)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              this.closePasswordForm();
            })
      });
    },
    createTimeFormatter(row, column, cellValue) {
      return this.$moment(parseInt(cellValue)).format("YYYY-MM-DD HH:mm:ss");
    },
  },
}
</script>

<style scoped>
/*form行内元素距离太远*/
.el-form--inline .el-form-item {
  margin-right: 10px;
}
</style>