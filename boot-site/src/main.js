import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "./plugins/axios"
import moment from "moment";
import {ElMessage, ElMessageBox} from "element-plus";

const app = createApp(App);
app.use(router);
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$moment = moment;
app.config.globalProperties.$message = ElMessage;
app.config.globalProperties.$confirm = ElMessageBox['confirm'];

app.mount('#app');
