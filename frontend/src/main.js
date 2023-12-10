// main JS file for instantiating the application

import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

// BootstrapVue for UI components and icons
Vue.use(BootstrapVue, {
    // global config for toast component
    BToast: {
        solid: true,
        toaster: "b-toaster-top-center",
        autoHideDelay: 4000,
        appendToast: true
    }
});
Vue.use(BootstrapVueIcons);

Vue.config.productionTip = false;

new Vue({
    router,
    render: (h) => h(App)
}).$mount("#app");
