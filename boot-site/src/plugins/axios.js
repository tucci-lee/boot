import axios from "axios"
import router from "@/router";
import {ElMessage} from "element-plus";
import 'element-plus/dist/index.css';

axios.defaults.withCredentials = true;
axios.defaults.baseURL = "http://localhost:8000"; // 开发使用
// axios.defaults.baseURL = "http://boot.2cci.cn/api/"; // 线上

/**
 * axios拦截器，错误直接提示
 */
axios.interceptors.response.use(
    resp => {
        if (resp.data.code === 10101) {
            router.push("/login");
            return;
        }
        if (!resp.data.status) {
            ElMessage.error(resp.data.message);
        }
        return resp.data;
    },
    err => {
        let response = err.response;
        if (response) {
            ElMessage.error(response.data.message)
        } else {
            ElMessage.error("网络异常")
        }
        return Promise.reject(err);
    });

export default axios;