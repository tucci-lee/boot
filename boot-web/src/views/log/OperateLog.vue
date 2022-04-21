<template>
  <el-form inline>
    <el-form-item label="搜索">
      <el-input v-model="log.query.username" placeholder="账号" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-input v-model="log.query.ip" placeholder="ip" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-select v-model="log.query.status" placeholder="状态" clearable>
        <el-option label="成功" value=true></el-option>
        <el-option label="失败" value=false></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-date-picker
          v-model="log.query.beginTime"
          type="date"
          value-format="x"
          placeholder="开始日期">
      </el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-date-picker
          v-model="log.query.endTime"
          type="date"
          value-format="x"
          placeholder="结束日期">
      </el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="queryLog">查询</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="log.data"
      v-loading="tableLoading"
      row-key="id"
      border>
    <el-table-column
        prop="username"
        label="账号">
    </el-table-column>
    <el-table-column
        prop="ip"
        label="ip">
    </el-table-column>
    <el-table-column
        prop="url"
        label="请求地址"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="method"
        label="请求方法"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="params"
        label="请求参数"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="result"
        label="返回结果"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="description"
        label="描述"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="errorMessage"
        label="错误信息"
        show-overflow-tooltip>
    </el-table-column>
    <el-table-column
        prop="status"
        label="状态">
      <template #default="scope">
        <el-tag :type="scope.row.status? '':'danger'"
                disable-transitions>{{ scope.row.status ? '成功' : '失败' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column
        prop="createTime"
        label="创建时间"
        :formatter="createTimeFormatter">
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

</template>

<script>
import {operateLogUrl} from "@/plugins/request";

export default {
  name: "OperateLog",
  data() {
    return {
      tableLoading: true,
      log: {
        data: [],
        total: 0,
        query: {
          username: '',
          ip: '',
          status: '',
          beginTime: '',
          endTime: '',
          pageNo: 1,
        }
      },
    }
  },
  created() {
    this.loadLog();
  },
  methods: {
    loadLog() {
      this.tableLoading = true;
      this.$axios.get(operateLogUrl, {
        params: this.log.query
      }).then(resp => {
        this.tableLoading = false;
        if (!resp.status) {
          return;
        }
        this.log.data = resp.data;
        this.log.total = resp.total;
      })
    },
    queryLog() {
      this.log.query.pageNo = 1;
      this.loadLog();
    },
    pageLog(page) {
      this.log.query.pageNo = page;
      this.loadLog();
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