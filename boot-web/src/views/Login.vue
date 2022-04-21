<template>
  <el-form :model="loginData" :rules="loginRules" size="large" ref="loginForm" class="loginForm">
    <h3>登陆</h3>
    <el-form-item prop="username">
      <el-input v-model="loginData.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="loginData.password" placeholder="密码" type="password"></el-input>
    </el-form-item>
    <el-row>
      <el-col :span="14">
        <el-form-item prop="captcha">
          <el-input v-model="loginData.captcha" placeholder="验证码"
                    @keyup.enter="signin()"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="10">
        <img :src="captchaBase64" @click="refreshCaptcha()"/>
      </el-col>
    </el-row>
    <el-form-item style="text-align: left">
      <el-checkbox v-model="loginData.rememberMe">免登陆</el-checkbox>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" style="width:100%;" @click="signin()">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {imageCaptchaUrl, loginUrl} from '@/plugins/request'

export default {
  name: "Login",
  data() {
    return {
      loginData: {
        username: '',
        password: '',
        captcha: '',
        rememberMe: false
      },
      captchaBase64: '',
      loginRules: {
        username: [{required: true, message: "用户名不能为空", trigger: 'blur'}],
        password: [{required: true, message: "密码不能为空", trigger: 'blur'}],
        captcha: [{required: true, message: "验证码不能为空", trigger: 'blur'}]
      }
    }
  },
  created() {
    this.refreshCaptcha();
  },
  methods: {
    /**
     * 刷新验证码图片
     */
    refreshCaptcha() {
      this.$axios.get(imageCaptchaUrl + Math.random())
          .then(resp => {
            if (resp.status) {
              this.captchaBase64 = 'data:image/jpeg;base64,' + resp.data.captcha;
            }
          })
    },
    /**
     * 登陆
     */
    signin() {
      // 校验
      this.$refs['loginForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        // 登陆请求
        this.$axios.post(loginUrl, this.loginData)
            .then(res => {
              if (res.status) {
                localStorage.setItem("username", this.loginData.username);
                this.$router.push("/");
              } else {
                this.refreshCaptcha();
              }
            })
      });
    }
  }
}
</script>

<style scoped>
.loginForm {
  border-radius: 10px;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin: 10% auto 0;
}
</style>