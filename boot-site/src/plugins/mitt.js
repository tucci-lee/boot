// 消息总线，组件通信
import mitt from "mitt"

const mittObj = mitt();

/**
 * 路由跳转
 * @param route
 */
export function publishRoute(route) {
    mittObj.emit("route", route);
}

/**
 * 监听路由跳转
 * @param fn
 */
export function listenerRoute(fn) {
    mittObj.on('route', data => fn(data));
}
