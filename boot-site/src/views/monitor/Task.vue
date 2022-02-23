<template>
  <el-form inline>
    <el-form-item label="搜索">
      <el-input v-model="task.query.name" placeholder="名称" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-select v-model="task.query.status" placeholder="状态" clearable>
        <el-option label="启动" value=true></el-option>
        <el-option label="停止" value=false></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="queryTask">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="openAddForm">添加</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="task.data"
      v-loading="tableLoading"
      row-key="id"
      border>
    <el-table-column
        prop="name"
        label="任务名称"
        min-width="160"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="className"
        label="任务类名"
        min-width="160"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="cron"
        label="cron"
        min-width="160"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="status"
        label="状态">
      <template #default="scope">
        <el-tag @click="editStatus(scope.row)"
                :type="scope.row.status ? '' : 'info'"
                disable-transitions>{{ scope.row.status ? '启动' : '停止' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column
        property="createTime"
        label="创建时间"
        min-width="160"
        :formatter="createTimeFormatter">
    </el-table-column>
    <el-table-column
        prop="remarks"
        label="备注"
        min-width="300"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column label="操作" min-width="360" fixed="right">
      <template #default="scope">
        <el-button
            @click="start(scope.row)">立即执行
        </el-button>
        <el-button
            @click="openLog(scope.row)">运行日志
        </el-button>
        <el-button
            @click="openEditForm(scope.row)">编辑
        </el-button>
        <el-button
            type="danger"
            @click="deleteTask(scope.row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <div style="display: flex; justify-content: center;">
    <el-pagination
        background
        layout="prev, pager, next"
        :total="task.total"
        :current-page="task.query.pageNo"
        @current-change="pageTask">
    </el-pagination>
  </div>

  <el-dialog title="添加任务" width="30%" center v-model="show.add">
    <el-form :model="body.add" :rules="rules" ref="addForm" label-width="80px">
      <el-form-item label="任务名称" prop="name">
        <el-input v-model="body.add.name"></el-input>
      </el-form-item>
      <el-form-item label="任务类名" prop="className">
        <el-input v-model="body.add.className"></el-input>
      </el-form-item>
      <el-form-item label="cron" prop="cron">
        <el-input v-model="body.add.cron"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="body.add.remarks" type="textarea"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="addTask()">添加</el-button>
      <el-button @click="closeAddForm">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="修改任务" width="30%" center v-model="show.edit">
    <el-form :model="body.edit" :rules="rules" ref="editForm" label-width="80px">
      <el-form-item label="任务名称" prop="name">
        <el-input v-model="body.edit.name"></el-input>
      </el-form-item>
      <el-form-item label="任务类名" prop="className">
        <el-input v-model="body.edit.className"></el-input>
      </el-form-item>
      <el-form-item label="cron" prop="cron">
        <el-input v-model="body.edit.cron"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="body.edit.remarks" type="textarea"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="editTask()">修改</el-button>
      <el-button @click="closeEditForm">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="运行日志" width="40%" center v-model="show.log">
    <el-table :data="log.data"
              v-loading="logTableLoading"
              row-key="id"
              border>
      <el-table-column property="status" label="运行状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status ? '' : 'danger'"
                  disable-transitions>{{ scope.row.status ? '成功' : scope.row.status === false ? '失败' : '运行中' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          property="runTime"
          label="执行时间/ms"
          width="120">
      </el-table-column>
      <el-table-column
          property="startTime"
          label="运行时间"
          width="180"
          :formatter="createTimeFormatter">
      </el-table-column>
      <el-table-column
          property="message"
          label="运行信息"
          show-overflow-tooltip>
      </el-table-column>
    </el-table>
    <div style="display: flex; justify-content: center;">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="log.total"
          :current-page="log.query.pageNo"
          @current-change="pageLog">
      </el-pagination>
    </div>
  </el-dialog>
</template>

<script>

export default {
  name: "Task",
  data() {
    return {
      tableLoading: true, // table加载
      logTableLoading: true,
      task: {
        data: [],
        total: 0,
        query: {
          name: '',
          status: '',
          pageNo: 1,
        }
      },
      log: { //运行日志
        data: [],
        total: 0,
        query: {
          taskId: '',
          pageNo: 1,
        }
      },
      show: {     // 添加修改的form显示
        add: false,
        edit: false,
        log: false,
      },
      body: {         // 添加修改的数据
        add: {},
        edit: {},
      },
      rules: {         // 添加修改数据校验
        name: [
          {required: true, message: '请输入任务名称', trigger: 'blur'},
          {max: 20, message: '长度在20个字符', trigger: 'blur'}
        ],
        className: [
          {required: true, message: '请输入任务类名', trigger: 'blur'},
          {max: 200, message: '长度在200个字符', trigger: 'blur'}
        ],
        cron: [
          {required: true, message: '请输入cron', trigger: 'blur'},
          {max: 100, message: '长度在100个字符', trigger: 'blur'}
        ],
        remarks: [
          {max: 200, message: '长度在 200 个字符'}
        ],
      },
    }
  },
  created() {
    this.loadTask();
  },
  methods: {
    loadTask() {
      this.tableLoading = true;
      this.$axios.get("/task", {
        params: this.task.query,
      }).then(resp => {
        this.tableLoading = false;
        if (!resp.status) {
          return;
        }
        this.task.data = resp.data;
        this.task.total = resp.total;
      })
    },
    pageTask(page) {
      this.task.query.pageNo = page;
      this.loadTask();
    },
    queryTask() {
      this.task.query.pageNo = 1;
      this.loadTask();
    },
    openAddForm() {
      this.show.add = true;
    },
    closeAddForm() {
      this.show.add = false;
    },
    addTask() {
      this.$refs['addForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.post("/task", this.body.add)
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
              this.loadTask();
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
    editTask() {
      this.$refs['editForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put("task", this.body.edit)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 隐藏添加资源的form
              this.closeEditForm();
              // 重新加载table
              this.loadTask();
            })
      })
    },
    editStatus(task) {
      let msg = "";
      if (!task.status) {
        msg = "此操作将开启任务, 是否继续?"
      } else {
        msg = "此操作将停止任务, 是否继续?";
      }
      this.$confirm(msg, '提示', {
        type: 'warning',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(() => {
        let body = {};
        body.id = task.id;
        body.status = !task.status;
        this.$axios.put("/task/status", body)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              // 重新加载table
              this.loadTask();
            })
      })
    },
    deleteTask(data) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        type: 'warning',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(() => {
        this.$axios.delete("/task/" + data.id)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("删除成功");
              this.loadTask();
            })
      })
    },
    start(row) {
      this.$axios.post("/task/start/" + row.id)
          .then(resp => {
            if (!resp.status) {
              return;
            }
            this.$message.success("操作成功");
          });
    },
    openLog(data) {
      this.log.query.taskId = data.id;
      this.log.query.pageNo = 1;
      this.loadLog();
      this.show.log = true;
    },
    loadLog() {
      this.logTableLoading = true;
      this.$axios.get("/task/log", {
        params: this.log.query,
      }).then(resp => {
        this.logTableLoading = false;
        if (!resp.status) {
          return;
        }
        this.log.data = resp.data;
        this.log.total = resp.total;
      });
    },
    pageLog(page) {
      this.log.query.pageNo = page;
      this.loadLog();
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