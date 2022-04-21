<template>
  <div class="left-option">
    <el-link href="/" type="primary" :underline="false"><h3>管理后台</h3></el-link>
  </div>
  <el-space size="large" class="right-option">
    <el-dropdown>
      <el-avatar style="cursor: pointer;">{{ avatar }}</el-avatar>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="openChangePasswordForm">修改密码</el-dropdown-item>
          <el-dropdown-item @click="logout()">退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-space>
  <el-dialog title="修改密码" width="30%" center v-model="show.changePassword">
    <el-form :model="body.changePassword" :rules="rules.changePassword" ref="changePasswordForm">
      <el-form-item prop="oldPassword">
        <el-input v-model="body.changePassword.oldPassword" placeholder="旧密码" type="password"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="body.changePassword.password" placeholder="新密码" type="password"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input v-model="body.changePassword.confirmPassword" placeholder="确认密码" type="password"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="closeChangePasswordForm">取 消</el-button>
      <el-button type="primary" @click="changePassword()">确 定</el-button>
    </template>
  </el-dialog>
</template>

<script>
import {changePasswordUrl, logoutUrl} from "@/plugins/request";

export default {
  name: "Header",
  data() {
    let confirmPasswordCheck = (rule, value, callback) => {
      console.log(value)
      console.log(this.body.changePassword.password)
      if (value !== this.body.changePassword.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      avatar: '',
      show: {
        changePassword: false
      },
      body: {
        changePassword: {}
      },
      rules: {
        changePassword: {
          oldPassword: [
            {required: true, message: '请输入旧密码', trigger: 'blur'},
            {min: 6, max: 32, message: '长度在6到32个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入新密码', trigger: 'blur'},
            {min: 6, max: 32, message: '长度在6到32个字符', trigger: 'blur'}
          ],
          confirmPassword: [
            {validator: confirmPasswordCheck, trigger: 'blur'}
          ],
        }
      }
    }
  },
  created() {
    this.getProfile();
  },
  methods: {
    /**
     * 获取用户信息，设置头像
     */
    getProfile() {
      this.avatar = localStorage.getItem("username");
    },
    openChangePasswordForm() {
      this.show.changePassword = true;
    },
    closeChangePasswordForm() {
      this.show.changePassword = false;
    },
    changePassword() {
      // 校验
      this.$refs['changePasswordForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(changePasswordUrl, this.body.changePassword)
            .then(resp => {
              if (!resp.status) {
                return;
              }
              this.$message.success("修改成功");
              this.$router.push("/login");
            })
      });
    },
    /**
     * 登出
     */
    logout() {
      this.$axios.post(logoutUrl)
          .then(res => {
            if (res.status) {
              localStorage.removeItem("username");
              this.$router.push("/login");
            }
          })
    }
  }
}
</script>

<style scoped>
.left-option {
  height: 100%;
  display: flex;
  align-items: center;
  float: left;
}

.right-option {
  height: 100%;
  float: right;
}
</style>