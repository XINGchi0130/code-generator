 
import axios from 'axios';

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const instance = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: 'http://localhost:8081/',
  // 超时
  timeout: 10000
})

// 请求拦截器
instance.interceptors.request.use(config => {
  
  return config;
}, error => {
  console.log(error);
  return Promise.reject(error);
});
 
// 响应拦截器
instance.interceptors.response.use(response => {
  const data = response.data;
  if (data && data.code !== 200) {
      alert(`Error code ${data.code}: ${data.message}`);
      return Promise.reject(new Error(data.message));
  } else {
      return data;
  }
}, error => {
  console.log(error);
  return Promise.reject(error);
});
 
export default instance;