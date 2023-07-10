/**
 Uncaught runtime errors:
 ERROR
 ResizeObserver loop limit exceeded
 at handleError (webpack-internal:///./node_modules/webpack-dev-server/client/overlay.js:299:58)
 at eval (webpack-internal:///./node_modules/webpack-dev-server/client/overlay.js:318:7)
 */
const debounce = (fn, delay) => {
  let timer = null;
  return function () {
    let context = this;
    let args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    super(callback);
  }
}
